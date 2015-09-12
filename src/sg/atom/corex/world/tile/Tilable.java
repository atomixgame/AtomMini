/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.tile;

import sg.atom.corex.math.Vector2i;

/**
 * Tiable is a interface for object that meant to be tiled around in space.
 *
 * <p>It has to have positional attributes and should be clonable. The "tiling"
 * also include the meaning that a Tile should be "relative" to others in a
 * context. This context can be a sheet or a result of tiling job.
 *
 * <p>Align proposed a method for the tiler to "tile" this object.
 *
 * @author cuong.nguyenmanh2
 */
public interface Tilable<C> extends Cloneable {
    public boolean isOriginal();

    public Vector2i getPosition();

    public Object getRelative(Vector2i otherPosition);

    public void setRelative(Tilable otherTile, Vector2i otherPosition);

    public void align(Vector2i position);
}
