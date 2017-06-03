/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage;

import com.jme3.animation.LoopMode;
import com.jme3.app.Application;
import com.jme3.cinematic.Cinematic;
import com.jme3.cinematic.PlayState;
import com.jme3.cinematic.events.CinematicEvent;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class GameAction implements Callable, Runnable, CinematicEvent {

    int id;
    GameActor actor;
    int status;
    boolean actived;
    boolean isTransparent;
    int type;

    public GameAction(GameActor actor) {
        this.actor = actor;
    }

    public void run() {
    }

    public Object call() throws Exception {
        return null;
    }

    public void play() {
        
    }

    public void stop() {
        
    }

    public void forceStop() {
        
    }

    public void pause() {
        
    }

    public float getDuration() {
        return 0;
    }

    public void setSpeed(float speed) {
        
    }

    public float getSpeed() {
        return 0;
    }

    public PlayState getPlayState() {
        return PlayState.Stopped;
    }

    public void setLoopMode(LoopMode loop) {
        
    }

    public LoopMode getLoopMode() {
        return LoopMode.DontLoop;
    }

    public float getInitialDuration() {
        return 0;
    }

    public void setInitialDuration(float initialDuration) {
        
    }

    public void internalUpdate(float tpf) {
        
    }

    public void initEvent(Application app, Cinematic cinematic) {
        
    }

    public void setTime(float time) {
        
    }

    public float getTime() {
        return 0;
    }

    public void dispose() {
        
    }

    public void write(JmeExporter ex) throws IOException {
        
    }

    public void read(JmeImporter im) throws IOException {
        
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public GameActor getActor() {
        return actor;
    }
}
