package sg.atom.corex.fx;

/**
 * Performs some interpolated operations based on a value between 0 and 1.
 *
 * @author Paul Speed
 */
public interface Interpolator {

    public void interpolate(double t);
}
