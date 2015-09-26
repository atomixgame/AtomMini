package sg.atom.gameplay;

import com.jme3.app.Application;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;
import sg.atom.gameplay.ai.Agent;
import sg.atom.corex.stage.GameAction;
import sg.atom.corex.stage.GameActor;
import sg.atom.corex.managers.StageManager;

/**
 *
 * @author cuong.nguyen
 */
public abstract class AbstractGameCharacter implements Control, Agent, Cloneable, GameActor, ManagableObject, Savable {

    protected long id;
    protected AtomMain app;
    protected GamePlayManager gamePlayManager;
    protected StageManager stageManager;

    protected ArrayList<GameAction> actions;
    protected SpatialEntity entity;
    protected boolean isNpc = true;
    //Stage, appearance
    protected Spatial model;
    // Attributes
    protected String name;
    protected Player owner;
    //Skills and secondary properties
    protected boolean staged = false;
    protected int status = 0;
    protected Control characterControl;

    public AbstractGameCharacter(String name, Spatial model) {
        this.name = name;
        this.model = model;
    }

    public void init(Application app) {
        this.app = (AtomMain) app;
        this.stageManager = getApp().getStageManager();
        this.gamePlayManager = getApp().getGamePlayManager();
    }

    public void initManagers(IGameCycle... managers) {
    }

    public void act(int type, Object param) {
    }

    public void config(Configuration configuration) {
    }

    // Events -------------------------------------
    public void finish() {
    }

    public ArrayList<GameAction> getActions() {
        return actions;
    }

    public SpatialEntity getEntity() {
        return entity;
    }

    public AtomMain getApp() {
        return app;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    public Spatial getModel() {
        return model;
    }

    //GETTER & SETTER-----------------------------------------------------------
    public Node getModelNode() {
        return (Node) model;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }

    public int getStatus() {
        return status;
    }

    public boolean isDead() {
        return false;
    }

    public boolean isNpc() {
        return isNpc;
    }

    public void read(JmeImporter im) throws IOException {
    }

    public void setCharacterControl(Control characterControl) {
        this.characterControl = characterControl;
    }

    public void setModel(Spatial playerModel) {
        this.model = playerModel;
    }

    public void setPlayer(Player owner) {
        this.owner = owner;
    }

    public void update(float tpf) {
    }

    public void removeAction(Class<? extends Control> actionType) {
        for (int i = 0; i < actions.size(); i++) {
            if (actionType.isAssignableFrom(actions.get(i).getClass())) {
                GameAction action = actions.remove(i);
//                action.setSpatial(null);
                break;
            }
        }

    }

    public boolean removeAction(GameAction action) {
        boolean result = actions.remove(action);
        if (result) {
//            action.setSpatial(null);
        }

        return result;
    }

    public <T extends GameAction> T getAction(Class<T> actionType) {
        for (GameAction c : actions) {
            if (actionType.isAssignableFrom(c.getClass())) {
                return (T) c;
            }
        }
        return null;
    }

    // For Collections of Characters!-----------------------------------------

    public void write(JmeExporter ex) throws IOException {
    }

    public long getId() {
        return id;
    }

}
