/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Table;
import com.jme3.math.Transform;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import sg.atom.corex.math.Vector2i;
import sg.atom.world.grid.GridMap;
import sg.atom.world.tile.Tilable;
import sg.atom.world.tile.TilingSystem;

/**
 * Contains layers of GridMap. Data structure for points cloud!
 *
 * <p>A backed GridMap is built from initial data. When a Cell data is changed.
 * The backed GridMap is update acordingly. This is a very sotiphicated
 * implementation, for lightweight implementation you should use IntMap2D
 * instead.
 *
 * <p>This Map is also a generic tiling system, which is very common in games.
 * Tiling rules can work over this.
 *
 * <p>The way DefaultMap2D accumulate changes is very similar to a dirty
 * collecting of 2D bitmap, then watch set/get/update of a big table, which seen
 * in common ES for example. Finally it use a BTree to help its owner (MeshMap2D
 * for example) manage updating the underlying Buffer of 3D Mesh instead of do
 * blindy Meshing!
 *
 * <p>Support denote a generic terrain in meaningful way and than store into its
 * data.
 *
 * <p>A DefaultMap2D can generate a RTree, QuadTree, KDTree, HashLife upon its
 * stored data - point clouds for specific to speed up internal query. </p>
 *
 * <li>Read about HashLife for unbound grid:
 * http://en.wikipedia.org/wiki/Hashlife
 *
 * http://www.drdobbs.com/jvm/an-algorithm-for-compressing-space-and-t/184406478
 *
 * http://blog.notdot.net/2009/11/Damn-Cool-Algorithms-Spatial-indexing-with-Quadtrees-and-Hilbert-Curves
 *
 * https://code.google.com/p/xxl/
 *
 * @author cuong.nguyenmanh2
 */
public class DefaultMap2D extends GridMap<Cell2D> implements TilingSystem<Cell2D, Vector2i> {

    public static enum DefaultMapType {

        Normal, Isometric
    }
    DefaultMapType type;
    ArrayList<GridMap> layers;
    Transform mapTransform;

    public DefaultMap2D() {
        super(Cell2D.class);
    }

    public DefaultMap2D(int width, int height) {
        super(Cell2D.class, width, height);
    }

    // Layer management -----------------------------------------------------
    public List<GridMap> getLayers() {
        return new ArrayList<GridMap>();
    }

    public void addLayer(GridMap layer) {
        layers.add(layer);
    }

    public List<Map2D> getChildren() {
        List<Map2D> result = new LinkedList<Map2D>();
        result.addAll(layers);
        return result;
    }

    public GridMap getLayer(int index) {
        return layers.get(index);
    }

    public void removeLayer(int index) {
        layers.remove(index);
    }

    public void removeLayer(GridMap layer) {
        layers.remove(layer);
    }
    // Positional --------------------------------------------------------------

    public boolean isInside(Pair position) {
        for (GridMap c : layers) {
            if (c.isInside(position)) {
                return true;
            }
        }
        return false;
    }

    
    //Data & Query -------------------------------------------------------------
    public Cell2D getAt(Pair position) {
        Cell2D locatedCell = this.getAt(position);
        if (locatedCell.isDirty()) {
            //this.put(position, this);
        }
        return null;
    }
    
    public void query(Object q) {
    }

    
    public Cell2D buildCellData(Pair position) {
        Cell2D newCell = new Cell2D(this);
        List result = new LinkedList();
        for (GridMap c : layers) {
            result.add(c.getAt(position));
        }
        newCell.setData(result);
        return newCell;
    }


    public Cell2D putAt(Pair<Integer, Integer> position, Cell2D tile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    public void process(Map2DProcessor processor){
//        
//    }
//    
//    public void generate(Map2DGenerator generator){
//        
//    }
    // Tiling ------------------------------------------------------------------

    public Tilable getTileAt(Vector2i location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void setTileAt(Vector2i location, Tilable tile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void fillTiles(Vector2i location, Tilable tile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void fillTiles(Vector2i location, Function<Tilable, Tilable> function) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void fillTiles(Vector2i location, Predicate<Tilable> condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Collection<Tilable> getRelatives(Vector2i location, int travel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Collection<Tilable> getRelatives(Vector2i location, Predicate<Tilable> condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//
//    public Map<Integer, Tilable> getRelativesMap(Vector2i location, Predicate<Tilable> condition) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }


    public Map2D<Tilable> getFullMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


//    public Table<Integer, Integer, Tilable> getRelativesAligned(Vector2i location, Predicate<Tilable> condition) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }


    public void notifyTileEvent(Vector2i location, Tilable tile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    // Events ------------------------------------------------------------------
    
    // GETTER & SETTER ---------------------------------------------------------
    
}
