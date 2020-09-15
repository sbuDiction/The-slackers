package taxi;

import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taxi.destinations.Destination;
import taxi.destinations.StringMethod;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseConnection {

//    @BeforeEach
//    public void cleanUpTables() {
//        try {
//            try (Connection conn = getConnection()) {
//                Statement statement = conn.createStatement();
//                statement.addBatch("delete from greetings");
//                statement.executeBatch();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

    static Jdbi getJdbiDatabaseConnection(String defualtJdbcUrl) throws URISyntaxException, SQLException {
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
        return Jdbi.create(defualtJdbcUrl);
    }

    @Test
    public void destinationsTest() {

        try {
            Jdbi jdbi = getJdbiDatabaseConnection("jdbc:postgresql://localhost/taxi_fare?user=slacker&password=1234");
            List<Destination> places = jdbi.withHandle((h) -> {
                List<Destination> locations = h.createQuery("SELECT LOCATION_NAME, PRICE FROM DESTINATIONS").mapToBean(Destination.class).list();
                return locations;
            });
            assertEquals(places.size(), 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transactionTest() {

        try {
            Jdbi jdbi = getJdbiDatabaseConnection("jdbc:postgresql://localhost/taxi_fare?user=slacker&password=1234");
            List<Destination> places = jdbi.withHandle((h) -> {
                List<Destination> locations = h.createQuery("SELECT FIRST_NAME, AMAOUNT FROM USER_TRANSACTIONS").mapToBean(Destination.class).list();
                return locations;
            });
            assertEquals(places.size(), 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
