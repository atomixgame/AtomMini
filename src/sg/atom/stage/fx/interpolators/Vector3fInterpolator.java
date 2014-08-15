/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.fx.interpolators;

import com.jme3.math.Vector3f;
import sg.atom.corex.math.InterpolateFunction;
import sg.atom.corex.math.Tweenf;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Vector3fInterpolator extends InterpolateFunction<Vector3f> {

    public Vector3fInterpolator(Tweenf.Easing easing) {
        this.easing = easing;
    }

    public Vector3f interpolate(Vector3f first, Vector3f next, float amount) {
        Vector3f result = new Vector3f();
        result.interpolate(first, next, amount);
        return result;
    }

    @Override
    protected float getDistance(Vector3f first, Vector3f next) {
        return 1;
    }

    @Override
    protected Float doForward(Vector3f a) {
        return 0F;
    }

    @Override
    protected Vector3f doBackward(Float floatValue) {
        return new Vector3f(floatValue, floatValue, floatValue);
    }
    
}
