import java.time.LocalTime;
abstract class AbstractVehicle {
    private final String registration;
    AbstractVehicle(String registration) { this.registration = registration.toUpperCase(); }
    public String getRegistration() { return registration; }
    public abstract double hourlyRate(LocalTime time);
}
interface Chargeable { double chargingFee(double kwh); }
class AbstractCar extends AbstractVehicle {
    AbstractCar(String registration) { super(registration); }
    public double hourlyRate(LocalTime time) { return 30.0; }
}
class AbstractElectricCar extends AbstractVehicle implements Chargeable {
    AbstractElectricCar(String registration) { super(registration); }
    public double hourlyRate(LocalTime time) { return 30.0; }
    public double chargingFee(double kwh) {
        if (kwh < 0) throw new IllegalArgumentException("Charging energy cannot be negative");
        return kwh * 12.0;
    }
}
public class AbstractionDemo {
    public static void main(String[] args) {
        AbstractVehicle car = new AbstractCar("TN01AA1111");
        AbstractElectricCar ev = new AbstractElectricCar("TN02EV2222");
        LocalTime entry = LocalTime.of(10, 30);
        System.out.printf("Car %s rate: Rs. %.2f/hour%n", car.getRegistration(), car.hourlyRate(entry));
        System.out.printf("EV %s rate: Rs. %.2f/hour%n", ev.getRegistration(), ev.hourlyRate(entry));
        System.out.printf("EV charging 4 kWh: Rs. %.2f%n", ev.chargingFee(4));
        System.out.println("Abstract class shares vehicle state; Chargeable defines an optional capability.");
    }
}
