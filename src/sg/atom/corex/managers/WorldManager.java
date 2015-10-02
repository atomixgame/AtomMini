package sg.atom.corex.managers;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import java.util.HashMap;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.AbstractHelper;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.gameplay.GameLevel;
import sg.atom.corex.world.WorldSettings;

/**
 *
 * @author CuongNguyen
 */
public class WorldManager extends AbstractManager {

    protected WorldSettings worldSettings;
    //Nodes
    protected Node worldNode;
    protected Node helperNode;
    //Commons spatials
    protected Node gizmo = new Node("gizmo");
    protected Geometry groundGeo;
    protected Geometry gridGeo;
    protected Geometry markGeo;
    //Physics
    //Lights
    //Materials
    protected Material unshadedMat;
    protected Material lightMat;
    protected Material matGround;
    // Simple maps for indirect assets management
    protected HashMap<String, String> levelMap;
    protected HashMap<String, String> modelMap;

    public WorldManager(AtomMain app) {
        super(app);
        this.worldNode = new Node("WorldNode");
    }

    public WorldManager(AtomMain app, Node worldNode) {
        super(app);
        this.worldNode = worldNode;
    }

    public void createTerrain() {
        //FIXME: Use generic terrain.
    }

    public void createLight() {
        setupLight(ColorRGBA.White);
    }

    public void setupLight() {
        setupLight(ColorRGBA.White);
    }

