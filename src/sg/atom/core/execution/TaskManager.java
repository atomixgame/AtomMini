/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.lifecycle.IGameCycle;

/**
 * TaskManager or ExecutionService?
 * @author CuongNguyen
 */
public class TaskManager implements IGameCycle{
    // Futures , Caches?
    protected ListeningScheduledExecutorService executor;
    protected ListenableFuture<Boolean> loadGUIAsset, loadDataTask, loadAssetTask, loadStageTask, configTask, socialTask;
    
    public void init() {
        executor = MoreExecutors.listeningDecorator(new ScheduledThreadPoolExecutor(4));
    }

    public void submit(){
//        return new AtomTask();
    }
    public void submitTasks(){
        
    }
    public void load() {
    }

    public void config(Configuration props) {
    }

    public void update(float tpf) {
    }

    public void finish() {
    }
    
}
