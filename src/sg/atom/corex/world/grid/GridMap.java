/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.grid;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Table;
import com.jme3.math.Vector2f;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import sg.atom.corex.math.Vector2i;
import sg.atom.corex.world.map.Map2D;
import sg.atom.corex.world.map.Map2D.MapUsage;
//import sg.atom.utils.datastructure.collection.Pair;


/**
 * A Generic Grid map with each Cell is an object with utils. A GridMap is a
 * discrete field of T value with integer coordinate. It's a data structure to
 * store objects whom coordinate aligned in a grid. Consider a GridMap of T can
 * act like a 2Dimensional Array of T for example if the GridMap 's dense. You
 * can tell it made of boxes, yes, other implemenation of grids are hex and tris
 * also provided within atom2d framework. This is a very sotiphicated
 * implementation, for lightweight implementation you should use IntMap2D
 * instead.
 *
 * <p>Under the curtain, it backed with a 2d array of Cell object if it's dense
 * or a Guava Table if it's spare. So there are two available constructors for
 * those purpose. This map can also be filled and painted like a bitmap,
 * generate a buffer on it, conver from and to bytes.
 *
 * <p>A GridMap can generate a RTree or QuadTree upon its stored data to speed
 * up internal query. </p>
 *
 * <p>Ten common "walk" functions avaiable in directions: <ul><li>Left, Right,
 * Top, Bottom</ul> A custom external "walk" function can be specified to run
 * upon its stored data if need. More over, GridMap can generate a Graph upon
 * its walkable/non walkable area.
 *
 * <p>Another common functions is to compare/compose maps with same
 * "resolution", which is not only same size or kind of same data meaning. Walk
 * and bitset compare in this scenario also supported.
 *
 * <p>This implementation inspired by a lot articles in the internet about
 * GridMap implementation:
 *
 * <ul> <li>DataStructure for game programmers</ul>
 *
 * <p>If you save a giant map, you may interested in streaming your map into the
 * logic or rendering pipeline. This enabled by partitioning your map and save
 * into trunk of tiles. A directional advide are also can be configed for
 * streaming scheme. Read:
 * http://gamearchitect.net/Articles/StreamingBestiary.html
 *
 * @author cuong.nguyenmanh2
 */
public class GridMap<T> implements Map2D<T> {

    public static int DEFAUL_MAP_SIZE = 100;
    public static int DEFAUL_MAP_SIZE_WIDTH = 100;
    public static int DEFAUL_MAP_SIZE_HEIGHT = 100;
    protected int width;
    protected int height;
    public MapUsage mapUsage;
    private T[][] mapArray;
    private Table<Integer, Integer, T> mapTable;
    private HashMap<T, Properties> mapProperties;
    private static Object travelResult;
    private boolean enableFullSearch = true;
    private boolean immutable = false;
    private GridTravel.GridTravelMethod defaultTravelMethod;

    public GridMap(Class<T> clazz) {
        this(clazz, DEFAUL_MAP_SIZE_WIDTH, DEFAUL_MAP_SIZE_HEIGHT, MapUsage.Native, false);
    }

    public GridMap(Class<T> clazz, int width, int height) {
        this(clazz, width, height, MapUsage.Native, false);
    }

    public GridMap(Class<T> clazz, int width, int height, MapUsage density, boolean immutable) {
        this.width = width;
        this.height = height;
        this.immutable = immutable;
        this.mapUsage = density;
        if (density == MapUsage.Native) {
            this.mapArray = (T[][]) Array.newInstance(clazz, width, height);
        } else if (immutable) {
            //this.mapTable = ImmutableTable.builder().
            //this.mapTable = ImmutableTable.of(0, 0, width);
        } else {
            if (density == MapUsage.Dense) {
                this.mapTable = ArrayTable.create(createRangeSet(width), createRangeSet(height));
            } else {
                this.mapTable = HashBasedTable.create(width, height);
            }
        }
    }

    public static <T> GridMap create(Class<T> clazz, int width, int height, MapUsage density) {
        return new GridMap(clazz, width, height, density, false);
    }

    public int size() {
        return width * height;
    }

    public boolean isEmpty() {
        if (mapUsage.equals(MapUsage.Native)) {
            return false;
        } else {
            return mapTable.isEmpty();
        }
    }

    public boolean containsKey(Object key) {
        if (key != null && key instanceof Pair) {
            return isInside((Pair) key);
        } else {
            return false;
        }
    }

    public boolean containsValue(Object value) {
        if (mapUsage == MapUsage.Native) {
            if (!enableFullSearch) {
                throw new UnsupportedOperationException("Not supported yet.");
            } else {
                /*
                 //FIXME: Better search. Foodfill and random search
                 travelColRow(new GridTravel<Boolean>() {
                 @Override
                 public Boolean visit(int x, int y) {
                 throw new UnsupportedOperationException("Not supported yet.");
                 }
                 });
                 */
                return false;
            }
        } else {
            return mapTable.containsValue(value);
        }
    }

