/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.sound;

import com.jme3.cinematic.events.AbstractCinematicEvent;
import com.jme3.export.Savable;

/**
 * Warpter for AudioNode.
 *
 * @author cuong.nguyenmanh2
 */
public class SoundClip extends AbstractCinematicEvent implements Savable {

    String name;
    String path;
    int id;
    int status;
    float duration;
    float volume;
    float fade;
    int type;
    int musicType;
    int playingType;

    @Override
    protected void onPlay() {
    }

    @Override
    protected void onUpdate(float tpf) {
    }

    @Override
    protected void onStop() {
    }

    @Override
    public void onPause() {
    }

}
