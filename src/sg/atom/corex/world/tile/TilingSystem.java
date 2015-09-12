/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.tile;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import java.util.Collection;
import sg.atom.corex.world.map.Map2D;

/**
 * A tiling system offer a context for tiling jobs and handy methods for Tile,
 * TileSheet and TileRule.
 *
 * <p>A TilingSystem is a "Common" approach for tiling problem in common games.
 * It's the "one place" for tiling job, has any info a Tile,TileSheet,TileRule
 * needs. Manage them. And more over, a bridge to access the overlay data above/
 * outside of the system.</p>
 *
 * <p>For example: if a grass tile only grow in the highland country, in which
 * the TileRule have to ask the "height" of the Cell in the GridMap. A Guava
 * Function has to transform the real location of the Tile embeding in the
 * GridMap and return it to the TileRule.</p>
 *
 * <p>TilingSystem also should provide a a monitoring facilities for its
 * operated Tiling jobs. This is a simple of ShapeGrammarSystem architecture.
 * Take a look at CityGen framework to learn more about ShapeGrammar.
 *
 * @author cuong.nguyenmanh2
 */
public interface TilingSystem<T extends Tilable, C> {

    public Tilable getTileAt(C location);

    public void setTileAt(C location, Tilable tile);

    public void fillTiles(C location, Tilable tile);

    public void fillTiles(C location, Function<Tilable, Tilable> function);

    public void fillTiles(C location, Predicate<Tilable> condition);

    public Collection<Tilable> getRelatives(C location, int travel);

    public Collection<Tilable> getRelatives(C location, Predicate<Tilable> condition);

//    public Map<C, Tilable> getRelativesMap(C location, Predicate<Tilable> condition);

    public Map2D<Tilable> getFullMap();

//    public Table<Integer, Integer, Tilable> getRelativesAligned(C location, Predicate<Tilable> condition);

    public void notifyTileEvent(C location, Tilable tile);
}
