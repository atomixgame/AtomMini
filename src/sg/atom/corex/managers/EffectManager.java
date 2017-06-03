/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.managers;

import com.google.common.base.Function;
import com.jme3.asset.AssetKey;
import com.jme3.cinematic.Cinematic;
import com.jme3.effect.ParticleEmitter;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.fx.EffectFactory;
import sg.atom.corex.stage.fx.EffectEvent;

/**
 * An EffectManager is a Factory for Effects (Spatial + EffectControl).
 *
 * Effect built by EffectBuilder and get play back with Cinematic. Example:
 *
 *
 *
 * @author cuong.nguyenmanh2
 */
public class EffectManager extends AbstractManager {

    //Motions
    public static final int MOTION_SIN = 0;
    public static final int MOTION_COS = 1;
    public static final int MOTION_CIRCLE = 2;
    public static final int MOTION_RECT = 3;
    public static final int MOTION_TRI = 4;
    //Timing & curves
    protected HashMap<String, String> effectMap;
    protected HashMap<String, Spatial> effectModelMap;
    protected Node worldNode;
    protected Node effectsModel;
    protected Cinematic effectCine;
    private EffectFactory effectFactory;

    public EffectManager() {
        super();
    }

    @Inject
    public EffectManager(AtomMain app) {
        super(app);
        this.effectFactory = new EffectFactory(app);
        this.effectMap = new HashMap<String, String>();
        this.effectModelMap = new HashMap<String, Spatial>();
        this.effectCine = new Cinematic(worldNode);
        app.getStateManager().attach(effectCine);
    }

    public void init() {
    }

    public void load(String path) {

    }

    public void load(AssetKey assetKey) {

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
        effectCine.initialize(this.getApp().getStateManager(), this.getApp());
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

    public void setPositions() {

    }

    public void setAttributes() {

    }

    public void load() {
    }

    public void config(Configuration props) {
    }

    public EffectFactory getEffectFactory() {
        return this.effectFactory;
    }

}
