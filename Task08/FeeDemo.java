import java.time.LocalTime;
import java.util.Scanner;

abstract class RateVehicle {
    private final String plate;
    RateVehicle(String plate) { this.plate = plate.trim().toUpperCase(); }
    public String getPlate() { return plate; }
    public abstract double hourlyRate(LocalTime entryTime);
}
class RateBike extends RateVehicle { RateBike(String p) { super(p); } @Override public double hourlyRate(LocalTime t) { return 10.0; } }
class RateCar extends RateVehicle { RateCar(String p) { super(p); } @Override public double hourlyRate(LocalTime t) { return 30.0; } }
class RateEbike extends RateVehicle { RateEbike(String p) { super(p); } @Override public double hourlyRate(LocalTime t) { return 8.0; } }
class RateVan extends RateVehicle {
    RateVan(String p) { super(p); }
    @Override public double hourlyRate(LocalTime t) {
        return !t.isBefore(LocalTime.of(9, 0)) && t.isBefore(LocalTime.of(17, 0)) ? 60.0 : 30.0;
    }
}
public class FeeDemo {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("POLYMORPHIC HOURLY RATE SELECTION");
            System.out.print("Enter entry time (HH:mm): ");
            LocalTime time = LocalTime.parse(input.nextLine().trim());
            RateVehicle[] vehicles = {
                new RateBike("TN01AA1111"), new RateCar("TN02BB2222"),
                new RateEbike("TN03EB3333"), new RateVan("TN04DV4444")
            };
            for (RateVehicle vehicle : vehicles) {
                System.out.printf("%-12s plate=%s time=%s rate=Rs.%.2f/hour%n",
                    vehicle.getClass().getSimpleName(), vehicle.getPlate(), time, vehicle.hourlyRate(time));
            }
            System.out.println("The same hourlyRate() call selects each object's overridden method at runtime.");
        } catch (Exception ex) {
            System.out.println("Invalid time. Enter the time in 24-hour HH:mm format, for example 10:30.");
        }
    }
}
