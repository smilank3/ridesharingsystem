package ridesharing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		 
	        Logger.log("MAIN", " Demo ");

	        // Create shared resources
	        SharedTaskQueue taskQueue = new SharedTaskQueue();
	        SharedResultsList resultsList = new SharedResultsList();

	        // Number of worker threads
	        int numWorkers = 3;

	        // Create and start worker threads using ExecutorService
	        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);
	        Logger.log("MAIN", "Starting " + numWorkers + " worker threads...");

	        for (int i = 1; i <= numWorkers; i++) {
	            executor.submit(new WorkerThread("Worker-" + i, taskQueue, resultsList));
	        }

	        // Add ride tasks to the shared queue
	        Logger.log("MAIN", "Adding ride tasks to queue...");

	        Ride[] concurrentRides = {
	        		new StandardRide(1, "A", "B", 10.0),
	        		new PremiumRide(2, "C", "D", 15.5),
	        		new StandardRide(3, "E", "F", 4.2),
	        		new PremiumRide(4, "A", "B", 20.0),
	        		new PremiumRide(5, "C", "D", 6.8),
	        		new StandardRide(6, "E", "F", 3.0),
	        		new PremiumRide(7, "A", "B", 5.5),
	        		new PremiumRide(8, "C", "D", 8.5),
	        		new StandardRide(9, "E", "F", 2.1),
	        		new PremiumRide(10, "A", "B", 7.3),
	        		new StandardRide(11, "C", "D", 4.0),
	        		new StandardRide(12, "E", "F", 0),
	        };

	        for (Ride ride : concurrentRides) {
	            taskQueue.addTask(ride);
	            try {
	                Thread.sleep(50);
	            } catch (InterruptedException e) {
	                Logger.log("MAIN", "ERROR: Interrupted while adding tasks: " + e.getMessage());
	            }
	        }

	        Logger.log("MAIN", "All " + concurrentRides.length + " tasks added to queue.");

	        // Signal shutdown after all tasks are added
	        taskQueue.shutdown();

	        // Shutdown executor and wait for completion
	        executor.shutdown();
	        try {
	            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
	                Logger.log("MAIN", "WARNING: Workers did not finish in time. Forcing shutdown.");
	                executor.shutdownNow();
	            }
	        } catch (InterruptedException e) {
	            Logger.log("MAIN", "ERROR: Interrupted while waiting for workers: " + e.getMessage());
	            executor.shutdownNow();
	        }

	        // Display results
	        System.out.println();
	     
	        System.out.println(" Concurrent processing result");
	        

	        List<String> results = resultsList.getResults();
	        for (String result : results) {
	            System.out.println("  " + result);
	        }

	        System.out.println();
	        System.out.println("  Total Rides Processed : " + results.size() + "/" + concurrentRides.length);

	        // Write results to file with exception handling
	        try {
	            writeResultsToFile("ride_results.txt", results);
	            Logger.log("MAIN", "Results written to ride_results.txt");
	        } catch (IOException e) {
	            Logger.log("MAIN", "ERROR: Could not write results file: " + e.getMessage());
	        }

	        System.out.println();
	
	        System.out.println("--------------- End -------------------");


	        Logger.close();

	}
	  private static void writeResultsToFile(String filename, List<String> results) throws IOException {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
	            writer.println(" Processing Results");
	       
	            writer.println();
	            for (String result : results) {
	                writer.println(result);
	            }
	            writer.println();
	            writer.println("Total Rides Processed: " + results.size());
	        }
	    }

}
