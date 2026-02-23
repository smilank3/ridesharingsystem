package main

import "fmt"

type Driver struct {
	driverID      int
	name          string
	rating        float64
	assignedRides []Ride
}

func NewDriver(id int, name string, rating float64) *Driver {
	return &Driver{
		driverID:      id,
		name:          name,
		rating:        rating,
		assignedRides: []Ride{},
	}
}

func (d *Driver) AddRide(ride Ride) {
	d.assignedRides = append(d.assignedRides, ride)
	fmt.Printf("  Ride #(%d, (%s)) assigned to driver %s\n",
		ride.GetRideID(), ride.RideType(), d.name)
}

func (d *Driver) GetDriverInfo() {
	fmt.Println()

	fmt.Println("Driver Info:")

	fmt.Printf("  Driver ID    : %d\n", d.driverID)
	fmt.Printf("  Name         : %s\n", d.name)
	fmt.Printf("  Rating       : %.1f / 5.0\n", d.rating)
	fmt.Printf("  Total Rides  : %d\n", len(d.assignedRides))

	if len(d.assignedRides) > 0 {
		totalEarnings := 0.0
		fmt.Println()
		fmt.Println("Assigned Ride")
		for _, ride := range d.assignedRides {
			ride.RideDetails()
			totalEarnings += ride.Fare()
		}
		fmt.Printf("  Total Earnings : $%.2f\n", totalEarnings)
	}

}
