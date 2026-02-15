
#include "ride.h"

Ride::Ride(int id, string pickup, string dropoff, double dist)
    : rideID(id), pickupLocation(pickup), dropoffLocation(dropoff),
      distance(dist), rideFare(0.0) {}

Ride::~Ride() {}

double Ride::fare() {
    rideFare = distance * 1.50;
    return rideFare;
}


void Ride::rideDetails() {
    cout << "ID: #" << rideID << " [" << getRideType() << "]" << endl;
    cout << "Route: " << pickupLocation << " -> " << dropoffLocation << endl;
    cout << "Stats: " << fixed << setprecision(1) << distance << " mi | $"
         << setprecision(2) << fare() << endl;
}

string Ride::getRideType() const { return "Base"; }

int Ride::getRideID() const { return rideID; }
string Ride::getPickup() const { return pickupLocation; }
string Ride::getDropoff() const { return dropoffLocation; }
double Ride::getDistance() const { return distance; }
