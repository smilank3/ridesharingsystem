package ridesharing;

import java.util.ArrayList;
import java.util.List;

class Driver {
    private int driverID;
    private String name;
    private double rating;
    private List<Ride> assignedRides; 

    public Driver(int id, String driverName, double driverRating) {
        this.driverID = id;
        this.name = driverName;
        this.rating = driverRating;
        this.assignedRides = new ArrayList<>();
    }

    public void addRide(Ride ride) { 
        assignedRides.add(ride);
        System.out.println("  [+] Ride #" + ride.getRideID()
                + " (" + ride.getRideType() + ") assigned to driver " + name);
    }

    public void getDriverInfo() {
        System.out.println();
     
        System.out.println("Driver Info:");
 
        System.out.println("  Driver ID    : " + driverID);
        System.out.println("  Name         : " + name);
        System.out.printf("  Rating       : %.1f / 5.0%n", rating);
        System.out.println("  Total Rides  : " + assignedRides.size());

        if (!assignedRides.isEmpty()) {
            double totalEarnings = 0.0;
            System.out.println();
            System.out.println(" Assigned Rides");
            for (Ride ride : assignedRides) {
                ride.rideDetails();                  
                totalEarnings += ride.fare();         
            }
            System.out.printf("  Total Earnings : $%.2f%n", totalEarnings);
        }
     
    }


    public int getDriverID() { return driverID; }
    public String getName() { return name; }
    public double getRating() { return rating; }
    public int getTotalRides() { return assignedRides.size(); }
}
