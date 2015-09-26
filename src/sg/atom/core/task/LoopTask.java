package sg.atom.core.task;


/**
 *  A variable-duration task that delegates to another task
 *  that it will loop over.  
 *
 *  @author    Paul Speed
 */
public class LoopTask implements Task {

    private Task delegate;
    private double time;
    private double duration;
    
    public LoopTask( Task delegate, double duration ) {
        this.delegate = delegate;
        this.duration = duration;
    }

    protected double executePart( double tpf ) {
    
        double d = delegate.getTimeRemaining();
        TaskStatus result = delegate.execute(tpf);
        
        double actual = Math.min(tpf, d);
        time += actual;
        
        if( result == TaskStatus.Done && time < duration ) {
            // Reset for next time
            delegate.stopping();
        } else if( time >= duration && result != TaskStatus.Done ) {
            // We are done and need to tell the delegate to
            // at least pause
            delegate.pausing();
            
            // Let the caller know we used everything, basically.
            return tpf;
        }
        return actual;
    }

    public TaskStatus execute( double tpf ) {
        if( time >= duration ) {
            return TaskStatus.Done;
        }
        while( tpf > 0 ) {
            tpf -= executePart(tpf);
        }
        return time < duration ? TaskStatus.Continue : TaskStatus.Done;        
    }

    public double getDuration() {
        return duration;
    }

    public double getTimeRemaining() {
        return Math.max(0, time - duration); 
    }

    public void pausing() {
        delegate.pausing();
    }

    public void stopping() {
        delegate.stopping();
        time = 0;
    }
}
