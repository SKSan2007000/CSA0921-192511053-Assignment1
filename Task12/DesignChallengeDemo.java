import java.time.LocalTime;
import java.util.Scanner;

class DesignVehicle {
    private final String plate;
    private final String type;
    DesignVehicle(String plate, String type) {
        if (plate == null || plate.trim().isEmpty()) throw new IllegalArgumentException("Registration plate cannot be blank");
        this.plate = plate.trim().toUpperCase();
        this.type = type;
    }
    public String getPlate() { return plate; }
    public String getType() { return type; }
    public double hourlyRate(LocalTime time) {
        switch (type) {
            case "BIKE": return 10;
            case "EBIKE": return 8;
            case "CAR": case "EV": return 30;
            case "SERVICE": return 20;
            case "VAN": return !time.isBefore(LocalTime.of(9, 0)) && time.isBefore(LocalTime.of(17, 0)) ? 60 : 30;
            default: throw new IllegalArgumentException("Unsupported vehicle category: " + type);
        }
    }
}
public class DesignChallengeDemo {
    private static double parkingFee(DesignVehicle vehicle, long minutes, boolean monthlyPass, boolean visitorDayPass, LocalTime entry) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        if (monthlyPass || visitorDayPass) return 0;
        long hours = minutes == 0 ? 0 : (minutes + 59) / 60;
        return hours * vehicle.hourlyRate(entry);
    }
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("CAMPUS SMART PARKING - REDESIGNED FEE RULES");
            System.out.print("Registration plate: ");
            String plate = input.nextLine().trim();
            System.out.print("Vehicle type (BIKE/EBIKE/CAR/EV/SERVICE/VAN): ");
            String type = input.nextLine().trim().toUpperCase();
            System.out.print("Parking duration in minutes: ");
            long minutes = Long.parseLong(input.nextLine().trim());
            System.out.print("Entry time (HH:mm): ");
            LocalTime entry = LocalTime.parse(input.nextLine().trim());
            System.out.print("Valid monthly pass? (y/n): ");
            boolean monthly = input.nextLine().trim().equalsIgnoreCase("y");
            System.out.print("Visitor one-day pass? (y/n): ");
            boolean dayPass = input.nextLine().trim().equalsIgnoreCase("y");
            DesignVehicle vehicle = new DesignVehicle(plate, type);
            long billedHours = minutes == 0 ? 0 : (minutes + 59) / 60;
            double rate = vehicle.hourlyRate(entry);
            double fee = parkingFee(vehicle, minutes, monthly, dayPass, entry);
            System.out.println("\n--- Calculation ---");
            System.out.println("Registration : " + vehicle.getPlate());
            System.out.println("Vehicle type : " + vehicle.getType());
            System.out.println("Hourly rate  : Rs. " + String.format("%.2f", rate));
            System.out.println("Billed hours : " + billedHours);
            System.out.printf("Parking fee  : Rs. %.2f%n", fee);
            if (type.equals("EV")) System.out.println("EV charging is separate: kWh x Rs.12; it is not waived by a parking pass.");
            if (type.equals("VAN")) System.out.println("Van rate window: Rs.60/hour from 09:00 up to (but not including) 17:00; otherwise Rs.30/hour.");
        } catch (Exception ex) {
            System.out.println("Input error: check the vehicle type, non-negative minutes and HH:mm time format.");
            System.out.println("Example vehicle types: BIKE, EBIKE, CAR, EV, SERVICE, VAN.");
        }
    }
}
