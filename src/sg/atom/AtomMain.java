package sg.atom;

import com.google.common.base.Stopwatch;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ServiceManager;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.device.DeviceInfo;
import sg.atom.corex.entity.EntityManager;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.corex.managers.MaterialManager;
import sg.atom.corex.asset.AtomAssetManager;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.net.NetworkManager;
import sg.atom.corex.managers.EffectManager;
import sg.atom.corex.managers.SoundManager;
import sg.atom.corex.managers.StageManager;
import sg.atom.common.state.GameStateManager;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.managers.WorldManager;

/**
 * An optimized version of AtomMain which hard coded the Managers and the Cycle.
 */
public class AtomMain extends SimpleApplication implements IGameCycle {

    
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
    protected GameStateManager gameStateManager;
    protected DeviceInfo deviceInfo;
    protected boolean customCycle = false;
    protected boolean injected = false;
    protected ArrayList<IGameCycle> cycles = new ArrayList<IGameCycle>();
    protected EventBus eventBus = new EventBus("AtomMain");
    protected Stopwatch stopwatch = Stopwatch.createStarted();
    protected ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    protected ListeningScheduledExecutorService scheduler = MoreExecutors.listeningDecorator(new ScheduledThreadPoolExecutor(2));

    @Override
    public void simpleInitApp() {
        deviceInfo = new DeviceInfo(this);
        init();
        load();
        config(null);
    }

    protected void registerServices() {
    }

    protected void initServices() {
        //network service
        //social service
        //game leaderboard service
        //content update service
    }

    protected void registerManagers() {
        if (!injected) {
            this.materialManager = new MaterialManager(this);
            this.soundManager = new SoundManager(this);
            this.worldManager = new WorldManager(this);
            this.stageManager = new StageManager(this);
            this.guiManager = new GUIManager(this);
            this.gamePlayManager = new GamePlayManager(this);
        }
    }

    protected void initManagers() {
        if (!customCycle) {
            if (this.materialManager != null) {
                this.materialManager.init();
            }
            if (this.soundManager != null) {
                this.soundManager.init();
            }
            if (this.stageManager != null) {
                this.stageManager.init();
            }
            if (this.worldManager != null) {
                this.worldManager.init();
            }
            if (this.gamePlayManager != null) {
                this.gamePlayManager.init();
            }
            if (this.guiManager != null) {
                this.guiManager.init();
            }
        }
    }

    public void initStates() {
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
        registerServices();
        initServices();
    }

    public void load() {
        loadData();
        if (!customCycle) {
            //show loading screen
            //if there any loading
            if (materialManager != null) {
                materialManager.load();
            }
            if (materialManager != null) {
                soundManager.load();
            }
            if (worldManager != null) {
                worldManager.load();
            }
            if (gamePlayManager != null) {
                gamePlayManager.load();
            }
            if (stageManager != null) {
                stageManager.load();
            }
            if (guiManager != null) {
                guiManager.load();
            }
        }
    }

    public void config(Configuration configuration) {
        if (!customCycle) {
            if (materialManager != null) {
                materialManager.config(null);
            }
            if (soundManager != null) {
                soundManager.config(null);
            }
            if (worldManager != null) {
                worldManager.config(null);
            }
            if (gamePlayManager != null) {
                gamePlayManager.config(null);
            }
            if (stageManager != null) {
                stageManager.config(null);
            }
            if (guiManager != null) {
                guiManager.config(null);
            }
        }
    }

    public void update(float tpf) {
        if (!customCycle) {
            if (materialManager != null) {
                materialManager.update(tpf);
            }
            if (soundManager != null) {
                soundManager.update(tpf);
            }
            if (worldManager != null) {
                worldManager.update(tpf);
            }
            if (stageManager != null) {
                stageManager.update(tpf);
            }
            if (gamePlayManager != null) {
                gamePlayManager.update(tpf);
            }
            if (guiManager != null) {
                guiManager.update(tpf);
            }
        }
    }

    public void finish() {
        if (!customCycle) {
            if (guiManager != null) {
                guiManager.finish();
            }
            if (stageManager != null) {
                stageManager.finish();
            }
            if (worldManager != null) {
                worldManager.finish();
            }
            if (gamePlayManager != null) {
                gamePlayManager.finish();
            }
            if (soundManager != null) {
                soundManager.finish();
            }
            if (materialManager != null) {
                materialManager.finish();
            }
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

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public ListeningExecutorService getExecutorService() {
        return executorService;
    }

    public ListeningScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public Stopwatch getStopwatch() {
        return stopwatch;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
