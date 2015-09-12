/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.artemis;

import com.artemis.Entity;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.corex.managers.StageManager;

/**
 * An simple EntityManager implementation which have basic Spatial - Entity
 * relationship management.
 *
 * <ul>
 *
 * <li>It has a Cache implementation of original entities beside of one in
 * AssetManager.</li>
 *
 * <li> Also support dependency injection to create Entity.</li>
 *
 * <li> It hashed its managed entities with regular "good" hash method to manage
 * indexing.</li>
 *
 * <li> provides Functions like
 * set/get/insert/remove/swap/replace/lookup/search/scanning/distributing/inheritance
 * inspecting over its managed entities. <b>aka. 1 step ref or direct
 * modification</b></li>
 *
 * <li> Provide services over its managed entities. <b>aka. 2 steps
 * refs</b></li>
 *
 * <li> Provide reflection, references/ versioned tricks and other functions
 * over lookup result of Components. <b>aka. 3 steps refs</b></li>
 *
 * <li> open gates to manage also components of Entities (like other ES
 * implementation). In the moment of speech, other ES implementation "usually"
 * manage a "isolated view" of available components. Its meant to be using
 * "Class level of atomicity" in Java language!</li>
 *
 * <li> Most important, with reactive facilities. It open gates to managed actor
 * framework and the transactional memory model.</li> </ul>
 *
 * <p><b>NOTE:</b> Overview, the system works like this: <ul>
 *
 * <li>A waving of access (can be asynchoronous as in concurrent processing...)
 * to Entities lead to a waving of lookup (non-access) to their associated
 * Component(s).
 *
 * <li>The reference allow direct modification or transactional modification.
 *
 * <li> </p>
 *
 * <p>FIXME: Replace or intergrate with Zay-ES or Artemis.
 *
 * @author atomix
 */
@Deprecated
public class ArtermisEntityManager implements IGameCycle {

    protected StageManager stageManager;
    protected ArtermisEntityFactory entityFactory;
    // Entity management 
    protected HashMap<Long, Entity> entities = new HashMap<Long, Entity>();
    private long totalEntityId = -1;
    public static long NONE_ID = -1;
    // Lookup
//    protected Function<AbstractEntity, AbstractComponent> lookupFunction = null;
    // Services

    public ArtermisEntityManager(StageManager stageManager) {
        this.stageManager = stageManager;
        this.entityFactory = new ArtermisEntityFactory(this);
    }

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

    public void addEntity(Entity e) {
//        if (e.id == null || e.id == NONE_ID) {
//            Long newId = getNewEntityId();
//            e.id = newId;
//        }
//        entities.put(e.id, e);
    }

    public void removeEntity(Long id) {
        entities.remove(id);
    }

    public void removeEntity(Entity e) {
//        entities.remove(e.id);
    }

    /**
     * Should be overiden to determinate the relationship between a spatial and
     * its associated Entity
     *
     * @param selectableSpatial
     * @return
     */
    public boolean isEntitySpatial(Spatial selectableSpatial) {
        return true;
    }
    /* Search and filter over entities */

    public ArrayList<ArtermisSpatialEntity> getAllSpatialEntities() {
        // do filter...
        ArrayList<ArtermisSpatialEntity> result = new ArrayList<ArtermisSpatialEntity>();
        for (Entity entity : entities.values()) {
//            if (entity instanceof SpatialEntity) {
//                result.add((SpatialEntity) entity);
//            }
        }
        //
        return result;
    }

    public ArrayList<ArtermisSpatialEntity> getAllSpatialEntitiesByGroup(String groupName) {
        // do filter...
        ArrayList<ArtermisSpatialEntity> result = new ArrayList<ArtermisSpatialEntity>();

        for (Entity entity : entities.values()) {
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

    public <T extends Entity> ArrayList<T> getEntitiesByClass(Class<T> clazz) {
        // do filter...
        ArrayList<T> result = new ArrayList<T>();
        for (Entity entity : entities.values()) {
            if (clazz.isAssignableFrom(entity.getClass())) {
                result.add((T) entity);
            }
        }
        //
        return result;
    }
    // For Collections of Characters!-----------------------------------------

    public static List<Entity> getBy(List<Entity> characters, Predicate<Entity> predicate) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        for (Entity gc : characters) {
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public static List<Entity> getBy(List<Entity> characters, Predicate<Entity>... filters) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        for (Entity gc : characters) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public List<Entity> getBy(Predicate<Entity>... filters) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        for (Entity gc : entities.values()) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public <T extends ArtermisEntityFactory> T getEntityFactory(Class<T> clazz) {
        return (T) entityFactory;
    }

    public Entity toEntity(Spatial sp) {
        return null;
    }

    public Entity getEntityBySpatial(Spatial spatial) {
        return null;
    }

    public Entity getEntityById(long id) {
        return entities.get(id);
    }

    public void setEntityById(long id, Entity newEntity) {
        entities.put(id, newEntity);
    }


    public void init() {
    }


    public void load() {
    }


    public void config(Configuration props) {
    }


    public void update(float tpf) {
        //For sometime we will require a consist view.
        //framework.get
    }


    public void finish() {
    }

}
