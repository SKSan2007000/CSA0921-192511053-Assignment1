# Campus Smart Parking System — Java Assignment

**Student Name:** B. Santhosh Kumar  
**Registration Number:** 192511053  
**Project Title:** Campus Smart Parking System  
**Language Used:** Java  
**Assignment:** Object-Oriented Programming — 12 Tasks

## About the Project

This assignment is based on a Campus Smart Parking System. The main idea is to manage parking slots, register vehicles, assign and release slots, and calculate parking fees using Java.

I have completed 12 tasks covering problem analysis, data representation, constructors, static members, inheritance, method overriding, polymorphism, abstraction, encapsulation, validation, and the final design challenge. The project also includes an integrated console program that brings the main parking operations together.

The Java source code and supporting evidence are included in this submission. The separate browser-based prototype is not included.

## Tasks Completed

| Task | Program | Concept Covered |
|---|---|---|
| 1 | `ProblemAnalysis.java` | Problem analysis and identifying inputs, processes, and outputs |
| 2 | `DataRepresentation.java` | Representing parking data using Java |
| 3 | `ParkingFacility.java` | Parking slot allocation, release, and capacity |
| 4 | `ConstructorDemo.java` | Constructors and object initialization |
| 5 | `StaticTicketDemo.java` | Static members and ticket management |
| 6 | `InheritanceDemo.java` | Inheritance and vehicle types |
| 7 | `OverrideDemo.java` | Method overriding, `equals()`, and `hashCode()` |
| 8 | `FeeDemo.java` | Polymorphism and parking fee calculation |
| 9 | `AbstractionDemo.java` | Abstraction |
| 10 | `EncapsulationDemo.java` | Encapsulation and controlled access to data |
| 11 | `ValidationDemo.java` | Input and data validation |
| 12 | `DesignChallengeDemo.java` | Applying OOP concepts to the parking system |

## Software Requirements

- Java Development Kit (JDK) 17 or newer is recommended.
- A Java editor such as VS Code or Eclipse can be used.
- The programs can be compiled and run using the terminal.

The commands below should be run from the `Campus_Smart_Parking_Submission` project folder.

## How to Run an Individual Task

Each task has its own Java source file. For example, to run Task 2, open the terminal in the project folder and enter:

```bash
javac -d out/Task02 Task02/DataRepresentation.java
java -cp out/Task02 DataRepresentation
```

Use the matching source file and class name for the other tasks. The complete list is provided in the task table above.

## How to Run the Integrated Program

The integrated program is available in the `src/parking` folder. Compile the files first:

```bash
javac -d out src/parking/CampusSmartParking.java src/parking/ValidationChecks.java
```

Run the validation checks:

```bash
java -cp out parking.ValidationChecks
```

To start the parking application, run:

```bash
java -cp out parking.CampusSmartParking
```

The console menu provides these options:

- `1` — Vehicle entry
- `2` — Vehicle exit
- `3` — Find an active parking ticket
- `4` — View parking status
- `0` — Exit the program

Follow the prompts displayed in the terminal while using the application. In VS Code, make sure the program runs in the integrated terminal when keyboard input is required.

## Parking Rules Used

The following rates and rules are used in the assignment:

- Bike: ₹10 per hour
- E-bike: ₹8 per hour
- Car and electric car: ₹30 per hour
- Service vehicle: ₹20 per hour
- Delivery van: ₹60 per hour from 9:00 AM to before 5:00 PM, and ₹30 per hour outside that period
- EV charging: ₹12 per kWh, charged separately from parking
- A valid student or faculty monthly pass waives the parking fee but does not waive the EV charging fee.
- The demonstration assumes that a one-day visitor pass waives the parking fee. This interpretation should be confirmed with the faculty.
- Started parking hours are rounded up. If the duration is zero minutes, the billed duration is zero hours.

## Parking Layout and Demonstration Details

The example system uses five floors with capacities of 20, 40, 17, 23, and 31 slots, giving a total of 131 parking slots.

For the reserved-slot demonstration, Floor 2, Slot `S036` is reserved for the example registration plate `SAN-0001`.

## Evidence and Diagrams

The separate evidence-and-diagrams package contains the expected text output for the tasks and the supporting UML diagrams. These materials help explain how the programs work and how the classes are related.

The text output files are provided as reference evidence; they are not screenshots of an actual terminal. If screenshots are required for submission, run each program and capture the output from your own computer.

## Limitations

This is an educational Java project. It demonstrates parking operations and OOP concepts, but it is not connected to a live camera, automatic number-plate recognition, a shared database, a real payment gateway, a charging meter, or a physical parking gate. Authentication and production notification services are also not implemented.

