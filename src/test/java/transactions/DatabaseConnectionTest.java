package transactions;

import taxi.destinations.Destination;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

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
    public void destinationsTest() {

        try {
            Jdbi jdbi = getJdbiDatabaseConnection();
            List<Destination> places = jdbi.withHandle((h) -> {
                //                System.out.println();
                return h.createQuery("SELECT LOCATION_NAME, PRICE FROM DESTINATIONS").mapToBean(Destination.class).list();

            });
            System.out.println(new Destination().getLocationName());
            assertEquals(places.size(), 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void transactionTest() {
//        String name = "Sibusiso";
//        int locationId = 2;
//        double amountPaid = 20.00;
//        double change = 3.50;
//
//        try {
//            Jdbi jdbi = getJdbiDatabaseConnection();
//            jdbi.useHandle(h -> {
//                h.createUpdate("INSERT INTO USER_TRANSACTIONS (first_name, amount_paid, change, travel_ref) VALUES (?, ?, ?, ?)")
//                        .bind(0, name)
//                        .bind(1, amountPaid)
//                        .bind(2, change)
//                        .bind(3, locationId)
//                        .execute();
//            });
//
//            assertEquals(0, 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void driverTrackerTest() throws URISyntaxException, SQLException {
//        Jdbi jdbi = getJdbiDatabaseConnection();
//        try {
//
//            assertEquals(0, 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
