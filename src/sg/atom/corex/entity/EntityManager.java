/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.managers.StageManager;

/**
 * relationship management.
 *
 * <ul>
 *
 * <li>It has a Cache implementation of original entities beside of one in
 * AssetManager.</li>
 *
 * @author atomix
 */
@Deprecated
public class EntityManager extends AbstractManager {

    public static final Logger logger = LoggerFactory.getLogger(EntityManager.class.getName());
    protected StageManager stageManager;
    protected EntityFactory entityFactory;
    // ComposableEntity management 
    protected ConcurrentMap<Long, ComposableEntity> entities = new MapMaker()
            .concurrencyLevel(10)
            .weakKeys()
            .weakValues().makeMap();
    protected HashMap<String, Node> nodes = new HashMap<String, Node>();
    private long totalEntityId = -1;
    public static long DEFAULT_NONE_ID = SpatialEntity.DEFAULT_NONE_ID;

    public EntityManager(){
        
    }
    
    @Inject
    public EntityManager(AtomMain app) {
        super(app);
    }

    public static class EntityEvent {

        long timeStamp;
        ComposableEntity entity;
        long entityId;

        public EntityEvent(ComposableEntity e) {
            this.entity = e;
            this.entityId = e.getIid();
        }

        public EntityEvent(ComposableEntity e, long timeStamp) {
            this.timeStamp = timeStamp;
            this.entity = e;
            this.entityId = e.getIid();
        }

        public ComposableEntity getEntity() {
            return entity;
        }

        public long getEntityId() {
            return entityId;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }
    }

    public static class EntityAddEvent extends EntityEvent {

        public EntityAddEvent(ComposableEntity e) {
            super(e);
        }

        public EntityAddEvent(ComposableEntity e, long timeStamp) {
            super(e, timeStamp);
        }
    }

    public static class EntityRemovalEvent extends EntityEvent {

        public EntityRemovalEvent(ComposableEntity e, long timeStamp) {
            super(e, timeStamp);
        }

        public EntityRemovalEvent(ComposableEntity e) {
            super(e);
        }
    }
//    public static class EntityRemoteEvent {
//        
//    }
    /* Manage entities's type as primary lookup methods */
    public void registerEntityType() {
    }

    public void registerEntityTypes() {
    }

    public ArrayList<String> getEntityAssets() {
        return new ArrayList<String>();
    }

    public Long getNewEntityId() {
        totalEntityId++;
        return new Long(totalEntityId);
    }

    public void addEntity(SpatialEntity e) {
        if (e.iid == DEFAULT_NONE_ID) {
            Long newId = getNewEntityId();
            e.iid = newId;
        }
        entities.put(e.iid, e);
        app.getEventBus().post(new EntityAddEvent(e, app.getStopwatch().elapsed(TimeUnit.MICROSECONDS)));
    }

    public ComposableEntity removeEntity(Long id) {
        ComposableEntity e = entities.remove(id);
        app.getEventBus().post(new EntityRemovalEvent(e, app.getStopwatch().elapsed(TimeUnit.MICROSECONDS)));
        return e;
    }

    public ComposableEntity removeEntity(ComposableEntity e) {
        return removeEntity(e.getIid());
    }

    /**
     * Should be overiden to determinate the relationship between a spatial and
     * its associated ComposableEntity
     *
     * @param selectableSpatial
     * @return
     */
    public boolean isEntitySpatial(Spatial selectableSpatial) {
        return true;
    }

    public boolean isHasNoId(SpatialEntity entity) {
        return entity.iid == SpatialEntity.DEFAULT_NONE_ID;
    }
    /* Search and filter over entities */

    public ArrayList<SpatialEntity> getAllSpatialEntities() {
        // do filter...
        ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();
        for (ComposableEntity entity : entities.values()) {
            if (entity instanceof SpatialEntity) {
                result.add((SpatialEntity) entity);
            }
        }
        //
        return result;
    }

    public ArrayList<SpatialEntity> getAllSpatialEntitiesByGroup(String groupName) {
        // do filter...
        ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();

        for (ComposableEntity entity : entities.values()) {
            //System.out.println(" ByGroup " + entity.id);
//            if (entity instanceof SpatialEntity) {
//                if (entity.getGroup().equals(groupName)) {
//                    result.add((SpatialEntity) entity);
//                }
//            }
        }
        //
        return result;
    }

    public <T extends ComposableEntity> ArrayList<T> getEntitiesByClass(Class<T> clazz) {
        // do filter...
        ArrayList<T> result = new ArrayList<T>();
        for (ComposableEntity entity : entities.values()) {
            if (clazz.isAssignableFrom(entity.getClass())) {
                result.add((T) entity);
            }
        }
        //
        return result;
    }

    //Cycle--------------------------------------------------------------------

    public void init() {
        this.entityFactory = new EntityFactory(app);
        this.setEnabled(true);
    }

    public void load() {
    }

    public void config(Configuration props) {
    }

    @Override
    public void update(float tpf) {
        if (actived) {
            if (!customCycle) {
                //For sometime we will require a consist view.
                Iterator<ComposableEntity> iterator = entities.values().iterator();
                while (iterator.hasNext()) {
                    ComposableEntity entity = iterator.next();
                    if (entity instanceof SpatialEntity) {
                        ((SpatialEntity) entity).update(tpf);
                    }
                }
            }
        }
    }

    public void finish() {
    }
    //GETTER & SETTER
    // For Collections of Entities!-----------------------------------------

    public static List<ComposableEntity> getBy(List<ComposableEntity> characters, Predicate<ComposableEntity> predicate) {
        ArrayList<ComposableEntity> result = new ArrayList<ComposableEntity>();
        for (ComposableEntity gc : characters) {
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public static List<ComposableEntity> getBy(List<ComposableEntity> characters, Predicate<ComposableEntity>... filters) {
        ArrayList<ComposableEntity> result = new ArrayList<ComposableEntity>();
        for (ComposableEntity gc : characters) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public List<ComposableEntity> getBy(Predicate<ComposableEntity>... filters) {
        ArrayList<ComposableEntity> result = new ArrayList<ComposableEntity>();
        for (ComposableEntity entity : entities.values()) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(entity)) {
                result.add(entity);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public SpatialEntity getEntityFrom(Spatial sp) {
        SpatialEntity result = null;
        Iterator<ComposableEntity> iterator = entities.values().iterator();
        while (iterator.hasNext()) {
            ComposableEntity entity = iterator.next();
            if (entity instanceof SpatialEntity) {
                Spatial esp = ((SpatialEntity) entity).getSpatial();
                if (esp instanceof Node && sp.hasAncestor((Node) esp)) {
                    result = (SpatialEntity) entity;
                    break;
                } else if (sp == esp) {
                    result = (SpatialEntity) entity;
                    break;
                }
            }
        }
        return result;
    }

    public List<SpatialEntity> getAllEntitiesFrom(Node node) {
        final ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();

        return result;
    }

    public ComposableEntity getEntityById(long id) {
        return entities.get(id);
    }

    public void setEntityById(long id, ComposableEntity newEntity) {
        entities.put(id, newEntity);
    }

    public <T extends EntityFactory> T getEntityFactory(Class<T> clazz) {
        return (T) entityFactory;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }
}
