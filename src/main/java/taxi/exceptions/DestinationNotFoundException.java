package taxi.exceptions;

public class DestinationNotFoundException extends TaxiExceptions {

    public DestinationNotFoundException() {
        super("The requested destination does not exist");
    }
}
