package main

import (
	"fmt"
	"sync"
	"time"
)

type RideStatus int

const (
	REQUESTED RideStatus = iota
	IN_PROGRESS
	COMPLETED
	CANCELLED
)

func (s RideStatus) String() string {
	switch s {
	case REQUESTED:
		return "Requested"
	case IN_PROGRESS:
		return "In Progress"
	case COMPLETED:
		return "Completed"
	case CANCELLED:
		return "Cancelled"
	default:
		return "Unknown"
	}
}

type SharedResultsList struct {
	mu      sync.Mutex
	results []string
}

func NewSharedResultsList() *SharedResultsList {
	return &SharedResultsList{results: []string{}}
}

func (s *SharedResultsList) AddResult(result string) {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.results = append(s.results, result)
}

func (s *SharedResultsList) GetResults() []string {
	s.mu.Lock()
	defer s.mu.Unlock()
	copied := make([]string, len(s.results))
	copy(copied, s.results)
	return copied
}

func main() {

	// initialize logger
	logger := NewLogger("ride_system_log.txt")
	defer logger.Close() // Ensure logger is closed with defer

	logger.Log("MAIN", " Demo ")

	// Create shared resources
	// Channel  as  task queue
	taskChan := make(chan Ride, 20)
	resultsList := NewSharedResultsList()

	// Number of worker goroutines
	numWorkers := 3
	var wg sync.WaitGroup

	// Start worker goroutines
	logger.Log("MAIN", fmt.Sprintf("Starting %d worker goroutines...", numWorkers))

	for i := 1; i <= numWorkers; i++ {
		wg.Add(1)
		go worker(fmt.Sprintf("Worker-%d", i), taskChan, resultsList, logger, &wg)
	}

	// Define concurrent ride tasks
	logger.Log("MAIN", "Adding ride tasks to queue...")

	concurrentRides := []Ride{
		NewStandardRide(1, "A", "B", 10.0),
		NewPremiumRide(2, "C", "D", 15.5),
		NewStandardRide(3, "E", "F", 4.2),
		NewPremiumRide(4, "A", "B", 20.0),
		NewStandardRide(5, "C", "D", 6.8),
		NewStandardRide(6, "E", "F", 3.0),
		NewPremiumRide(7, "A", "B", 5.5),
		NewPremiumRide(8, "C", "D", 8.5),
		NewStandardRide(9, "E", "F", 2.1),
		NewPremiumRide(10, "A", "B", 7.3),
		NewPremiumRide(11, "C", "D", 4.0),
		NewStandardRide(12, "E", "F", 0),
	}

	// Add tasks to channel
	for _, ride := range concurrentRides {
		logger.Log("QUEUE", fmt.Sprintf("Added: Ride #(%d , %s )", ride.GetRideID(), ride.RideType()))
		taskChan <- ride
		time.Sleep(50 * time.Millisecond)
	}

	logger.Log("MAIN", fmt.Sprintf("All %d tasks added to queue.", len(concurrentRides)))

	// Close channel to signal workers no more tasks (shutdown)
	close(taskChan)

	// Wait for all workers to finish
	wg.Wait()

	// Display results
	fmt.Println()

	fmt.Println(" processing result:")

	results := resultsList.GetResults()
	for _, result := range results {
		fmt.Println("  " + result)
	}

	fmt.Println()
	fmt.Printf("  Total Rides Processed : %d/%d\n", len(results), len(concurrentRides))

	// Write  to file with error handling
	if err := writeResultsToFile("ride_results.txt", results); err != nil {
		logger.Log("MAIN", "ERROR: Could not write results file: "+err.Error())
	} else {
		logger.Log("MAIN", "Results written to ride_results.txt")
	}

	fmt.Println()

	fmt.Println("--------End -------------------")

}
