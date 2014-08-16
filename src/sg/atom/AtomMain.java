package sg.atom;

import com.google.common.util.concurrent.ServiceManager;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.device.DeviceInfo;
import sg.atom.core.entity.EntityManager;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.corex.MaterialManager;
import sg.atom.corex.asset.AtomAssetManager;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.net.NetworkManager;
import sg.atom.stage.EffectManager;
import sg.atom.stage.SoundManager;
import sg.atom.stage.StageManager;
import sg.atom.state.GameStateManager;
import sg.atom.ui.GUIManager;
import sg.atom.ui.ToneGodGUIManager;
import sg.atom.world.WorldManager;

/**
 * An optimized version of AtomMain which hard coded the Managers and the Cycle.
 */
public class AtomMain extends SimpleApplication implements IGameCycle {

    protected static AtomMain defaultInstance;
    protected AtomAssetManager exAssetManager;
    protected StageManager stageManager;
    protected GUIManager guiManager;
    protected GamePlayManager gamePlayManager;
    protected WorldManager worldManager;
    protected SoundManager soundManager;
    protected ServiceManager serviceManager;
    protected MaterialManager materialManager;
    protected EffectManager effectManager;
    protected EntityManager entityManager;
    protected NetworkManager networkManager;
    protected DeviceInfo deviceInfo;
    protected boolean customCycle = false;

    public static synchronized AtomMain getInstance() {
        if (defaultInstance == null) {
            defaultInstance = new AtomMain();
        }
        return defaultInstance;
    }
    private GameStateManager gameStateManager;

    @Override
    public void simpleInitApp() {
        deviceInfo = new DeviceInfo(this);
        init();
        load();
        config(null);
    }

    protected void initServices() {
        //network service
        //social service
        //game leaderboard service
        //content update service
        
    }

    protected void initManagers() {
        this.materialManager = new MaterialManager(assetManager);
        this.soundManager = new SoundManager(this);
        this.worldManager = new WorldManager(this);
        this.stageManager = new StageManager(this);
        //this.guiManager = new NiftyGUIManager(this);
        this.guiManager = new ToneGodGUIManager(this);
        this.gamePlayManager = new GamePlayManager(this);

        if (!customCycle) {
            materialManager.init();
            soundManager.init();
            stageManager.init();
            worldManager.init();
            gamePlayManager.init();
            guiManager.init();
        }
    }

    public void initStates(){
        
    }
    protected void initConfigurations() {
    }

    public void loadData() {
    }

    @Override
    public void simpleUpdate(float tpf) {
        update(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void destroy() {
        finish();
        super.destroy();
    }

    public void quit() {
        this.stop();
    }
    //CYCLE --------------------------------------------------------

    public void init() {
        initConfigurations();
        initServices();
        initManagers();
    }

    public void load() {
        loadData();
        if (!customCycle) {
            //show loading screen
            //if there any loading
            materialManager.load();
            soundManager.load();
            worldManager.load();
            gamePlayManager.load();
            stageManager.load();
            guiManager.load();
        }
    }

    public void config(Configuration configuration) {
        if (!customCycle) {
            materialManager.config(null);
            soundManager.config(null);
            worldManager.config(null);
            gamePlayManager.config(null);
            stageManager.config(null);
            guiManager.config(null);
        }
    }

    public void update(float tpf) {
        if (!customCycle) {
            materialManager.update(tpf);
            soundManager.update(tpf);
            worldManager.update(tpf);
            stageManager.update(tpf);
            gamePlayManager.update(tpf);
            guiManager.update(tpf);
        }
    }

    public void finish() {
        if (!customCycle) {
            guiManager.finish();
            stageManager.finish();
            worldManager.finish();
            gamePlayManager.finish();
            soundManager.finish();
            materialManager.finish();
        }
    }
    //SETTER & GETTER --------------------------------------------------------

    public AppSettings getSettings() {
        return settings;
    }

    public MaterialManager getMaterialManager() {
        return materialManager;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public GUIManager getGUIManager() {
        return guiManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setCustomCycle(boolean customCycle) {
        this.customCycle = customCycle;
    }

    public AtomAssetManager getExAssetManager() {
        return exAssetManager;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }  
    
}
