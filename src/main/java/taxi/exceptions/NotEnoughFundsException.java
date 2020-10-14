package taxi.exceptions;

public class NotEnoughFundsException extends TaxiExceptions {

    public NotEnoughFundsException() {
        super("You don't have enough funds for the current destination.");
    }
}
