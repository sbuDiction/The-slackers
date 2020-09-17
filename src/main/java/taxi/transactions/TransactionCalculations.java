package taxi.transactions;

public class TransactionCalculations {

    String firstName;
    double amountPaid;
    double change;
    String locationName;
    double price;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAmountPaid(double amountPaid) {
        System.out.println(amountPaid);
        this.amountPaid = amountPaid;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFirstName() {
        return firstName;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public double getChange() {
        System.out.println();
        return calculateChange(amountPaid, price);
    }

    public String getLocationName() {
        return locationName;
    }

    public double getPrice() {
        return price;
    }

    public double calculateChange(double amount_paid, double price) {
        double amount = this.amountPaid = amount_paid;
        double cost = this.price = price;
        change = amount_paid - price;
        return change;
    }
}
