/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.common.state;

import org.apache.commons.configuration.Configuration;
import sg.atom.core.lifecycle.IGameCycle;

/**
 * GameStateManager is the Manager who deal with AbstractMainState, State - State, State - Manager
 * relationship.
 *
 * @author CuongNguyen
 */
public class GameStateManager implements IGameCycle{

    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public void config(Configuration props) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
