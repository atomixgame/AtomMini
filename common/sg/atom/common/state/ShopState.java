/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.common.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import java.util.logging.Logger;
import sg.atom.gameplay.Shop;
import sg.atom.common.ui.nifty.NiftyGUIManager;
import sg.atom.common.ui.nifty.UIShopScreen;
import sg.atom.state.BaseGameState;

/**
 *
 * @author CuongNguyen
 */
public class ShopState extends BaseGameState {

    private static final Logger logger = Logger.getLogger(CreditState.class.getName());
    private UIShopScreen screenController;
    private Shop shop;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
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
        stageManager.onStageReady();
    }

    private void resumeGame() {
        goInGame();
    }

    private void goShop() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}