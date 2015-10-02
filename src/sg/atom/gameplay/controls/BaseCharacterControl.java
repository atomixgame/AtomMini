package sg.atom.gameplay.controls;

import sg.atom.corex.anim.GenericAnimationControl;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.util.List;
import sg.atom.AtomMain;
import sg.atom.ai.Agent;
import sg.atom.ai.Behaviour;
import sg.atom.ai.movement.Mover3f;
import sg.atom.corex.input.InputRecorder;
import sg.atom.gameplay.CommonGameCharacter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class BaseCharacterControl extends AbstractControl implements Agent {

    protected CommonGameCharacter character;
    protected Node playerModel;
    protected Mover3f mover;
    protected GenericAnimationControl animationControl;
    protected boolean[] signals;
    protected InputRecorder inputRecorder;
//    protected boolean usePhysicControl = false;
//    protected boolean inputControlled = false;
    private AtomMain app;

    public BaseCharacterControl(CommonGameCharacter character) {
        this.character = character;
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return this;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    // Rountines  ---------------------------------------------------------------
    // Events
    public void onStage() {
        character.getModel().addControl(this);
    }
    //Actions---------------------------------------------------------------

    public void let(String command) {
    }

    public void face(CommonGameCharacter target) {
        spatial.lookAt(target.getLocation(), Vector3f.UNIT_Y);
    }

    public void talk(String msg) {
    }

    protected void attack(Character target) {
    }

    protected void defend(int status) {
    }

    protected void setupAnimationController() {
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

    public void setCurrentPos(Vector3f newPos) {
        playerModel.setLocalTranslation(newPos);
    }

    public void setLocation(Vector3f location) {
        //physicControl.warp(location);
    }

    public Mover3f getMover() {
        return mover;
    }

    public void setMover(Mover3f mover) {
        this.mover = mover;
    }

    public CommonGameCharacter getCharacter() {
        return character;
    }

    public long getId() {
        return 0;
    }

    public List<Behaviour> getBehaviours() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addBehaviour(Behaviour behaviour) {
        this.spatial.addControl(behaviour.getControl());
    }

    public void react(Object signal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public AtomMain getApp() {
        return app;
    }
}
