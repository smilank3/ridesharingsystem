package main

import "fmt"

// Rider

type Rider struct {
	riderID        int
	name           string
	requestedRides []Ride 
}

func NewRider(id int, name string) *Rider {
	return &Rider{
		riderID:        id,
		name:           name,
		requestedRides: []Ride{},
	}
}

func (r *Rider) RequestRide(ride Ride) {
	r.requestedRides = append(r.requestedRides, ride)
	fmt.Printf("  [+] Rider %s requested Ride #%d (%s)\n",
		r.name, ride.GetRideID(), ride.RideType())
}

func (r *Rider) ViewRides() {
	fmt.Println()

	fmt.Println("Rider history")

	fmt.Printf("  Rider ID     : %d\n", r.riderID)
	fmt.Printf("  Name         : %s\n", r.name)
	fmt.Printf("  Total Rides  : %d\n", len(r.requestedRides))

	if len(r.requestedRides) > 0 {
		totalSpent := 0.0
		fmt.Println()
		fmt.Println(" Requested Rides ")
		for _, ride := range r.requestedRides {
			ride.RideDetails()
			totalSpent += ride.Fare()
		}
		fmt.Printf("  Total Spent    : $%.2f\n", totalSpent)
	}

}
