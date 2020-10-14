package taxi.passengers;

import taxi.destinations.Destinations;
import taxi.exceptions.NotEnoughFundsException;
import taxi.exceptions.TaxiExceptions;

import java.net.URISyntaxException;
import java.sql.SQLException;

abstract public class Passengers {
    private Destinations destinations;
    private double amount;
    private String firstName = "";
    private String lastName = "";
    private String passcode = "";

    abstract public void bookTaxi(String firstName, Destinations destinations, double amount) throws TaxiExceptions, SQLException, URISyntaxException;

    abstract public double passengersChange();


}
