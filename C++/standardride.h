
#ifndef STANDARDRIDE_H
#define STANDARDRIDE_H

#include "ride.h"


// Derived Class: StandardRide

class StandardRide : public Ride {
public:
    StandardRide(int id, string pickup, string dropoff, double dist);

    double fare() override;
    void rideDetails() override;
    string getRideType() const override;
};

#endif
