package creation.dataworks;

import java.time.Duration;
import java.time.Instant;

public class StopWatch {

    private Instant startTime;
    private Duration duration;
    private boolean isRunning = false;

    public void start() {

        if (isRunning) {
            throw new RuntimeException("Stopwatch is already running.");
        }
        this.isRunning = true;
        startTime = Instant.now();
    }

    public Duration stop() {
        Instant endTime = Instant.now();
        if (!isRunning) {
            throw new RuntimeException("Stopwatch has not been started yet");
        }

        isRunning = false;
        Duration result = Duration.between(startTime, endTime);

        if (this.duration == null) { this.duration = result; }
        else { this.duration = duration.plus(result);  }

        return this.getElapsedTime();
    }

    public Duration getElapsedTime() {
        return this.duration;
    }

    public void reset() {
        if (this.isRunning) {
            this.stop();
        }
        this.duration = null;
    }
}