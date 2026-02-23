package ridesharing;

enum RideStatus {
    REQUESTED("Requested"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String label;
    RideStatus(String label) { this.label = label; }
    @Override public String toString() { return label; }
}

abstract class Ride {
    protected int rideID;
    protected String pickupLocation;
    protected String dropoffLocation;
    protected double distance;
    protected double rideFare;
    protected RideStatus status;

    public Ride(int id, String pickup, String dropoff, double dist) {
        this.rideID = id;
        this.pickupLocation = pickup;
        this.dropoffLocation = dropoff;
        this.distance = dist;
        this.rideFare = 0.0;
        this.status = RideStatus.REQUESTED;
    }

    // overridden by subclasses
    public abstract double fare();
    public abstract void rideDetails();
    public abstract String getRideType();

   
    public void startRide() {
        if (status == RideStatus.REQUESTED) {
            status = RideStatus.IN_PROGRESS;
            System.out.println("  >> Ride #" + rideID + " is now IN PROGRESS.");
        } else {
            System.out.println("  >> Ride #" + rideID + " cannot be started. Current status: " + status);
        }
    }

    public void completeRide() {
        if (status == RideStatus.IN_PROGRESS) {
            status = RideStatus.COMPLETED;
            System.out.println("  >> Ride #" + rideID + " is now COMPLETED.");
        } else {
            System.out.println("  >> Ride #" + rideID + " cannot be completed. Current status: " + status);
        }
    }

    public void cancelRide() {
        if (status == RideStatus.REQUESTED || status == RideStatus.IN_PROGRESS) {
            status = RideStatus.CANCELLED;
            System.out.println("  >> Ride #" + rideID + " has been CANCELLED.");
        } else {
            System.out.println("  >> Ride #" + rideID + " cannot be cancelled. Current status: " + status);
        }
    }

 
    public RideStatus getStatus() { return status; }
    public String getStatusString() { return status.toString(); }
    public int getRideID() { return rideID; }
    public String getPickup() { return pickupLocation; }
    public String getDropoff() { return dropoffLocation; }
    public double getDistance() { return distance; }
}
