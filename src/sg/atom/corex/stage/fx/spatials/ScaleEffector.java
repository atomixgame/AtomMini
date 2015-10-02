/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage.fx.spatials;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import sg.atom.corex.stage.fx.Effector;

/**
 *
 * @author cuong.nguyen
 */
public class ScaleEffector implements Effector<Spatial> {

    public void affect(Spatial object, String propertyName, Object value) {
        object.setLocalScale((Vector3f) value);
    }
}
