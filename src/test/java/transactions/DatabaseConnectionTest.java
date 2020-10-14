package transactions;


import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import taxi.destinations.Destinations;
import taxi.passengers.Passenger;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseConnectionTest {

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


    @Test
    public void bookTaxiTest() {
        try {
            Passenger passenger = new Passenger();
            passenger.bookTaxi("sibusiso", Destinations.DurbanVile, 100);

            assertEquals(85.00, passenger.passengersChange());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
