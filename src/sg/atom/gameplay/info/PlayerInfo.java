/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.info;

import de.lessvoid.nifty.render.NiftyImage;
import sg.atom.ui.GUIManager;
import sg.atom.ui.NiftyGUIManager;

/**
 *
 * @author CuongNguyen
 */
public class PlayerInfo {

    int num;
    String name;
    String icon;
    int score;
    private String AVATAR_DIR = "Interface/Images/Ingame/Portrait/";

    public PlayerInfo(int num, String name, String icon, int score) {
        this.num = num;
        this.name = name;
        this.icon = icon;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public NiftyImage getIconImage() {
        return NiftyGUIManager.getInstance().createNiftyImage(AVATAR_DIR + icon);
    }

    public String getIcon() {
        return icon;
    }

    public int getNum() {
        return num;
    }

    public int getScore() {
        return score;
    }
    
    
}
