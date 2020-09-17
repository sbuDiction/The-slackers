package taxi.calculations;

public class CalculateChange {
    //    int id;
//    String locationName;
//    double change;
//    double price;
    double amountPaid;
//    double destinationAmount;

    public CalculateChange() {

    }

    public CalculateChange(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        System.out.println(amountPaid + " cash");
        this.amountPaid = amountPaid;
    }

//    public void setDestinationAmount(double destinationAmount) {
//        this.destinationAmount = destinationAmount;
//    }
//
//    public void setId(int id) {
//        System.out.println(id + " destination id");
//        this.id = id;
//    }
//
//    public void setPrice(double price) {
//        System.out.println(price + " destination price");
//        this.price = price;
//    }
//
//    public void setLocationName(String locationName) {
//        System.out.println(locationName + " destination name");
//        this.locationName = locationName;
//    }

    public double getUserAmount() {
        return amountPaid;
    }

//    public double getDestinationAmount() {
//        return destinationAmount;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setChange(double change) {
//
//        this.change = change;
//    }
}
