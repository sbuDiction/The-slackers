package taxi.destinations;

public enum Destinations {
    Guguluthu(20.00),
    Khayelitsha(21.00),
    Rondebosch(25.00),
    DurbanVile(15.00),
    Claremont(25.00),
    Tableview(12.00),
    Killarney(9.00),
    Sandown(13.00),
    Parddox(13.50),
    CenterPoint(20.00),
    Waterfront(30.00),
    Goodwood(17.00),
    Langa(17.00);

    private final double cost;

    Destinations(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
}
