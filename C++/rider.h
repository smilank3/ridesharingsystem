#ifndef RIDER_H
#define RIDER_H

#include <vector>
#include <memory>
#include "ride.h"


// Rider Class

class Rider {
private:
    int riderID;
    string name;
    vector<shared_ptr<Ride>> requestedRides;

public:
    Rider(int id, string riderName);

    void requestRide(shared_ptr<Ride> ride);
    void viewRides();


    int getRiderID() const;
    string getName() const;
};

#endif
