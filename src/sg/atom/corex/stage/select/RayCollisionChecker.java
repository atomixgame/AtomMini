package sg.atom.corex.stage.select;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import sg.atom.corex.logic.Command;
import sg.atom.corex.world.collision.CollisionChecker;

/**
 *
 * @author cuong.nguyen
 */
public class RayCollisionChecker implements CollisionChecker {

    private CollisionResults currentResults;
    private boolean debugEnabled;

    public RayCollisionChecker() {
        this.currentResults = new CollisionResults();
    }

    public CollisionResults getCurrentResults() {
        return currentResults;
    }

    public CollisionResults checkRayCollision(Ray ray, Collidable collidable, Command<CollisionResults> callbackFunction) {
        currentResults.clear();

        // Collect intersections between ray and all nodes in results list.
        collidable.collideWith(ray, currentResults);

        // FIXME: Debug
        if (debugEnabled) {
            for (int i = 0; i < currentResults.size(); i++) {
                // (For each "hit", we know distance, impact point, geometry.)
                float dist = currentResults.getCollision(i).getDistance();
                Vector3f pt = currentResults.getCollision(i).getContactPoint();
                String target = currentResults.getCollision(i).getGeometry().getName();
                System.out.println("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
            }
        }
        if (callbackFunction != null) {
            if (currentResults.size() > 0) {
                callbackFunction.execute(currentResults);
            }
        }

        return currentResults;
    }

    public CollisionResults checkRayCollision(Ray ray, Collidable collidable) {
        return checkRayCollision(ray, collidable, null);
    }

    public void setDebug(boolean debuging) {
        debugEnabled = debuging;
    }

    public CollisionResults checkBoundCollision(Vector4f bound, Collidable shootables) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public CollisionResults checkBoundCollision(Vector4f bound, Collidable shootables, Command<CollisionResults> callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
