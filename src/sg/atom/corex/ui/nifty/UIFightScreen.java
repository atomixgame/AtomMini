/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.ui.nifty;

import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.HashMap;
import sg.atom.gameplay.CommonGameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.corex.ui.NiftyGUIManager;
import sg.atom.corex.ui.hud.HealthBarNode;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class UIFightScreen implements ScreenController {

    private Screen screen;
    private NiftyGUIManager guiManager;
    private Nifty nifty;
    private HashMap<CommonGameCharacter, HealthBarNode> healthBars;
    private Node healthBarNode;

    public UIFightScreen(NiftyGUIManager guiManager) {
        this.guiManager = guiManager;
    }

    public void createCharacterInfos(GamePlayManager gamePlayManager) {
        if (healthBarNode == null) {
            healthBarNode = new Node("characterHealthBars");
        } else {
            healthBarNode.detachAllChildren();
        }
        guiManager.getApp().getGuiNode().attachChild(healthBarNode);
        healthBars = new HashMap<CommonGameCharacter, HealthBarNode>();
        for (CommonGameCharacter character : gamePlayManager.getCharacters()) {
            createCharacterInfoExtra(character);
        }
    }

    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
        this.nifty = nifty;
    }

    public void onStartScreen() {
//        createCharacterInfos(null);
    }

    public void onEndScreen() {
        healthBarNode.detachAllChildren();
    }

    public void createCharacterInfoExtra(CommonGameCharacter character) {
        HealthBarNode uiHealthBar = new HealthBarNode(character, guiManager);
        healthBars.put(character, uiHealthBar);
        healthBarNode.attachChild(uiHealthBar);
//        System.out.println("Create");
    }

    public void setCharacterInfoExtra(CommonGameCharacter character) {
        HealthBarNode healthBar = healthBars.get(character);
        if (healthBar != null) {
            healthBar.updateBar(guiManager.getApp().getStageManager().getCurrentCamera());
        } else {
        }
    }
}
