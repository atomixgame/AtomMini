/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.assets.sprites;

import com.google.common.cache.Cache;
import com.jme3.texture.Texture;

/**
 *
 * @author cuong.nguyen
 */
public class SpriteSheet {
    class TextureRegionInfo {
        
    }
    Cache<TextureRegionInfo,Texture> textureCache;
    String format;
    boolean autoDetectFormat;
    
    public void fromXML(String path){
        
    }
    public void fromXMLText(String text){
        
    }
    public void fromJSON(String path){
        
    }
    public void fromJSONText(String text){
        
    }
}
