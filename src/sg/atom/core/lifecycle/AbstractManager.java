package sg.atom.core.lifecycle;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;

/**
 *
 * @author cuong.nguyen
 */
public class AbstractManager implements IGameCycle, AppState {

    protected AtomMain app;
    protected Node guiNode;
    protected Node rootNode;
    //Managers
    protected AssetManager assetManager;
    protected AppStateManager stateManager;
    protected InputManager inputManager;
    protected boolean actived;
    protected boolean initialized = false;
    protected boolean customCycle = false;
    protected AudioRenderer audioRenderer;
    protected ViewPort viewPort;

    protected AbstractManager() {
    }

    public AbstractManager(AtomMain app) {
        this.app = app;
        this.guiNode = app.getGuiNode();
        this.rootNode = app.getRootNode();
        this.audioRenderer = app.getAudioRenderer();
        this.viewPort = app.getViewPort();
        this.assetManager = app.getAssetManager();
        this.stateManager = app.getStateManager();
        this.inputManager = app.getInputManager();
        this.initialized = true;
    }

    public void init() {
    }

    public void load() {
    }

    public void config(Configuration props) {
    }

    public void update(float tpf) {
    }

    public void finish() {
    }

    public void addTask(Runnable task) {
    }

    public void initialize(AppStateManager stateManager, Application app) {

        if (this.initialized) {
        } else {
            this.initialized = true;
            this.app = (AtomMain) app;
            this.guiNode = this.app.getGuiNode();
            this.rootNode = this.app.getRootNode();
            this.assetManager = app.getAssetManager();
            this.stateManager = app.getStateManager();
            this.inputManager = app.getInputManager();
            this.audioRenderer = app.getAudioRenderer();
            this.viewPort = app.getViewPort();
        }

    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setEnabled(boolean active) {
        actived = active;
    }

    public boolean isEnabled() {
        return actived;
    }

    public void stateAttached(AppStateManager stateManager) {
    }

    public void stateDetached(AppStateManager stateManager) {
    }

    public void render(RenderManager rm) {
    }

    public void postRender() {
    }

    public void cleanup() {
    }

    public AtomMain getApp() {
        return app;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public Node getGuiNode() {
        return guiNode;
    }

    public AppStateManager getStateManager() {
        return stateManager;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public AudioRenderer getAudioRenderer() {
        return audioRenderer;
    }

    public ViewPort getViewPort() {
        return viewPort;
    }

}
