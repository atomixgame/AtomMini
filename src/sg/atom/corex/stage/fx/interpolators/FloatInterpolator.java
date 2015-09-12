/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage.fx.interpolators;

import sg.atom.corex.math.InterpolateFunction;
import sg.atom.corex.math.Tweenf;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FloatInterpolator extends InterpolateFunction<Float> {

    public FloatInterpolator(Tweenf.Easing easing) {
        this.easing = easing;
    }

    @Override
    protected float getDistance(Float first, Float next) {
        return next - first;
    }

    @Override
    protected Float doForward(Float a) {
        return a;
    }

    @Override
    protected Float doBackward(Float b) {
        return b;
    }

    public Float interpolate(Float first, Float next, float amount) {
        return getEasing().ease(amount, first, next - first, 1);
    }
    
}
