import java.util.ArrayList;
import java.util.List;

public class ProblemAnalysis {
    static class Requirement {
        final String input, processing, output;
        Requirement(String input, String processing, String output) {
            this.input = input; this.processing = processing; this.output = output;
        }
    }
    public static void main(String[] args) {
        List<Requirement> requirements = new ArrayList<>();
        requirements.add(new Requirement("Registration plate", "Normalize and check duplicates", "Valid vehicle identity"));
        requirements.add(new Requirement("Vehicle/user category", "Select eligible slot and rate", "Assigned bay and hourly rate"));
        requirements.add(new Requirement("Entry/exit time", "Calculate elapsed and billed hours", "Parking fee"));
        requirements.add(new Requirement("EV kWh", "Multiply by charging tariff", "Separate charging fee"));
        System.out.println("CAMPUS SMART PARKING — PROBLEM ANALYSIS");
        System.out.printf("%-24s | %-38s | %s%n", "INPUT", "PROCESS", "OUTPUT");
        System.out.println("-".repeat(105));
        for (Requirement r : requirements)
            System.out.printf("%-24s | %-38s | %s%n", r.input, r.processing, r.output);
        System.out.println("\nCore objects: Vehicle, ParkingSlot, ParkingTicket, ParkingFacility.");
        System.out.println("Constraints: one vehicle per slot; no duplicate active plate; reserved bays are protected.");
    }
}
