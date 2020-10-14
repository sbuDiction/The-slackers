package taxi.routing;

import taxi.api.TaxiServicesApi;
import taxi.tools.ObjectToJson;

import static spark.Spark.*;

public class Router {
    TaxiServicesApi api = new TaxiServicesApi();

    public Router() {
        get("/", (request, response) -> {
            response.redirect("index.html");
            return null;
        });

        get("/api/v1/destinations", api.destinationDropDown());
        post("/book", api.bookTaxi());
    }
}
