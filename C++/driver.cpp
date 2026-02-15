
#include "driver.h"

Driver::Driver(int id, string driverName, double driverRating)
    : driverID(id), name(driverName), rating(driverRating) {}

void Driver::addRide(shared_ptr<Ride> ride) {
    assignedRides.push_back(ride);
    cout << "Ride #" << ride->getRideID()
         << " (" << ride->getRideType() << ") assigned to driver " << name << endl;
}

void Driver::getDriverInfo() {
    cout << endl;

    cout << "Driver Info." << endl;

    cout << "  Driver ID    : " << driverID << endl;
    cout << "  Name         : " << name << endl;
    cout << "  Rating       : " << fixed << setprecision(1) << rating << " / 5.0" << endl;
    cout << "  Total Rides  : " << assignedRides.size() << endl;

    if (!assignedRides.empty()) {
        double totalEarnings = 0.0;
        cout << endl << "Assigned Rides:" << endl;
        for (auto& ride : assignedRides) {
            ride->rideDetails();
            totalEarnings += ride->fare();
        }
        cout << "  Total Earnings : $" << fixed << setprecision(2)
             << totalEarnings << endl;
    }
    cout << endl;
}

int Driver::getDriverID() const { return driverID; }
string Driver::getName() const { return name; }
double Driver::getRating() const { return rating; }
int Driver::getTotalRides() const { return assignedRides.size(); }
