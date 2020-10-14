package taxi.passengers;

import org.jdbi.v3.core.Jdbi;
import taxi.destinations.Destinations;
import taxi.exceptions.DestinationNotFoundException;
import taxi.exceptions.NotEnoughFundsException;
import taxi.exceptions.TaxiExceptions;
import taxi.exceptions.TaxiOverLoadedException;
import taxi.mappings.Bookings;
import taxi.mappings.DestinationDropDown;
import taxi.services.TaxiServicesDb;
import taxi.tools.ObjectToJson;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Passenger extends Passengers {
    private double change;
    private final static int limit = 16;

    @Override
    public void bookTaxi(String firstName, Destinations destinations, double amount) throws TaxiExceptions, URISyntaxException, SQLException {
        int taxiPassengerCount = 0;
        if (destinations != null) {
            if (amount < destinations.getCost()) throw new NotEnoughFundsException();
            if (taxiPassengerCount >= limit) throw new TaxiOverLoadedException();
            else {
                String username = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
                double change = amount - destinations.getCost();
                new TaxiServicesDb().addBookings(username, destinations.name(), amount, change);
                this.change = change;
                taxiPassengerCount++;
                System.out.println(taxiPassengerCount);
            }
        }

        if (destinations == null) {
            throw new DestinationNotFoundException();
        }
    }

    @Override
    public double passengersChange() {
        return change;
    }


    public List<String> buildDestinationDropDown() throws Exception {
        List<String> places = new ArrayList<>();
        HashMap<String, Double> locations = new HashMap();
        for (Destinations destinations : Destinations.values()) {
            places.add(new ObjectToJson().render(new DestinationDropDown(destinations, destinations.getCost())));
        }
        return places;
    }
}

