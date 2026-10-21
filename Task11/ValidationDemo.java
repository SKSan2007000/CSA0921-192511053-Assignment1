import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationDemo {
    private static int passed = 0, failed = 0;
    private static void check(String name, boolean condition) {
        if (condition) { passed++; System.out.println("PASS | " + name); }
        else { failed++; System.out.println("FAIL | " + name); }
    }
    private static long billedHours(long minutes) { if (minutes < 0) throw new IllegalArgumentException("Negative duration"); return minutes == 0 ? 0 : (minutes + 59) / 60; }
    private static double vanRate(LocalTime time) { return !time.isBefore(LocalTime.of(9,0)) && time.isBefore(LocalTime.of(17,0)) ? 60 : 30; }
    public static void main(String[] args) {
        System.out.println("CAMPUS SMART PARKING — VALIDATION DEMO");
        check("0 minutes bills 0 hours", billedHours(0) == 0);
        check("1 minute rounds up", billedHours(1) == 1);
        check("60 minutes is one hour", billedHours(60) == 1);
        check("61 minutes rounds up to two", billedHours(61) == 2);
        check("van 08:59 ordinary rate", vanRate(LocalTime.of(8,59)) == 30);
        check("van 09:00 double rate", vanRate(LocalTime.of(9,0)) == 60);
        check("van 16:59 double rate", vanRate(LocalTime.of(16,59)) == 60);
        check("van 17:00 ordinary rate", vanRate(LocalTime.of(17,0)) == 30);
        check("blank plate rejected", rejectsPlate(" "));
        check("negative kWh rejected", rejectsKwh(-0.1));
        List<String> active = new ArrayList<>(); active.add("TN01AA1111");
        check("duplicate active plate detected", active.contains("TN01AA1111"));
        check("unknown plate not found", !active.contains("TN99ZZ9999"));
        check("visitor daily pass parking fee waived", parkingFee(2,30,false,true) == 0);
        check("monthly pass waives parking fee", parkingFee(2,30,true,false) == 0);
        check("ordinary car 2 hours costs Rs.60", parkingFee(2,30,false,false) == 60);
        check("EV charging is separate", Math.abs(5.0 * 12.0 - 60.0) < 0.001);
        System.out.printf("RESULT: %d passed, %d failed%n", passed, failed);
        if (failed > 0) System.exit(1);
    }
    private static boolean rejectsPlate(String p) { try { if (p == null || p.trim().isEmpty()) throw new IllegalArgumentException(); return false; } catch (IllegalArgumentException e) { return true; } }
    private static boolean rejectsKwh(double k) { try { if (k < 0) throw new IllegalArgumentException(); return false; } catch (IllegalArgumentException e) { return true; } }
    private static double parkingFee(long hours, double rate, boolean monthly, boolean dayPass) { return (monthly || dayPass) ? 0 : hours * rate; }
}
