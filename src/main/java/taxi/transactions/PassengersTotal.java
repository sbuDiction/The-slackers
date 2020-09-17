package taxi.transactions;

public class PassengersTotal {

    double price;
    double total;

    public void setPrice(double price) {
        System.out.println(price + " passenger total");
        setTotal(price);
//        this.price = price;
        getTotal();
    }

    public double getPrice() {
        return price;
    }

    public void setTotal(double total) {
        this.total += total;
    }

    public double getTotal() {
        System.out.println(total + " total");
        return total;
    }
}
