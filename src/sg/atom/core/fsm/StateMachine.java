/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.fsm;

/**
 *
 * @author cuong.nguyen
 */
public interface StateMachine<S,E> {
    
    S getCurrentState();
            
    void fireEvent(E event, Object param);
}
