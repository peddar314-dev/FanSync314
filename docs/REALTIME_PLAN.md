# Realtime synchronization next phase

## Existing foundation

CheerSession accepts an event ID, immutable cheer text, and an absolute deadline in the device's elapsed-realtime clock. Rendering derives remaining time from that deadline; callback delays do not accumulate. Repeated delivery of the current event ID does not reset timing or repeat vibration. Activity recreation preserves the current event and cue state. Returning late suppresses stale vibration. The local trigger supplies a UUID and now + 5000 ms.

## Network adapter contract

Introduce a transport independently of CheerSession. The server is authoritative for rooms, operator permissions, sequence numbers and event times. A proposed versioned payload is:

```json
{"schemaVersion":1,"sessionId":"game-room","eventId":"uuid","sequence":42,"cheerText":"GO TEAM!","cheerAtServerMs":1800000000000,"expiresAtServerMs":1800000001000}
```

The operator requests a cheer; the authenticated server validates room membership and operator role, caps text length, assigns a future deadline, then publishes the same event to every room member. Client UI must never determine operator authority. Fans cannot publish events. Use encrypted connections and deny writes by default.

Estimate server time with repeated request/response samples using the local monotonic clock and round-trip measurements. Convert the server deadline once into elapsed-realtime time: local monotonic now + (server deadline - estimated server now). Recheck offset and uncertainty on reconnect. Do not start a fresh five-second timer when a network packet arrives. A backend choice is deliberately deferred until deployment/account requirements are available.

Before calling CheerSession.start, validate version, room, text size, sequence and expiry. Persist the highest accepted sequence per session; discard old/duplicate events even after newer events or app restart. The current model deduplicates only the active ID and is not a network replay filter. Define ordered reset/cancel events against a specific event ID, and prevent late packets from reviving canceled cheers.

## Next-phase acceptance tests

- Two or more physical phones on different networks receive one operator event and cheer at the shared deadline.
- Measure p50/p95 cue skew and clock uncertainty; initial target p95 under 150 ms under agreed foreground/network conditions, measured rather than promised.
- Inject duplicates, reordering, delayed delivery, disconnection, cancellation and clock changes.
- Expired events never vibrate. Reconnect retrieves current room state without replaying old cues.
- Unauthorized fan writes and cross-room events are rejected by server tests.
- Show connectivity and clock readiness before claiming the phone is synchronized.

Background delivery cannot rely on this Activity's foreground callbacks. Decide explicitly whether the product supports only foreground participation or needs a separately designed background notification/service experience before launch.
