package sg.atom.ai.movement;

import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import java.util.List;
import sg.atom.ai.Agent;
import sg.atom.ai.steering.Steering;

/**
 * Agent that move.
 *
 * @author CuongNguyen
 */
public interface Vehicle extends Agent {

    List<Steering> getSteeringBehaviors();

    void getTransform(Transform transform);

    Vector3f getVelocity();

    Vector3f getLocation();

    float getRadius();
}
