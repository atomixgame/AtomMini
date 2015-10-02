/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.managers;

import java.util.ArrayList;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.gameplay.Player;

/**
 *
 * @author CuongNguyen
 */
public class PlayerManager extends AbstractManager {

    //Constant
    public static final int STATUS_NONE = 0;
    public static final int STATUS_JOINED = 1;
    public static final int STATUS_BANNED = 2;

    ArrayList<Player> players;

    public PlayerManager(AtomMain app) {
        super(app);
    }

    public void collectPlayerInfo() {
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
}
