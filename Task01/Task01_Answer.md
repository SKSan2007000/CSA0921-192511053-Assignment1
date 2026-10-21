# Task 01 — Problem analysis and object identification (4 marks)

Inputs: registration number, vehicle category, user category, monthly-pass validity, EV charging request and kWh, entry/exit time, floor capacities and visitor-pass choice.

Outputs: unique ticket, entry time, allocated floor/slot, lane guidance, active-ticket lookup, parking fee, separate EV charging fee, released slot and capacity summary.

Objects: ParkingVehicle owns vehicle/user/pass data; ParkingSlot owns location, charging capability, reservation and occupancy; ParkingTicket records identity and timestamps; ParkingFacility allocates slots and manages active tickets; CampusSmartParking handles console input/output.

Rules: a slot holds at most one vehicle; ticket IDs are unique within a run; duplicate active plates are rejected; reserved slots are not given to other users; EV charging uses a charging-capable slot; valid student/faculty monthly passes remove parking fee, but not charging fee; visitor fees apply unless a one-day pass is selected.

Assumptions: fees are illustrative and require faculty approval; started hours are rounded up; the console simulates camera recognition; QR payload is a demonstration string, not an actual QR image; sample reserved plate SAN-0001 is a placeholder for Santhosh.

Objects vs parallel arrays: objects keep plate, type, pass and state together and are easier to extend. Parallel arrays need matching indices and can become inconsistent when fields are added. Linear search is O(n); a HashMap active-ticket lookup is average O(1), at the cost of O(n) extra storage.

## Pseudocode / trace

```text
START
 read plate, vehicle type, user type, pass and charging details
 reject blank plate, invalid type, negative kWh or duplicate active plate
 if registered plate has a reserved slot: use it only if free and suitable
 otherwise find first free, non-reserved slot suitable for the vehicle
 if no suitable slot exists: report failure and issue no ticket
 create unique ticket; record entry time; occupy slot; store active ticket
END
```
