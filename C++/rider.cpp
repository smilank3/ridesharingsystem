
#include "rider.h"

Rider::Rider(int id, string riderName)
    : riderID(id), name(riderName) {}

void Rider::requestRide(shared_ptr<Ride> ride) {
    requestedRides.push_back(ride);
    cout << "  Rider " << name << " requested Ride #"
         << ride->getRideID() << " (" << ride->getRideType() << ")" << endl;
}

void Rider::viewRides() {
    cout << endl;

    cout << "Rider history:" << endl;

    cout << "  Rider ID     : " << riderID << endl;
    cout << "  Name         : " << name << endl;
    cout << "  Total Rides  : " << requestedRides.size() << endl;

    if (!requestedRides.empty()) {
        double totalSpent = 0.0;
        cout << endl << " Requested Rides" << endl;
        for (auto& ride : requestedRides) {
            ride->rideDetails();
            totalSpent += ride->fare();
        }
        cout << "  Total Spent    : $" << fixed << setprecision(2)
             << totalSpent << endl;
    }
    cout << endl;
}

int Rider::getRiderID() const { return riderID; }
string Rider::getName() const { return name; }
