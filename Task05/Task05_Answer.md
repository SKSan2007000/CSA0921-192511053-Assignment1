# Task 05 — Static members and object lifecycle (3 marks)

ParkingTicket.nextNumber is static, so all tickets share one counter rather than each ticket starting from the same number. It creates a unique ID during a single program run.

Static state belongs to the class; entry time and slot belong to each ticket/vehicle. A static counter resets when the program restarts unless persisted in a database.

Java passes arguments by value. When an object reference is passed, the copied reference points to the same object, so methods can change its fields; reassigning the local reference does not change the caller's reference.

An object becomes eligible for garbage collection when it is no longer reachable. System.gc() is only a request and should not be used to manage parking records.

## Pseudocode / trace

```text
ticket A -> static counter becomes 1001
ticket B -> same counter becomes 1002
remove all references to an object -> eligible for garbage collection when unreachable
```
