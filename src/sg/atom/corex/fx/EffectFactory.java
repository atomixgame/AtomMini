/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.fx;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import sg.atom.AtomMain;
import static sg.atom.corex.math.Tweenf.QUAD_IN;
import static sg.atom.corex.math.Tweenf.QUAD_IN_OUT;
import sg.atom.corex.stage.fx.EffectControl;
import sg.atom.corex.stage.fx.EffectEvent;
import sg.atom.corex.stage.fx.interpolators.Vector3fInterpolator;

/**
 *
 * @author cuongnguyen
 */
public class EffectFactory {

    protected AtomMain app;

    public EffectFactory(AtomMain app) {
        this.app = app;
    }

    public void createMovement(Spatial spatial, float durationTime, float[] times, Vector3f... moves) {
        spatial.addControl(new EffectControl.Builder()
                .withEffectManager(this.getApp().getEffectManager())
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
                        .buildSequence())
                .build());
    }

    public void createEffects() {
//        effectsModel = (Node) assetManager.loadModel("Models/FX/Effects.j3o");
//        ParticleEmitter fireEffect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
//        Material fireMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
//        fireMat.setTexture("Texture", assetManager.loadTexture("Textures/Effects/Explosion/flame.png"));
//        fireEffect.setMaterial(fireMat);
//        fireEffect.setImagesX(2);
//        fireEffect.setImagesY(2); // 2x2 texture animation
//        fireEffect.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
//        fireEffect.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
//        //fireEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
//        fireEffect.setStartSize(0.6f);
//        fireEffect.setEndSize(0.1f);
//        fireEffect.setGravity(0f, 0f, 0f);
//        fireEffect.setLowLife(0.5f);
//        fireEffect.setHighLife(3f);
//        fireEffect.getParticleInfluencer().setVelocityVariation(0.3f);
//        //worldNode.attachChild(fireEffect);
//
//        ParticleEmitter debrisEffect = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 10);
//        Material debrisMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
//        debrisMat.setTexture("Texture", assetManager.loadTexture("Textures/Effects/Explosion/Debris.png"));
//        debrisEffect.setMaterial(debrisMat);
//        debrisEffect.setImagesX(3);
//        debrisEffect.setImagesY(3); // 3x3 texture animation
//        debrisEffect.setRotateSpeed(4);
//        debrisEffect.setSelectRandomImage(true);
//        debrisEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
//        debrisEffect.setStartColor(new ColorRGBA(1f, 1f, 1f, 1f));
//        debrisEffect.setGravity(0f, 6f, 0f);
//        debrisEffect.getParticleInfluencer().setVelocityVariation(.60f);
//        //worldNode.attachChild(debrisEffect);
//        debrisEffect.emitAllParticles();
//
//        Sphere ballMesh = new Sphere(32, 32, 0.1f);
//        Geometry ballGeo = new Geometry("RedBall", ballMesh);
//        Material unshadedMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        unshadedMat.setColor("Color", ColorRGBA.White); // needed for shininess
//        ballGeo.setMaterial(unshadedMat);
//
//        // Setup the map of effects
//        effectModelMap.put("fire", fireEffect);
//        effectModelMap.put("debris", debrisEffect);
//        effectModelMap.put("Healing", fireEffect);
//        effectModelMap.put("Healing-001", fireEffect);
//        effectModelMap.put("Healing-002", fireEffect);
//        effectModelMap.put("Thunder", effectsModel.getChild("Thunder"));
//        effectModelMap.put("Thunder-001", fireEffect);
//        effectModelMap.put("Thunder-002", fireEffect);

    }

    public AtomMain getApp() {
        return app;
    }
}
