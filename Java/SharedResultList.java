package ridesharing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

class SharedResultsList {
    private final List<String> results = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void addResult(String result) {
        lock.lock();
        try {
            results.add(result);
        } finally {
            lock.unlock();
        }
    }

    public List<String> getResults() {
        lock.lock();
        try {
            return new ArrayList<>(results);
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return results.size();
        } finally {
            lock.unlock();
        }
    }
}
