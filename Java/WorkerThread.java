package ridesharing;


//Worker Thread -

class WorkerThread implements Runnable {
 private final String workerName;
 private final SharedTaskQueue taskQueue;
 private final SharedResultsList resultsList;
 private int tasksProcessed = 0;

 public WorkerThread(String workerName, SharedTaskQueue taskQueue, SharedResultsList resultsList) {
     this.workerName = workerName;
     this.taskQueue = taskQueue;
     this.resultsList = resultsList;
 }

 @Override
 public void run() {
     Logger.log(workerName, "Worker started.");

     while (true) {
         try {
             Ride ride = taskQueue.getTask();

             if (ride == null) {
                 Logger.log(workerName, "No more tasks. Shutting down. Processed: " + tasksProcessed);
                 break;
             }

             processRide(ride);

         } catch (InterruptedException e) {
             Logger.log(workerName, "ERROR: Thread interrupted: " + e.getMessage());
             Thread.currentThread().interrupt();
             break;
         } catch (Exception e) {
             Logger.log(workerName, "ERROR: Unexpected exception: " + e.getMessage());
         }
     }

     Logger.log(workerName, "Worker terminated.");
 }

 private void processRide(Ride ride) {
     Logger.log(workerName, "Processing: Ride #(" + ride.getRideID()
             + " , " + ride.getRideType() + " )");

     long startTime = System.currentTimeMillis();

     try {
         // 200-800ms delay for simulation
         int delay = 200 + (int) (Math.random() * 600);
         Thread.sleep(delay);

         // Calculate fare 
         double fare = ride.fare();

         // Validate fare
         if (fare <= 0) {
             throw new ArithmeticException("Invalid fare calculated: " + fare);
         }

         // Update ride status
         ride.startRide();
         ride.completeRide();

         long elapsed = System.currentTimeMillis() - startTime;

         String result = String.format(" %s >> Ride #(%d , %s , $%.2f , %s ) -> %dms ",
                 workerName, ride.getRideID(), ride.getRideType(), 
                 fare,  ride.getStatus().toString(), elapsed);
         resultsList.addResult(result);

         Logger.log(workerName, String.format("Completed: Ride #(%d , $%.2f ) -> %dms",
                 ride.getRideID(), fare, elapsed));

         tasksProcessed++;

     } catch (InterruptedException e) {
         Logger.log(workerName, "ERROR: Processing interrupted for Ride #" + ride.getRideID());
         Thread.currentThread().interrupt();
     } catch (ArithmeticException e) {
         Logger.log(workerName, "ERROR: Fare calculation failed for Ride #"
                 + ride.getRideID() + ": " + e.getMessage());
         ride.cancelRide();
         long elapsed = System.currentTimeMillis() - startTime;
         String result = String.format(" %s >> Ride #(%d , %s , $%.2f ,%s ) -> %dms -> Error: %s ",
                 workerName, ride.getRideID(), ride.getRideType(),
                 ride.fare(),  ride.getStatus().toString(), elapsed, e.getMessage());
         resultsList.addResult(result);
     }
 }
}
