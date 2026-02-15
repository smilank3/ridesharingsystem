#include <iostream>
#include <vector>
#include <memory>
#include <iomanip>

#include "Ride.h"
#include "StandardRide.h"
#include "PremiumRide.h"

#include "Driver.h"
#include "Rider.h"

using namespace std;

int main() {
  // Create rides of different types
    shared_ptr<Ride> ride1 = make_shared<StandardRide>(101, "Home", "Airport", 12.5);
    shared_ptr<Ride> ride2 = make_shared<PremiumRide>(102, "Airport ", "Hotel", 15.0);
    shared_ptr<Ride> ride3 = make_shared<StandardRide>(103, "Hotel", "University", 8.3);
    shared_ptr<Ride> ride4 = make_shared<PremiumRide>(104, "University", "Airport", 67.0);

    // list of rides

    vector<shared_ptr<Ride>> allRides = {ride1, ride2, ride3, ride4};

    for (auto& ride : allRides) {

        ride->rideDetails();
    }
// Fare comparison table
    cout << endl;

    cout << " FARE COMPARISON " << endl;

    cout << "  " << left << setw(10) << "Ride ID"
         << setw(14) << "Type"
         << setw(14) << "Distance"
         << setw(12) << "Fare" << endl;
    cout << "  " << string(50, '-') << endl;

    for (auto& ride : allRides) {
        cout << "  " << left << setw(10) << ride->getRideID()
             << setw(14) << ride->getRideType()
             << fixed << setprecision(1) << setw(14) << ride->getDistance()
             << "$" << fixed << setprecision(2) << ride->fare() << endl;
    }

    // Create Driver and assign rides
    cout << endl;
    cout << "  Assigning rides " << endl;

    Driver driver1(201, "John Johnson", 4.8);
    driver1.addRide(ride1);
    driver1.addRide(ride2);

    driver1.getDriverInfo();

    //  Create Rider and request rides
    cout << endl;

    cout << " Requesting rides " << endl;


    Rider rider1(301, "John Smith");
    rider1.requestRide(ride1);
    rider1.requestRide(ride3);
    rider1.requestRide(ride4);
    rider1.viewRides();

    //  Second driver and rider ---
    cout << endl;

    cout << " Additional driver" << endl;

    Driver driver2(202, "Carol S", 4.5);
    driver2.addRide(ride3);
    driver2.addRide(ride4);
    driver2.getDriverInfo();

    Rider rider2(302, "Jason M");
    rider2.requestRide(ride2);

    rider2.viewRides();


    return 0;
}
