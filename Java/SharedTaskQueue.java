package ridesharing;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class SharedTaskQueue {
    private final Queue<Ride> queue = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private boolean shutdown = false;

    public void addTask(Ride ride) {
        lock.lock();
        try {
            queue.add(ride);
            Logger.log("QUEUE", "Added: Ride #(" + ride.getRideID() + " ," + ride.getRideType() + ")");
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Ride getTask() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty() && !shutdown) {
                notEmpty.await();
            }
            if (queue.isEmpty() && shutdown) {
                return null;
            }
            Ride ride = queue.poll();
            Logger.log("QUEUE", "Retrieved: Ride #(" + ride.getRideID() + " , " + ride.getRideType() + ")");
            return ride;
        } finally {
            lock.unlock();
        }
    }

    public void shutdown() {
        lock.lock();
        try {
            shutdown = true;
            notEmpty.signalAll();
            Logger.log("QUEUE", "Shutdown signal sent. Remaining tasks: " + queue.size());
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}