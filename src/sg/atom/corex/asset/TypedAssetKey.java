/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.asset;

import com.jme3.asset.AssetKey;

/**
 * More feature in AssetKey.
 *
 * @author CuongNguyen
 */
public class TypedAssetKey<T> extends AssetKey<T> {

    boolean asCollection;
    Class rootClass;

    public TypedAssetKey(String path, Class rootClass, boolean asCollection) {
        super(path);
        this.asCollection = asCollection;
        this.rootClass = rootClass;
    }

    public Class getRootClass() {
        return rootClass;
    }

    public boolean isAsCollection() {
        return asCollection;
    }
}
