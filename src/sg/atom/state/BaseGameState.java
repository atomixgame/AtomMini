/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import sg.atom.AtomMain;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.managers.StageManager;

/**
 *
 * @author cuongnguyen
 */
public class BaseGameState extends AbstractAppState {

    protected AtomMain app;
    protected Node rootNode;
    protected Node guiNode;
    protected AssetManager assetManager;
    protected AppStateManager stateManager;
    protected GameStateManager gameStateManager;
    protected GUIManager guiManager;
    protected StageManager stageManager;
    protected InputManager inputManager;
    private ViewPort viewPort;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app;

        this.rootNode = this.app.getRootNode();
        this.guiNode = this.app.getGuiNode();

        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.guiManager = this.app.getGUIManager();
        this.stageManager = this.app.getStageManager();
        this.viewPort = this.app.getViewPort();
    }

    public AtomMain getApp() {
        return app;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public AppStateManager getStateManager() {
        return stateManager;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public Node getGuiNode() {
        return guiNode;
    }

    public void setGuiNode(Node guiNode) {
        this.guiNode = guiNode;
    }

    public ViewPort getViewPort() {
        return viewPort;
    }

}
