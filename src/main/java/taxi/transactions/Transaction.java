package taxi.transactions;


public class Transaction {
    int user_ref;
    double change;
    int travel_ref;

    public Transaction() {
        super();
    }


    public void setChange(double change) {
        this.change = change;
    }



    public double getChange() {
        return change;
    }

    public void setUser_ref(int user_ref) {
        this.user_ref = user_ref;
    }

    public void setTravel_ref(int travel_ref) {
        System.out.println(travel_ref);
        this.travel_ref = travel_ref;
    }

    public int getTravel_ref() {
        return travel_ref;
    }

    public int getUser_ref() {
        return user_ref;
    }
}
