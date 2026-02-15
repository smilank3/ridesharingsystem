
#include "standardRide.h"

StandardRide::StandardRide(int id, string pickup, string dropoff, double dist)
    : Ride(id, pickup, dropoff, dist) {}

double StandardRide::fare() {
    rideFare = distance * 2.50; // $2.50 per mile
    return rideFare;
}

void StandardRide::rideDetails() {
   cout << "ID: #" << rideID << " [" << getRideType() << "]" << endl;
    cout << "Route: " << pickupLocation << " -> " << dropoffLocation << endl;
    cout << "Stats: " << fixed << setprecision(1) << distance << " mi | $"
         << setprecision(2) << fare() << endl;
}

string StandardRide::getRideType() const { return "Standard"; }
