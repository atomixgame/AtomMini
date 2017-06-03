package sg.atom.corex.managers;

import com.google.common.base.Predicate;
import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.Service;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.logic.Trigger;

/**
 *
 * @author CuongNguyen
 */
public class TriggerManager extends AbstractManager {
    // You can also write an executor instead of update loop.

    Service service;
    EventBus eventBus;
    // A Map or a Class your choice!
    BiMap<Spatial, Trigger> spatialTriggers;
    Multimap<Trigger, Predicate> predicates;
    ArrayList<Trigger> triggers;

    public TriggerManager(AtomMain app) {
        super(app);
    }

    public void update(float tpf) {
        for (Trigger trigger : triggers) {
//            if (Predicates.from(predicates.get(trigger)).apply(spatialTriggers.get(trigger)) {
//                trigger.active();
//            }
        }
    }

//    public void 
}
