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
import sg.atom.corex.managers.StageManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class BaseGamePlay extends AbstractAppState implements IGameCycle {

    protected GamePlayManager gamePlayManager;
    protected AtomMain app;

    public BaseGamePlay(AtomMain app) {
        this.app = app;
        this.gamePlayManager = app.getGamePlayManager();
    }

    public BaseGamePlay(GamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
        this.app = gamePlayManager.getApp();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app;
        this.gamePlayManager = getApp().getGamePlayManager();
//        this.stageManager = getApp().getStageManager();
//        this.inputManager = getApp().getInputManager();
    }

    //SETTER & GETTER
    public StageManager getStageManager() {
        return app.getStageManager();
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    public void setCharacters(List<CommonGameCharacter> characters) {
        gamePlayManager.setupCharacters(characters);
    }

    public AtomMain getApp() {
        return app;
    }
}
