package sg.atom.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import java.util.logging.Logger;
import sg.atom.AtomMain;
import sg.atom.stage.StageManager;
import sg.atom.ui.GUIManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MainMenuState extends AbstractAppState {

    private AtomMain app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private GameStateManager gameStateManager;
    private static final Logger logger = Logger.getLogger(CreditState.class.getName());
    private GUIManager guiManager;
    private StageManager stageManager;

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
        setEnabled(true);
    }

    public void bindUI() {
        //this.creditScreenController = (UICreditScreen) guiManager.getScreenController();
    }
    
    public void bindUI(GUIManager guiManager,Object ui){
        
    }
    public void unbindUI(GUIManager guiManager,Object ui){
        
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