    public void setupLight(ColorRGBA color) {
        // light . sun
        /**
         * A white, spot light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(color);
        worldNode.addLight(ambient);

        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(color);
        worldNode.addLight(sun);
    }

    public void createSky() {
//        Spatial sky = SkyFactory.createSky(assetManager, "Scenes/Beach/FullskiesSunset0068.dds", false);
//        sky.setLocalScale(350);
//
//        worldNode.attachChild(sky);
    }

    public void createTestWorld() {
        createGizmo();
        createGrid(10, 10);
    }

    public void createFlatGround(int size) {
        Box b = new Box(new Vector3f(0, -0.1f, size / 2), size, 0.01f, size);
        b.scaleTextureCoordinates(new Vector2f(40, 40));
        groundGeo = new Geometry("Ground", b);
        matGround = getMatGround();

        groundGeo.setMaterial(matGround);
        groundGeo.setShadowMode(RenderQueue.ShadowMode.Receive);
        worldNode.attachChild(groundGeo);
    }

    public Geometry createBox(ColorRGBA color) {
        Box box2 = new Box(new Vector3f(1, 3, 1), 1, 1, 1);
        Geometry red = new Geometry("Box", box2);
        red.setMaterial(getColoredMat(color));
        return red;
    }

    public Geometry putShape(Node node, Mesh shape, ColorRGBA color) {
        Geometry g = new Geometry("shape", shape);
        Material mat = getColoredMat(color).clone();
        mat.getAdditionalRenderState().setWireframe(true);
        g.setMaterial(mat);
        node.attachChild(g);
        return g;
    }

    public void putArrow(Vector3f pos, Vector3f dir, ColorRGBA color) {
        Arrow arrow = new Arrow(dir);
        arrow.setLineWidth(4); // make arrow thicker

        putShape(gizmo, arrow, color).setLocalTranslation(pos);
        worldNode.attachChild(gizmo);
        gizmo.scale(1);
    }

//    public void putTrigger(SpatialTrigger spatialTrigger, Vector3f pos) {
//    }
//
//    public void putTrigger(SpatialTrigger spatialTrigger, Transform pos) {
//    }
    public void putHelper(AbstractHelper helper, Transform pos) {
    }

    public void attachLevel(GameLevel level) {
        worldNode.attachChild(level.getLevelNode());
    }

    public void attachScene() {
    }

    public void attachWorld() {
        rootNode.attachChild(worldNode);
    }

    public void createGizmo() {
        putArrow(Vector3f.ZERO, Vector3f.UNIT_X, ColorRGBA.Red);
        putArrow(Vector3f.ZERO, Vector3f.UNIT_Y, ColorRGBA.Green);
        putArrow(Vector3f.ZERO, Vector3f.UNIT_Z, ColorRGBA.Blue);
    }

    public void createGrid(int gw, int gh) {
        Material mat = getColoredMat(ColorRGBA.DarkGray).clone();

        Grid grid = new Grid(gw - 1, gh - 1, 1);
        gridGeo = new Geometry("Grid", grid);
        gridGeo.setMaterial(mat);
        gridGeo.setLocalTranslation(-gw / 2, 0.1f, -gh / 2);
        worldNode.attachChild(gridGeo);
    }

    public void loadModels() {
        modelMap = new HashMap<String, String>();
    }

    public void loadLevels() {
        // Add with cache
        levelMap = new HashMap<String, String>();
//        levels = new ArrayList<GameLevel>();
    }

    public void loadLevel(GameLevel level) {
        level.load(assetManager);
    }

    public void putEntity(Node parentNode, SpatialEntity entity, Vector2f mapPos) {
        Spatial spatial = entity.getSpatial();
        parentNode.attachChild(spatial);
        spatial.setLocalTranslation(new Vector3f(mapPos.x, 0, mapPos.y));
    }

    public void putEntity(SpatialEntity entity, Vector2f mapPos) {
        putEntity(worldNode, entity, mapPos);
    }

    public void putEntity(SpatialEntity entity, Vector2f mapPos, int state) {
    }

    public void putEntity(Node parentNode, SpatialEntity entity, Vector3f mapPos) {
        Spatial spatial = entity.getSpatial();
        parentNode.attachChild(spatial);
        spatial.setLocalTranslation(new Vector3f(mapPos));
    }

    public void putEntity(SpatialEntity entity, Vector3f mapPos) {
        putEntity(worldNode, entity, mapPos);
    }

    public void putEntity(SpatialEntity entity, Vector3f mapPos, int state) {
    }

    public void putSpatial(Spatial spatial, Vector3f mapPos) {
        worldNode.attachChild(spatial);
        spatial.setLocalTranslation(new Vector3f(mapPos));
    }

    public void putSpatial(Spatial spatial, Vector2f mapPos) {
        worldNode.attachChild(spatial);
    }

    public void putSpatial(Spatial spatial) {
        worldNode.attachChild(spatial);
    }

    public void putSpatial(Node parent, Spatial spatial) {
        parent.attachChild(spatial);
    }
    //Cycle -------------------------------------------------------------------

    public void init() {
    }

    public void config(Configuration configuration) {
    }

    public void load() {
    }

    public void update(float tpf) {
    }

    public void finish() {
    }

    // GETTER & SETTER
    //Material . Should move to MaterialManager ------------------------------
    //    public Material getColoredMat(ColorRGBA color) {
//        //return MaterialManager.getDefaultInstance(assetManager).getColoredMat(color);
//    }
    public Material getMatGround() {
        if (matGround == null) {
            matGround = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
            grass.setWrap(Texture.WrapMode.Repeat);
            matGround.setTexture("DiffuseMap", grass);
        }
        return matGround;
    }

    public Material getLightMat() {
        return lightMat;
    }

    public Material getUnshadedMat() {
        if (unshadedMat == null) {
            unshadedMat = new Material(assetManager,
                    "Common/MatDefs/Misc/Unshaded.j3md");
        }
        return unshadedMat;
    }

    public Material getColoredMat(ColorRGBA color) {
        Material mat = getUnshadedMat().clone();
        mat.setColor("Color", color);
        return mat;
    }

    public Spatial getModel(String modelName) {
        return assetManager.loadModel(modelMap.get(modelName));
    }

    public void setWorldNode(Node worldNode) {
        this.worldNode = worldNode;
    }

    public Node getWorldNode() {
        return worldNode;
    }

    public Node getGizmo() {
        return gizmo;
    }

    public Spatial createPlaceHolder(String type) {
        return null;
    }
}
