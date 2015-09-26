/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.fx;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author cuong.nguyen
 */
public class TimeSpan {

    public long startTime;
    public long endTime;
    public Vector3f startPos;
    public Vector3f endPos;
    public Quaternion startRot;
    public Quaternion endRot;
    public Spatial spatial;

    public TimeSpan(Vector3f endPos, Quaternion endRot, long startTime, long endTime) {
        this.endPos = endPos;
        this.endRot = endRot;
        this.startTime = startTime;
        this.endTime = endTime;
        if (startTime == endTime) {
            startPos = endPos;
        }
    }

    public boolean apply(long now) {
        if (now < startTime) {
            return true;
        }

        if (startPos == null) {
            startPos = spatial.getLocalTranslation().clone();
            startRot = spatial.getLocalRotation().clone();
        }

        if (now >= endTime) {
            // Force the spatial to the last position
            spatial.setLocalTranslation(endPos);
            spatial.setLocalRotation(endRot);

            return false; // no more to go               
        } else {
            // Interpolate... guaranteed to have a non-zero time delta here
            double part = (now - startTime) / (double) (endTime - startTime);

            // Do our own interp calculation because Vector3f's is inaccurate and
            // can return values out of range... especially in cases where part is
            // small and delta between coordinates is 0.  (Though this probably
            // wasn't the issue I was trying to fix, it is worrying in general.)                
            Vector3f v = spatial.getLocalTranslation();
            double x = v.x;
            double y = v.y;
            double z = v.z;
            x = startPos.x + (endPos.x - startPos.x) * part;
            y = startPos.y + (endPos.y - startPos.y) * part;
            z = startPos.z + (endPos.z - startPos.z) * part;
            spatial.setLocalTranslation((float) x, (float) y, (float) z);

            Quaternion rot = startRot.clone();
            rot.nlerp(endRot, (float) part);
            spatial.setLocalRotation(rot);

            return true; // still have more to go
        }
    }

    public String toString() {
        return "TimeSpan[" + startPos + " to:" + endPos + ", from:" + startTime + " to:" + endTime + "]";
    }
}
