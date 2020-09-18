package taxi;

import taxi.calculations.AmountPaid;
import org.jdbi.v3.core.Jdbi;
import taxi.destinations.Destination;
import taxi.graphs.Xaxis;
import taxi.graphs.Yaxis;
import taxi.person.Person;
import taxi.settings.SetGoalTracker;
import taxi.transactions.PassengersTotal;
import taxi.transactions.Transaction;
import taxi.transactions.TransactionCalculations;
import taxi.transactions.TransactionHistory;
import taxi.weeks.Days;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


public class TaxiDriverDBMethods {

    private Jdbi jdbi;
    List<Person> personList;
    int id;
    String message;
    double change;

    public TaxiDriverDBMethods(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Person> createUser(String firstName, double amount) {
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
        return people;
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
//                setMessage("Payment");
            } else {
                jdbi.withHandle(handle -> handle.createUpdate("UPDATE USER_TRANSACTIONS SET TRAVEL_REF = ? WHERE USER_REF = ?")
                        .bind(0, travelId)
                        .bind(1, userId)
                        .execute());

            }
            setMessage("Payment");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void getMoneyIn() {
//        jdbi.withHandle(handle -> handle.createQuery("select price from amount_paid join destinations on amount_paid.price_ref = destinations.id")
//                .mapToBean(CalculateChange.class)
//                .list());
//    }

    public void addTotal(int priceId) {
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time.getDayOfWeek());
        getPassengerTotal();

//        List<TransactionHistory> transactionMaps = jdbi.withHandle(handle -> handle.createQuery("SELECT DAYS_REF FROM HISTORY WHERE DAYS_REF = ?")
//                .bind(0, getCurrentDayId())
//                .mapToBean(TransactionHistory.class)
//                .list());

//        if (transactionMaps.size() == 0) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO HISTORY (PRICE, DAYS_REF, TIME_STAMP) VALUES (?, ?, ?)")
                .bind(0, getDestinationCost(priceId))
                .bind(1, getCurrentDayId())
                .bind(2, time.getDayOfWeek())
                .execute());


//        } else {
//            jdbi.withHandle(handle -> handle.createUpdate("UPDATE HISTORY SET PRICE =+? WHERE DAYS_REF = ?")
//                    .bind(0, getDestinationCost(priceId))
//                    .bind(1, getCurrentDayId())
//                    .execute());
//        }
    }


    public List<Destination> getLocations() {
        List<Destination> destinationsList = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM DESTINATIONS ORDER BY PRICE LIMIT 5")
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

    public double getDestinationCost(int locId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT PRICE FROM DESTINATIONS WHERE ID = ?")
                .bind(0, locId)
                .mapToBean(AmountPaid.class)
                .findOnly()
                .getPrice());

    }

    public List<TransactionCalculations> getPassengerData() {
        return jdbi.withHandle(handle -> handle.createQuery("select first_name, amount_paid, change, location_name, price from user_names join user_transactions on user_names.id = user_transactions.user_ref join destinations on destinations.id = user_transactions.travel_ref limit 10").mapToBean(TransactionCalculations.class).list());
    }


    public void getPassengerChange(int id) {
        setChange(jdbi.withHandle(handle -> handle.createQuery("select first_name, amount_paid, change, location_name, price from user_names join user_transactions on user_names.id = user_transactions.user_ref join destinations on destinations.id = user_transactions.travel_ref where user_ref = ?").bind(0, id).mapToBean(TransactionCalculations.class).list().get(0).getChange()));
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getChange() {
        return change;
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

    public int getCurrentDayId() {
        LocalDateTime time = LocalDateTime.now();
        List<Days> result = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM DAYS WHERE days_in_a_week = ?")
                .bind(0, time.getDayOfWeek())
                .mapToBean(Days.class)
                .list());
        return result.get(0).getId();
    }

    public List<PassengersTotal> getPassengerTotal() {
        List<PassengersTotal> passengersTotals = jdbi.withHandle(handle -> handle.createQuery("select price,days_in_a_week from days join history on days.id = history.days_ref;").mapToBean(PassengersTotal.class).list());

        return passengersTotals;
    }

    public List<TransactionHistory> buildGraph() {
        List<TransactionHistory> histories = jdbi.withHandle(handle -> handle.createQuery("select price,days from days join history on days.id = history.days_ref")
                .mapToBean(TransactionHistory.class)
                .list());
        return histories;
    }

    public List<Xaxis> getXaxis() {
        return jdbi.withHandle(handle -> handle.createQuery("select distinct days_in_a_week from days join history on days.id = history.days_ref")
                .mapToBean(Xaxis.class)
                .list());
    }

    public List<Yaxis> getYaxis(int daysId) {
        System.out.println(daysId);
        return jdbi.withHandle(handle -> handle.createQuery("select sum(price) from days join history on days.id = history.days_ref where days_ref = ?")
                .bind(0, daysId)
                .mapToBean(Yaxis.class)
                .list());
    }

    public String getMessage() {
        System.out.println(message);
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}