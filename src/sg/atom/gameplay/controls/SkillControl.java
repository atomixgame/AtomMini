/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.controls;

import com.google.common.base.Predicate;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.List;
import sg.atom.corex.math.Forces3f;
import sg.atom.gameplay.Skill;
import sg.atom.corex.stage.fx.EffectControl;
import sg.atom.gameplay.CommonGameCharacter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SkillControl extends AbstractControl {

    public static long globalId = 0;
    // movement  ---------------------------------
    public float defaultSpeed = 1;
    public float speed = 1;
//    public float veclocity = 1;
//    public float accel = 1.5f;
    public float passedTime = 0;
    public float durationTime = 1;
    public float stepTime = 0.05f;
    private float lastTime = -1;
//    public float maxSpeed = 5;
//    public float moveSpeed = 1;
    public float orientation;
    public Vector3f initialVec;
    public Vector3f currentVec;
    public Vector3f currentForce;
    public List<Vector3f> forces;
    public int motionType;
    public boolean started = false;
    // Gameplay involve
    protected CommonGameCharacter gameCharacter;
    protected Skill skill;
    protected EffectControl fxControl;
    protected Vector3f startPos, targetPos;
    protected Predicate<Spatial> hitCondition;
    private final long id;

    public SkillControl(Skill skill) {
        globalId++;
        this.id = globalId;
        this.skill = skill;
        this.motionType = Skill.SKILL_MOTION_STRAIGHT;
        //this.hitCondition
    }

    public void onStart(Vector3f startPos, Vector3f targetPos) {

        this.speed = defaultSpeed;
        this.startPos = startPos;
        this.targetPos = targetPos;

        if (targetPos != null && startPos != null) {
            started = true;
            passedTime = 0;
        }
    }

    public void onHit() {
    }

    public void onStep(float tpf) {
        if (skill.motionType == Skill.SKILL_MOTION_STRAIGHT) {
            //fxControl = getEffectControl();
            Vector3f projectilePos = Forces3f.linearVecTargetedDuration(passedTime, durationTime, startPos, targetPos);
            if (id == 1) {
//                System.out.println(startPos + " " + targetPos + " ");
//                System.out.println(id + " " + skill.name + " projectile " + projectilePos);
            }
            this.spatial.setLocalTranslation(projectilePos);
        }
    }

    public void onEnd() {
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (started) {
            if (passedTime > durationTime) {
                this.setEnabled(false);
                this.spatial.removeFromParent();
                this.spatial.removeControl(this);
                onEnd();
            } else {
                passedTime += tpf;
                if (passedTime - lastTime > stepTime) {
                    if (hitCondition != null && hitCondition.apply(spatial)) {
                        onHit();
                    }
                    onStep(tpf);
                    lastTime = passedTime;
                }
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
