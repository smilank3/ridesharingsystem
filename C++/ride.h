#ifndef RIDE_H
#define RIDE_H

#include <string>
#include <iostream>
#include <iomanip>

using namespace std;


// Base Class: Ride

class Ride {
protected:
    int rideID;
    string pickupLocation;
    string dropoffLocation;
    double distance;
    double rideFare;

public:
    Ride(int id, string pickup, string dropoff, double dist);
    virtual ~Ride();

    // Virtual methods — overridden by subclasses
    virtual double fare();
    virtual void rideDetails();

    virtual string getRideType() const;

    // Getters
    int getRideID() const;
    string getPickup() const;
    string getDropoff() const;
    double getDistance() const;
};

#endif
