/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Cylinder;
import com.jme3.texture.Texture;
import sg.atom.stage.StageManager;

/**
 *
 * @author CuongNguyen
 */
public class CursorControl extends AbstractControl {

    protected boolean show3DCussor = true;
    protected float cursorHeight = 2f;
    protected float cursorHeightAngle = 0;
    protected float cursorHeightAngleInc = 5;
    private Spatial cursor3D;
    private final StageManager stageManager;
    private Material cursorMat;
    private boolean animated = true;

    public CursorControl(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public Spatial createCursor(AssetManager assetManager, Node worldNode) {
        //FIXME: Cache some thing...
        cursor3D = new Geometry("Gizmo2", new Cylinder(3, 20, 1, 0.01f, true));
        cursorMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

//        Texture tex = assetManager.loadTexture("Textures/Cursors/circle1.png");
//        cursorMat.setTexture("ColorMap", tex);
        cursorMat.setColor("Color", ColorRGBA.Blue);
        cursor3D.setMaterial(cursorMat);
        Quaternion q = new Quaternion();
        q.lookAt(Vector3f.UNIT_Y, Vector3f.UNIT_X);
        cursor3D.setLocalRotation(q);

        //Attach it!
        worldNode.attachChild(cursor3D);

        //clickedMat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        cursor3D.setQueueBucket(RenderQueue.Bucket.Transparent);

        cursorMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.AlphaAdditive);
//        cursorMat.getAdditionalRenderState().setDepthTest(false);
//        cursorMat.getAdditionalRenderState().setWireframe(true);

        cursor3D.addControl(this);
        return cursor3D;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.cursor3D = spatial;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (animated) {
            if (cursorHeightAngle < FastMath.PI) {
                cursorHeightAngle += tpf * cursorHeightAngleInc;
            } else {
                cursorHeightAngle = 0;
            }
            float height = cursorHeight * FastMath.sin(cursorHeightAngle);

            //gizmo2.setLocalTranslation(tl.add(new Vector3f(intersection.x, h, intersection.z)));
            cursor3D.setLocalScale(1 - FastMath.sin(cursorHeightAngle) / 2);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void placeCursor(Vector3f pos) {
        this.cursor3D.setLocalTranslation(pos.clone());
    }

    public void setShow3DCussor(boolean show3DCussor) {
        this.show3DCussor = show3DCussor;
        if (show3DCussor) {
            cursor3D.setCullHint(Spatial.CullHint.Inherit);
        } else {
            cursorHeightAngle = 0;
            cursor3D.setCullHint(Spatial.CullHint.Always);
        }
    }

    public void setColor(ColorRGBA color) {
        cursorMat.setColor("Color", color);
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }
    
    
}
