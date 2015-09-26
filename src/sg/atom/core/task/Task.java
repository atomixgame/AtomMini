package sg.atom.core.task;


/**
 *  A general executable task that periodically performs
 *  some operation.
 *
 *  @author    Paul Speed
 */
public interface Task {

    /**
     *  Executes a frame of this task.
     */
    public TaskStatus execute( double tpf );
    
    /**
     *  Returns the duration of this task if known.  Returns
     *  0 for instant tasks and -1 for tasks where the duration
     *  is continuous or unknown.
     */
    public double getDuration();
    
    /**
     *  Returns the time remaining for this task if known.
     *  Returns 0 if the task has been completed or -1 if the
     *  duration of the task is unknown or the task is otherwise
     *  "continuous", ie: unending.
     */
    public double getTimeRemaining();
    
    /**
     *  Called when the task is pausing.  This indicates to the
     *  task that execute() will not be called for a while but that
     *  when it is, execution should resume where it left off.
     */
    public void pausing();
    
    /**
     *  Called when the task is stopping.  This indicates to the
     *  task that execute() will not be called for a while but that
     *  when it is, execution should start over from the beginning.
     */
    public void stopping();
}


