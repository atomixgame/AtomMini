package sg.atom.common.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import java.util.logging.Logger;
import sg.atom.AtomMain;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.managers.GUIManager;
import sg.atom.state.base.BaseGameState;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MainMenuState extends BaseGameState {

    private static final Logger logger = Logger.getLogger(MainMenuState.class.getName());

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        setEnabled(true);
    }

    public void bindUI() {
        //this.creditScreenController = (UICreditScreen) guiManager.getScreenController();
    }

    public void bindUI(GUIManager guiManager, Object ui) {
    }

    public void unbindUI(GUIManager guiManager, Object ui) {
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            this.guiManager.showCursor(true);
            guiManager.goToScreen("MainMenuScreen");
        } else {
        }
    }

    public void toState(Class<? extends AbstractAppState> newState) {
        if (newState.isAssignableFrom(CreditState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new CreditState());
        } else if (newState.isAssignableFrom(InGameState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new InGameState());
        } else {
            throw new IllegalStateException("Can not change to new state!" + newState.getName());
        }

    }
}
