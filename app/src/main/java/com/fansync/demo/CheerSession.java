package com.fansync.demo;

/** Pure timing state. Deadlines use elapsed realtime, never wall-clock time. */
public final class CheerSession {
    public static final long COUNTDOWN_MS = 5000;
    private String eventId;
    private String message = "GO TEAM!";
    private long deadline;
    private boolean cueDelivered;

    public void start(String id, String text, long deadlineElapsedMs) {
        if (id == null || id.isEmpty()) throw new IllegalArgumentException("Event ID required");
        if (id.equals(eventId)) return;
        eventId = id;
        message = text == null || text.trim().isEmpty() ? "GO TEAM!" : text.trim();
        deadline = deadlineElapsedMs;
        cueDelivered = false;
    }
    public boolean isActive() { return eventId != null; }
    public boolean isCounting(long now) { return isActive() && now < deadline; }
    public long secondsRemaining(long now) { return Math.max(0, (deadline - now + 999) / 1000); }
    public String getMessage() { return message; }
    public String getEventId() { return eventId; }
    public long getDeadline() { return deadline; }
    public boolean isCueDelivered() { return cueDelivered; }

    /** Stale events may show their result but must not vibrate late on resume. */
    public boolean consumeCue(long now) {
        if (!isActive() || now < deadline || cueDelivered) return false;
        cueDelivered = true;
        return now - deadline <= 1000;
    }
    public void restore(String id, String text, long deadlineElapsedMs, boolean delivered) {
        start(id, text, deadlineElapsedMs);
        cueDelivered = delivered;
    }
    public void reset() { eventId = null; cueDelivered = false; }
}
