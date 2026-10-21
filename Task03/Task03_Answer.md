# Task 03 — Control logic and pseudocode (4 marks)

The entry operation validates the vehicle, finds a slot, creates a ticket and only then stores occupancy. This ordering avoids issuing tickets when the facility is full.

The exit operation finds an active ticket, rejects unknown plates or exit-before-entry, calculates charges, releases the slot and removes the active entry.

Loops stop after finding a suitable slot or after all floors are searched. If no slot matches, the method returns null and reports no suitable space.

The supplied ParkingFacility.java in this folder is a small standalone demonstration of allocation. The integrated program is in src/parking/CampusSmartParking.java.

## Pseudocode / trace

```text
ALLOCATE(vehicle)
 for each floor
   for each slot on floor
     if slot reserved for vehicle and user is student/faculty
       return slot if free and suitable, otherwise fail
 for each floor
   for each slot on floor
     skip occupied and reserved slots
     if EV charging requested, skip non-charging slots
     return slot
 return NO_SLOT
```
