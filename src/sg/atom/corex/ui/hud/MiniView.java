/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.ui.hud;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import org.apache.commons.collections4.BidiMap;
import sg.atom.AtomMain;
import sg.atom.corex.world.map.MapChangeListener;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MiniView extends Node implements MapChangeListener {

    protected BitmapText text;
    protected Node guiNode;
    protected AssetManager assetManager;
    protected final Material unshadeMat;
    protected Vector2f screenSize;
    protected Vector2f miniMapSize;
    protected Vector2f viewSize;
    protected final Vector2f patchSize;
    protected float zoom = 1;
    protected final AtomMain app;

    public MiniView(AtomMain app) {
        super("MiniView");
        this.app = app;

        assetManager = app.getAssetManager();
        guiNode = app.getGuiNode();
        BitmapFont guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        text = new BitmapText(guiFont, false);
        text.setSize(guiFont.getCharSet().getRenderedSize());
        text.setText("Hello World");
        text.setLocalTranslation(300, text.getLineHeight(), 0);

        this.attachChild(text);
        guiNode.attachChild(this);

        unshadeMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        unshadeMat.setColor("Color", ColorRGBA.Green);

        screenSize = new Vector2f(app.getCamera().getWidth(), app.getCamera().getHeight());
        miniMapSize = new Vector2f(100, 100);
        viewSize = new Vector2f(40, 40);
        patchSize = new Vector2f(20, 20);
        setLocalTranslation(screenSize.x - miniMapSize.x, screenSize.y - miniMapSize.y, -100);
        Geometry bg = addBox("Bg", miniMapSize.x, miniMapSize.y, new ColorRGBA(0, 0, 0, 0.4f));
        bg.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

    }

    protected Geometry addBox(String name, float w, float h, ColorRGBA color) {
        Quad quad = new Quad(w, h);
        Geometry boxGeo = new Geometry(name, quad);
        Material blueMat = unshadeMat.clone();
        blueMat.setColor("Color", color);
        boxGeo.setMaterial(blueMat);
        attachChild(boxGeo);
        return boxGeo;
    }

    public void regionActive(int id) {
//        Geometry box = addBox("Region" + id, patchSize.x, patchSize.y, ColorRGBA.Blue);
//        box.setLocalTranslation((miniMapSize.x - patchSize.x) / 2, (miniMapSize.y - patchSize.y) / 2, 0);
    }

    public void regionAdded(int id) {
    }

    public void regionRemoved(int id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void updateSpatial(BidiMap<Integer, Spatial> npcMap) {
        //FIXME:
        Vector3f center = new Vector3f();
        Spatial model = new Node();
        int id = 0;
        
        Vector3f miniViewPos = transformPosToMap(center, model.getLocalTranslation());

        Spatial npcBox = getChild("" + id);
        if (npcBox != null) {
            if (miniViewPos != null) {
                npcBox.setLocalTranslation(miniViewPos);
            } else {
                detachChild(npcBox);
            }
        } else {
            attachChild(addBox("" + id, 2, 2, ColorRGBA.Green));
        }

        Geometry playerBox = addBox("Player", 4, 4, ColorRGBA.Orange);
        playerBox.setLocalTranslation(transformPosToMap(center, center));
        attachChild(playerBox);

    }

//    protected void updatePlayer(){
//        
//    }
    protected Vector3f transformPosToMap(Vector3f center, Vector3f pos) {
//        System.out.println(" " + center + " " + pos);
        if (pos.x >= center.x - viewSize.x / 2
                && pos.x <= center.x + viewSize.x / 2
                && pos.z >= center.z - viewSize.y / 2
                && pos.z <= center.z + viewSize.y / 2) {
            Vector3f orginalVec = pos.subtract(center);
            orginalVec.multLocal(-miniMapSize.x / viewSize.x, 0, -miniMapSize.y / viewSize.y);
//            orginalVec.addLocal(screenSize.x - miniMapSize.x, 0, screenSize.y - miniMapSize.y);
            orginalVec.addLocal(miniMapSize.x / 2, 0, miniMapSize.y / 2);
            Vector3f resutVec = new Vector3f(orginalVec.z, orginalVec.x, 0);
            return resutVec;
        } else {
            return null;
        }

    }
}
