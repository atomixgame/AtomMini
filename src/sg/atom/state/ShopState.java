/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import java.util.logging.Logger;
import sg.atom.AtomMain;
import sg.atom.gameplay.Shop;
import sg.atom.stage.StageManager;
import sg.atom.ui.GUIManager;
import sg.atom.ui.NiftyGUIManager;
import sg.atom.ui.nifty.UIShopScreen;

/**
 *
 * @author CuongNguyen
 */
public class ShopState extends AbstractAppState {

    private AtomMain app;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private GameStateManager gameStateManager;
    private static final Logger logger = Logger.getLogger(CreditState.class.getName());
    private GUIManager guiManager;
    private StageManager stageManager;
    private UIShopScreen screenController;
    private Shop shop;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app; // can cast Application to something more specific

        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        //this.gameStateManager = this.app.getGameStateManager();

        this.guiManager = this.app.getGUIManager();
        this.stageManager = this.app.getStageManager();
        setEnabled(true);
    }

    public void toState(Class<? extends AbstractAppState> newState) {
        if (newState.isAssignableFrom(CreditState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new CreditState());
        } else {
            throw new IllegalStateException("Can not change to new state!" + newState.getName());
        }

    }

    public void bindUI() {

        this.guiManager.getInputManager().setCursorVisible(true);
        if (guiManager instanceof NiftyGUIManager) {
            this.screenController = (UIShopScreen) ((NiftyGUIManager) guiManager).getScreenController();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            goShop();
        } else {
        }
    }

    public void goInGame() {
        guiManager.goToScreen("InGameScreen");
        stageManager.goInGame();
    }

    private void resumeGame() {
        goInGame();
    }

    private void goShop() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}