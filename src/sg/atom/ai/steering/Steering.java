package sg.atom.ai.steering;

/**
 *
 * @author cuongnguyen
 */
import com.jme3.math.Vector3f;

public abstract interface Steering {

    void interpolated(float paramFloat);

    Vector3f calculateForce();

    boolean isEnabled();
}
