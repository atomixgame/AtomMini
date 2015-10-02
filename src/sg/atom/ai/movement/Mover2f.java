/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.movement;

import com.jme3.math.Vector2f;

/**
 *
 * @author cuong.nguyen
 */
public class Mover2f {

    public float orientation;
    public float veclocity = 1;
    public float accel = 1.5f;
    public float speed = 1;
    public float maxSpeed = 5;
    public float moveSpeed = 1;
    protected float changeViewSpeed = 0.5f;
//    public float initSpeed = 1;
//    public float initVec = 1;
    public boolean jumping;
    public float airTime = 0;
    protected boolean prepareJump;
    protected boolean rolling;
    protected Vector2f targetPos;
    protected Vector2f viewDirection;
    protected Vector2f walkDirection = new Vector2f();

    public Mover2f() {
        this.walkDirection = new Vector2f();
        this.viewDirection = new Vector2f();
    }

//    public void letClearTargetPos() {
//        this.targetPos = null;
//    }

    public Vector2f getViewDirection() {
        return viewDirection;
    }

    public void setMoveSpeed(float speed) {
        this.moveSpeed = speed;
    }
}
