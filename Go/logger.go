package main

import (
	"fmt"
	"os"
	"sync"
	"time"
)

// Logger

type Logger struct {
	mu   sync.Mutex
	file *os.File
}

func NewLogger(filename string) *Logger {
	file, err := os.Create(filename)
	if err != nil {
		fmt.Fprintf(os.Stderr, "ERROR: Could not create log file: %v\n", err)
		return &Logger{}
	}
	l := &Logger{file: file}
	l.Log("LOGGER", "Log file initialized: "+filename)
	return l
}

func (l *Logger) Log(source, message string) {
	l.mu.Lock()
	defer l.mu.Unlock()

	timestamp := time.Now().Format("15:04:05.000")
	logLine := fmt.Sprintf("[%s] [%-12s] %s", timestamp, source, message)
	fmt.Println(logLine)

	if l.file != nil {
		fmt.Fprintln(l.file, logLine)
	}
}

func (l *Logger) Close() {
	l.mu.Lock()
	defer l.mu.Unlock()

	if l.file != nil {
		l.file.Close()
		l.file = nil
	}
}

// Write results to file with error handling using defer

func writeResultsToFile(filename string, results []string) error {
	file, err := os.Create(filename)
	if err != nil {
		return fmt.Errorf("could not create file: %w", err)
	}
	defer file.Close() // Ensure file is closed with defer

	fmt.Fprintln(file, "= Processing Results")

	fmt.Fprintln(file)

	for _, result := range results {
		fmt.Fprintln(file, result)
	}

	fmt.Fprintln(file)
	fmt.Fprintf(file, "Total Rides Processed: %d\n", len(results))

	return nil
}
