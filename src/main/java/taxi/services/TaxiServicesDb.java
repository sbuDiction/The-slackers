package taxi.services;

import org.jdbi.v3.core.Jdbi;
import taxi.destinations.Destinations;
import taxi.mappings.Bookings;
import taxi.passengers.Passenger;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class TaxiServicesDb {

    static Jdbi getJdbiDatabaseConnection() throws URISyntaxException, SQLException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String database_url = processBuilder.environment().get("DATABASE_URL");
        if (database_url != null) {

            URI uri = new URI(database_url);
            String[] hostParts = uri.getUserInfo().split(":");
            String username = hostParts[0];
            String password = hostParts[1];
            String host = uri.getHost();

            int port = uri.getPort();

            String path = uri.getPath();
            String url = String.format("jdbc:postgresql://%s:%s%s", host, port, path);

            return Jdbi.create(url, username, password);
        }
        return Jdbi.create("jdbc:postgresql://localhost/taxi_fare?user=slacker&password=1234");
    }


    public void addBookings(String firstName, String destinations, double amount, double change) throws URISyntaxException, SQLException {
        Jdbi jdbi = getJdbiDatabaseConnection();
        try {
            jdbi.useHandle(handle -> handle
                    .createUpdate("INSERT INTO TAXI_PASSENGERS (first_name, destination, passenger_amount, change) VALUES (?, ?, ?, ?)")
                    .bind(0, firstName)
                    .bind(1, destinations)
                    .bind(2, amount)
                    .bind(3, change)
                    .execute());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