    public T remove(Object key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public T remove(Pair<Integer, Integer> key) {
        if (mapUsage == MapUsage.Native) {
            mapArray[key.getLeft()][key.getRight()] = null;
        } else if (mapUsage == MapUsage.Dense) {
            ((ArrayTable) mapTable).erase(key.getLeft(), key.getRight());
        } else {
            mapTable.remove(key.getLeft(), key.getRight());
        }
        return null;
    }

    public List<Map2D> getChildren() {
        return null;
    }

    public boolean isInside(Pair position) {
        return !isOutOfBorder((Integer) position.getLeft(), (Integer) position.getLeft());
    }

    public Set<Vector2f> coveredPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void putAll(Map<? extends Pair<Integer, Integer>, ? extends T> m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public T putAt(Pair<Integer, Integer> position, T tile) {
        return putAt(((Integer) position.getLeft()).intValue(), ((Integer) position.getRight()).intValue(), tile);
    }

    public T putAt(int x, int y, T tile) {
        if (this.mapUsage == MapUsage.Native) {
            mapArray[x][y] = tile;
            return tile;
        } else {
            return mapTable.put(x, y, tile);
        }
    }

    public T put(Pair<Integer, Integer> key, T value) {
        return putAt(((Integer) key.getLeft()).intValue(), ((Integer) key.getRight()).intValue(), value);
    }

    public T get(Object key) {
        if (key != null && key instanceof Pair) {
            return getAt((Pair) key);
        } else {
            return null;
        }
    }

    public T getAt(int x, int y) {
        if (this.mapUsage == MapUsage.Native) {
            return mapArray[x][y];
        } else {
            return mapTable.get(x, y);
        }
    }

    public T getAt(Pair position) {
        return getAt(position.getLeft(), position.getLeft());
    }

    public T getAt(Object x, Object y) {
        if (this.mapUsage == MapUsage.Native) {
            return mapArray[((Integer) x).intValue()][((Integer) y).intValue()];
        } else {
            return mapTable.get(x, y);
        }
    }

    public void clear() {
        if (mapUsage == MapUsage.Native) {
            Arrays.fill(mapArray, null);
        } else {
            this.mapTable.clear();
        }
    }

    public Set<Pair<Integer, Integer>> keySet() {
        LinkedHashSet<Pair<Integer, Integer>> result = new LinkedHashSet<Pair<Integer, Integer>>(width * height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; i < height; i++) {
                result.add(new ImmutablePair<Integer, Integer>(i, j));
            }
        }
        return result;
    }

    public Collection<T> values() {
        if (mapUsage == MapUsage.Native) {
            return Lists.newArrayList((T[]) mapArray);
        } else {
            return mapTable.values();
        }
    }

    public Set<Entry<Pair<Integer, Integer>, T>> entrySet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterable<Integer> createRangeSet(int max) {
        return ContiguousSet.create(Range.closed(0, max), DiscreteDomain.integers());
    }

    public void buildMap() {
        // init and build map array
    }
    //GEOGRAPHIC ----------------------------------------------------------

    public T adjacentTo(T cell, GridTravel.GridRelativePostion position) {
        return null;
    }

    public T adjacentTo(T cell, GridTravel.GridDirection direction) {
        return null;
    }

    public T nextTo(T cell) {
        return null;
    }

    public T leftTo(T cell) {
        return null;
    }

    public T rightTo(T cell) {
        return null;
    }

    public T topTo(T cell) {
        return null;
    }

    public T bottomTo(T cell) {
        return null;
    }
    // Some bitmap functions----------------------------------------------------

    public void paint(Map2D brush) {
    }
    //TRAVEL----------------------------------------------------------

    public void travelRow(int row) {
    }

    public void travelCol(int col) {
    }

    public void travelRowCol(GridTravel action) {
        //FIXME: Better search. Foodfill and random search
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                travelResult = action.visit(x, y, getAt(x, y));
            }
        }
    }

    public void travelColRow(GridTravel action) {
        //FIXME: Better search. Foodfill and random search
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                travelResult = action.visit(x, y, getAt(x, y));
            }
        }
    }

    public void travelFloodFill(int x, int y) {
    }
    //TRANSFORM----------------------------------------------------------

    // SETTER & GETTER----------------------------------------------------------
    public T[][] getRawView(Class<T> clazz) {
        if (mapArray != null) {
            return mapArray;
        } else {
            return (T[][]) ((ArrayTable) mapTable).toArray(clazz);
        }
    }

    public Table<Integer, Integer, T> getTableView() {

        return mapTable;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isOutOfBorder(int x, int y) {
        return ((x >= width) || (x < 0) || (y >= height) || (y < 0));
    }

    public int totalIndexedTile() {
        return 0;
    }

    public HashMap<T, Properties> getMapProperties() {
        return mapProperties;
    }

    public Properties getMapProperty(T cell) {
        return mapProperties.get(cell);
    }

    public MapUsage getMapUsage() {
        return mapUsage;
    }

    public boolean isInside(Vector2f position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public T getAt(Vector2f position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public T putAt(Vector2f position, T tile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
