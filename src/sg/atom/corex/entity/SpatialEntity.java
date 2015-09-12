package sg.atom.corex.entity;

import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;
import java.util.ArrayList;
import sg.atom.corex.stage.GameAction;

/**
 * A Predefined Entity which assume it has an associated Spatial.
 *
 * SpatialEntity also contain a list(empty by default) of its action which is contribute to the gameworld. 
 * 
 * This is the most "common" kind of Entity available in a "common" JME3 game!
 * 
 * In this implementation I add fundamental ES support for this common SceneGraph embeded Entity! 
 * <ul>
 * <li>It can get an un-ordered list of its associated Component(s). Include {@link SpatialInfo} as link to Spatial components </li>
 * 
 * <li>It can get/set an ordered list of its associcated GameAction(s).</li>
 * 
 * <li>It procedure BeanInfo.</li>
 * 
 * <li>It proxy for its Spatial Controls.</li>
 * 
 * </ul>
 *
 * @author atomix
 */
public class SpatialEntity implements Entity {
    protected static final long DEFAULT_NONE_ID = Long.MIN_VALUE;
    protected Spatial spatial;
    protected ArrayList<GameAction> actions = new ArrayList<GameAction>();
    protected long iid;
    protected String type;
    protected int[] functionIds;
    protected int group;
    protected String tags;
    
    /**
     * Only use internal for factory initilization
     **/
    protected SpatialEntity(){
        this.iid = DEFAULT_NONE_ID;
    }
    public SpatialEntity(long iid){
        this.iid = iid;
    }
    public SpatialEntity(long iid, String type) {
        this.iid = iid;
        this.type = type;
    }

    public SpatialEntity(long iid, String type, Spatial spatial) {
        this.iid = iid;
        this.type = type;
        this.spatial = spatial;
    }

    public Spatial getSpatial() {
        return spatial;
    }

    public void setSpatial(Spatial model) {
        this.spatial = model;

    }

    public void addAction(GameAction action) {
        actions.add(action);
    }

    public void act(GameAction action){
        
    }
    
    public void onEvent(Object event){
        
    }
    public <T extends Control> T getControl(Class<T> controlType) {
        return spatial.getControl(controlType);
    }

    public EntityId getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <T extends EntityComponent> T get(Class<T> arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void set(EntityComponent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isComplete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public EntityComponent[] getComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
