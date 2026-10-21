package parking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*
 * Campus Smart-Parking System
 * Console simulation for the Java assignment. Camera/QR/mobile features are
 * represented as input and ticket-location data; no physical camera is claimed.
 */
enum VehicleType { BIKE, CAR, EV, E_BIKE, DELIVERY_VAN, SERVICE_VEHICLE }
enum UserType { STUDENT, FACULTY, VISITOR, SERVICE }
class ParkingVehicle {
    private final String plate;
    private final VehicleType type;
    private final UserType userType;
    private final boolean monthlyPass;
    private final boolean chargingRequested;
    private final double chargingKWh;

    ParkingVehicle(String plate, VehicleType type, UserType userType,
                   boolean monthlyPass, boolean chargingRequested, double chargingKWh) {
        if (plate == null || plate.trim().isEmpty()) throw new IllegalArgumentException("Plate is required");
        if (chargingKWh < 0) throw new IllegalArgumentException("kWh cannot be negative");
        this.plate = plate.trim().toUpperCase();
        this.type = Objects.requireNonNull(type);
        this.userType = Objects.requireNonNull(userType);
        this.monthlyPass = monthlyPass;
        this.chargingRequested = chargingRequested;
        this.chargingKWh = chargingKWh;
    }
    String getPlate() { return plate; }
    VehicleType getType() { return type; }
    UserType getUserType() { return userType; }
    boolean hasMonthlyPass() { return monthlyPass && (userType == UserType.STUDENT || userType == UserType.FACULTY); }
    boolean needsCharging() { return chargingRequested && type == VehicleType.EV; }
    double getChargingKWh() { return chargingKWh; }
    double hourlyRate(LocalTime entryTime) {
        switch(type) {
            case BIKE: return 10;
            case E_BIKE: return 8;
            case CAR: return 30;
            case EV: return 30;
            case DELIVERY_VAN:
                return !entryTime.isBefore(LocalTime.of(9,0)) && entryTime.isBefore(LocalTime.of(17,0)) ? 60 : 30;
            case SERVICE_VEHICLE: return 20;
            default: return 30;
        }
    }
}
class ParkingSlot {
    private final int floor;
    private final String code;
    private final boolean charging;
    private final String reservedPlate;
    private String occupiedPlate;

    ParkingSlot(int floor, String code, boolean charging, String reservedPlate) {
        this.floor=floor; this.code=code; this.charging=charging;
        this.reservedPlate=reservedPlate == null ? null : reservedPlate.trim().toUpperCase();
    }
    boolean isFree() { return occupiedPlate == null; }
    boolean isCharging() { return charging; }
    boolean isReserved() { return reservedPlate != null; }
    boolean isReservedFor(String plate) { return reservedPlate != null && reservedPlate.equalsIgnoreCase(plate); }
    int getFloor() { return floor; }
    String getCode() { return code; }
    String location() { return "Floor " + floor + ", Slot " + code; }
    void occupy(String plate) { if (!isFree()) throw new IllegalStateException("Slot is occupied"); occupiedPlate=plate; }
    void release() { occupiedPlate=null; }
}
class ParkingTicket {
    private static int nextNumber=1000;
    final String id;
    final ParkingVehicle vehicle;
    final ParkingSlot slot;
    final LocalDateTime entry;
    LocalDateTime exit;
    boolean oneDayPass;
    ParkingTicket(ParkingVehicle v, ParkingSlot s, LocalDateTime entry, boolean oneDayPass) {
        this.id="CSP-"+(++nextNumber); this.vehicle=v; this.slot=s; this.entry=entry; this.oneDayPass=oneDayPass;
    }
    long minutesAt(LocalDateTime end) { return Math.max(0, Duration.between(entry,end).toMinutes()); }
    long billedHours(LocalDateTime end) { long m=minutesAt(end); return m==0?0:(m+59)/60; }
    String qrPayload() { return "campus-parking://ticket/"+id+"?floor="+slot.getFloor()+"&slot="+slot.getCode(); }
}
class ParkingFacility {
    private final ParkingSlot[][] floors;
    private final Map<String,ParkingTicket> activeByPlate=new HashMap<String,ParkingTicket>();
    private final Map<String,ParkingTicket> history=new LinkedHashMap<String,ParkingTicket>();
    private static final double CHARGING_RATE_PER_KWH=12.0;

