package taxi.exceptions;

public class TaxiOverLoadedException extends TaxiExceptions {

    public TaxiOverLoadedException() {
        super("The taxi is full");
    }
}
