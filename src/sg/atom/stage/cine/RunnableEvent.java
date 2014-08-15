/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.cine;

import com.google.common.util.concurrent.ListenableFuture;
import com.jme3.cinematic.events.AbstractCinematicEvent;
import java.util.concurrent.Callable;

/**
 *
 * @author CuongNguyen
 */
public class RunnableEvent extends AbstractCinematicEvent {

    Runnable runnable;
    Callable callable;
    ListenableFuture future;
    boolean once;
    float stepTime;
    
    @Override
    protected void onPlay() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onStop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
