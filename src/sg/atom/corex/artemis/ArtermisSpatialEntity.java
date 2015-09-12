package sg.atom.corex.artemis;

import com.artemis.Entity;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import sg.atom.corex.stage.GameAction;

/**
 * A Predefined Entity which assume it has an associated Spatial.
 *
 * SpatialEntity also contain a list(empty by default) of its action which is
 * contribute to the gameworld.
 *
 * This is the most "common" kind of Entity available in a "common" JME3 game!
 * In this implementation I add fundamental ES support for this common
 * SceneGraph embeded Entity. 
 *
 * @author atomix
 */
public class ArtermisSpatialEntity {
    protected String type; // aka Classname or Prototype name.
    protected Spatial spatial; // The linked spatial
    protected ArrayList<GameAction> actions = new ArrayList<GameAction>();
    protected Entity entity; // The linked Artermis Entity

    public ArtermisSpatialEntity(long id) {
    }

    public ArtermisSpatialEntity(long id, String type) {
    }

    public ArtermisSpatialEntity(long id, String type, Spatial spatial) {
        this.spatial = spatial;
    }

    public void addAction(GameAction action) {
        actions.add(action);
    }

    //SETTER & GETTER
    public <T extends Control> T getControl(Class<T> controlType) {
        return spatial.getControl(controlType);
    }

    public ArrayList<GameAction> getActions() {
        return actions;
    }

    public int getId() {
        return entity.getId();
    }

    public Spatial getSpatial() {
        return spatial;
    }

    public void setSpatial(Spatial model) {
        this.spatial = model;

    }

    public Entity getEntity() {
        return entity;
    }
    
    
}
