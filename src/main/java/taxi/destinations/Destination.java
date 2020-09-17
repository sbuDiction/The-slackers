package taxi.destinations;


public class Destination {
    int id;
    String locationName;
    double price;

    public Destination() {
        super();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        System.out.println(locationName);
        this.locationName = locationName;
    }

    public void setId(int id) {
        System.out.println(id);
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        System.out.println(price);
        this.price = price;
    }

    public int getId() {
        return id;
    }
}
