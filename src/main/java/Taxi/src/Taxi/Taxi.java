package Taxi;

public class Taxi {
    public static void main(String[] args) {
        Transaction him = new Transaction();

        System.out.println(him.calculateChange(50, 16));
    }
}
