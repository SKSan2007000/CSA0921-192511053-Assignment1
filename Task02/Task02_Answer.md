# Task 02 — Data representation and Java types (3 marks)

String: registration number, ticket ID and slot code. String normalisation to uppercase avoids treating abc123 and ABC123 as different plates.

int: floor numbers, array indexes and slot counts. boolean: pass validity, charging request and occupancy state. LocalDateTime/LocalTime: entry, exit and pricing window. double is used for a student demo; BigDecimal or integer paise is safer for real money.

ParkingSlot[][] is a jagged array: each floor can have a different capacity. The demonstration uses {20,40,17,23,31}; the reserved Floor 2 S036 exists. Active tickets use HashMap<String,ParkingTicket> keyed by plate.

Started-hour billing: minutes 0 -> 0 hours; 1 -> 1; 60 -> 1; 61 -> 2. Using integer formula (minutes+59)/60 avoids casting a fractional hour down. Negative duration is rejected.

Scanner reads console values as strings then parses/enums them. Invalid enum names and numeric input are caught by the menu exception handler. Production input should validate before accepting the entry.

## Pseudocode / trace

```text
minutes = duration between entry and exit
if minutes < 0: reject
if minutes == 0: billedHours = 0
else: billedHours = (minutes + 59) / 60
parkingFee = billedHours * hourlyRate
chargingFee = chargingRequested ? kWh * chargingRate : 0
```
