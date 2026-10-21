class VehicleRecord {
    private String registration;
    private String category;
    VehicleRecord() { this("NOT-SET", "UNKNOWN"); }
    VehicleRecord(String registration, String category) {
        this.registration = registration.toUpperCase(); this.category = category;
    }
    public String getRegistration() { return registration; }
    public String getCategory() { return category; }
    public String toString() { return registration + " [" + category + "]"; }
}
class ElectricCarRecord extends VehicleRecord {
    private final int batteryKwh;
    ElectricCarRecord(String registration, int batteryKwh) {
        super(registration, "ELECTRIC CAR");
        if (batteryKwh <= 0) throw new IllegalArgumentException("Battery capacity must be positive");
        this.batteryKwh = batteryKwh;
        System.out.println("ElectricCarRecord constructor: battery=" + batteryKwh + " kWh");
    }
    public int getBatteryKwh() { return batteryKwh; }
}
public class ConstructorDemo {
    public static void main(String[] args) {
        VehicleRecord defaultVehicle = new VehicleRecord();
        VehicleRecord visitorCar = new VehicleRecord("tn01aa1111", "CAR");
        System.out.println("Default constructor : " + defaultVehicle);
        System.out.println("Parameterized       : " + visitorCar);
        ElectricCarRecord ev = new ElectricCarRecord("TN09EV2026", 60);
        System.out.println("Child object        : " + ev + ", battery=" + ev.getBatteryKwh() + " kWh");
        System.out.println("Constructor chaining uses this(...); inheritance initialisation uses super(...).");
    }
}
