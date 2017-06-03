package sg.atom.corex.stage;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

/**
 *
 * @author DaiPhongPC
 */
public interface VirtualGamePlayUnit {

    public Spatial getNode();

    public void load(AssetManager assetManager);
    
}
