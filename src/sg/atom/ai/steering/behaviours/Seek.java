package sg.atom.ai.steering.behaviours;

import com.jme3.math.Vector3f;
import com.jme3.scene.control.Control;
import java.util.Set;
import sg.atom.ai.Agent;
import sg.atom.ai.movement.Vehicle;
import sg.atom.ai.steering.SteeringBehaviour;

/**
 *
 * @author cuongnguyen
 */
public class Seek extends SteeringBehaviour {

    Vector3f target;

    public Seek() {
    }

    public Seek(Vector3f location, Vector3f velocity, float speed, Vector3f target) {
        this.location = location;
        this.velocity = velocity;
        this.speed = speed;
        this.target = target;
    }

    public Vector3f calculateForce(Vector3f location, Vector3f velocity, float speed, Vector3f target) {
        Vector3f desiredVel = target.subtract(location).normalize().mult(speed);
        Vector3f steering = desiredVel.subtract(velocity);

        return steering;
    }

    public Vector3f calculateForce() {
        return calculateForce(this.location, this.velocity, this.speed, this.target);
    }

    public Vector3f getTarget() {
        return this.target;
    }

    public void setTarget(Vector3f target) {
        this.target = target;
    }

    public Set<Vehicle> getVehicles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void interpolated(float percent) {
    }

    public Control getControl() {
        return this;
    }

    public void active(Agent input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deactive(Agent input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
