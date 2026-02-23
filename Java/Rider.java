package ridesharing;

import java.util.ArrayList;
import java.util.List;

class Rider {
    private int riderID;
    private String name;
    private List<Ride> requestedRides;

    public Rider(int id, String riderName) {
        this.riderID = id;
        this.name = riderName;
        this.requestedRides = new ArrayList<>();
    }

    public void requestRide(Ride ride) { 
        requestedRides.add(ride);
        System.out.println("  [+] Rider " + name + " requested Ride #"
                + ride.getRideID() + " (" + ride.getRideType() + ")");
    }

    public void viewRides() {
        System.out.println();
 
        System.out.println("Rider history");

        System.out.println("  Rider ID     : " + riderID);
        System.out.println("  Name         : " + name);
        System.out.println("  Total Rides  : " + requestedRides.size());

        if (!requestedRides.isEmpty()) {
            double totalSpent = 0.0;
            System.out.println();
            System.out.println("  --- Requested Rides ---");
            for (Ride ride : requestedRides) {
                ride.rideDetails();               
                totalSpent += ride.fare();         
            }
            System.out.printf("  Total Spent    : $%.2f%n", totalSpent);
        }
       
    }

   
    public int getRiderID() { return riderID; }
    public String getName() { return name; }
}