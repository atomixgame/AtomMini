package sg.atom.corex.world.collision;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector4f;
import sg.atom.corex.logic.Command;

/**
 * CollisionChecker check and cache the results!
 * @author cuong.nguyen
 */
public interface CollisionChecker {

    public CollisionResults checkRayCollision(Ray ray, Collidable shootables);

    public CollisionResults checkRayCollision(Ray ray, Collidable shootables, Command<CollisionResults> callback);

    public CollisionResults getCurrentResults();
    
    public CollisionResults checkBoundCollision(Vector4f bound, Collidable shootables);
    
    public CollisionResults checkBoundCollision(Vector4f bound, Collidable shootables, Command<CollisionResults> callback);
    
    public void setDebug(boolean debuging);
}
