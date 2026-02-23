package ridesharing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;

class Logger {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static PrintWriter fileWriter = null;

    public static void init(String filename) {
        try {
            fileWriter = new PrintWriter(new FileWriter(filename, false));
            log("LOGGER", "Log file initialized: " + filename);
        } catch (IOException e) {
            System.err.println("ERROR: Could not create log file: " + e.getMessage());
        }
    }

    public static void log(String source, String message) {
        lock.lock();
        try {
            String timestamp = LocalDateTime.now().format(formatter);
            String threadName = Thread.currentThread().getName();
            String logLine = String.format("[%s] [%-10s] [%-12s] %s", timestamp, threadName, source, message);
            System.out.println(logLine);
            if (fileWriter != null) {
                fileWriter.println(logLine);
                fileWriter.flush();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void close() {
        lock.lock();
        try {
            if (fileWriter != null) {
                fileWriter.close();
                fileWriter = null;
            }
        } finally {
            lock.unlock();
        }
    }
}