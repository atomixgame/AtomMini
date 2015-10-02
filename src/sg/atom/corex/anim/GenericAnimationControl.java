/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.anim;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SkeletonControl;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import java.util.HashMap;

/**
 * To use with CharacterCustomiztation and Animation state machine later!
 *
 * @author cuong.nguyen
 */
public class GenericAnimationControl extends AbstractControl implements AnimEventListener {

    protected HashMap<String, String> animNameMap = new HashMap<String, String>();
    protected AnimChannel animationChannel;
    protected AnimControl animationControl;
    protected SkeletonControl skeletonControl;
//    protected int currentAttackType = 1;
//    protected AnimChannel shootingChannel;
//    protected AnimChannel lowerBody, upperBody;

    protected void init() {
        animationChannel = animationControl.createChannel();

        animNameMap.put("Walk", "Walk");
        animNameMap.put("Jump", "Jump");
        animNameMap.put("Stand", "Idle1");
        animNameMap.put("Run", "Run");
        animationControl = getSpatial().getControl(AnimControl.class);
        animationControl.addListener(this);

        //        lowerBody = animationControl.createChannel();
//        lowerBody.addFromRootBone("thigh_L");
//        lowerBody.addFromRootBone("thigh_R");
//        upperBody = animationControl.createChannel();
//        upperBody.addFromRootBone("spine");
        /*
         // will blend over 15 seconds to stand
         feet.setAnim("Walk", 15);
         feet.setSpeed(0.25f);
         feet.setLoopMode(LoopMode.Cycle);
         // left hand will pull
         upperBody.addFromRootBone("spine");
         upperBody.setAnim("Magic1");
         upperBody.setSpeed(0.5f);
         upperBody.setLoopMode(LoopMode.Cycle);
         */
    }

    public void setAnim(String animName, float transitionTime, float speed, LoopMode loop) {
        if (animNameMap.get(animName) == null) {
            //throw new RuntimeException("Can not find animName :" + animName);
        } else {
            animationChannel.setAnim(animNameMap.get(animName), transitionTime);
            animationChannel.setLoopMode(loop);
            animationChannel.setSpeed(speed);
        }
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    public boolean isCurrentAnim(String animName) {
        if (animNameMap.get(animName) != null) {
            if (animationChannel.getAnimationName() != null) {
                return animationChannel.getAnimationName().equalsIgnoreCase(animName);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
