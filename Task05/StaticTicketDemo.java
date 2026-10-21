class DemoTicket {
    private static long nextNumber = 1000;
    private final long ticketId;
    private final String plate;
    DemoTicket(String plate) { this.plate = plate.toUpperCase(); this.ticketId = ++nextNumber; }
    public long getTicketId() { return ticketId; }
    public String getPlate() { return plate; }
    public static long getLastIssuedNumber() { return nextNumber; }
    public String toString() { return "T" + ticketId + " for " + plate; }
}
public class StaticTicketDemo {
    public static void main(String[] args) {
        DemoTicket first = new DemoTicket("TN01AA1111");
        DemoTicket second = new DemoTicket("TN09EV2026");
        DemoTicket third = new DemoTicket("TN22BB3333");
        System.out.println(first); System.out.println(second); System.out.println(third);
        System.out.println("Shared static counter: " + DemoTicket.getLastIssuedNumber());
        System.out.println("Each ticket has its own plate and ID; the counter is shared by the class.");
        System.out.println("Counter resets on restart because this demo does not persist it to a database.");
    }
}
