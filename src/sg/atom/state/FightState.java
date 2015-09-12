/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.List;
import java.util.logging.Logger;
import sg.atom.AtomMain;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.ui.NiftyGUIManager;
import sg.atom.corex.ui.nifty.UIFightScreen;
import sg.atom.corex.ui.nifty.UIInGameScreen;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FightState extends AbstractAppState {

    private AtomMain app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private GameStateManager gameStateManager;
    private static final Logger logger = Logger.getLogger(CreditState.class.getName());
    private GUIManager guiManager;
    private StageManager stageManager;
    private GamePlayManager gamePlayManager;
    private UIFightScreen screenController;
    private boolean gamePause;
    private InputManager inputManager;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app; // can cast Application to something more specific

        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        //this.gameStateManager = this.app.getGameStateManager();

        this.guiManager = this.app.getGUIManager();
        this.stageManager = this.app.getStageManager();
        this.inputManager = this.app.getInputManager();
        setEnabled(true);
    }

    public void toState(Class<? extends AbstractAppState> newState) {
        if (newState.isAssignableFrom(CreditState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new CreditState());
        } else if (newState.isAssignableFrom(MainMenuState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new MainMenuState());
        } else if (newState.isAssignableFrom(InGameState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new InGameState());
        } else {
            throw new IllegalStateException("Can not change to new state!" + newState.getName());
        }

    }

    public void setupInput() {
//        inputManager.addMapping("dialogue", new KeyTrigger(KeyInput.KEY_K));
//        inputManager.addListener(actionListener, "dialogue");

        inputManager.addMapping("fight", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addListener(actionListener, "fight");
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
//            if (name.equals("dialogue") && keyPressed) {
//                if (!guiManager.isCurrentScreen("InGameScreen")) {
//                    guiManager.goToScreen("InGameScreen");
//                } else {
//                    guiManager.goToScreen("DialogueScreen");
//                }
//            } else 

            if (name.equals("fight") && keyPressed) {
                toState(InGameState.class);
            }

        }
    };

    public void bindUI() {
        if (guiManager.isCurrentScreen("FightScreen")) {
            this.guiManager.getInputManager().setCursorVisible(true);
            if (guiManager instanceof NiftyGUIManager) {
                this.screenController = (UIFightScreen) ((NiftyGUIManager) guiManager).getScreenController();
            }
            this.screenController.createCharacterInfos(stageManager.getGamePlayManager());
        } else {
        }
    }

    public void unbindUI() {
        if (guiManager.isCurrentScreen("FightScreen")) {
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            setupInput();
//            stageManager.goFightScene(stageManager.getCurrentLevel().getName());
//            stageManager.getGamePlayManager().goFightScene();
            guiManager.goToScreen("FightScreen");
        } else {
        }
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        if (!gamePause) {
            guiManager.update(tpf);
            stageManager.update(tpf);
            stageManager.getGamePlayManager().update(tpf);

            if (screenController != null) {
                stageManager.getGamePlayManager().updateGUI(screenController, tpf);
            }
        }
    }

    public List<Vector3f> getStageConner() {
        return null;
    }
}
