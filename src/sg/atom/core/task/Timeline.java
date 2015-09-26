package sg.atom.core.task;

import com.jme3.util.SafeArrayList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *  Performs a set of scheduled tasks at specific times
 *  for whatever duration those tasks choose to run.
 *
 *  @author    Paul Speed
 */
public class Timeline implements Task {

    private List<ScheduledTask> schedule = new ArrayList<ScheduledTask>();
    
    private int nextRun = 0;
    private Double duration;
    private double time;
    private SafeArrayList<ScheduledTask> running = new SafeArrayList<ScheduledTask>(ScheduledTask.class);  

    public Timeline() {
    }
 
    public double getDuration() {
        if( duration == null ) {
            // Recalculate the duration
            double d = 0;
            for( ScheduledTask t : schedule ) {
                double td = t.task.getDuration();
                if( td >= 0 ) {
                    d = Math.max(d, t.time + td);
                } else {
                    // We cannot determine duration
                    d = -1; 
                    break;
                }                
            }
            duration = d;            
        }
        return duration;
    }
    
    public double getTimeRemaining() {
        double d = getDuration();
        if( d < 0 ) {
            return -1;
        }
        return Math.max(0, d - time);
    }
 
    /**
     *  Adds a task that will be run at the specified time
     *  in seconds relative to the timeline's beginning.  The
     *  task will then be executed until it returns TaskStatus.Done.
     */   
    public void addEvent( double time, Task task ) {
        ScheduledTask event = new ScheduledTask(time, task);
        int index = Collections.binarySearch(schedule, event);
        if( index < 0 ) {
            index = -index - 1;
        }
        if( index < schedule.size() ) {
            schedule.add(index, event);
        } else {
            schedule.add(event);
        }
    }

    /**
     *  Moves the timeline forward by the specified time in seconds,
     *  performing any newly pending tasks.
     */
    public TaskStatus execute( double tpf ) {
        time += tpf;
        
        // Move any scheduled tasks to running
        for( ; nextRun < schedule.size(); nextRun++ ) {
            ScheduledTask event = schedule.get(nextRun);
            if( event.time > time ) {
                // Still in the future
                break;
            }
            
            // Else move it to running
            running.add(event);
        }
        
        // Run the events
        for( ScheduledTask event : running.getArray() ) {
            TaskStatus status = event.task.execute(tpf);
            if( status == TaskStatus.Done ) {
                running.remove(event);
            }
        }
 
        if( running.isEmpty() && nextRun >= schedule.size() ) {
            return TaskStatus.Done;
        }       
        return TaskStatus.Continue;
    }
 
    /**
     *  Lets the timeline know that it is pausing.  All currently 
     *  running tasks will also have their pausing() methods called
     *  but the pending tasks will not.
     *  Subsequent execute() calls will continue the timeline where
     *  it left off.     
     */   
    public void pausing() {
        // Let the running tasks know
        for( ScheduledTask event : running.getArray() ) {
            event.task.pausing();
        }
        duration = null;
    }
 
    /**
     *  Lets the timeline know that it is stopping and that the
     *  internal state should be reset.  All currently running tasks
     *  will also have their stopping() methods called but the pending
     *  tasks will not.
     *  Subsequent execute calls will restart the timeline from the
     *  beginning. 
     */   
    public void stopping() {
        // Let the running tasks know
        for( ScheduledTask event : running.getArray() ) {
            event.task.stopping();
        }
        
        // And reset everything to 0 state
        running.clear();
        duration = null;
        nextRun = 0;
        time = 0;
    }
 
    @Override
    public String toString() {
        return "TimeLine[schedule=" + schedule + "]";
    }
 
    private class ScheduledTask implements Comparable<ScheduledTask> {
        double time;
        Task task;
        
        public ScheduledTask( double time, Task task ) {
            this.time = time;
            this.task = task;
        }

        public int compareTo(ScheduledTask o) {
            return (int)(time - o.time);
        }
        
        @Override
        public String toString() {
            return "ScheduledTask[time=" + time + ", task=" + task + "]";
        }
    }          
}



