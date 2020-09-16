package taxi;

import org.jdbi.v3.core.Jdbi;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {

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
            TaxiDriverDBMethods taxiDriver = new TaxiDriverDBMethods(getJdbiDatabaseConnection());

            get("/", (req, res) -> {
                Map<String, Object> map = new HashMap<>();
                return new ModelAndView(map, "index.hbs");
            }, new HandlebarsTemplateEngine());


            post("/passenger", (request, response) -> {
                String name = request.queryParams("name");
                String amount = request.queryParams("amount");

                taxiDriver.createUser(name, Double.parseDouble(amount));
                response.redirect("/");
                return "";
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
