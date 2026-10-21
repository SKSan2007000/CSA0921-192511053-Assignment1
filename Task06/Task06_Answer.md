# Task 06 — Inheritance hierarchy and UML (4 marks)

Use an abstract/general Vehicle base and specialised TwoWheeler, Car, ElectricCar, EBike, DeliveryVan and ServiceVehicle classes. The supplied examples demonstrate hierarchical inheritance.

Inheritance reuses common registration and identity behaviour. ElectricCar can extend Car and implement Chargeable. Chargeable is an interface because charging is a capability, not a family relationship.

Constructor order is base-to-derived. When ElectricCar is created, Vehicle/Car initialisation occurs before ElectricCar's constructor body.

UML images are included under diagrams/. Solid generalisation arrows represent inheritance; dashed realisation arrows represent interface implementation.

## Pseudocode / trace

```text
Vehicle
  |-- TwoWheeler
  |-- Car
        |-- ElectricCar implements Chargeable
  |-- EBike
  |-- DeliveryVan
  |-- ServiceVehicle
```
