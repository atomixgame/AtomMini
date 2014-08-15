/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.HashMap;

/**
 *
 * @author CuongNguyen
 */
public class GameLevel {

    protected int id;
    protected String name;
    protected int point;
    protected int status;
    protected int picture;
    protected String path;
    protected String description;
    protected HashMap<String, Transform> camAngles;
    protected Node levelNode;

    public GameLevel(String name) {
        this.name = name;
    }

    
    
    public Vector3f getStartPos() {
        return Vector3f.ZERO;
    }

    public Node getLevelNode() {
        return new Node("LevelNode");
    }

    public void load() {
    }

    public GameLevel nextLevel() {
        return null;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
    
}
