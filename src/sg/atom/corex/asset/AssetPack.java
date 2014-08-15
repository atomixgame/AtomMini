package sg.atom.corex.asset;

import com.jme3.asset.AssetKey;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.joda.time.DateTime;
import sg.atom.core.lang.AUID;

/**
 * AssetPack is a collections of AssetKey with its description author. Merged
 * artifact from SDK to in-game editor. AssetPack also provide artifacts for
 * scenario based asset loading.
 * 
 * <h4>Why you should use AssetPack instead of just AssetKey?</h4>
 *
 * @author cuong.nguyenmanh2
 */
public class AssetPack extends AssetKey implements Comparable<AssetPack>, Cloneable{

    String id;
    AUID uid;
    String author;
    String license;
    DateTime createDate;
    Collection<AssetKey> assetKeys;
    boolean typed;
    int type;
    int scenerio;
    float loadedPercentage;

    public AssetPack merge(AssetPack anotherPack) {
        return null;
    }

    public int compareTo(AssetPack o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Save & Load -------------------------------------------------------------
    /**
     *
     * @param filePath
     */
    public void toXML(String filePath) {
    }

//    public void build(ProjectBuildMechanism buildMechanism) {
//
//    }
    @Override
    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection<AssetKey> getAssetKeys() {
        return assetKeys;
    }
}
