/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.managers;

import com.jme3.app.state.AbstractAppState;
import sg.atom.corex.stage.sound.SoundClip;
import java.util.ArrayList;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.corex.stage.sound.AudioChannel;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SoundManager extends AbstractAppState implements IGameCycle{
    protected AtomMain app;
    protected ArrayList<SoundClip> fxSounds;
    protected ArrayList<SoundClip> musicSounds;
    protected ArrayList<AudioChannel> channels;
    
    public SoundManager(AtomMain app) {
        
    }
    
    public void playSound(String soundName) {
    }
    
    //Cycle -------------------------------------------------------------------
    public void init(){
        
    }
    
    public void config(Configuration configuration){
        
    }
    
    public void load(){
        
    }
    
    public void update(float tpf){
        
    }
    
    public void finish(){
        
    }
    
    //GETTER & SETTER
}
