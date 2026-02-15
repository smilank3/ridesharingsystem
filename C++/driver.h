
#ifndef DRIVER_H
#define DRIVER_H

#include <vector>
#include <memory>
#include "Ride.h"


// Driver Class

class Driver {
private:
    int driverID;
    string name;
    double rating;
    vector<shared_ptr<Ride>> assignedRides;

public:
    Driver(int id, string driverName, double driverRating);

    void addRide(shared_ptr<Ride> ride);
    void getDriverInfo();

    // Getters
    int getDriverID() const;
    string getName() const;
    double getRating() const;
    int getTotalRides() const;
};

#endif
