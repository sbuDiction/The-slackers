package taxi.mappings;

public class Bookings {
    private String firstName;
    private String destination;
    private double amount;
    private double change;

    public Bookings(String firstName, String destination, double amount) {
        this.firstName = firstName;
        this.destination = destination;
        this.amount = amount;
    }

    public Bookings(String firstName, String destination, double amount, double change) {
        this.firstName = firstName;
        this.destination = destination;
        this.amount = amount;
        this.change = change;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getDestination() {
        return destination;
    }

    public double getAmount() {
        return amount;
    }

    public double getChange() {
        return change;
    }
}
