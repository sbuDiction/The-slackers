package taxi;

import taxi.calculations.AmountPaid;
import taxi.calculations.CalculateChange;
import org.jdbi.v3.core.Jdbi;
import taxi.destinations.Destination;
import taxi.person.Person;
import taxi.settings.SetGoalTracker;
import taxi.transactions.Transaction;
import taxi.transactions.TransactionCalculations;
import taxi.transactions.TransactionHistory;
import taxi.weeks.Days;
import taxi.weeks.DaysInWeeks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class TaxiDriverDBMethods {

    private Jdbi jdbi;
    List<Person> personList;
    int id;

    public TaxiDriverDBMethods(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createUser(String firstName, double amount) {
        String username = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);

        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO USER_NAMES (first_name, amount_paid) VALUES (?, ?)")
                .bind(0, username)
                .bind(1, amount)
                .execute());

        List<Person> people = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM USER_NAMES WHERE FIRST_NAME = ?")
                .bind(0, username)
                .mapToBean(Person.class)
                .list());
        setPersonList(username);
    }

    public void setDestinationForUser(int userId, int travelId) {
        try {
            getAmountPaid(userId);
            getDestinationCost(travelId);
            getPassengerData();
            addTotal(travelId);
            List<Transaction> personList = jdbi.withHandle((handle) -> handle
                    .createQuery("SELECT * FROM USER_TRANSACTIONS WHERE USER_REF = ?")
                    .bind(0, userId)
                    .mapToBean(Transaction.class)
                    .list());
            if (personList.size() == 0) {
                jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO USER_TRANSACTIONS (USER_REF, CHANGE, TRAVEL_REF) VALUES (?, ?, ?)")
                        .bind(0, userId)
                        .bind(1, 0) // zero for now
                        .bind(2, travelId)
                        .execute());
            } else {
                jdbi.withHandle(handle -> handle.createUpdate("UPDATE USER_TRANSACTIONS SET TRAVEL_REF = ? WHERE USER_REF = ?")
                        .bind(0, travelId)
                        .bind(1, userId)
                        .execute());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getMoneyIn() {
        jdbi.withHandle(handle -> handle.createQuery("select price from amount_paid join destinations on amount_paid.price_ref = destinations.id")
                .mapToBean(CalculateChange.class)
                .list());
    }

    public void addTotal(int priceId) {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
        String formattedDate = time.format(myFormatObj);
        System.out.println(formattedDate);

        List<TransactionHistory> transactionMaps = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM HISTORY WHERE PRICE_REF = ?")
                .bind(0, priceId))
                .mapToBean(TransactionHistory.class)
                .list();

        if (transactionMaps.size() == 0) {
            jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO HISTORY (PRICE_REF, DAYS_REF, TIME_STAMP) VALUES (?, ?, ?)")
                    .bind(0, priceId)
                    .bind(1, "")
                    .bind(2, formattedDate)
                    .execute());
        } else {
            jdbi.withHandle(handle -> handle.createUpdate("UPDATE HISTORY SET PRICE_REF = ? WHERE PRICE_REF = ?")
                    .bind(0, priceId));
        }
    }


    public List<Destination> getLocations() {
        List<Destination> destinationsList = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM DESTINATIONS")
                .mapToBean(Destination.class)
                .list());
        return destinationsList;
    }


    public List<AmountPaid> getAmountPaid(int userId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT AMOUNT_PAID FROM USER_NAMES WHERE ID = ?")
                .bind(0, userId)
                .mapToBean(AmountPaid.class)
                .list());
    }

    public List<AmountPaid> getDestinationCost(int locId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT PRICE FROM DESTINATIONS WHERE ID = ?")
                .bind(0, locId)
                .mapToBean(AmountPaid.class)
                .list());
    }

    public List<TransactionCalculations> getPassengerData() {
        return jdbi.withHandle(handle -> handle.createQuery("select first_name, amount_paid, change, location_name, price from user_names join user_transactions on user_names.id = user_transactions.user_ref join destinations on destinations.id = user_transactions.travel_ref").mapToBean(TransactionCalculations.class).list());
    }

    public void setGoalTracker(String day, double moneyIn, double setTarget, double targetMeasure) {
        List<SetGoalTracker> goalTrackerList = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM DRIVER_TRACKER WHERE DAY = ?").bind(0, day).mapToBean(SetGoalTracker.class).list());
        if (goalTrackerList.size() == 0) {
            jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO DRIVER_TRACKER (CURRENT_DAY, MONEY_IN, SET_TARGET, TARGET_MEASURE) VALUES (?, ?, ?, ?)")
                    .bind(0, day))
                    .bind(1, moneyIn)
                    .bind(2, setTarget)
                    .bind(3, targetMeasure)
                    .execute();
        } else {
            jdbi.withHandle(handle -> handle.createUpdate("UPDATE DRIVER_TRACKER SET CURRENT_DAY = ?, MONEY_IN = ?, SET_TARGET = ?, TARGET_MEASURE = ? WHERE DAY = ?")
                    .bind(0, day)
                    .bind(1, moneyIn)
                    .bind(2, setTarget)
                    .bind(3, setTarget)
                    .bind(4, day)
                    .execute());
        }
    }

    public List<Days> getDays() {
        List<Days> daysList = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM DAYS")
                .mapToBean(Days.class)
                .list());
        return daysList;
    }

    public List<Person> setPersonList(String name) {
        return this.personList = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM USER_NAMES WHERE FIRST_NAME = ?")
                .bind(0, name)
                .mapToBean(Person.class)
                .list());

    }

    public List<Person> getPersonList() {
        System.out.println(personList.size());
        return personList;
    }
}
