package taxi;

import org.jdbi.v3.core.Jdbi;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import taxi.calculations.CalculateChange;
import taxi.weeks.DaysInWeeks;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            TaxiDriverDBMethods taxiDriver = new TaxiDriverDBMethods(getJdbiDatabaseConnection());
            taxiDriver.setDestinationForUser(19, 3);
            get("/", (req, res) -> {
                Map<String, Object> map = new HashMap<>();

                List<CalculateChange> change = new ArrayList<>();

                return new ModelAndView(map, "index.hbs");
            }, new HandlebarsTemplateEngine());

            get("/destination", (request, response) -> {
                Map<String, Object> map = new HashMap<>();

                map.put("destinations", taxiDriver.getLocations());
                map.put("person", taxiDriver.getPersonList());
                return new ModelAndView(map, "addDestination.hbs");
            }, new HandlebarsTemplateEngine());

            get("/driver", (request, response) -> {
                Map<String, Object> map = new HashMap<>();

                map.put("passengers", taxiDriver.getPassengerData());
                map.put("data", "[1,3,5,6,7,100,9]");
                map.put("theGraphLabel", "The graph label");
                map.put("days", taxiDriver.getDays());
                return new ModelAndView(map, "driver.hbs");
            }, new HandlebarsTemplateEngine());

            post("/passenger", (request, response) -> {
                Map<String, Object> map = new HashMap<>();
                String name = request.queryParams("name");
                String amount = request.queryParams("amount");

                taxiDriver.createUser(name, Double.parseDouble(amount));
                response.redirect("/destination");
                return "";
            });

            post("/add_destination", (request, response) -> {
                String userId = request.queryParams("name");
                String locationId = request.queryParams("destination");
                System.out.println(userId);
                System.out.println(locationId);

                taxiDriver.setDestinationForUser(Integer.parseInt(userId), Integer.parseInt(locationId));
                response.redirect("/destination");
                return "";
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
