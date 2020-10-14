package taxi.methods;

import taxi.destinations.Destinations;
import taxi.mappings.DestinationDropDown;
import taxi.tools.ObjectToJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaxiServicesMethods {

    public List<String> buildDestinationDropDown() throws Exception {
        List<String> places = new ArrayList<>();
        HashMap<String, Double> locations = new HashMap();
        for (Destinations destinations : Destinations.values()) {
            places.add(new ObjectToJson().render(new DestinationDropDown(destinations, destinations.getCost())));
        }
        return places;
    }
}
