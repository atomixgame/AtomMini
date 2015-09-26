package sg.atom.core.task;

import sg.atom.corex.fx.Interpolator;

/**
 * Performs an interpolation over a specific time duration.
 *
 * @author Paul Speed
 */
public class DurationTask implements Task {

    private double duration;
    private double time;
    private Interpolator interp;

    public DurationTask(double duration, Interpolator interp) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0.");
        }
        if (interp == null) {
            throw new IllegalArgumentException("Interpolator cannot be null.");
        }

        this.duration = duration;
        this.interp = interp;
    }

    /**
     * Sets the duration of this task, conventionally in seconds. It's really up
     * to the caller of execute how duration is interpretted.
     */
    public void setDuration(double d) {
        this.duration = d;
    }

    /**
     * Returns the duration of this task, conventionally in seconds. It's really
     * up to the caller of execute how duration is interpretted.
     */
    public double getDuration() {
        return duration;
    }

    public double getTimeRemaining() {
        return Math.max(0, duration - time);
    }

    public TaskStatus execute(double tpf) {
        time += tpf;
        double t = Math.min(1, time / duration);
        interp.interpolate(t);

        return t < 1 ? TaskStatus.Continue : TaskStatus.Done;
    }

    public void pausing() {
        // Nothing to do
    }

    public void stopping() {
        // Just reset the time
        time = 0;
    }

    @Override
    public String toString() {
        return "DurationTask[duration=" + duration + ", interp=" + interp + "]";
    }
}
