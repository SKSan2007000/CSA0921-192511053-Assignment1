# Task 11 — Implementation, testing and defence (5 marks)

The integrated system is a runnable console model. It supports entry, exit, ticket lookup, status, monthly passes, EV charging, reserved slots, visitor one-day passes and the design-challenge categories.

Average active-ticket lookup is O(1) using HashMap; finding a free slot is O(S), where S is total slots. Memory is O(S + T), where T is tickets retained in history.

Validation cases should include: ordinary visitor, monthly-pass student, EV charging, duplicate plate, unknown exit, zero-minute fee, 61-minute fee, last available slot, full facility, reserved-slot protection, invalid plate/kWh and delivery van rate boundaries.

Evidence folder includes compile/test logs. Run the program locally and capture genuine terminal screenshots before submission; generated text logs are not a substitute for screenshots requested by your faculty.

## Pseudocode / trace

```text
TESTS
1. 0 minutes -> 0 billed hours
2. 1 minute -> 1 billed hour
3. 60 minutes -> 1 billed hour
4. 61 minutes -> 2 billed hours
5. van at 08:59 -> car rate; 09:00 -> 2x car rate; 17:00 -> car rate
6. duplicate active plate -> rejected
7. unknown plate on exit -> rejected
8. fixed SAN-0001 -> Floor 2 S036 when free
```