    ParkingFacility(int[] capacities) {
        if(capacities==null || capacities.length==0) throw new IllegalArgumentException("At least one floor is required");
        floors=new ParkingSlot[capacities.length][];
        for(int f=0;f<capacities.length;f++) {
            if(capacities[f]<1) throw new IllegalArgumentException("Every floor needs at least one slot");
            floors[f]=new ParkingSlot[capacities[f]];
            for(int s=0;s<capacities[f];s++) {
                String code=String.format("S%03d",s+1);
                boolean charger=((s+1)%10==0) || (f==1 && code.equals("S036"));
                String reserved=null;
                if(f==1 && code.equals("S036")) reserved="SAN-0001"; // replace with approved plate for private demo
                floors[f][s]=new ParkingSlot(f+1,code,charger,reserved);
            }
        }
    }
    ParkingTicket enter(ParkingVehicle v, boolean oneDayPass) {
        if(activeByPlate.containsKey(v.getPlate())) throw new IllegalArgumentException("This plate already has an active ticket");
        ParkingSlot slot=findSlot(v);
        if(slot==null) throw new IllegalStateException("No suitable free slot is available");
        LocalDateTime now=LocalDateTime.now();
        slot.occupy(v.getPlate());
        ParkingTicket t=new ParkingTicket(v,slot,now,oneDayPass);
        activeByPlate.put(v.getPlate(),t); history.put(t.id,t);
        return t;
    }
    private ParkingSlot findSlot(ParkingVehicle v) {
        // Reserved registration has priority, and must not be silently moved elsewhere.
        for(ParkingSlot[] floor:floors) for(ParkingSlot s:floor) {
            if(s.isReservedFor(v.getPlate()) && (v.getUserType()==UserType.STUDENT || v.getUserType()==UserType.FACULTY)) {
                return s.isFree() && (!v.needsCharging() || s.isCharging()) ? s : null;
            }
        }
        for(ParkingSlot[] floor:floors) for(ParkingSlot s:floor) {
            if(!s.isFree() || s.isReserved()) continue;
            if(v.needsCharging() && !s.isCharging()) continue;
            return s;
        }
        return null;
    }
    double exit(String plate, LocalDateTime end) {
        ParkingTicket t=activeByPlate.get(plate.trim().toUpperCase());
        if(t==null) throw new IllegalArgumentException("No active ticket found for this plate");
        if(end.isBefore(t.entry)) throw new IllegalArgumentException("Exit time cannot be before entry");
        t.exit=end;
        double parking;
        if(t.vehicle.hasMonthlyPass() || t.oneDayPass && t.vehicle.getUserType()==UserType.VISITOR) parking=0;
        else parking=t.billedHours(end)*t.vehicle.hourlyRate(t.entry.toLocalTime());
        double charging=t.vehicle.needsCharging()?t.vehicle.getChargingKWh()*CHARGING_RATE_PER_KWH:0;
        t.slot.release(); activeByPlate.remove(t.vehicle.getPlate());
        System.out.printf(Locale.US,"Duration: %d minutes (%d billed hours)%n",t.minutesAt(end),t.billedHours(end));
        System.out.printf(Locale.US,"Parking fee: Rs %.2f%nCharging fee: Rs %.2f%nTotal due: Rs %.2f%n",parking,charging,parking+charging);
        return parking+charging;
    }
    ParkingTicket findActive(String plate) { return activeByPlate.get(plate.trim().toUpperCase()); }
    int freeSlots() { int n=0; for(ParkingSlot[] f:floors) for(ParkingSlot s:f) if(s.isFree())n++; return n; }
    int totalSlots() { int n=0; for(ParkingSlot[] f:floors)n+=f.length; return n; }
    void printStatus() {
        System.out.println("Capacity: "+(totalSlots()-freeSlots())+"/"+totalSlots()+" occupied; "+freeSlots()+" free");
        for(ParkingSlot[] f:floors) {
            int free=0; for(ParkingSlot s:f)if(s.isFree())free++;
            System.out.println("Floor "+f[0].getFloor()+": "+free+"/"+f.length+" free");
        }
    }
}
public class CampusSmartParking {
    private static final Scanner in=new Scanner(System.in);
    public static void main(String[] args) {
        ParkingFacility facility=new ParkingFacility(new int[]{20,40,17,23,31}); // unequal floors; S036 is on Floor 2
        System.out.println("CAMPUS SMART-PARKING — CONSOLE DEMO");
        while(true) {
            System.out.println("\n1 Entry  2 Exit  3 Find active ticket  4 Status  0 Quit");
            System.out.print("Choice: ");
            String choice=in.nextLine().trim();
            try {
                if(choice.equals("0")) break;
                if(choice.equals("1")) entry(facility);
                else if(choice.equals("2")) exit(facility);
                else if(choice.equals("3")) find(facility);
                else if(choice.equals("4")) facility.printStatus();
                else System.out.println("Choose 0 to 4.");
            } catch(Exception ex) { System.out.println("Error: "+ex.getMessage()); }
        }
        System.out.println("Demo ended.");
    }
    private static void entry(ParkingFacility f) {
        System.out.print("Vehicle plate: "); String plate=in.nextLine().trim();
        System.out.print("Vehicle type (BIKE/CAR/EV/E_BIKE/DELIVERY_VAN/SERVICE_VEHICLE): ");
        VehicleType type=VehicleType.valueOf(in.nextLine().trim().toUpperCase());
        System.out.print("User type (STUDENT/FACULTY/VISITOR/SERVICE): ");
        UserType user=UserType.valueOf(in.nextLine().trim().toUpperCase());
        System.out.print("Valid monthly pass? (yes/no): "); boolean monthly=in.nextLine().trim().equalsIgnoreCase("yes");
        boolean charge=false; double kwh=0;
        if(type==VehicleType.EV) {
            System.out.print("Need EV charging? (yes/no): "); charge=in.nextLine().trim().equalsIgnoreCase("yes");
            if(charge) { System.out.print("Charging energy in kWh (demo input): "); kwh=Double.parseDouble(in.nextLine().trim()); }
        }
        boolean dayPass=false;
        if(user==UserType.VISITOR) { System.out.print("One-day visitor pass? (yes/no): "); dayPass=in.nextLine().trim().equalsIgnoreCase("yes"); }
        ParkingVehicle v=new ParkingVehicle(plate,type,user,monthly,charge,kwh);
        ParkingTicket t=f.enter(v,dayPass);
        System.out.println("Ticket: "+t.id);
        System.out.println("Entry time: "+t.entry.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("Allocated: "+t.slot.location());
        System.out.println("Entrance lane guidance: "+laneFor(v));
        System.out.println("QR payload (demo only): "+t.qrPayload());
        System.out.println("Shareable app message: Ticket "+t.id+"; "+t.slot.location()+"; plate "+v.getPlate());
        System.out.println("Note: camera/app/QR are simulated; this program does not connect physical hardware.");
    }
    private static String laneFor(ParkingVehicle v) {
        if(v.getUserType()==UserType.STUDENT || v.getUserType()==UserType.FACULTY) return v.getType()==VehicleType.BIKE || v.getType()==VehicleType.E_BIKE ? "Lane 1: student/faculty two-wheelers" : "Lane 2: student/faculty cars and EVs";
        if(v.getType()==VehicleType.SERVICE_VEHICLE || v.getType()==VehicleType.DELIVERY_VAN) return "Lane 3: lorries, delivery and maintenance/service vehicles";
        if(v.getType()==VehicleType.BIKE || v.getType()==VehicleType.E_BIKE) return "Lane 4: visitor/outside two-wheelers";
        return "Lane 3: visitor cars / large vehicles are directed to attendant check";
    }
    private static void exit(ParkingFacility f) {
        System.out.print("Vehicle plate: "); String plate=in.nextLine().trim();
        f.exit(plate,LocalDateTime.now());
        System.out.println("Ticket closed; slot released.");
    }
    private static void find(ParkingFacility f) {
        System.out.print("Vehicle plate: "); String plate=in.nextLine().trim();
        ParkingTicket t=f.findActive(plate);
        if(t==null) System.out.println("No active ticket.");
        else System.out.println(t.id+" — "+t.slot.location()+" — entered "+t.entry);
    }
}
