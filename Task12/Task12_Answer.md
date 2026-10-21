# Task 12 — Design challenge and redesign (8 marks)

Decision: modify and extend, not rewrite. The core ticket, vehicle and slot responsibilities remain useful; new categories use subclass/enum additions and the fee policy is isolated in hourlyRate(LocalTime).

E-bike is added as a two-wheeler-like category with its own rate. DeliveryVan costs twice the car rate from 09:00 inclusive to 17:00 exclusive, and the ordinary rate outside that window. Boundary tests at 08:59, 09:00, 16:59 and 17:00 are important.

One-day visitor pass is represented as an explicit visitor option. The demonstration treats it as zero parking fee for the day; confirm this interpretation with the faculty because the task wording may specify a different pass rule. EV charging remains a separate fee.

Five floors have unequal capacities {20,40,17,23,31}. The layout is configuration-based; adding floors does not require hard-coded floor logic. Floor 2 has at least 36 slots so the example reserved slot exists.

Additional campus proposal: Lane 1 student/faculty two-wheelers; Lane 2 student/faculty cars/EVs; Lane 3 visitor cars, lorries, delivery and maintenance/service vehicles with attendant support; Lane 4 outside visitor two-wheelers. A speed table slows vehicles for number-plate capture, without a physical gate for pre-registered students/staff.

QR payload carries ticket ID and floor/slot. The separate web-app demo shows capacity, entry form, allocation, a visual ticket and location. Camera recognition, real QR encoding, notifications, authentication, database, payment and barrier control require production integrations.

Risks and improvements: duplicate plate detection, manual fallback when camera fails, protected personal data, reserved slot policy, separate charging meter, admin audit trail, and clear no-space handling.

## Pseudocode / trace

```text
EXTEND WITHOUT REWRITING CORE
add EBike category + its rate
add DeliveryVan category + time-dependent rate
enable visitor one-day pass policy
configure five unequal floor capacities
run boundary, fee, allocation, duplicate and full-capacity tests
retain Vehicle/Ticket/Slot/Facility responsibilities
```
