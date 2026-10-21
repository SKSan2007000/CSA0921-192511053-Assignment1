import java.util.HashMap;
import java.util.Map;

public class DataRepresentation {
    public static void main(String[] args) {
        String registration = "TN01AA1111";
        int floor = 2, slot = 36;
        long ticketNo = 10001L;
        boolean occupied = true;
        double sampleFee = 60.00;

        // Unequal floor capacities, represented as a jagged array.
        int[][] parkingSlots = { new int[20], new int[40], new int[17], new int[23], new int[31] };
        Map<String, Long> activeTickets = new HashMap<>();
        activeTickets.put(registration, ticketNo);

        long minutes = 61;
        long billedHours = minutes == 0 ? 0 : (minutes + 59) / 60;
        double parkingFee = billedHours * 30.00;

        System.out.println("CAMPUS SMART PARKING — DATA REPRESENTATION");
        System.out.println("Registration : " + registration);
        System.out.println("Floor / slot : " + floor + " / S" + String.format("%03d", slot));
        System.out.println("Ticket       : " + ticketNo + " (long)");
        System.out.println("Occupied     : " + occupied + " (boolean)");
        System.out.printf("Sample fee   : Rs. %.2f%n", sampleFee);
        System.out.println("\nFloor capacities:");
        for (int i = 0; i < parkingSlots.length; i++)
            System.out.printf("Floor %d: %d slots%n", i + 1, parkingSlots[i].length);
        System.out.println("Active ticket lookup: " + activeTickets.get(registration));
        System.out.printf("61 minutes -> %d billed hours -> Rs. %.2f%n", billedHours, parkingFee);
    }
}
