package taxi.mappings;

import taxi.destinations.Destinations;

public class DestinationDropDown {
    private Destinations location;
    private double price;

    public DestinationDropDown(Destinations location, double price) {
        this.location = location;
        this.price = price;
    }
}
