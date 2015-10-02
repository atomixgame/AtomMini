/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.common.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import java.util.logging.Logger;
import sg.atom.common.ui.nifty.NiftyGUIManager;
import sg.atom.common.ui.nifty.UICreditScreen;
import sg.atom.state.base.BaseGameState;

/**
 *
 * @author CuongNguyen
 */
public class CreditState extends BaseGameState {

    private static final Logger logger = Logger.getLogger(CreditState.class.getName());
    private UICreditScreen creditScreenController;
    float thisTime = 0, lastTime = 0, stepTime = 0.2f;

    public CreditState() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setEnabled(true);

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            guiManager.goToScreen("CreditScreen");
        } else {
            //unbindUI();
        }
    }

    public void bindUI() {

        this.guiManager.getInputManager().setCursorVisible(true);
        if (guiManager instanceof NiftyGUIManager) {

            this.creditScreenController = (UICreditScreen) ((NiftyGUIManager) guiManager).getScreenController();
        }
        this.thisTime = 0;
        lastTime = 0;
    }

    public void unbindUI() {
        this.creditScreenController = null;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);


        if (this.creditScreenController != null) {
            thisTime += tpf;
            if (thisTime - lastTime > stepTime) {
                this.creditScreenController.scroll();
                lastTime = thisTime;
            }

        }
    }

    public void toState(Class<? extends AbstractAppState> newState) {
        if (newState.isAssignableFrom(MainMenuState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new MainMenuState());
        } else {
            throw new IllegalStateException("Can not change to new state!" + newState.getName());
        }
    }
}
