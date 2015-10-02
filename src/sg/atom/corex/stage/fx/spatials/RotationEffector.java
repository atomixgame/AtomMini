package sg.atom.corex.stage.fx.spatials;

import com.jme3.math.Quaternion;
import com.jme3.scene.Spatial;
import sg.atom.corex.stage.fx.Effector;

/**
 *
 * @author cuong.nguyen
 */
public class RotationEffector implements Effector<Spatial> {
    
    public void affect(Spatial object, String propertyName, Object value) {
        object.setLocalRotation((Quaternion) value);
    }
}
