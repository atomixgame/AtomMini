/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.managers;

import com.jme3.app.state.AbstractAppState;
import java.util.ArrayList;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.gameplay.Player;

/**
 *
 * @author CuongNguyen
 */
public class PlayerManager extends AbstractAppState  implements IGameCycle{
    //Constant
    public static final int STATUS_NONE = 0;
    public static final int STATUS_JOINED = 1;
    public static final int STATUS_BANNED = 2;
    
        ArrayList<Player> players;

    public void collectPlayerInfo(){
        // If on Desktop
        // If on Android
    }
    //Events--------------------------------------------------------------------
    public void onPlayerJoined(String gameLeague) {
    }

    public void onPlayerRegistered(String gameLeague) {
    }
    //
    //Cycle
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
