package Taxi;

public class Tracker extends Transaction {
    double set_target;
    double target_measure;
    double money_in = 0;
    boolean hasPaid = true;

    public double setTarget() {
        return set_target;
    }

    public double money_in() {
        return money_in;
    }

    public double getMoney_in() {
        if (hasPaid) {
            money_in += price;
        }
        return money_in;
    }

    public double getTarget_measure() {
        target_measure = (money_in / set_target) * 100;
        return target_measure;
    }
}
