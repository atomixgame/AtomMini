/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.map;

import com.jme3.math.Vector2f;
import java.util.List;
import java.util.Set;
//import sg.atom.utils.datastructure.collection.Pair;

/**
 * Positional map in 2D.
 *
 * <p>Contains children (layers, coodinates, entities). Coordinate type is
 * Tuple, implementations can involve Vector or other types!
 *
 * <p>Bound check, range check.
 *
 * <p>Storing facilities
 *
 * <p>Pair is generic container represent 2 dimentional object. This class is
 * unsafe but for the ease of use is fine.
 *
 * @author cuong.nguyenmanh2
 */
public interface Map2D<T> {

    public static enum MapUsage {

        Native, Full, Dense, Sparse, Single, None;
    };

    public List<Map2D> getChildren();

    public boolean isInside(Vector2f position);

    public T getAt(Vector2f position);

    public T putAt(Vector2f position, T tile);

    public Set<Vector2f> coveredPosition();
    
    public MapUsage getMapUsage();
}
