import java.util.Objects;
class RegisteredVehicle {
    private final String registration;
    RegisteredVehicle(String registration) {
        if (registration == null || registration.trim().isEmpty()) throw new IllegalArgumentException("Registration cannot be blank");
        this.registration = registration.trim().toUpperCase();
    }
    public String getRegistration() { return registration; }
    public double hourlyRate() { return 30.0; }
    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof RegisteredVehicle)) return false;
        RegisteredVehicle that = (RegisteredVehicle) other;
        return registration.equals(that.registration);
    }
    @Override public int hashCode() { return Objects.hash(registration); }
    @Override public String toString() { return registration + " @ Rs." + hourlyRate() + "/hour"; }
}
class BikeVehicle extends RegisteredVehicle {
    BikeVehicle(String registration) { super(registration); }
    @Override public double hourlyRate() { return 10.0; }
}
class EVVehicle extends RegisteredVehicle {
    EVVehicle(String registration) { super(registration); }
    @Override public double hourlyRate() { return 30.0; }
}
public class OverrideDemo {
    public static void main(String[] args) {
        RegisteredVehicle car = new RegisteredVehicle("tn01aa1111");
        RegisteredVehicle bike = new BikeVehicle("TN02BB2222");
        RegisteredVehicle sameCar = new RegisteredVehicle("TN01AA1111");
        System.out.println("Car: " + car);
        System.out.println("Bike: " + bike);
        System.out.println("Same normalized registration equals: " + car.equals(sameCar));
        System.out.println("Equal objects have equal hash codes: " + (car.hashCode() == sameCar.hashCode()));
    }
}
