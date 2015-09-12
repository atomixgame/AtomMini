package sg.atom.gameplay.controls;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.SkeletonControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.util.HashMap;
import sg.atom.gameplay.CommonGameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.managers.EffectManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class BaseCharacterControl extends AbstractControl implements AnimEventListener {
    // Shortcuts   

    protected GamePlayManager gamePlayManager;
    protected StageManager stageManager;
    //Visual
    protected CommonGameCharacter character;
    protected Node playerModel;
    // movement  ---------------------------------
    public float defaultSpeed = 0.05f;
    public float speed = 1;
    public float veclocity = 1;
    public float accel = 1.5f;
    public float airTime = 0;
    public float maxSpeed = 5;
    public float moveSpeed = 1;
    public float orientation;
    protected float changeViewSpeed = 0.5f;
    public float initSpeed = 1;
    public float initVec = 1;
    public boolean jumping;
    protected boolean prepareJump;
    protected boolean rolling;
    protected Vector3f targetPos;
    protected Vector3f viewDirection;
    protected Vector3f walkDirection = new Vector3f();
    //animation  ---------------------------------
    // AnimNameMap provide a safe way to change animation names!
    protected HashMap<String, String> animNameMap = new HashMap<String, String>();
    protected AnimChannel animationChannel;
    protected AnimControl animationControl;
    protected SkeletonControl skeletonControl;
    protected int currentAttackType = 1;
    protected AnimChannel shootingChannel;
    protected AnimChannel lowerBody, upperBody;
    protected boolean left = false, right = false, up = false, down = false;
    // FIXME: Replace with AnimBehaviours
    // Controls  ---------------------------------
    protected boolean usePhysicControl = false;
    protected boolean inputControlled = false;
    // collision ---------------------------------
    protected float collisionTimePassed = 0;
    protected float collisionTimeInterval = 3;
    protected float awareItemDistance = 3;
//    CamShaker camShaker;
    // target ------------------------------------
    protected Spatial selectedEntitySpatial;

    public BaseCharacterControl(StageManager stageManager, CommonGameCharacter character) {

        this.stageManager = stageManager;
        this.gamePlayManager = stageManager.getGamePlayManager();
        this.character = character;
        this.moveSpeed = defaultSpeed;
        this.walkDirection = new Vector3f(Vector3f.UNIT_X);
        this.viewDirection = new Vector3f(Vector3f.UNIT_X);
        character.setCharacterControl(this);
        character.getModel().addControl(this);
    }

    public BaseCharacterControl() {
    }
    // Animation ---------------------------------------------------------------
    // FIXME: Replace with AnimBehavours

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {

//         if (channel == shootingChannel) {
//         channel.setAnim("stand");
//         } else if (channel == upperBody) {
//         animationControl.clearChannels();
//         setupBodyAnim();
//         }
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

    protected void animAttack(float speed) {
//        String add = "";
//        if (currentAttackType < 3) {
//            currentAttackType++;
//            add = Integer.toString(currentAttackType);
//        } else {
//            currentAttackType = 1;
//        }
        //setupPartsAnim();
    }

    protected void animDefend(float speed) {
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return new BaseCharacterControl();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    protected void createPhysicCharacter() {
        //CapsuleCollisionShape capsule = new CapsuleCollisionShape(0.6f, 0.9f);
//        physicControl = new BetterCharacterControl(0.4f, 1.8f, 60f);
//        physicControl.setJumpForce(new Vector3f(0.0f, 270f, 0.0f));
//        physicControl.setGravity(new Vector3f(0.0f, -40f, 0.0f));
//        playerModel.addControl(physicControl);
    }
    // Rountines  ---------------------------------------------------------------
    // Events

    public void onSkillAttackHurt(CommonGameCharacter attacker) {
        Vector3f directionOfAttack = spatial.getLocalTranslation().subtract(attacker.getLocation()).normalize();
        EffectManager effectManager = character.getStageManager().getEffectManager();
        effectManager.createMovement(spatial, 0.6f, new float[]{0.4f},
                spatial.getLocalTranslation().add(directionOfAttack),
                spatial.getLocalTranslation().clone());

    }
    //Actions---------------------------------------------------------------

    public void talk(String msg) {
        //        System.out.println("" + msg);
        //        inGameUI.alert(msg);
    }

    protected void attack(Character target) {
    }

    protected void defend(int status) {
    }

    public void skillAttack(CommonGameCharacter target) {
        EffectManager effectManager = character.getStageManager().getEffectManager();
        effectManager.createMovement(spatial, 4.5f, new float[]{2, 2.5f},
                spatial.getLocalTranslation().add(0, 1, 0),
                target.getLocation().clone(),
                spatial.getLocalTranslation().clone());

//        target.getCharacterControl().onSkillAttackHurt(character);
    }

    public void letClearTargetPos() {
        this.targetPos = null;
    }
    // Movements  ---------------------------------------------------------------

    public void letMoveTargeted(float tpf) {
        if (targetPos != null) {
            moveTo(this.targetPos, tpf);
        }
    }

    public void letMoveTo(Vector3f targetPos) {
        this.targetPos = targetPos;
    }

    public void moveChar(float tpf) {
    }

    public void moveTo(Vector3f targetPos, float tpf) {
        boolean reach = false;
        Vector3f currentPos = getCurrentPos().clone();
        float distance = currentPos.distance(targetPos);
        Vector3f newPos = new Vector3f();
        if (moveSpeed <= distance) {
            newPos = currentPos.interpolateLocal(targetPos, moveSpeed / distance);
            getSpatial().lookAt(newPos, Vector3f.UNIT_Y);
        } else {
            newPos.set(targetPos);
            reach = true;
            letClearTargetPos();
        }
        setCurrentPos(newPos);
        //        System.out.println("(*) MOVE         Current:" + currentPos + " Target : " + targetPos + " Dis : " + distance + " ratio: " + (moveSpeed / distance));
    }

    protected void setViewDirection(Vector3f viewDir, float amount) {
        Quaternion newRot = new Quaternion().fromAngleAxis(viewDirection.angleBetween(viewDir), Vector3f.UNIT_Y);
        this.viewDirection.set(viewDir);
        this.playerModel.getLocalRotation().slerp(newRot, amount);
        //        this.playerModel.setLocalRotation();
    }

    public void setWalkDirection(Vector3f walkDir) {
        Vector3f newLocalPos = getCurrentPos().add(walkDir);
        setCurrentPos(newLocalPos);
        this.walkDirection.set(walkDir);
        //        cam.setLocation(newLocalPos.add(new Vector3f(0, 10, 15)));
    }

    protected void setupAnimationController() {
        animNameMap.put("Walk", "Walk");
        animNameMap.put("Jump", "Jump");
        animNameMap.put("Stand", "Idle1");
        animNameMap.put("Run", "Run");
        animationControl = playerModel.getControl(AnimControl.class);
        animationControl.addListener(this);
        setupBodyAnim();
    }

    protected void setupBodyAnim() {
        animationChannel = animationControl.createChannel();
    }

    protected void setupPartsAnim() {
    }

    public void updateAnimation(float tpf) {
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    // setter & getter ---------------------------------------------------------------
    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        playerModel = (Node) spatial;
        //        setupAnimationController();
        //        createPhysicCharacter();
        //        attachPhysic();
    }

    protected Vector3f getCurrentPos() {
        return playerModel.getLocalTranslation();
    }

    public Vector3f getLocation() {
        return playerModel.getLocalTranslation();
    }

    protected Vector3f getViewDirection() {
        return viewDirection;
    }

    public void setCurrentPos(Vector3f newPos) {
        playerModel.setLocalTranslation(newPos);
    }

    public void setLocation(Vector3f location) {
        //physicControl.warp(location);
    }

    public void setMoveSpeed(float speed) {
        this.moveSpeed = speed;
    }

    public void let(String die) {
    }

    public void face(CommonGameCharacter target) {
        spatial.lookAt(target.getLocation(), Vector3f.UNIT_Y);
    }
}
