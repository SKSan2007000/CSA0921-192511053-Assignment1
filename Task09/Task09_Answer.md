# Task 09 — Abstraction and interface (4 marks)

An abstract Vehicle class can store common state and declare abstract methods such as hourlyRate. It cannot be instantiated directly.

Chargeable declares charging behaviour. Electric vehicles implement it; ordinary vehicles do not need dummy charging methods.

If Vehicle were an interface, shared instance fields and constructor initialisation would need another class or composition. Interfaces are useful when unrelated classes share a capability.

## Pseudocode / trace

```text
abstract class Vehicle { registration; abstract double hourlyRate(LocalTime time); }
interface Chargeable { double chargingFee(); }
class ElectricCar extends Car implements Chargeable { ... }
```
