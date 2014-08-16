package sg.atom.stage;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.cinematic.Cinematic;
import com.jme3.collision.CollisionResults;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.ui.Picture;
import com.simsilica.es.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.gameplay.GameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.stage.select.CursorControl;
import sg.atom.gameplay.GameLevel;
import sg.atom.state.FightState;
import sg.atom.state.InGameState;
import sg.atom.world.WorldManager;

/**
 * StageManager for Stage, Actors (Characters) and Camera.
 *
 * @author CuongNguyen
 */
public class StageManager  implements IGameCycle{

    protected final AtomMain app;
    // Managers
    protected final AssetManager assetManager;
    protected final ViewPort viewPort;
    protected final InputManager inputManager;
    protected final AudioRenderer audioRenderer;
    protected GamePlayManager gamePlayManager;
    protected EffectManager effectManager;
    protected WorldManager worldManager;
    // Controls
    protected ChaseCamera chaseCam;
    protected CursorControl cursorControl;
    // Characters
    protected ArrayList<GameCharacter> characters;
    // Nodes
    protected Node rootNode;
    protected Node worldNode;
    protected Spatial cursor3D;
    protected Node levelNode;
    protected Node fightLevelNode;
    protected Spatial playerModel;
    // Background
    protected Material backgroundMaterial;
    protected Picture backgroundPicture;
    protected boolean staticBg = false;
    protected ViewPort backgroundViewport;

    protected Cinematic cinematic;
    protected ListeningScheduledExecutorService executor = MoreExecutors.listeningDecorator(new ScheduledThreadPoolExecutor(2));
    protected boolean firstRun = true;


    public StageManager(AtomMain app) {
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.inputManager = app.getInputManager();
        this.audioRenderer = app.getAudioRenderer();
        this.viewPort = app.getViewPort();
        this.rootNode = app.getRootNode();
        this.worldNode = rootNode;
    }

    public void initManagers(){
        this.worldManager = app.getWorldManager();
        this.effectManager = new EffectManager(this.getApp());
        this.cinematic = new Cinematic();
        this.app.getStateManager().attach(cinematic);
    }
    
    public void goInGame() {

        if (firstRun == true) {

            if (app.getDeviceInfo().isDesktopApp()) {
                createBackground();
            }

//            loadLevels();
//            loadScenes();
//            loadModels();

            createLights();
            createCursor();
            setupCamera();
            setupInput();

            this.effectManager.createEffects();
            this.gamePlayManager = new GamePlayManager(app);
//            startLevel(levels.get(0));
            gamePlayManager.goInGame();
            firstRun = false;
        }
    }

    public void createBackground() {
        //viewPort.setBackgroundColor(ColorRGBA.LightGray);
        backgroundMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        backgroundPicture = new Picture("background");
        backgroundPicture.setMaterial(backgroundMaterial);
//        backgroundPicture.setQueueBucket(RenderQueue.Bucket.Opaque);
//        float bgWidth = app.getSettings().getWidth();
//        float bgHeight = app.getSettings().getHeight();
//        float bgScale = 50;
//        backgroundPicture.scale(bgWidth / bgScale, bgHeight / bgScale, 0);
//        //float distance = getCurrentActiveCamera().get
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

        backgroundPicture.updateGeometricState();
    }

    public Material getBackgroundMaterial() {
        return backgroundMaterial;
    }

    public void setBackgroundImage(String path) {/* A colored lit cube. Needs light source! */
        backgroundMaterial.setTexture("ColorMap", assetManager.loadTexture(path));
    }

