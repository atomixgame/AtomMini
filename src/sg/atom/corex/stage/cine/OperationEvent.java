/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage.cine;

import com.jme3.cinematic.events.AbstractCinematicEvent;

/**
 * Wrapper for Method interaction.
 * @author CuongNguyen
 */
public class OperationEvent extends AbstractCinematicEvent {
    boolean silent;
    String methodName;
    Object[] params;
    
    @Override
    protected void onPlay() {
        
    }

    @Override
    protected void onUpdate(float tpf) {
        
    }

    @Override
    protected void onStop() {
        
    }

    @Override
    public void onPause() {
        
    }
    
}
