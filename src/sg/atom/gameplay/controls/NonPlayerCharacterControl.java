/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.controls;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import sg.atom.gameplay.CommonGameCharacter;
import sg.atom.corex.managers.StageManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class NonPlayerCharacterControl extends BaseCharacterControl {

    public NonPlayerCharacterControl(StageManager stageManager, CommonGameCharacter character) {
        super(stageManager, character);
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
