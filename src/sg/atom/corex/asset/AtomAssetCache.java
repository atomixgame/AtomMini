/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.asset;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.jme3.app.Application;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import com.jme3.asset.cache.AssetCache;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;

/**
 * Evict with remove policy Cache based in GuavaCache for chained/ linked
 * elements.
 *
 * <p>JME3 Cache is based in oncurrentHashMap<AssetKey, Object> or
 * ConcurrentHashMap<AssetKey, AssetRef>. It not very well suited with the flows
 * / channels / tasks architecture of Atom. That's why a fork! This Cache
 * implements JME AssetCache so it can also be used in normal AssetManager. It
 * also a LoadingCache and can be use as Normal GuavaCache.
 *
 * <p>http://code.google.com/p/guava-libraries/wiki/CachesExplained . GuavaCache
 * provide some feature like Size/Timed/Reference Evict, with Removal Listener.
 *
 * <p>AtomAssetCache can wrap around any Asset to make it semi-persistent.
 *
 * <p>Take a look at AtomCacheUtils also provide some functions just related to
 * normal Java object instead of Asset.
 *
 * @author cuong.nguyenmanh2
 */
public class AtomAssetCache implements AssetCache, ManagableObject {

    LoadingCache<AssetKey, Object> loadingCache;
    
//    public Observable<AssetKey> getObservable() {
//        return null;
//    }

    public CacheBuilder getCacheBuilder() {
        return CacheBuilder.newBuilder();
    }


    public <T> void addToCache(AssetKey<T> key, T obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public <T> void registerAssetClone(AssetKey<T> key, T clone) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void notifyNoAssetClone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public <T> T getFromCache(AssetKey<T> key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public boolean deleteFromCache(AssetKey key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void clearCache() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void init(Application app) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void initManagers(IGameCycle... managers){
        
    }

    public void load(AssetManager assetManager) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void config(Configuration configuration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void finish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
