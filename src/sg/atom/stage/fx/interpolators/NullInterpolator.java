/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.fx.interpolators;

import sg.atom.corex.math.InterpolateFunction;
import sg.atom.corex.math.Tweenf;

/**
 *
 * @author CuongNguyen
 */
public class NullInterpolator extends InterpolateFunction<Object> {

    public NullInterpolator(Tweenf.Easing easing) {
        this.easing = easing;
    }

    @Override
    protected float getDistance(Object first, Object next) {
        return 0;
    }

    @Override
    protected Float doForward(Object a) {
        return new Float(0);
    }

    @Override
    protected Object doBackward(Float b) {
        return null;
    }

    public Object interpolate(Object first, Object next, float amount) {
        return first;
    }
}
