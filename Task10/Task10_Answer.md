# Task 10 — Packages and encapsulation (3 marks)

The integrated program is in package parking. Compile from the project root with javac -d out src/parking/CampusSmartParking.java and run with java -cp out parking.CampusSmartParking.

Private fields prevent arbitrary direct edits; public methods validate changes. The slot's occupy method refuses an already occupied slot. The vehicle constructor rejects blank plates and negative charging energy.

Access modifiers: private for internal state, package-private for small demo collaborators, public for the entry-point class and methods intended for external use. A larger submission can split each class into its own file.

## Pseudocode / trace

```text
if plate is blank: reject
if kWh < 0: reject
if slot is occupied: reject
otherwise occupy slot
```
