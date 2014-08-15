package sg.atom.gameplay.controls;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;

import com.jme3.input.ChaseCamera;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitorAdapter;
import com.jme3.scene.Spatial;
import java.util.logging.Logger;
import sg.atom.gameplay.GameCharacter;
import sg.atom.stage.StageManager;

/**
 * This character control try to immitate the Character movement in the game
 * PrinceOfPersian - The two thrones.
 *
 * @author hungcuong
 */
public class PlayerCharacterControl extends BaseCharacterControl implements ActionListener {

    protected static final Logger logger = Logger.getLogger(PlayerCharacterControl.class.getName());
    //    GameEffectManager gameEffectManager;
    //camera  ---------------------------------
    protected ChaseCamera chaseCam;
    protected Camera cam;
//    protected BetterCharacterControl physicControl;
//    protected WeaponControl weaponControl;
//    CamShaker camShaker;
    // target ------------------------------------

    public PlayerCharacterControl(StageManager stageManager) {
        this(stageManager, null);
    }

    public PlayerCharacterControl(StageManager stageManager, GameCharacter character) {
        super(stageManager, character);
//        this.gameEffectManager = stageManager.getGameEffectManager();
//        inGameUI = (UIIngameController) stageManager.getGUIManager().getNifty().getCurrentScreen().getScreenController();
    }

    @Override
    protected void controlUpdate(float tpf) {
        super.controlUpdate(tpf);
        if (inputControlled && (left || right || up || down)) {
            letClearTargetPos();
            moveCharByInput(tpf);
        } else {
            letMoveTargeted(tpf);
        }
//        if (!gamePlayManager.fightMode) {
//            setCamPos();
//        }
        //updateAnimation(tpf);
        //checkCollision(stageManager.getRootNode(), tpf);
    }

    public void moveCharByInput(float tpf) {
        this.cam = stageManager.getCurrentActiveCamera();
        //         if (isOnWater()) {
        //         moveSpeed = 0.04f;
        //         }
        //
        Vector3f camDir = cam.getDirection().mult(moveSpeed);
        Vector3f camLeft = cam.getLeft().mult(moveSpeed);
        camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        //        System.out.println("left:" + left + " right:" + right + " up:" + up + " down:" + down);
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        if (usePhysicControl) {
            //        if (!physicControl.isOnGround()) {
            //            //System.out.println(airTime);
            //            airTime = airTime + tpf;
            //
            //            if (airTime > 0.05f) {
            //                jumping = true;
            //            }
            //
            //        } else {
            //            airTime = 0;
            //            jumping = false;
            //            rolling = false;
            //        }
            //        Vector3f tempViewDir = physicControl.getViewDirection().clone();
            //
            //        physicControl.setViewDirection(tempViewDir.interpolate(walkDirection, 1 / changeViewSpeed * tpf));
            //        physicControl.setWalkDirection(walkDirection);
        } else {
            setWalkDirection(walkDirection);
            setViewDirection(walkDirection.clone(), 1 / changeViewSpeed * tpf);
        }
    }

