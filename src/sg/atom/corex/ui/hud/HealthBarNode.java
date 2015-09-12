/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.ui.hud;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import sg.atom.gameplay.CommonGameCharacter;
import sg.atom.corex.managers.GUIManager;

/**
 * HealthBar display a indicator about the status of unit.
 *
 * @author cuong.nguyenmanh2
 */
public class HealthBarNode extends Node {

    Picture healthBar1, healthBar2;
    private CommonGameCharacter unit;
    private int maxWidth = 50;
    private int barHeight = 8;
    private Material blackMat;
    private final Material greenMat;
    private float yUp = 8;

    public HealthBarNode(CommonGameCharacter unit, GUIManager gameGUIManager) {
        super("heathbar" + unit.getName());

        AssetManager assetManager = gameGUIManager.getAssetManager();
        this.unit = unit;
        
        healthBar1 = new Picture("heathBarBg");
        healthBar2 = new Picture("heathBar");

        // Mat and Color
        blackMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        blackMat.setColor("Color", ColorRGBA.Black);
   
        // Black
        healthBar1.setMaterial(blackMat);
        
        greenMat = blackMat.clone();
        greenMat.setColor("Color", ColorRGBA.Green);

        healthBar2.setMaterial(greenMat);

        // Location
        attachChild(healthBar1);
        attachChild(healthBar2);
        healthBar1.setLocalTranslation(0, 0, 0);
        healthBar2.setLocalTranslation(0, 0, 1);
        updateBar(null);

        
        
    }

    public void updateBar(Camera cam) {
        int maxValue = unit.getMaxHP();
        int value = unit.getHealth();

        Vector3f screenPos = new Vector3f();
        if (cam != null) {
            screenPos.set(cam.getScreenCoordinates(unit.getLocation().add(0, 2, 0)).add(-maxWidth / 2, 0, 0));
        }

        setLocalTranslation(screenPos);

        healthBar1.setWidth(maxWidth);
        healthBar1.setHeight(barHeight);

        healthBar2.setWidth((float) maxWidth * value / maxValue);
        healthBar2.setHeight((float) barHeight * 0.9f);
    }
}
