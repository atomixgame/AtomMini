package sg.atom.corex.managers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.TreeTraverser;
import com.google.common.eventbus.EventBus;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;

/**
 *
 * @author CuongNguyen
 */
public class GUIManager extends AbstractAppState implements IGameCycle {
    //Manager

    protected final AtomMain app;
    protected final ViewPort guiViewPort;
    protected final InputManager inputManager;
    protected final AudioRenderer audioRenderer;
    protected final AssetManager assetManager;
    // Tonegod GUI
    //Default UI
    protected final Node guiNode;
    protected BitmapFont guiFont;
    protected static GUIManager instance;
    protected BiMap<String, Object> commonScreens;
    protected EventBus eventBus;

    public static class ScreenInfo {

        String path;
        String name;
        String controlType;
        int id;
        int status;
        boolean loaded;
        boolean actived;
    }

    public GUIManager(AtomMain app) {
        this.app = app;
        this.guiNode = app.getGuiNode();
        this.assetManager = app.getAssetManager();
        this.guiViewPort = app.getGuiViewPort();
        this.inputManager = app.getInputManager();
        this.audioRenderer = app.getAudioRenderer();
        this.instance = this;
        commonScreens = HashBiMap.create();
    }

    public static GUIManager getInstance() {
        return instance;
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

    public void createCrosshair() {
        //        guiNode.detachAllChildren();
//        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
//        BitmapText helloText = new BitmapText(guiFont, false);
//        helloText.setSize(guiFont.getCharSet().getRenderedSize());
//        helloText.setText("Hello World");
//        helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
//        guiNode.attachChild(helloText);
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
    public AssetManager getAssetManager() {
        return assetManager;
    }

    public ViewPort getGuiViewPort() {
        return guiViewPort;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public AtomMain getApp() {
        return app;
    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }

    public Node getGuiNode() {
        return guiNode;
    }

    public BiMap<String, Object> getCommonScreens() {
        return commonScreens;
    }
}