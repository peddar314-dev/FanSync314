package com.fansync.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView status;
    private TextView countdown;
    private EditText cheerText;
    private Button triggerButton;
    private Button resetButton;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.status);
        countdown = findViewById(R.id.countdown);
        cheerText = findViewById(R.id.cheerText);
        triggerButton = findViewById(R.id.triggerButton);
        resetButton = findViewById(R.id.resetButton);

        triggerButton.setOnClickListener(v -> startCheer());
        resetButton.setOnClickListener(v -> resetDemo());
    }

    private void startCheer() {
        if (timer != null) {
            timer.cancel();
        }

        triggerButton.setEnabled(false);
        cheerText.setEnabled(false);
        status.setText("GET READY");
        status.setTextColor(Color.WHITE);

        timer = new CountDownTimer(5000, 1000) {
            int shown = 5;

            @Override
            public void onTick(long millisUntilFinished) {
                countdown.setText(String.valueOf(shown));
                shown--;
            }

            @Override
            public void onFinish() {
                String cheer = cheerText.getText().toString().trim();
                if (cheer.isEmpty()) {
                    cheer = "GO TEAM!";
                }

                status.setText("CHEER NOW!");
                countdown.setText(cheer);
                countdown.setTextSize(38);

                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null && vibrator.hasVibrator()) {
                    vibrator.vibrate(VibrationEffect.createOneShot(
                        450,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    ));
                }

                triggerButton.setEnabled(true);
                cheerText.setEnabled(true);
            }
        }.start();
    }

    private void resetDemo() {
        if (timer != null) {
            timer.cancel();
        }
        status.setText("Ready for synchronized cheering");
        countdown.setText("JOINED");
        countdown.setTextSize(64);
        triggerButton.setEnabled(true);
        cheerText.setEnabled(true);
    }
}
