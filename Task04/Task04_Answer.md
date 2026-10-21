# Task 04 — Classes, objects and constructors (4 marks)

ParkingVehicle, ParkingSlot, ParkingTicket and ParkingFacility model the real entities. Constructor parameters initialise required state at object creation.

A no-argument constructor can provide defaults; a parameterised constructor receives values; overloaded constructors share a name but differ in parameter list. Constructor chaining uses this(...) to reuse initialisation. The keyword this refers to the current object.

Objects are created using new. For example, new ParkingVehicle(plate,type,user,monthly,charge,kwh). Private fields and getters keep the state controlled.

Constructor trace for an electric car: Vehicle parent constructor runs first, then ElectricCar constructor body. The example hierarchy in Task04 uses simple console output to show the order.

## Pseudocode / trace

```text
create VehicleRecord using no-argument constructor
create VehicleRecord using parameterised constructor
print both records
create ElectricCar -> parent constructor first -> child constructor second
```
