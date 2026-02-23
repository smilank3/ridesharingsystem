package ridesharing;

class StandardRide extends Ride {
    public StandardRide(int id, String pickup, String dropoff, double dist) {
        super(id, pickup, dropoff, dist);
    }

    @Override
    public double fare() {
        rideFare = distance * 1.50;
        return rideFare;
    }

    @Override
    public void rideDetails() {
        System.out.println("--------------------------------------------");        System.out.println("  Ride ID      : " + rideID);
        System.out.println("  Type         : Standard Ride");
        System.out.println("  Status       : " + getStatusString());
        System.out.println("  Pickup       : " + pickupLocation);
        System.out.println("  Dropoff      : " + dropoffLocation);
        System.out.printf("  Distance     : %.1f miles%n", distance);
        System.out.printf("  Fare         : $%.2f%n", fare());
       
    }

    @Override
    public String getRideType() { return "Standard"; }
}