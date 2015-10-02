package sg.atom.ai.steering;

import com.jme3.math.Vector3f;
import java.util.Set;
import sg.atom.ai.SpatialBehaviour;
import sg.atom.ai.movement.Vehicle;

/**
 *
 * @author CuongNguyen
 */
public abstract class SteeringBehaviour extends SpatialBehaviour
        implements Steering {

    protected Vector3f location;
    protected Vector3f velocity;
    protected float speed;

    public void updateTransformation() {
    }

    public abstract Set<Vehicle> getVehicles();

    public Vector3f compose(SteeringBehaviour... behaviours) {
        return new Vector3f();
    }

    public Vector3f getLocation() {
        return this.location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }

    public Vector3f getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
