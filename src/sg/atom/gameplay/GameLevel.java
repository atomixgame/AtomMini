/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;

/**
 *
 * @author CuongNguyen
 */
public class GameLevel implements ManagableObject, Savable {

    protected int id;
    protected String name;
    protected int point;
    protected int status;
    protected String picture;
    protected String path;
    protected String description;
    protected Node levelNode;
    protected HashMap<String, Transform> levelPoints;
    protected Application app;

    public GameLevel(String name) {
        this.name = name;
    }

    public GameLevel(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public void load() {
    }

    public void init(Application app) {
        this.app = app;
    }

    public void initManagers(IGameCycle... managers) {
    }

    public void load(AssetManager assetManager) {
        if (path != null && !path.isEmpty()) {
            this.levelNode = (Node) assetManager.loadModel(this.path);
        } else {
            loadDefault();
        }
    }

    protected void loadDefault() {
        this.levelNode = new Node("EmptyNode");
    }

    public void config(Configuration configuration) {
    }

    public void update(float tpf) {
    }

    public void finish() {
    }

    //GETTER & SETTER
    public Vector3f getStartPos() {
        return Vector3f.ZERO;
    }

    public Node getLevelNode() {
        return levelNode;
    }

    public GameLevel getNextLevel() {
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

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
