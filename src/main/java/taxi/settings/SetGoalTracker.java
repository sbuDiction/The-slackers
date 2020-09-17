package taxi.settings;

public class SetGoalTracker {

    String day;
    double moneyIn;
    double setTarget;
    double targetMeasure;

    public void setDay(String day) {
        this.day = day;
    }

    public void setMoneyIn(double moneyIn) {
        this.moneyIn = moneyIn;
    }

    public void setSetTarget(double setTarget) {
        this.setTarget = setTarget;
    }

    public void setTargetMeasure(double targetMeasure) {
        this.targetMeasure = targetMeasure;
    }

    public String getDay() {
        return day;
    }

    public double getMoneyIn() {
        return moneyIn;
    }

    public double getSetTarget() {
        return setTarget;
    }

    public double getTargetMeasure() {
        return targetMeasure;
    }
}
