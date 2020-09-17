package taxi.calculations;

public class AmountPaid {
    double amountPaid;
    double price;

    public void setAmountPaid(double amountPaid) {
        System.out.println(amountPaid + " passenger amount");
        this.amountPaid = amountPaid;
    }

    public void setPrice(double price) {
        System.out.println(price + " destination price");
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public double getAmountPaid() {
        return amountPaid;
    }
}
