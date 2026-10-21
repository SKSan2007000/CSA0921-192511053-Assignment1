# Task 08 — Polymorphism (4 marks)

Overloading uses the same method name with different parameter lists and is resolved at compile time. Overriding is selected at runtime according to the actual object.

FeeDemo stores subclass objects through a Vehicle reference and calls the overridden rate method. Dynamic dispatch avoids repeated instanceof tests.

Repeated instanceof branches require edits whenever a new type is added and can violate the open/closed principle. A common overridable method makes the change local.

## Pseudocode / trace

```text
Vehicle[] vehicles = {new TwoWheeler(...), new Car(...), new ElectricCar(...)}
for each v: print(v.hourlyRate(entryTime)) // runtime dispatch
```
