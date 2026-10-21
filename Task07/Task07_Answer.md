# Task 07 — Method overriding, equality and hash code (4 marks)

Overriding gives a subclass its own implementation of an inherited method, with the same signature and a compatible return type. @Override lets the compiler catch signature mistakes.

Fee rules are selected through vehicle behaviour rather than editing one large if/else chain. A new category adds a subclass or pricing rule.

final can prevent overriding a method whose behaviour must remain fixed, but it should not be applied to the fee method if categories must customise it.

If two Vehicle objects are considered equal by registration number, equals() and hashCode() must use the same normalised registration value. Otherwise HashMap/HashSet behaviour can be incorrect.

## Pseudocode / trace

```text
Vehicle v = new Car(...)
call v.hourlyRate(...)
runtime selects Car's overridden method
```
