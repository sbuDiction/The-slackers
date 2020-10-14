package taxi;

import org.jdbi.v3.core.Jdbi;
import taxi.api.TaxiServicesApi;
import taxi.passengers.Passenger;
import taxi.routing.Router;
import taxi.tools.ObjectToJson;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

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


    public static void main(String[] args) {
        try {
            port(getHerokuAssignedPort());
            staticFiles.location("/content");
            new Router();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
