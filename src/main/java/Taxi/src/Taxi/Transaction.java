package Taxi;

public class Transaction {
    double amount_paid;
    double change;
    double price;
    public double setAmountPaid(double amount_paid) {
        return amount_paid;
    }
    public double setPrice(double price) {
        return price;
    }
    public double calculateChange(double amount_paid, double price) {
        change = amount_paid - price;
        return change;
    }

}
