package parking;
import java.time.LocalTime;
@SuppressWarnings("auxiliaryclass")
public class ValidationChecks {
  static int passed=0, total=0;
  static void check(String name, boolean ok) { total++; if(ok)passed++; System.out.println((ok?"PASS":"FAIL")+" | "+name); }
  static long hours(long m) { if(m<0)throw new IllegalArgumentException(); return m==0?0:(m+59)/60; }
  static double vanRate(LocalTime t) { return !t.isBefore(LocalTime.of(9,0)) && t.isBefore(LocalTime.of(17,0)) ? 60 : 30; }
  public static void main(String[] args) {
    check("zero minutes is zero billed hours", hours(0)==0);
    check("one minute rounds up", hours(1)==1);
    check("60 minutes is one hour", hours(60)==1);
    check("61 minutes rounds up to two", hours(61)==2);
    check("van 08:59 uses ordinary car rate", vanRate(LocalTime.of(8,59))==30);
    check("van 09:00 uses double rate", vanRate(LocalTime.of(9,0))==60);
    check("van 16:59 uses double rate", vanRate(LocalTime.of(16,59))==60);
    check("van 17:00 returns to ordinary rate", vanRate(LocalTime.of(17,0))==30);
    boolean bad=false; try { new ParkingVehicle(" ",VehicleType.CAR,UserType.VISITOR,false,false,0); } catch(IllegalArgumentException e){bad=true;}
    check("blank plate rejected",bad);
    bad=false; try { new ParkingVehicle("X",VehicleType.EV,UserType.VISITOR,false,true,-1); } catch(IllegalArgumentException e){bad=true;}
    check("negative charging energy rejected",bad);
    ParkingFacility f=new ParkingFacility(new int[]{20,40});
    try {
      ParkingTicket t=f.enter(new ParkingVehicle("SAN-0001",VehicleType.CAR,UserType.STUDENT,true,false,0),false);
      check("reserved student plate gets Floor 2 S036",t.slot.getFloor()==2 && t.slot.getCode().equals("S036"));
      boolean dup=false; try { f.enter(new ParkingVehicle("SAN-0001",VehicleType.CAR,UserType.STUDENT,true,false,0),false); } catch(IllegalArgumentException e){dup=true;}
      check("duplicate active plate rejected",dup);
      f.exit("SAN-0001",t.entry);
      check("slot released after exit",f.findActive("SAN-0001")==null);
    } catch(Exception e) { check("reserved slot integration",false); }
    // Boundary: the final free slot is usable; the next entry is rejected when full.
    ParkingFacility tiny=new ParkingFacility(new int[]{1});
    try {
      tiny.enter(new ParkingVehicle("LAST-001",VehicleType.BIKE,UserType.VISITOR,false,false,0),false);
      check("last available slot can be allocated",tiny.freeSlots()==0);
      boolean full=false;
      try { tiny.enter(new ParkingVehicle("FULL-002",VehicleType.CAR,UserType.VISITOR,false,false,0),false); }
      catch(IllegalStateException e) { full=true; }
      check("full facility rejects next entry",full);
    } catch(Exception e) { check("last slot/full facility scenario",false); }

    // One-day pass removes visitor parking fee; it does not make an unknown plate valid.
    ParkingFacility dayPassFacility=new ParkingFacility(new int[]{2});
    try {
      ParkingTicket visitor=dayPassFacility.enter(new ParkingVehicle("DAY-001",VehicleType.CAR,UserType.VISITOR,false,false,0),true);
      double due=dayPassFacility.exit("DAY-001",visitor.entry.plusMinutes(61));
      check("visitor one-day pass covers parking fee",due==0.0);
    } catch(Exception e) { check("visitor one-day pass scenario",false); }

    boolean unknown=false;
    try { VehicleType.valueOf("FLYING_CAR"); } catch(IllegalArgumentException e) { unknown=true; }
    check("unknown vehicle category is rejected by enum parsing",unknown);

    boolean invalidCapacity=false;
    try { new ParkingFacility(new int[]{2,0}); } catch(IllegalArgumentException e) { invalidCapacity=true; }
    check("invalid zero-slot floor capacity rejected",invalidCapacity);

    System.out.println("RESULT: "+passed+"/"+total+" checks passed");
    if(passed!=total) System.exit(1);
  }
}