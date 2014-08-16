/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.fx.sprite;

import java.util.ArrayList;
import java.util.Iterator;
import sg.atom.corex.animation.AtomAnimation;


/**
 * A Sprite Animation is a kind of Animation which replace the current Sprite
 * with the next Sprite. Underlying, a sprite animation is a kind of Frame based
 * animation.
 *
 * @author CuongNguyen
 */
public class SpriteAnimation extends AtomAnimation {

    public ArrayList<Integer> getFrameIndexes() {
        return null;
    }

    public ArrayList<SpriteImage> getFrames() {
        return null;
    }

    public String getName() {
        return "";
    }

    public SpriteImage getFrameAt(float time) {
        return null;
    }

//
//    public AtomBuffer getBuffer() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//
//    public AtomBuffer getSlice(StreamCursor cursor) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }


//    public int compareTo(AtomAnimation o) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//
//    public Iterator<IAnimationState> iterator() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
}
