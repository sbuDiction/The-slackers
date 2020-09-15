package taxi.destinations;

import java.beans.ConstructorProperties;

public class Destination {
    String locationName;
    double price;

    public Destination() {
    }

    @ConstructorProperties({"location_name", "price"})
    public Destination(String location_name, double price) {
        super();
        this.locationName = location_name;
        this.price = price;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        System.out.println(locationName);
        this.locationName = locationName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        System.out.println(price);
        this.price = price;
    }
}
