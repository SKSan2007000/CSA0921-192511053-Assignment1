class EncapsulatedSlot {
    private final int floor;
    private final int slotNumber;
    private boolean occupied;
    private final boolean chargingCapable;
    EncapsulatedSlot(int floor, int slotNumber, boolean chargingCapable) {
        if (floor < 1 || slotNumber < 1) throw new IllegalArgumentException("Floor and slot must be positive");
        this.floor = floor; this.slotNumber = slotNumber; this.chargingCapable = chargingCapable;
    }
    public boolean occupy(boolean chargingRequested) {
        if (occupied) return false;
        if (chargingRequested && !chargingCapable) return false;
        occupied = true; return true;
    }
    public boolean release() { if (!occupied) return false; occupied = false; return true; }
    public boolean isOccupied() { return occupied; }
    public String location() { return "Floor " + floor + ", S" + String.format("%03d", slotNumber); }
}
class ValidatedVehicle {
    private final String registration;
    private final double chargingKwh;
    ValidatedVehicle(String registration, double chargingKwh) {
        if (registration == null || registration.trim().isEmpty()) throw new IllegalArgumentException("Registration cannot be blank");
        if (chargingKwh < 0) throw new IllegalArgumentException("kWh cannot be negative");
        this.registration = registration.trim().toUpperCase(); this.chargingKwh = chargingKwh;
    }
    public String getRegistration() { return registration; }
    public double getChargingKwh() { return chargingKwh; }
}
public class EncapsulationDemo {
    public static void main(String[] args) {
        EncapsulatedSlot slot = new EncapsulatedSlot(2, 36, true);
        ValidatedVehicle ev = new ValidatedVehicle("tn03ev3333", 5.5);
        System.out.println("Vehicle: " + ev.getRegistration() + ", energy=" + ev.getChargingKwh() + " kWh");
        System.out.println("First occupy: " + slot.occupy(true));
        System.out.println("Second occupy: " + slot.occupy(false) + " (must fail because occupied)");
        System.out.println("Location/status: " + slot.location() + ", occupied=" + slot.isOccupied());
        System.out.println("Release: " + slot.release() + "; occupied=" + slot.isOccupied());
        try { new ValidatedVehicle("   ", -1); } catch (IllegalArgumentException ex) { System.out.println("Invalid input rejected: " + ex.getMessage()); }
    }
}
