package sg.atom.corex.managers;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.jme3.app.state.AbstractAppState;
import com.jme3.cinematic.Cinematic;
import com.jme3.input.ChaseCamera;
import com.jme3.material.Material;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.ui.Picture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.corex.spatial.VirtualSpatialUnit;
import sg.atom.corex.stage.GameScene;
import sg.atom.corex.stage.VirtualGameActor;
import sg.atom.corex.stage.VirtualGamePlayUnit;
import sg.atom.corex.stage.VirtualGameScene;
import sg.atom.corex.stage.select.CursorControl;

/**
 * StageManager for Stage, Actors (Characters) and Camera.
 *
 * @author CuongNguyen
 */
public class StageManager extends AbstractManager implements IGameCycle {

    // Controls
    protected ChaseCamera chaseCam;
    protected CursorControl cursorControl;
    // Characters
    protected ArrayList<VirtualGameActor> characters;
    // Nodes
    protected Spatial cursor3D;
    protected Spatial playerModel;
    // Background
    protected Material backgroundMaterial;
    protected Picture backgroundPicture;
    protected boolean staticBg = false;
    protected ViewPort backgroundViewport;
    protected Cinematic cinematic;
    protected ListeningScheduledExecutorService executor = MoreExecutors.listeningDecorator(new ScheduledThreadPoolExecutor(2));
    protected HashMap<String, String> scenesMap;
    protected ArrayList<VirtualGameScene> scenes;
    protected GameScene currentScene;
    protected int defaultTransistionType;
    protected EffectManager effectManager;
    protected float time;

    public StageManager(AtomMain app) {
        super(app);
    }

    public void initManagers() {
        this.effectManager = new EffectManager(this.getApp());
        this.cinematic = new Cinematic();
        this.app.getStateManager().attach(cinematic);
    }

    public void onStageReady() {
        createCursor();
        setupCamera();
        setupInput();
    }

    public void createBackground() {
        backgroundMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        backgroundPicture = new Picture("background");
        backgroundPicture.setMaterial(backgroundMaterial);
        backgroundPicture.setQueueBucket(RenderQueue.Bucket.Gui);
        backgroundPicture.setLocalTranslation(0, 0, 0);
        backgroundPicture.center();
//        backgroundPicture.addControl(new BillboardControl());
        backgroundPicture.setWidth(app.getSettings().getWidth());
        backgroundPicture.setHeight(app.getSettings().getHeight());
        backgroundPicture.setPosition(0, 0);

        backgroundViewport = app.getRenderManager().createPreView("background", app.getCamera());
        backgroundViewport.setClearFlags(true, true, true);
        backgroundViewport.attachScene(backgroundPicture);
    }

    public Material getBackgroundMaterial() {
        return backgroundMaterial;
    }

    public void setBackgroundImage(String path) {/* A colored lit cube. Needs light source! */

        backgroundMaterial.setTexture("ColorMap", assetManager.loadTexture(path));
    }

    public void setupInput() {
        inputManager.setCursorVisible(true);
    }

    public void setupCamera() {
        app.getFlyByCamera().setDragToRotate(true);
        app.getFlyByCamera().setMoveSpeed(10);
        app.getCamera().setLocation(new Vector3f(0, 3, 5));
        app.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    public void loadScenes() {

    }

    public void startLevel(VirtualGamePlayUnit level, VirtualSpatialUnit spatial) {
    }

    public void startScene(String sceneName) {
        Spatial scene = assetManager.loadModel(sceneName);
//        levelNode.attachChild(scene);
        // camera?
    }

    public void startScene(GameScene scene) {

    }

    public void goToScene(String sceneName) {
    }

    public void goToScene(GameScene scene) {

    }

    public void onState(Class<? extends AbstractAppState> newState) {
    }

    public void putCharacter(VirtualGameActor character) {
        character.onStage(this);
    }

    public void putCharacter(VirtualGameActor character, Vector3f pos) {
        character.getModel().setLocalTranslation(pos);
        character.onStage(this);
    }

    public void putCharacter(VirtualGameActor character, Transform pos) {
        character.getModel().setLocalTransform(pos);
        character.onStage(this);
    }

    public void removeCharacter(VirtualGameActor character) {
//        levelNode.detachChild(character.getModel());
    }

    public void removeCharacter(VirtualGameActor pc, float delayTime) {
    }
    //Stage---------------------------------------------------------------------

    boolean isOnTerrain(Geometry target) {
        //return target.hasAncestor(levelNode);
        return target.getName().equalsIgnoreCase("Ground1");
    }
    //Action management---------------------------------------------------------

    public <T> void queueAction(final T callee, final Function<T, T> function, float time) {
        ListenableFuture<T> actionFuture
                = executor.schedule(new Callable<T>() {
                    public T call() {
                        return function.apply(callee);
                    }
                }, (int) time * 1000, TimeUnit.MILLISECONDS);

        Futures.addCallback(actionFuture, new FutureCallback<T>() {
            public void onSuccess(T result) {
            }

            public void onFailure(Throwable t) {
            }
        });
    }

    public <T> void queueAction(final T callee, final Callable<T> actionCall, float time) {
        ListenableFuture<T> actionFuture
                = executor.schedule(actionCall, (int) time * 1000, TimeUnit.MILLISECONDS);

        Futures.addCallback(actionFuture, new FutureCallback<T>() {
            public void onSuccess(T result) {
            }

            public void onFailure(Throwable t) {
            }
        });
    }

    public <T> void queueAction(final T callee, final Runnable actionCall, float time) {
        ListenableFuture<?> actionFuture = executor.schedule(actionCall, (int) time * 1000, TimeUnit.MILLISECONDS);

        Futures.addCallback(actionFuture, new FutureCallback() {
            public void onSuccess(Object result) {
            }

            public void onFailure(Throwable t) {
            }
        });
    }

    public void createCursor() {
        Node worldNode = app.getWorldManager().getWorldNode();
        cursorControl = new CursorControl(this);
        cursor3D = cursorControl.createCursor(assetManager, worldNode);
    }
    //Cycle -------------------------------------------------------------------

    public void init() {
    }

    public void config(Configuration configuration) {
    }

    public void load() {
        //            loadLevels();
//            loadScenes();
//            loadModels();
    }

    public void update(float tpf) {
        this.time += tpf;
    }

    public void finish() {
        while (!executor.isShutdown()) {
            executor.shutdownNow();
            executor.shutdown();
//            System.out.println("Attemp to shut down executor!");
        }
//        System.out.println("Shut downed!");
    }

    //GETTER & SETTER ----------------------------------------------------------
    public float getTime() {
        return time;
    }

    public Camera getCurrentCamera() {
        return app.getCamera();
    }

    public Spatial getPlayerModel() {
        return playerModel;
    }

    public VirtualGamePlayUnit getCurrentLevel() {
        return null;
    }

    public AtomMain getApp() {
        return app;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public void playCinematic(VirtualGameScene scene) {
    }

}
