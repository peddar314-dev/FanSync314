package com.fansync.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.UUID;

public class MainActivity extends Activity {
    private TextView status;
    private TextView countdown;
    private EditText cheerText;
    private Button triggerButton;
    private final CheerSession session = new CheerSession();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable tick = this::render;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.view.View root = findViewById(R.id.root);
        // Keep controls clear of system bars on Android 15's edge-to-edge layout.
        root.setOnApplyWindowInsetsListener((view, insets) -> {
            view.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                    insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            return insets;
        });
        root.requestApplyInsets();
        status = findViewById(R.id.status);
        countdown = findViewById(R.id.countdown);
        cheerText = findViewById(R.id.cheerText);
        triggerButton = findViewById(R.id.triggerButton);
        triggerButton.setOnClickListener(v -> {
            session.start(UUID.randomUUID().toString(), cheerText.getText().toString(),
                    SystemClock.elapsedRealtime() + CheerSession.COUNTDOWN_MS);
            render();
        });
        findViewById(R.id.resetButton).setOnClickListener(v -> {
            session.reset();
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) vibrator.cancel();
            render();
        });
        if (savedInstanceState != null && savedInstanceState.containsKey("eventId")) {
            session.restore(savedInstanceState.getString("eventId"),
                    savedInstanceState.getString("message"), savedInstanceState.getLong("deadline"),
                    savedInstanceState.getBoolean("cueDelivered"));
        }
    }
    @Override protected void onResume() { super.onResume(); render(); }
    @Override protected void onPause() {
        handler.removeCallbacks(tick);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onPause();
    }
    @Override protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        if (session.isActive()) {
            out.putString("eventId", session.getEventId());
            out.putString("message", session.getMessage());
            out.putLong("deadline", session.getDeadline());
            out.putBoolean("cueDelivered", session.isCueDelivered());
        }
    }
    private void render() {
        handler.removeCallbacks(tick);
        long now = SystemClock.elapsedRealtime();
        boolean counting = session.isCounting(now);
        triggerButton.setEnabled(!counting);
        cheerText.setEnabled(!counting);
        countdown.setAutoSizeTextTypeUniformWithConfiguration(18,
                counting || !session.isActive() ? 64 : 38, 1, android.util.TypedValue.COMPLEX_UNIT_SP);
        if (!session.isActive()) {
            status.setText(R.string.ready);
            countdown.setText(R.string.joined);
        } else if (counting) {
            status.setText(R.string.get_ready);
            countdown.setText(String.valueOf(session.secondsRemaining(now)));
        } else {
            status.setText(R.string.cheer_now);
            countdown.setText(session.getMessage());
            if (session.consumeCue(now)) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null && vibrator.hasVibrator()) {
                    vibrator.vibrate(VibrationEffect.createOneShot(450, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        }
        if (counting) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            handler.postDelayed(tick, Math.min(50, session.getDeadline() - now));
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}
