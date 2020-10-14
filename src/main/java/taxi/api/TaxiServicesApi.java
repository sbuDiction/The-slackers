package taxi.api;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import taxi.destinations.Destinations;
import taxi.exceptions.TaxiExceptions;
import taxi.mappings.Bookings;
import taxi.passengers.Passenger;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class TaxiServicesApi {

    public Route destinationDropDown() {
        return this::handle;
    }

    public Route bookTaxi() {
        return this::handle2;
    }

    private Object handle(Request request, Response response) throws Exception {
        Passenger passenger = new Passenger();
        return passenger.buildDestinationDropDown();
    }

    private Object handle2(Request request, Response response) throws URISyntaxException, SQLException {
        try {
            response.type("application/json");
            Passenger passenger = new Passenger();

            String firstName = request.queryParams("demo-name");
            double amount = Double.parseDouble(request.queryParams("demo-email"));
            String location = request.queryParams("demo-category");
            new Passenger().bookTaxi(firstName, Destinations.valueOf(location), amount);

            response.redirect("/#intro");
        } catch (TaxiExceptions taxi) {
            taxi.printStackTrace();
        }
        return null;
    }
}
