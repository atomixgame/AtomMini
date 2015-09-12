package sg.atom.core.execution;

import com.sun.jmx.snmp.tasks.Task;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Hierachical Listenable Progressable Future
 * @author CuongNguyen
 */
public abstract class AtomTask<V> implements IProgress, Callable<V> {
    long id;
    float progress;
    float weight = Float.MIN_VALUE;
    Set<Task> subTasks;
    
    public long getId() {
        return id;
    }
    public Object getContext(){
        return null;
    }
    public float getProgress() {
        if (this.subTasks==null){
            return progress;
        } else {
            float totalProgress; 
            return progress;
        }        
    }

    public void setProgress(float progress) {
        if (this.subTasks==null){
            this.progress = progress;
        } else {
            throw new IllegalArgumentException("Cant now set progress of task with sub tasks");
        }
    }

    public float getContributedRate() {
        return DEFAULT_RATE;
    }
    
    public Set<Task> getSubTasks(){
        return subTasks;
    }
    
}
