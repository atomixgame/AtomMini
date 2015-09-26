package sg.atom.corex.managers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.TreeTraverser;
import com.google.common.eventbus.EventBus;
import com.jme3.app.state.AbstractAppState;
import com.jme3.font.BitmapFont;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;

/**
 *
 * @author CuongNguyen
 */
public class GUIManager extends AbstractManager {
    //Manager

    // Tonegod GUI
    //Default UI
    protected BitmapFont guiFont;
    protected BiMap<String, Object> commonScreens;
    protected EventBus eventBus;


    public GUIManager(AtomMain app) {
        super(app);
        commonScreens = HashBiMap.create();
    }

    public TreeTraverser getTreeTraverser() {
        return null;
    }

    public void setupGUI() {
        setupHUD();
        // If not run in Android?
//        if (app.getDeviceInfo().isDesktopApp()) {
//            setupCursors();
//        }
    }

    public void showCursor(boolean show) {
        inputManager.setCursorVisible(show);
    }

    public void setupHUD() {
    }

    public void setupCursors() {
        inputManager.setCursorVisible(true);
    }

    //Screen nagigation --------------------------------------------------------
    public void registerScreen(String screenName, Object screen) {
        commonScreens.put(screenName, screen);
    }

    public void goToScreen(String screenName) {
    }

    public boolean isCurrentScreen(String screenName) {
        return true;
    }

    public void onBind(AbstractAppState state, Object ui) {
    }

    public void onUnbind(AbstractAppState state, Object ui) {
    }

    // CYCLE
    public void init() {
        setupGUI();
    }

    public void load() {
    }

    public void config(Configuration props) {
    }

    public void update(float tpf) {
    }

    public void finish() {
        //Dispose related assets.
    }

    // SETTER & GETTER
    public BitmapFont getGuiFont() {
        return guiFont;
    }

    public BiMap<String, Object> getCommonScreens() {
        return commonScreens;
    }
}
