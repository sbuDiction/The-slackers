package taxi.person;

public class Person {
    int id;
    String firstName;
    double amountPaid;

    public Person() {
    }


    public Person(String firstName, double amountPaid) {
        this.firstName = firstName;
        this.amountPaid = amountPaid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

}
