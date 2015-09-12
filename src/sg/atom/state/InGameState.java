package sg.atom.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.Node;
import java.util.logging.Logger;

import sg.atom.AtomMain;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.ui.NiftyGUIManager;
import sg.atom.corex.ui.nifty.UIInGameScreen;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class InGameState extends AbstractAppState {

    private static final Logger logger = Logger.getLogger(InGameState.class.getName());
    //FIXME: Should not use shortcuts!
    protected AtomMain app;
    protected Node rootNode;
    protected AssetManager assetManager;
    protected AppStateManager stateManager;
    protected GameStateManager gameStateManager;
    protected GUIManager guiManager;
    protected StageManager stageManager;
    protected GamePlayManager gamePlayManager;
    protected UIInGameScreen screenController;
    protected InputManager inputManager;
    //FIXME: replace with Int state
    protected boolean gamePause;
    protected ActionListener actionListener;

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
        } else if (newState.isAssignableFrom(FightState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new FightState());
        } else {
            throw new IllegalStateException("Can not change to new state!" + newState.getName());
        }
    }

    public void fromState(Class<? extends AbstractAppState> previousState) {
    }

    public void setupInput() {
        actionListener = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf) {
                if (name.equals("dialogue") && keyPressed) {
                    if (!guiManager.isCurrentScreen("InGameScreen")) {
                        guiManager.goToScreen("InGameScreen");
                    } else {
                        guiManager.goToScreen("DialogueScreen");
                    }
                } else if (name.equals("fight") && keyPressed) {
                    toState(FightState.class);
                }
            }
        };

        inputManager.addMapping("dialogue", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addListener(actionListener, "dialogue");

        inputManager.addMapping("fight", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addListener(actionListener, "fight");
    }

    public void bindUI(GUIManager manager, Object ui) {
        if (guiManager.isCurrentScreen("InGameScreen")) {
            this.guiManager.getInputManager().setCursorVisible(true);
            if (guiManager instanceof NiftyGUIManager) {
                this.screenController = (UIInGameScreen) ((NiftyGUIManager) guiManager).getScreenController();
                this.screenController.createCharacterInfos(stageManager.getGamePlayManager());
            }

        } else {
        }
    }

    public void unbindUI(GUIManager manager, Object ui) {
        if (guiManager.isCurrentScreen("InGameScreen")) {
            //app.getGuiNode().detachAllChildren();
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

    public void pauseGame() {
        //FIXME: Move to PauseState
//        guiManager.pauseGame();
//        stageManager.pauseGame();
    }

    public void startGame() {
        setupInput();
        stageManager.onStageReady();
        guiManager.goToScreen("InGameScreen");
//        stageManager.goToScene("Adventure");
//        stageManager.getGamePlayManager().goNormalScene();
//        stateManager.attach(npcManager);
//        stateManager.attach(terrainManager);
    }

    public void restartGame() {
    }

    public void resumeGame() {
//        getGameGUIManager().resumeGame();
//        getStageManager().resumeGame();
    }

    //GETTER & SETTER
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            startGame();
        } else {
        }
    }

    public Object getUIFacade() {
        return null;
    }

    public UIInGameScreen getScreenController() {
        return screenController;
    }
}
