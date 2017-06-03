package sg.atom.cycle;

import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.corex.assets.DelegateJSONLoader;
import sg.atom.corex.assets.DelegateXMLLoader;
import sg.atom.corex.assets.sprite.SpriteSheetLoader;

/**
 *
 * @author DaiPhongPC
 */
public class SimpleCycle implements IGameCycle {

    AtomMain app;

    public void init() {
        initConfigurations();
        //app.registerServices();
        //app.initServices();
        //app.registerManagers();
        initManagers();
        activesManagers();
        //app.initStates();
    }

    public void initConfigurations() {

    }

    public void initManagers() {

        if (app.getEntityManager() != null) {
            app.getEntityManager().init();
        }
        if (app.getMaterialManager() != null) {
            app.getMaterialManager().init();
        }
        if (app.getSoundManager() != null) {
            app.getSoundManager().init();
        }
        if (app.getStageManager() != null) {
            app.getStageManager().init();
        }
        if (app.getWorldManager() != null) {
            app.getWorldManager().init();
        }
        if (app.getEffectManager() != null) {
            app.getEffectManager().init();
        }

    }

    public void initAssets() {
        app.getAssetManager().registerLoader(SpriteSheetLoader.class, "sprites");
        app.getAssetManager().registerLoader(DelegateJSONLoader.class, "json");
        app.getAssetManager().registerLoader(DelegateXMLLoader.class, "xml");
    }

    public void activesManagers() {

        if (app.getEntityManager() != null) {
            app.getStateManager().attach(app.getEntityManager());
        }
        if (app.getMaterialManager() != null) {
            app.getStateManager().attach(app.getMaterialManager());
        }
        if (app.getSoundManager() != null) {
            app.getStateManager().attach(app.getSoundManager());
        }
        if (app.getStageManager() != null) {
            app.getStateManager().attach(app.getStageManager());
        }
        if (app.getWorldManager() != null) {
            app.getStateManager().attach(app.getWorldManager());
        }

        if (app.getEffectManager() != null) {
            app.getStateManager().attach(app.getEffectManager());
        }
        //getEventBus().register(app.getWorldManager());
    }

    public void load() {
        //show loading screen
        //if there any loading
        if (app.getMaterialManager() != null) {
            app.getMaterialManager().load();
        }
        if (app.getMaterialManager() != null) {
            app.getMaterialManager().load();
        }
        if (app.getWorldManager() != null) {
            app.getWorldManager().load();
        }
        if (app.getStageManager() != null) {
            app.getStageManager().load();
        }

    }

    public void config(Configuration configuration) {
        if (app.getMaterialManager() != null) {
            app.getMaterialManager().config(null);
        }
        if (app.getSoundManager() != null) {
            app.getSoundManager().config(null);
        }
        if (app.getWorldManager() != null) {
            app.getWorldManager().config(null);
        }
        if (app.getStageManager() != null) {
            app.getStageManager().config(null);
        }

    }

    public void update(float tpf) {
        if (app.getMaterialManager() != null) {
            app.getMaterialManager().update(tpf);
        }
        if (app.getSoundManager() != null) {
            app.getSoundManager().update(tpf);
        }
        if (app.getWorldManager() != null) {
            app.getWorldManager().update(tpf);
        }
        if (app.getStageManager() != null) {
            app.getStageManager().update(tpf);
        }

    }

    public void finish() {
        if (app.getStageManager() != null) {
            app.getStageManager().finish();
        }
        if (app.getWorldManager() != null) {
            app.getWorldManager().finish();
        }
        if (app.getSoundManager() != null) {
            app.getSoundManager().finish();
        }
        if (app.getMaterialManager() != null) {
            app.getMaterialManager().finish();
        }
    }
};
