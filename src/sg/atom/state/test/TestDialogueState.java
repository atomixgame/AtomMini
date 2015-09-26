/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.state.test;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.logging.Logger;
import sg.atom.AtomMain;
import sg.atom.corex.stage.story.SimpleDialogue;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.managers.EffectManager;
import sg.atom.common.state.GameStateManager;
import sg.atom.common.state.InGameState;
import sg.atom.corex.managers.GUIManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TestDialogueState extends AbstractAppState {

    private static final Logger logger = Logger.getLogger(InGameState.class.getName());
    private AtomMain app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private GameStateManager gameStateManager;
    private GUIManager gameGUIManager;
    private StageManager stageManager;
    private EffectManager effectManager;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app; // can cast Application to something more specific

        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
//        this.gameStateManager = this.app.getGameStateManager();

        this.gameGUIManager = this.app.getGUIManager();
        this.stageManager = this.app.getStageManager();
        setEnabled(true);

    }

    public void setupInputs() {
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            SimpleDialogue dialogue = new SimpleDialogue("First dialogue", stageManager);
            dialogue.load("Story/Dialogues/Dialogue1.txt");
            dialogue.start();
        } else {
        }
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

    }

    public GUIManager getGameGUIManager() {
        return gameGUIManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }
}
