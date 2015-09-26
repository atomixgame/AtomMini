/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import java.util.List;
import org.apache.commons.configuration.Configuration;
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

    public void init() {
    }

    public void load() {
    }

    public void config(Configuration props) {
    }

    public void finish() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app;
        this.gamePlayManager = getApp().getGamePlayManager();
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
