package main
import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

// Worker

func worker(name string, taskChan <-chan Ride, resultsList *SharedResultsList, logger *Logger, wg *sync.WaitGroup) {
	defer wg.Done()

	logger.Log(name, "Worker started.")
	tasksProcessed := 0

	for ride := range taskChan {
		logger.Log(name, fmt.Sprintf("Processing: Ride #(%d , %s )",
			ride.GetRideID(), ride.RideType()))

		startTime := time.Now()

		// 200-800ms delay
		delay := time.Duration(200+rand.Intn(600)) * time.Millisecond
		time.Sleep(delay)

		// Calculate fare with error handling (Polymorphic call)
		fare := ride.Fare()
		elapsed := time.Since(startTime).Milliseconds()

		// validate error: Error handling
		if fare <= 0 {
			errMsg := fmt.Sprintf("Invalid fare calculated: %.2f", fare)
			logger.Log(name, fmt.Sprintf("ERROR: Fare calculation failed for Ride #%d: %s",
				ride.GetRideID(), errMsg))
			ride.CancelRide()
			result := fmt.Sprintf("%s >> Ride #(%d , %s , %.2f , %s ) ->> %dms -> Error: %s",
				name, ride.GetRideID(), ride.RideType(), ride.Fare(), ride.GetStatusString(), elapsed, errMsg)
			resultsList.AddResult(result)
			continue
		}

		// Update ride status
		ride.StartRide()
		ride.CompleteRide()

		result := fmt.Sprintf("%s >> Ride #(%d , %s , $%.2f , %s ) -> %dms",
			name, ride.GetRideID(), ride.RideType(),
			fare, ride.GetStatusString(), elapsed)
		resultsList.AddResult(result)

		logger.Log(name, fmt.Sprintf("Completed: Ride #(%d , $%.2f ) -> %dms",
			ride.GetRideID(), fare, elapsed))
		tasksProcessed++
	}

	logger.Log(name, fmt.Sprintf("No more tasks. Shutting down. Processed: %d", tasksProcessed))
	logger.Log(name, "Worker terminated.")
}
