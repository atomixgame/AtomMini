/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.map;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.List;

/**
 *
 * @author CuongNguyen
 */
public class MapRegion {

    public static final int LEFT_TOP = 0;
    public static final int CENTER_TOP = 1;
    public static final int RIGHT_TOP = 2;
    public static final int LEFT_MID = 3;
    public static final int CENTER_MID = 4;
    public static final int RIGHT_MID = 5;
    public static final int LEFT_BOTTOM = 6;
    public static final int CENTER_BOTTOM = 7;
    public static final int RIGHT_BOTTOM = 8;
    public int id;
    public int gridX;
    public int gridY;
    public String modelPath;
    public Spatial model;
    public boolean loaded = false;
    public boolean actived = false;
    public boolean current = false;
    public int status;
    public List<MapChangeListener> listeners;
    public Vector3f localTranslation = new Vector3f();
    public Vector2f size = new Vector2f();
//    public MapRegionPolygon3D polygon3D;
//    public MapRegionPolygon2D polygon2D;

    public int getSubRegion(Vector3f pos) {
        Vector3f relativePos = pos.subtract(localTranslation);
        relativePos.addLocal(size.x / 2, 0, size.y / 2);
        
//        System.out.println(" pos :" + pos);
//        System.out.println(" size :" + size);
//        System.out.println(" localTranslation :" + localTranslation);
//        System.out.println(" Relative pos :" + relativePos);
        if (relativePos.z < size.y / 3) {
            if (relativePos.x < size.x / 3) {
                return LEFT_TOP;
            } else if (relativePos.x >= size.x / 3 && relativePos.x <= size.x / 3 * 2) {
                return CENTER_TOP;
            } else {
                return RIGHT_TOP;
            }
        } else if (relativePos.z > size.y / 3 && relativePos.y < size.y / 3 * 2) {
            if (relativePos.x < size.x / 3) {
                return LEFT_MID;
            } else if (relativePos.x >= size.x / 3 && relativePos.x <= size.x / 3 * 2) {
                return CENTER_MID;
            } else {
                return RIGHT_MID;
            }
        } else {
            if (relativePos.x < size.x / 3) {
                return LEFT_BOTTOM;
            } else if (relativePos.x > size.x / 3 && relativePos.x < size.x / 3 * 2) {
                return CENTER_BOTTOM;
            } else {
                return RIGHT_BOTTOM;
            }
        }
    }

    public Vector3f getCenter() {
        return localTranslation.clone();
    }

    public Vector3f getLocalTranslation() {
        return localTranslation;
    }

    public void applyPos(float gridSize) {
        size.set(gridSize, gridSize);
        localTranslation.set(gridX * gridSize, 0, gridY * gridSize);
    }

    public void applyPos(Vector2f gridSize) {
        size.set(gridSize);
        localTranslation.set(gridX * gridSize.x, 0, gridY * gridSize.y);
    }

    public void onRemoved() {
        for (MapChangeListener listener : listeners) {
            listener.regionRemoved(id);
        }
    }

    public void onAdded() {
        for (MapChangeListener listener : listeners) {
            listener.regionAdded(id);
        }
    }

    public void onActived() {
        for (MapChangeListener listener : listeners) {
            listener.regionActive(id);
        }
    }

    @Override
    public String toString() {
        return " Map Region : " + id + " path " + modelPath + " pos " + gridX + " " + gridY + " " + current;
    }
    
    
}
