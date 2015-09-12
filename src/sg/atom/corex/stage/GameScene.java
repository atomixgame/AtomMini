/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage;

import sg.atom.corex.world.WorldEnviroment;
import com.jme3.app.state.AbstractAppState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import java.util.LinkedList;
import java.util.List;
import sg.atom.gameplay.GameLevel;
import sg.atom.corex.stage.sound.SoundClip;

/**
 *
 * @author CuongNguyen
 */
public class GameScene extends AbstractAppState{

    protected List<Camera> cameraList = new LinkedList<Camera>();
    protected Camera currentCamera;
    protected GameLevel parentLevel;
    protected List<GameActor> actors;
    protected WorldEnviroment enviroment;
    protected SoundClip mainTheme;

    public GameScene(GameLevel parentLevel, Camera cam) {
        this.parentLevel = parentLevel;
        if (cam != null) {
            currentCamera = cam;
        } else {
            currentCamera = new Camera();
            currentCamera.setParallelProjection(false);
            currentCamera.setLocation(Vector3f.ZERO);
        }
    }

    public List<GameActor> getActors() {
        return actors;
    }

    public void sceneStart() {
    }

    public void sceneEnd() {
    }
}
