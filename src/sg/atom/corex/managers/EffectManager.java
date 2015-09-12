/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.managers;

import sg.atom.corex.stage.fx.interpolators.Vector3fInterpolator;
import com.google.common.base.Function;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import com.jme3.cinematic.Cinematic;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.stage.fx.EffectControl;
import sg.atom.corex.stage.fx.EffectEvent;
import static sg.atom.corex.math.Tweenf.*;

/**
 * An EffectManager is a Factory for Effects (Spatial + EffectControl).
 *
 * Effect built by EffectBuilder and get play back with Cinematic. Example:
 * 
 * 
 *
 * @author cuong.nguyenmanh2
 */
public class EffectManager extends AbstractAppState implements IGameCycle{

    //Motions
    public static final int MOTION_SIN = 0;
    public static final int MOTION_COS = 1;
    public static final int MOTION_CIRCLE = 2;
    public static final int MOTION_RECT = 3;
    public static final int MOTION_TRI = 4;
    //Timing & curves
    protected AtomMain app;
    protected StageManager stageManager;
    protected HashMap<String, String> effectMap;
    protected HashMap<String, Spatial> effectModelMap;
    protected AssetManager assetManager;
    protected Node worldNode;
    protected Node effectsModel;
    protected Cinematic effectCine;

    public EffectManager(AtomMain app) {
        this.stageManager = app.getStageManager();
        this.assetManager = app.getAssetManager();
//        this.worldNode = stageManager.getWorldNode();
        this.effectMap = new HashMap<String, String>();
        this.effectModelMap = new HashMap<String, Spatial>();
        this.effectCine = new Cinematic(worldNode);
        app.getStateManager().attach(effectCine);
    }

    public void init() {
    }

    public void load(String path){
        
    }
    public void load(AssetKey assetKey){
        
    }
    public void createEffects() {
        effectsModel = (Node) assetManager.loadModel("Models/FX/Effects.j3o");
        ParticleEmitter fireEffect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        Material fireMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        fireMat.setTexture("Texture", assetManager.loadTexture("Textures/Effects/Explosion/flame.png"));
        fireEffect.setMaterial(fireMat);
        fireEffect.setImagesX(2);
        fireEffect.setImagesY(2); // 2x2 texture animation
        fireEffect.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
        fireEffect.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        //fireEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        fireEffect.setStartSize(0.6f);
        fireEffect.setEndSize(0.1f);
        fireEffect.setGravity(0f, 0f, 0f);
        fireEffect.setLowLife(0.5f);
        fireEffect.setHighLife(3f);
        fireEffect.getParticleInfluencer().setVelocityVariation(0.3f);
        //worldNode.attachChild(fireEffect);

        ParticleEmitter debrisEffect = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 10);
        Material debrisMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        debrisMat.setTexture("Texture", assetManager.loadTexture("Textures/Effects/Explosion/Debris.png"));
        debrisEffect.setMaterial(debrisMat);
        debrisEffect.setImagesX(3);
        debrisEffect.setImagesY(3); // 3x3 texture animation
        debrisEffect.setRotateSpeed(4);
        debrisEffect.setSelectRandomImage(true);
        debrisEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
        debrisEffect.setStartColor(new ColorRGBA(1f, 1f, 1f, 1f));
        debrisEffect.setGravity(0f, 6f, 0f);
        debrisEffect.getParticleInfluencer().setVelocityVariation(.60f);
        //worldNode.attachChild(debrisEffect);
        debrisEffect.emitAllParticles();

        Sphere ballMesh = new Sphere(32, 32, 0.1f);
        Geometry ballGeo = new Geometry("RedBall", ballMesh);
        Material unshadedMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        unshadedMat.setColor("Color", ColorRGBA.White); // needed for shininess
        ballGeo.setMaterial(unshadedMat);

        // Setup the map of effects
        effectModelMap.put("fire", fireEffect);
        effectModelMap.put("debris", debrisEffect);
        effectModelMap.put("Healing", fireEffect);
        effectModelMap.put("Healing-001", fireEffect);
        effectModelMap.put("Healing-002", fireEffect);
        effectModelMap.put("Thunder", effectsModel.getChild("Thunder"));
        effectModelMap.put("Thunder-001", fireEffect);
        effectModelMap.put("Thunder-002", fireEffect);

    }

    public Spatial getModel(String name) {
        return effectModelMap.get(name);
    }

    public Spatial getEffect(String name) {
        return effectModelMap.get(name);
    }

    public Spatial getEffect(String name, String config) {
        return getEffect(name);
    }

    public void attachEffect(Vector3f location, String effectName, Function<Spatial, Void> callBackFunc) {
        Spatial effect = getEffect(effectName);
        effect.setLocalTranslation(location.clone());
        worldNode.attachChild(effect);
    }

    public void attachEffect(Vector3f location, Spatial effect, Function<Spatial, Void> callBackFunc) {
    }

    public void attachEffect(Vector3f location, Spatial effect, Callable<Void> callBackFunc) {
    }

    public void attachEffect(Vector3f location, Spatial effect, Runnable callBackFunc) {
    }

    public void attachEffect(Vector3f location, Spatial effect) {
        effect.setLocalTranslation(location.clone());
        worldNode.attachChild(effect);
    }

    public void animate() {
    }

    public void addKey(EffectEvent event) {
    }

    public void addKeys(List<EffectEvent> events) {
        //
//        System.out.println("Add keys");
        for (EffectEvent ee : events) {
//            System.out.println(" at: " + ee.startTime);
            this.effectCine.addCinematicEvent(ee.getStartTime(), ee);
        }
        refesh();
    }

    public void refesh() {
        effectCine.initialize(this.stageManager.getApp().getStateManager(), this.stageManager.getApp());
        effectCine.play();
    }

    public void update(float tpf) {
    }

    public void setColor(Spatial effectSpatial, ColorRGBA newColor) {
        if (effectSpatial instanceof ParticleEmitter) {
            ParticleEmitter emitter = (ParticleEmitter) effectSpatial;
            //ColorRGBA startColor = emitter.getStartColor()
            emitter.setStartColor(newColor);
            emitter.setEndColor(newColor);
        } else if (effectSpatial instanceof Geometry) {
            Material material = ((Geometry) effectSpatial).getMaterial();
            material.setColor("Color", newColor);
        }
    }

    public void setPositions(){
        
    }
    public void setAttributes(){
        
    }
    public void createMovement(Spatial spatial, float durationTime,  float[] times, Vector3f... moves) {
        spatial.addControl(new EffectControl.Builder()
                .withEffectManager(this)
                .withRemoveSpatialOnEnd(false)
                .withContinuePlay(false)
                .withDurationTime(durationTime)
                //Change pos
                .withEvents(new EffectEvent.Builder<Vector3f>()
                .withConverter(new Vector3fInterpolator(QUAD_IN_OUT))
                .withPropertyName("localTranslation")
                .withEffect(spatial)
                .withMultiDuration(times)
                .withEasing(QUAD_IN)
                .withMultiValue((Object[]) moves)
                .buildAll())
                .build());
    }

    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void config(Configuration props) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
