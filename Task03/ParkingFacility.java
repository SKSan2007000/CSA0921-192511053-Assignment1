import java.util.Scanner;

public class ParkingFacility {
    private final boolean[][] occupied;
    private final boolean[][] reserved;

    public ParkingFacility(int[] capacities) {
        occupied = new boolean[capacities.length][];
        reserved = new boolean[capacities.length][];
        for (int f = 0; f < capacities.length; f++) {
            occupied[f] = new boolean[capacities[f]];
            reserved[f] = new boolean[capacities[f]];
        }
        // Floor 2, slot S036 is reserved for the registered demo user.
        reserved[1][35] = true;
    }

    public String allocate(boolean registered, String plate) {
        if (registered && "SAN-0001".equalsIgnoreCase(plate)) {
            if (occupied[1][35]) return null;
            occupied[1][35] = true;
            return "Floor 2, S036 (reserved)";
        }
        for (int f = 0; f < occupied.length; f++) {
            for (int s = 0; s < occupied[f].length; s++) {
                if (!occupied[f][s] && !reserved[f][s]) {
                    occupied[f][s] = true;
                    return "Floor " + (f + 1) + ", S" + String.format("%03d", s + 1);
                }
            }
        }
        return null;
    }

    public boolean release(int floor, int slot) {
        if (floor < 1 || floor > occupied.length || slot < 1 || slot > occupied[floor - 1].length) return false;
        if (!occupied[floor - 1][slot - 1]) return false;
        occupied[floor - 1][slot - 1] = false;
        return true;
    }

    public void printStatus() {
        for (int f = 0; f < occupied.length; f++) {
            int used = 0;
            for (boolean value : occupied[f]) if (value) used++;
            System.out.printf("Floor %d: %d/%d occupied, %d free%n", f + 1, used, occupied[f].length, occupied[f].length - used);
        }
    }

    public static void main(String[] args) {
        ParkingFacility facility = new ParkingFacility(new int[]{20, 40, 17, 23, 31});
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("CAMPUS SMART PARKING - SLOT ALLOCATION DEMO");
            System.out.print("Enter registration plate (use SAN-0001 for reserved slot): ");
            String plate = input.nextLine().trim().toUpperCase();
            if (plate.isEmpty()) {
                System.out.println("Registration plate cannot be blank.");
                return;
            }
            System.out.print("Is this a registered student/faculty user? (y/n): ");
            boolean registered = input.nextLine().trim().equalsIgnoreCase("y");
            String slot = facility.allocate(registered, plate);
            if (slot == null) System.out.println("No suitable slot is available.");
            else System.out.println("Allocated slot: " + slot);
            System.out.println("Current capacity:");
            facility.printStatus();
            System.out.print("Release a slot now? (y/n): ");
            if (input.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.print("Floor number: ");
                int floor = Integer.parseInt(input.nextLine().trim());
                System.out.print("Slot number: ");
                int slotNumber = Integer.parseInt(input.nextLine().trim());
                System.out.println(facility.release(floor, slotNumber) ? "Slot released." : "Slot could not be released.");
                facility.printStatus();
            }
        } catch (NumberFormatException ex) {
            System.out.println("Floor and slot must be whole numbers.");
        }
    }
}
