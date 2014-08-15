/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import java.util.List;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.stage.StageManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class BaseGamePlay extends AbstractAppState implements IGameCycle{

    protected InputManager inputManager;
    protected GamePlayManager gamePlayManager;
    protected StageManager stageManager;
    protected AtomMain app;

    public BaseGamePlay(GamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
        this.app = stageManager.getApp();
        this.inputManager = this.app.getInputManager();
    }

    public StageManager getStageManager() {
        return app.getStageManager();
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    public void setCharacters(List<GameCharacter> characters) {
        gamePlayManager.setupCharacters(characters);
    }   

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); 
        this.app = (AtomMain)app;
        this.gamePlayManager = getApp().getGamePlayManager();
        this.stageManager = getApp().getStageManager();
        this.inputManager = this.app.getInputManager();
    }   

    public AtomMain getApp() {
        return app;
    }
}
