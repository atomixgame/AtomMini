/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.spatial;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector2f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import sg.atom.AtomMain;

/**
 *
 * @author cuong.nguyen
 */
public class GeometryFactory {

    private AtomMain app;

    public GeometryFactory(AtomMain app) {
        this.app = app;
    }

    public Geometry createTexturedQuad(String texturePath) {
        Quad quad = new Quad(1, 1);

        Geometry quadGeo = new Geometry("Quad", quad);
        Material unshadedMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture newTexture = app.getAssetManager().loadTexture(texturePath);
        unshadedMat.setTexture("ColorMap", newTexture);
        quadGeo.setMaterial(unshadedMat);

        return quadGeo;
    }

    public Geometry createTexturedExQuad(String texturePath) {
        ExQuad quad = new ExQuad(new Vector2f(-0.5f, -0.5f), 1, 1);

        Geometry quadGeo = new Geometry("Quad", quad);
        Material unshadedMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture newTexture = app.getAssetManager().loadTexture(texturePath);
        unshadedMat.setTexture("ColorMap", newTexture);
        quadGeo.setMaterial(unshadedMat);

        return quadGeo;
    }

    public Geometry createTexturedTransparentExQuad(String texturePath) {
        ExQuad quad = new ExQuad(new Vector2f(-0.5f, -0.5f), 1, 1);

        Geometry quadGeo = new Geometry("Quad", quad);
        Material unshadedMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture newTexture = app.getAssetManager().loadTexture(texturePath);
        unshadedMat.setTexture("ColorMap", newTexture);

        quadGeo.setQueueBucket(RenderQueue.Bucket.Translucent);
        unshadedMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Additive);
        unshadedMat.getAdditionalRenderState().setAlphaTest(false);
//        unshadedMat.getAdditionalRenderState().
        quadGeo.setMaterial(unshadedMat);

        return quadGeo;
    }
}
