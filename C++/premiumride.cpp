
#include "premiumRide.h"

PremiumRide::PremiumRide(int id, string pickup, string dropoff, double dist)
    : Ride(id, pickup, dropoff, dist) {}

double PremiumRide::fare() {
    rideFare = distance * 5.00 + 5.00; // $5.00/mile + $5.00 extra charge
    return rideFare;
}

void PremiumRide::rideDetails() {
     cout << "ID: #" << rideID << " [" << getRideType() << "]" << endl;
    cout << "Route: " << pickupLocation << " -> " << dropoffLocation << endl;
    cout << "Stats: " << fixed << setprecision(1) << distance << " mi | $"
         << setprecision(2) << fare() << endl;
}

string PremiumRide::getRideType() const { return "Premium"; }
