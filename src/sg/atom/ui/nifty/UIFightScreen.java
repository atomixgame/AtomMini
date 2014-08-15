/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.nifty;

import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.HashMap;
import sg.atom.gameplay.GameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.ui.NiftyGUIManager;
import sg.atom.ui.hud.HealthBarNode;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class UIFightScreen implements ScreenController {

    private Screen screen;
    private NiftyGUIManager guiManager;
    private Nifty nifty;
    private HashMap<GameCharacter, HealthBarNode> healthBars;
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
        healthBars = new HashMap<GameCharacter, HealthBarNode>();
        for (GameCharacter character : gamePlayManager.getCharacters()) {
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

    public void createCharacterInfoExtra(GameCharacter character) {
        HealthBarNode uiHealthBar = new HealthBarNode(character, guiManager);
        healthBars.put(character, uiHealthBar);
        healthBarNode.attachChild(uiHealthBar);
//        System.out.println("Create");
    }

    public void setCharacterInfoExtra(GameCharacter character) {
        HealthBarNode healthBar = healthBars.get(character);
        if (healthBar != null) {
            healthBar.updateBar(guiManager.getApp().getStageManager().getCurrentActiveCamera());
        } else {
        }
    }
}