    public void setupInput() {
        inputManager.setCursorVisible(true);

        inputManager.addMapping("Attack",
                new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping("Pick",
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        /**
         * Add an action to one or more listeners
         */
        inputManager.addListener(actionListener, "Attack", "Pick");
        inputManager.addListener(analogListener, "Attack", "Pick");
        /**
         * Use ActionListener to respond to pressed/released inputs (key
         * presses, mouse clicks)
         */
    }

    public void setupCamera() {
        app.getFlyByCamera().setDragToRotate(true);
        app.getFlyByCamera().setMoveSpeed(10);
        app.getCamera().setLocation(new Vector3f(0, 3, 5));
        app.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);

//        chaseCam = new ChaseCamera(app.getCamera(), playerModel, inputManager);
//        chaseCam.setDefaultDistance(12f);
//        chaseCam.setZoomSensitivity(0.0f);
//        chaseCam.setMinDistance(5.8f);
//        chaseCam.setMaxDistance(35f);
//        chaseCam.setRotationSensitivity(0);
//        chaseCam.setSmoothMotion(true);

    }



    public void loadScenes() {
        worldNode = new Node("WorldNode");
        levelNode = new Node("LevelNode");
        fightLevelNode = new Node("FightLevelNode");
        rootNode.attachChild(worldNode);

        // Add with cache
        //levelMap.put("Forest", );
//        worldManager.setRootNode(fightLevelNode);
//        worldManager.createGizmo();
//        worldManager.createGrid(20, 20);

    }



    public void startLevel(GameLevel level) {
//        String levelName = "Forest";
//        String sceneName = levelName;
        startScene(level.getPath());
//        this.currentLevel = level;
    }

    public void startScene(String sceneName) {
        Spatial scene = assetManager.loadModel(sceneName);
        levelNode.attachChild(scene);
        // camera?
    }

    public void onState(Class<? extends AbstractAppState> newState) {
    }

    // FIXME: Move to InGameState
    public void goNormalScene() {
        app.getFlyByCamera().setEnabled(true);
        app.getFlyByCamera().setDragToRotate(true);

        if (app.getDeviceInfo().isDesktopApp()) {
            viewPort.setClearFlags(true, true, true);
            viewPort.setBackgroundColor(ColorRGBA.Cyan);
            backgroundViewport.setEnabled(false);
        }
        fightLevelNode.removeFromParent();
        worldNode.attachChild(levelNode);

        //show cursor
        cursor3D.setCullHint(Spatial.CullHint.Inherit);

//        gamePlayManager.goNormalScene();
    }

    //FIXME: Move to FightState
//    public void goFightScene(String name) {
//        app.getFlyByCamera().setEnabled(false);
//        app.getFlyByCamera().setDragToRotate(false);
//
//        if (app.getDeviceInfo().isDesktopApp()) {
//            viewPort.setBackgroundColor(ColorRGBA.Black);
//            viewPort.setClearFlags(false, true, true);
//            backgroundViewport.setEnabled(true);
//            //        fightLevelNode.attachChild(backgroundPicture);
//            String bgTexture = scenesMap.get(name);
//            setBackgroundImage(bgTexture);
//        }
//        levelNode.removeFromParent();
//        worldNode.attachChild(fightLevelNode);
//
//
//        app.getCamera().setLocation(new Vector3f(0, 12, 12));
//        app.getCamera().lookAt(new Vector3f(0, 0, -2), Vector3f.UNIT_Y);
////        float far = app.getCamera().getFrustumFar();
////        float near = app.getCamera().getFrustumNear();
//        //app.getCamera().setFrustumPerspective(FastMath.HALF_PI * 0.5f, 0.5f, near, far);
//        //hide cursor
//        cursor3D.setCullHint(Spatial.CullHint.Always);
//
//        gamePlayManager.goFightScene();
//    }

    public void placeCharacter(GameCharacter character) {
//        System.out.println(" Place character " + character.name + " at :" + character.getModel().getLocalTranslation());
//        if (!gamePlayManager.fightMode) {
//            levelNode.attachChild(character.getModel());
//        } else {
//            fightLevelNode.attachChild(character.getModel());
//        }
        character.onStage(this);
    }

    public void placeCharacter(GameCharacter character, Vector3f pos) {
        character.getModel().setLocalTranslation(pos);
//        if (!gamePlayManager.fightMode) {
//            levelNode.attachChild(character.getModel());
//        } else {
//            fightLevelNode.attachChild(character.getModel());
//        }
        character.onStage(this);
    }

    public void placeCharacter(GameCharacter character, Transform pos) {
        character.getModel().setLocalTransform(pos);
//        if (!gamePlayManager.fightMode) {
//            levelNode.attachChild(character.getModel());
//        } else {
//            fightLevelNode.attachChild(character.getModel());
//        }
        character.onStage(this);
    }

    public void removeCharacter(GameCharacter character) {
        levelNode.detachChild(character.getModel());
    }

    public void removeCharacter(GameCharacter pc, float delayTime) {
    }

    public void createLights() {
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        worldNode.addLight(ambient);
        /**
         * A white, spot light source.
         */
//        PointLight lamp = new PointLight();
//        lamp.setPosition(new Vector3f(10, 10, 10));
//        lamp.setColor(ColorRGBA.White);
//        rootNode.addLight(lamp);
        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        worldNode.addLight(sun);
//        /* this shadow needs a directional light */
//        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, 1024, 2);
//        dlsr.setLight(sun);
//        viewPort.addProcessor(dlsr);

    }

    protected AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) {

            if (name.equals("Pick")) {
                // Reset results list.
                CollisionResults results = new CollisionResults();
                // Convert screen click to 3d position
                Vector2f click2d = inputManager.getCursorPosition();
                Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                Vector3f dir = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
                // Aim the ray from the clicked spot forwards.
                Ray ray = new Ray(click3d, dir);
                rootNode.collideWith(ray, results);

                if (results.size() > 0) {
                    // The closest result is the target that the player picked:
                    Geometry target = results.getClosestCollision().getGeometry();
                    if (!isOnTerrain(target)) {
                        onSelect(target, intensity);
                    } else {
                        Vector3f touchLoc = results.getClosestCollision().getContactPoint();
                        onTouch(touchLoc);
                    }
                }
            }

        }
    };
    //Stage---------------------------------------------------------------------
    boolean isOnTerrain(Geometry target) {
        //return target.hasAncestor(levelNode);
        return target.getName().equalsIgnoreCase("Ground1");
    }
    //Action management---------------------------------------------------------

    public <T> void queueAction(final T callee, final Function<T, T> function, float time) {
        ListenableFuture<T> actionFuture =
                executor.schedule(new Callable<T>() {
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
        ListenableFuture<T> actionFuture =
                executor.schedule(actionCall, (int) time * 1000, TimeUnit.MILLISECONDS);

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

        cursorControl = new CursorControl(this);
        cursor3D = cursorControl.createCursor(assetManager, worldNode);
    }
    //Events--------------------------------------------------------------------
    protected ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean pressed, float tpf) {
        }
    };

    public void onTouch(Vector3f touchLoc) {
        cursorControl.placeCursor(touchLoc);
//        gamePlayManager.getAdventureGamePlay().getCharacterControl().letMoveTo(touchLoc);
    }

    public void onSelect(Geometry target, float intensity) {
        // Here comes the action:
        if (target.hasAncestor((Node) gamePlayManager.getCharacter("Aerith").getModel())) {
//            if (!gamePlayManager.fightMode) {
//                app.getStateManager().getState(InGameState.class).toState(FightState.class);
//            } else {
//                app.getStateManager().getState(FightState.class).toState(InGameState.class);
//            }
        }
    }

    public void onSelect() {
    }

    public void onHover(GameCharacter gameCharacter){
        
    }
    public void onHover(Entity entity){
        
    }
    //Cycle -------------------------------------------------------------------
    public void init(){
        
    }
    
    public void config(Configuration configuration){
        
    }
    
    public void load(){
        
    }
    
    public void update(float tpf){
        
    }
    public void finish() {
        while (!executor.isShutdown()) {
            executor.shutdownNow();
            executor.shutdown();
//            System.out.println("Attemp to shut down executor!");
        }
        System.out.println("Shut downed!");
    }    
    //Update--------------------------------------------------------------------
    public void simpleUpdate(float tpf) {
    }
    
    //GETTER & SETTER ----------------------------------------------------------
    public Node getRootNode() {
        return app.getRootNode();
    }

    public Camera getCurrentActiveCamera() {

        return app.getCamera();
    }

    public Spatial getPlayerModel() {
        return playerModel;
    }

    public GameLevel getCurrentLevel(){
        return null;
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    public Node getWorldNode() {
        return worldNode;
    }

    public AtomMain getApp() {
        return app;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }
}
