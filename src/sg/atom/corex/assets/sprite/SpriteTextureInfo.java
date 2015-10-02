package sg.atom.corex.assets.sprite;

import com.jme3.math.Vector2f;

/**
 * SpriteTextureInfo represent a Sprite slot in SpriteSheet. Its concept is like
 * TextureAtlasTile, but used for loading TextureAtlasTile cell.
 *
 * @author cuong.nguyen
 */
public class SpriteTextureInfo{

    int x, y, width, height;
    String name;
    String path;
    String dir;

    public Vector2f getSize(){
        return new Vector2f(width,height);
    }
    @Override
    public String toString() {
        return "x=" +x+ "|y="+y+"|w="+width+"|h="+height;
    }
    
    public String toTonegodGUIFormat(){
        return "x=" +x+ "|y="+y+"|w="+width+"|h="+height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
}
