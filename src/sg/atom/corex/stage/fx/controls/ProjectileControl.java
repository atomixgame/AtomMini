/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage.fx.controls;

import com.google.common.base.Predicate;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import sg.atom.gameplay.CommonGameCharacter;

/**
 *
 * @author CuongNguyen
 */
public class ProjectileControl extends AbstractControl {

    Vector3f targetPos;
    float durationTime = 2;
    float passedTime = 0;
    float speed = 5;
    boolean targeted = true;
    Predicate<Spatial> hitCondition;

    public ProjectileControl(Vector3f targetPos, Predicate<Spatial> hitCondition) {
        this.targetPos = targetPos;
        this.hitCondition = hitCondition;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if (passedTime > durationTime) {
            this.setEnabled(false);
            this.spatial.removeFromParent();
            onEnd();
        } else {
            if (hitCondition.apply(spatial)) {
                onHit();
            }
            
            move(tpf);
        }

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    private void callBack() {
//    }
    public void fire() {
    }

    protected void move(float tpf) {
        //spatial.setLocalTranslation(targetPos);
    }

    public void fire(Vector3f direction, Vector3f initialVec) {
    }

    protected void onEnd() {
    }

    protected void onHit() {
    }
}
