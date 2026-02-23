package main

import "fmt"

type Ride interface {
	Fare() float64
	RideDetails()
	RideType() string
	GetRideID() int
	GetPickup() string
	GetDropoff() string
	GetDistance() float64
	GetStatusString() string
	StartRide()
	CompleteRide()
	CancelRide()
}

type BaseRide struct {
	rideID          int
	pickupLocation  string
	dropoffLocation string
	distance        float64
	rideFare        float64
	status          RideStatus
}

func NewBaseRide(id int, pickup, dropoff string, dist float64) BaseRide {
	return BaseRide{
		rideID:          id,
		pickupLocation:  pickup,
		dropoffLocation: dropoff,
		distance:        dist,
		rideFare:        0.0,
		status:          REQUESTED,
	}
}

func (r *BaseRide) GetRideID() int          { return r.rideID }
func (r *BaseRide) GetPickup() string       { return r.pickupLocation }
func (r *BaseRide) GetDropoff() string      { return r.dropoffLocation }
func (r *BaseRide) GetDistance() float64    { return r.distance }
func (r *BaseRide) GetStatusString() string { return r.status.String() }

func (r *BaseRide) StartRide() {
	if r.status == REQUESTED {
		r.status = IN_PROGRESS
		fmt.Printf("  >> Ride #%d is now IN PROGRESS.\n", r.rideID)
	} else {
		fmt.Printf("  >> Ride #%d cannot be started. Current status: %s\n", r.rideID, r.status)
	}
}

func (r *BaseRide) CompleteRide() {
	if r.status == IN_PROGRESS {
		r.status = COMPLETED
		fmt.Printf("  >> Ride #%d is now COMPLETED.\n", r.rideID)
	} else {
		fmt.Printf("  >> Ride #%d cannot be completed. Current status: %s\n", r.rideID, r.status)
	}
}

func (r *BaseRide) CancelRide() {
	if r.status == REQUESTED || r.status == IN_PROGRESS {
		r.status = CANCELLED
		fmt.Printf("  >> Ride #%d has been CANCELLED.\n", r.rideID)
	} else {
		fmt.Printf("  >> Ride #%d cannot be cancelled. Current status: %s\n", r.rideID, r.status)
	}
}

// StandardRide (Inheritance via embedding + Polymorphism)

type StandardRide struct {
	BaseRide
}

func NewStandardRide(id int, pickup, dropoff string, dist float64) *StandardRide {
	return &StandardRide{BaseRide: NewBaseRide(id, pickup, dropoff, dist)}
}

func (r *StandardRide) Fare() float64 {
	r.rideFare = r.distance * 1.50
	return r.rideFare
}

func (r *StandardRide) RideType() string { return "Standard" }

func (r *StandardRide) RideDetails() {

	fmt.Printf("  Ride ID      : %d\n", r.rideID)
	fmt.Printf("  Type         : Standard Ride\n")
	fmt.Printf("  Status       : %s\n", r.status)
	fmt.Printf("  Pickup       : %s\n", r.pickupLocation)
	fmt.Printf("  Dropoff      : %s\n", r.dropoffLocation)
	fmt.Printf("  Distance     : %.1f miles\n", r.distance)
	fmt.Printf("  Fare         : $%.2f\n", r.Fare())

}

// PremiumRide (Inheritance via embedding + Polymorphism)

type PremiumRide struct {
	BaseRide
}

func NewPremiumRide(id int, pickup, dropoff string, dist float64) *PremiumRide {
	return &PremiumRide{BaseRide: NewBaseRide(id, pickup, dropoff, dist)}
}

func (r *PremiumRide) Fare() float64 {
	r.rideFare = r.distance*3.00 + 5.00
	return r.rideFare
}

func (r *PremiumRide) RideType() string { return "Premium" }

func (r *PremiumRide) RideDetails() {

	fmt.Printf("  Ride ID      : %d\n", r.rideID)
	fmt.Printf("  Type         : Premium Ride\n")
	fmt.Printf("  Status       : %s\n", r.status)
	fmt.Printf("  Pickup       : %s\n", r.pickupLocation)
	fmt.Printf("  Dropoff      : %s\n", r.dropoffLocation)
	fmt.Printf("  Distance     : %.1f miles\n", r.distance)
	fmt.Printf("  Fare         : $%.2f\n", r.Fare())

}
