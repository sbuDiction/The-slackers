package taxi;

import taxi.calculations.CalculateChange;
import org.jdbi.v3.core.Jdbi;
import taxi.transactions.Transaction;
import taxi.transactions.TransactionMap;

import java.util.List;


public class TaxiDriverDBMethods {

    private Jdbi jdbi;

    public TaxiDriverDBMethods(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createUser(String firstName, double amount) {
        try {
            String username = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);

            jdbi.useHandle(handle -> {
                handle.createUpdate("INSERT INTO USER_NAMES (first_name, amount_paid) VALUES (?, ?)")
                        .bind(0, username)
                        .bind(1, amount)
                        .execute();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDestinationForUser(int userId, int travelId) {
        try {
            List<Transaction> personList = jdbi.withHandle((handle) -> handle
                    .createQuery("SELECT * FROM USER_TRANSACTIONS WHERE USER_REF = ?")
                    .bind(0, userId)
                    .mapToBean(Transaction.class)
                    .list());

            if (personList.size() == 0) {
                jdbi.withHandle((handle) -> handle.createQuery("SELECT AMOUNT_PAID FROM USER_NAMES WHERE ID = ?")
                        .bind(0, userId)
                        .mapToBean(CalculateChange.class)
                        .list());
                jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM DESTINATIONS WHERE ID = ?")
                        .bind(0, travelId)
                        .mapToBean(CalculateChange.class)
                        .list());
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
            addTotal(travelId);
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
        List<TransactionMap> transactionMaps = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM AMOUNT_PAID WHERE PRICE_REF = ?")
                .bind(0, priceId))
                .mapToBean(TransactionMap.class)
                .list();
        if (transactionMaps.size() == 0) {
            jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO AMOUNT_PAID (PRICE_REF) VALUES (?)")
                    .bind(0, priceId)
                    .execute());
        } else {
            jdbi.withHandle(handle -> handle.createUpdate("UPDATE AMOUNT_PAID SET PRICE_REF = ? WHERE PRICE_REF = ?").bind(0, priceId));
        }

    }
}
