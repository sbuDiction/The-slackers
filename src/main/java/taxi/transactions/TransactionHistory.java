package taxi.transactions;


public class TransactionHistory {
    double total;
    int priceRef;
    int daysRef;
    String timeStamp;

    public void setPriceRef(int priceRef) {
        this.priceRef = priceRef;
    }

    public void setDaysRef(int daysRef) {
        this.daysRef = daysRef;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getPriceRef() {
        return priceRef;
    }

    public int getDaysRef() {
        return daysRef;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }
}
