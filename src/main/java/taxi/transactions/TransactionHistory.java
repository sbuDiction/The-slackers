package taxi.transactions;


public class TransactionHistory {

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

    //    public static void main(String[] args) {
//        LocalDateTime time = LocalDateTime.now();
//        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
//
//        String formattedDate = time.format(myFormatObj);
//        System.out.println(formattedDate);
//    }
}
