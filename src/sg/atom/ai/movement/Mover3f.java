/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.movement;

import com.jme3.math.Vector3f;

/**
 *
 * @author cuong.nguyen
 */
public class Mover3f {
    
    public float orientation;
    public float veclocity = 1;
    public float accel = 1.5f;
    
//    public float defaultSpeed = 0.05f;
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
    protected Vector3f targetPos;
    protected Vector3f viewDirection = new Vector3f();
    protected Vector3f walkDirection = new Vector3f();

    public Vector3f getViewDirection() {
        return viewDirection;
    }

    public Vector3f getWalkDirection() {
        return walkDirection;
    }

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public float getVeclocity() {
        return veclocity;
    }

    public void setVeclocity(float veclocity) {
        this.veclocity = veclocity;
    }

    public float getAccel() {
        return accel;
    }

    public void setAccel(float accel) {
        this.accel = accel;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public float getChangeViewSpeed() {
        return changeViewSpeed;
    }

    public void setChangeViewSpeed(float changeViewSpeed) {
        this.changeViewSpeed = changeViewSpeed;
    }

    public boolean isRolling() {
        return rolling;
    }

    public void setRolling(boolean rolling) {
        this.rolling = rolling;
    }

    public Vector3f getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(Vector3f targetPos) {
        this.targetPos = targetPos;
    }
    
    
}
