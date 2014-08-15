/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.managers;

import org.apache.commons.configuration.Configuration;
import sg.atom.core.lifecycle.IGameCycle;

/**
 *
 * @author CuongNguyen
 */
public class PlayerManager  implements IGameCycle{
    //Constant
    public static final int STATUS_NONE = 0;
    public static final int STATUS_JOINED = 1;
    public static final int STATUS_BANNED = 2;
    
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void config(Configuration props) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
