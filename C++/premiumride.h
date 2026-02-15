
#ifndef PREMIUMRIDE_H
#define PREMIUMRIDE_H

#include "ride.h"


// Derived Class: PremiumRide

class PremiumRide : public Ride {
public:
    PremiumRide(int id, string pickup, string dropoff, double dist);

    double fare() override;
    void rideDetails() override;
    string getRideType() const override;
};

#endif
