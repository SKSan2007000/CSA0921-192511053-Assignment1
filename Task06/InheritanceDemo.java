abstract class Vehicle {
    private final String registration;
    Vehicle(String registration) { this.registration = registration.toUpperCase(); }
    public String getRegistration() { return registration; }
    public abstract String category();
}
class TwoWheeler extends Vehicle {
    TwoWheeler(String plate) { super(plate); }
    public String category() { return "Two-wheeler"; }
}
class Car extends Vehicle {
    Car(String plate) { super(plate); }
    public String category() { return "Car"; }
}
interface Chargeable { double chargingFee(double kwh); }
class ElectricCar extends Car implements Chargeable {
    ElectricCar(String plate) { super(plate); }
    public String category() { return "Electric car"; }
    public double chargingFee(double kwh) { if (kwh < 0) throw new IllegalArgumentException("kWh cannot be negative"); return kwh * 12.0; }
}
class EBike extends TwoWheeler { EBike(String plate) { super(plate); } public String category() { return "E-bike"; } }
class DeliveryVan extends Vehicle { DeliveryVan(String plate) { super(plate); } public String category() { return "Delivery van"; } }
class ServiceVehicle extends Vehicle { ServiceVehicle(String plate) { super(plate); } public String category() { return "Service vehicle"; } }
public class InheritanceDemo {
    public static void main(String[] args) {
        Vehicle[] vehicles = { new TwoWheeler("TN01AA1111"), new Car("TN02BB2222"), new ElectricCar("TN03EV3333"), new EBike("TN04EB4444"), new DeliveryVan("TN05DV5555"), new ServiceVehicle("TN06SV6666") };
        System.out.println("VEHICLE INHERITANCE HIERARCHY");
        for (Vehicle v : vehicles) System.out.printf("%-18s %s%n", v.category(), v.getRegistration());
        ElectricCar ev = new ElectricCar("TN03EV3333");
        System.out.printf("EV charging 5.5 kWh: Rs. %.2f%n", ev.chargingFee(5.5));
    }
}
