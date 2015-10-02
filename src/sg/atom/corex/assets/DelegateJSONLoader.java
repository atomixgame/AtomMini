package sg.atom.corex.assets;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author cuong.nguyen
 */
public class DelegateJSONLoader implements AssetLoader{

    HashMap<String,String> prefixToClassMap = new HashMap<String, String>();
    HashMap<String,String> folderToClassMap = new HashMap<String, String>();
    
    public DelegateJSONLoader() {
        prefixToClassMap.put("sprites", "sg.gatom.corex.assets.sprite.SpriteLoader");
        
        
    }

    public Object load(AssetInfo assetInfo) throws IOException {
//        assetInfo.getKey().g
        return null;
    }
    
}
