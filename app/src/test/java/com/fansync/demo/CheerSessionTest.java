package com.fansync.demo;

import org.junit.Test;
import static org.junit.Assert.*;

public class CheerSessionTest {
    @Test public void countdownUsesDeadlineDespiteDelayedTicks() {
        CheerSession s = new CheerSession();
        s.start("1", " Go Away Team! ", 6000);
        assertEquals(5, s.secondsRemaining(1000));
        assertEquals(4, s.secondsRemaining(2250));
        assertEquals(1, s.secondsRemaining(5999));
        assertFalse(s.isCounting(6000));
        assertEquals("Go Away Team!", s.getMessage());
        assertTrue(s.consumeCue(6000));
        assertFalse(s.consumeCue(6001));
    }
    @Test public void duplicateEventDoesNotRestartOrRevibrate() {
        CheerSession s = new CheerSession();
        s.start("1", "", 5000);
        assertEquals("GO TEAM!", s.getMessage());
        assertTrue(s.consumeCue(5000));
        s.start("1", "changed", 10000);
        assertEquals(5000, s.getDeadline());
        assertFalse(s.consumeCue(5001));
    }
    @Test public void resetCancelsPendingCueAndAllowsReplay() {
        CheerSession s = new CheerSession();
        s.start("1", null, 5000);
        s.reset();
        assertFalse(s.isActive());
        assertFalse(s.consumeCue(5000));
        s.start("2", "Again", 10000);
        assertTrue(s.isCounting(5000));
        assertTrue(s.consumeCue(10000));
    }
    @Test public void restorePreservesDeadlineAndDeliveredCue() {
        CheerSession s = new CheerSession();
        s.restore("1", "Team", 5000, true);
        assertFalse(s.consumeCue(5000));
        s.restore("2", "Team", 9000, false);
        assertEquals(2, s.secondsRemaining(7000));
        assertTrue(s.consumeCue(9000));
    }
    @Test public void lateResumeDoesNotVibrate() {
        CheerSession s = new CheerSession();
        s.start("1", "Team", 5000);
        assertFalse(s.consumeCue(7000));
        assertTrue(s.isCueDelivered());
    }
}
