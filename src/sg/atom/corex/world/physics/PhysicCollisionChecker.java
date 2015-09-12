/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.physics;

import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.math.Ray;
import com.jme3.scene.Node;

/**
 *
 * @author cuong.nguyen
 */
public interface PhysicCollisionChecker {

    public PhysicsRayTestResult checkRayCollision(Ray ray, Node shootables);
    
}
