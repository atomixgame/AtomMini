/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.fx.interpolators;

import com.jme3.math.ColorRGBA;
import sg.atom.corex.math.InterpolateFunction;
import sg.atom.corex.math.Tweenf;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ColorRGBAInterpolator extends InterpolateFunction<ColorRGBA> {

    public ColorRGBAInterpolator(Tweenf.Easing easing) {
        this.easing = easing;
    }

    public ColorRGBA interpolate(ColorRGBA first, ColorRGBA next, float amount) {
        ColorRGBA result = new ColorRGBA();
        result.interpolate(first, next, amount);
        return result;
    }

    @Override
    protected float getDistance(ColorRGBA first, ColorRGBA next) {
        return 1;
    }

    @Override
    protected Float doForward(ColorRGBA a) {
        return 0F;
    }

    @Override
    protected ColorRGBA doBackward(Float floatValue) {
        return new ColorRGBA(floatValue, floatValue, floatValue, floatValue);
    }
    
}