    //Animation-----------------------------------------------------------------
    public void updateAnimation(float tpf) {
        if (jumping) {
            if (!isCurrentAnim("Jump")) {
                setAnim("Jump", 0f, 1.0f, LoopMode.DontLoop);
                if (!rolling) {
                    //setAnim("Jump", 0.1f, 1.0f, LoopMode.DontLoop);
                    //System.out.println("Jumping");
                }
                //animationChannel.setTime(animationChannel.getAnimMaxTime() / 3 * 2);
            } else {
//                 if (airTime > 1f) {
//                 // On the air
//                 if (!isCurrentAnim("FlyRolling")) {
//                 setAnim("FlyRolling", 0.8f, 1.2f, LoopMode.Loop);
//                 rolling = true;
//                 }
//                 }
            }
        } else if (walkDirection.length() == 0) {
            // Standing
            if (!isCurrentAnim("Stand")) {
                setAnim("Stand", 0, 2f, LoopMode.Loop);
                moveSpeed = 1f;
            }
        } else {
            if (airTime > 0.1f) {
                // On the air
//                 if (!isCurrentAnim("Walk")) {
//                 setAnim("Walk", 0.2f, 2f, LoopMode.Loop);
//                 moveSpeed = 1f;
//                 }
            } else if (!isCurrentAnim("Run")) {
                setAnim("Run", 1.2f, 1f, LoopMode.Loop);
                moveSpeed = 4f;
            }
        }
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

    //    void attachWeapon() {
    //        //WorldManager worldManager = stageManager.getWorldManager();
    //        //ParticleEmitter flame = gameEffectManager.createFlame();
    //        //skeletonControl.getAttachmentsNode("hand_L").attachChild(flame);
    //        //flame.emitAllParticles();
    //        //skeletonControl.getAttachmentsNode("hand_L").attachChild(worldManager.createRedBox());
    //        //Node swordNode = WeaponControl.createSword();
//         TrailControl trailControl = new TrailControl(character, stageManager);
//         playerModel.addControl(trailControl);
    //    }
    //
    //    private void createWeapon() {
    //        weaponControl = new WeaponControl(physicControl, stageManager);
    //        playerModel.addControl(weaponControl);
    //    }
    
    
    protected void setupAnimationController() {
        animNameMap.put("Walk", "Walk");
        animNameMap.put("Jump", "Jump");
        animNameMap.put("Stand", "Idle1");
        animNameMap.put("Run", "Run");
        animationControl = playerModel.getControl(AnimControl.class);
        animationControl.addListener(this);
        setupBodyAnim();
        //setupPartsAnim();

    }

    protected void setupBodyAnim() {
        animationChannel = animationControl.createChannel();
    }

    protected void setupPartsAnim() {
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

    private void animAttackSword() {
        upperBody.setAnim("Attack_Sword");
        upperBody.setLoopMode(LoopMode.DontLoop);
        upperBody.setSpeed(speed);
    }

    //Actions-------------------------------------------------------------------
    public void onAction(String binding, boolean value, float tpf) {

        if (binding.equals("CharLeft")) {
            if (value) {
                left = true;
            } else {
                left = false;
            }
        } else if (binding.equals("CharRight")) {
            if (value) {
                right = true;
            } else {
                right = false;
            }
        } else if (binding.equals("CharUp")) {
            if (value) {
                up = true;
            } else {
                up = false;
            }
        } else if (binding.equals("CharDown")) {
            if (value) {
                down = true;
            } else {
                down = false;
            }
        } else if (binding.equals("CharJump")) {
            if (!value) {

                talk("Jumped");
                jumping = true;
//                camShaker.shake();
//                physicControl.jump();
            } else {
                //prepareJump = true;
            }
        }
//        } else if (binding.equals("CharShoot") && !value) {
//            if (weaponControl.isWeaponInHand()) {
//                attack(1);
//            }
//        } else if (binding.equals("CharWeapon") && !value) {
//            weaponControl.switchIt();
//        }
        //System.out.println("left:" + left + " right:" + right + " up:" + up + " down:" + down);
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    public void attachPhysic() {
//        System.out.println("Add physic control for Character");
//        WorldManager worldManager = stageManager.getWorldManager();
//        if (worldManager.getWorldSettings().usePhysics) {
//            worldManager.getPhysicsSpace().add(physicControl);
//            logger.warning("Add physic control for Character");
//        } else {
//            throw new RuntimeException("To use Character Control you have to support physics!");
//        }
    }
    // Commands  ---------------------------------------------------------------

    public boolean isOnWater() {
        float y = spatial.getLocalTranslation().y;
        if (y > 0.4f && y < 1.6f) {
            return true;
        } else {
            return false;
        }
    }

    // Events ------------------------------------------------------------------
    public void onTurn() {
    }
    // Animations  ---------------------------------------------------------------

//    public void doWalkBlend() {
//    }
//
//    public void doDuck() {
//    }
//
//    public void doHop() {
//    }
//
//    public void doCrouch() {
//    }
//
//    public void doRoll() {
//    }
//
//    public void doWallJump() {
//    }
//
//    public void doWallRun() {
//    }
//
//    public void doLedgeGrad() {
//    }
//
//    public void doPickItem() {
//    }
//
//    public void doSwitchHand() {
//    }
//
//    public void doSwingBody() {
//    }
//
//    public void doBackFlip() {
//    }
//
//    public void getBalancePoint() {
//    }
//
//    public void doSwim() {
//    }
    // Check collision ---------------------------------------------------------
    void checkCollision(Node root, float tpf) {
        if (collisionTimePassed > collisionTimeInterval) {
            collisionTimePassed = 0;
            doCheckCollision(root);
        } else {
            collisionTimePassed += tpf;
        }
    }

    public void doCheckCollision(Node root) {
        root.depthFirstTraversal(new SceneGraphVisitorAdapter() {
            @Override
            public void visit(Geometry geom) {
                super.visit(geom);
                checkEntity(geom);
            }
        });
    }

    public void checkEntity(Spatial sp) {
        float dis = sp.getWorldTranslation().distance(spatial.getWorldTranslation());
        if (dis < awareItemDistance) {
            String entityId = sp.getParent().getUserData("entityId");
            if (entityId != null) {
                //check Bound collision
                Geometry geo = (Geometry) sp;

                boolean isInside = geo.getModelBound().contains(spatial.getWorldTranslation().clone());

                if (isInside) {
                    //pickedEntity(sp);
                } else {
                }
                onPickedEntity(sp);
            }
        }
    }

    public void onPickedEntity(Spatial sp) {
        if (sp != selectedEntitySpatial) {
            String entityId = sp.getParent().getUserData("entityId");
            talk(sp.getName() + " is :" + entityId);
            selectedEntitySpatial = sp;
        }
    }

    boolean inRange() {
        return false;
    }

    boolean ifEntity() {
        return true;
    }

    String getEntityInfo(Spatial sp) {
        return sp.getName();
    }

    public void onAnalog(String name, float value, float tpf) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setCamPos() {
        this.cam = stageManager.getCurrentActiveCamera();
        cam.setLocation(getCurrentPos().add(new Vector3f(0, 6, 9)));
    }

    public void setInputControlled(boolean inputControlled) {
        this.inputControlled = inputControlled;
    }

    public boolean isInputControlled() {
        return inputControlled;
    }
}
