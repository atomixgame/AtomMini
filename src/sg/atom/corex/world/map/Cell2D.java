/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.map;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import sg.atom.corex.geo.Rectanglef;
import sg.atom.corex.math.Vector2i;
import sg.atom.corex.world.tile.Tilable;

/**
 * A Simple 2D Cell implemented to be element of primary GridMap of the game.
 *
 * <p>Cell contain internal data of a type. Cell can be nested but are not
 * contracted to be aligned. It's different from the GridMap. Beside of the
 * primary data, a Cell also can stored arbitrary data as decorators.
 *
 * <p>One example for this decorated attributes is the mark of adjacencies cells
 * like in a tiling system. Remember that the structure of this arbitrary data
 * is unclear. That mean you have a Map of properties and have to cast it to
 * something meaningful by your self. For high performance using of this Cell
 * class, please use typed data or built-in data. As said, this implementation
 * of Cell natively support: geographics, spatial indexing, path finding.
 *
 * <p>Built-in data:<ul>
 * <li>Coordinate data is Float, Containment data is Integers, Booleans.
 *
 * <li>For gridmap and tiling: 4 integer for 4 adjacencies cells
 *
 * <li>For indexing: 16 bytes for hash
 *
 * <li>For path finding: 5 integers and 5 booleans. </ul>
 *
 * @author cuong.nguyenmanh2
 */
public class Cell2D<T> implements Map2D<T>, Tilable {

    protected Map2D parent;
    //Attributes -------------------------------------------------------
    protected int _x, _y;
    //Pathfinding 
    public int type;
    public int height; // and to 3d.
    public int texType;
    public int status;
    public int group;
    public boolean visited, checked, actived, walkable, visible;
    // adjacencies
    public int adjLeft, adjRight, adjTop, adjBottom;
    public int index;
    //indexing
    protected byte[] _hashCode;
    //real data
    protected T data;
    // nested cells.
    protected ArrayList<Cell2D> children;
    //geometry
    protected Rectanglef bound;
    //FIXME: From Tile2D
    Properties properties;
    public Vector2f topLeft = new Vector2f();
    public Vector2f topRight = new Vector2f();
    public Vector2f bottomRight = new Vector2f();
    public Vector2f bottomLeft = new Vector2f();
    private boolean original;

    public Cell2D(Map2D parent) {
        this.parent = parent;
    }

    public Cell2D(Map2D parent, T data) {
        this.parent = parent;
        this.data = data;
    }

    public Cell2D(Vector2f topLeft, Vector2f topRight, Vector2f bottomRight, Vector2f bottomLeft) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }

    public Properties getProperties() {
        return properties;
    }

    public Vector2f getLocation2D() {
        return new Vector2f();
    }

    public Vector3f getLocation3D() {
        return new Vector3f();
    }

    public T getData() {
        return data;
    }

    public List getChildren() {
        return children;
    }

    public boolean isInside(Pair<Float, Float> position) {
        return bound.isInside(position.getLeft(), position.getRight());
    }

    public boolean isInside(int x, int y) {
        return bound.isInside(x, y);
    }

    public T getAt(Pair<Float, Float> position) {
//        ArrayList<Cell2D> result = new ArrayList<Cell2D>();
//        for (Cell2D c : children) {
//            if (c.isInside(position)) {
//                result.add(c);
//            }
//        }
        return data;
    }

    public Set coveredPosition() {
        HashSet result = new HashSet();
        result.add(bound);
        return result;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isDirty() {
        return false;
    }

    public T putAt(Vector2f position, T tile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    public Vector2f getPosition() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    public Object getRelative(Vector2i otherPosition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setRelative(Tilable otherTile, Vector2i otherPosition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void align(Vector2i position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isOriginal() {
        return original;
    }

    public void setTileBound(Vector2f v1, Vector2f v2, Vector2f v3, Vector2f v4) {
        topLeft = v1;
        topRight = v2;
        bottomLeft = v3;
        bottomRight = v4;
    }

    public void setTileBoundLocal(Vector2f v1, Vector2f v2, Vector2f v3, Vector2f v4) {
        topLeft.set(v1);
        topRight.set(v2);
        bottomLeft.set(v3);
        bottomRight.set(v4);
    }

    public MapUsage getMapUsage() {
        return MapUsage.Single;
    }

    public boolean isInside(Vector2f position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public T getAt(Vector2f position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Vector2i getPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
