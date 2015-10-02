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
import javax.inject.Inject;
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
import sg.atom.corex.assets.DelegateJSONLoader;
import sg.atom.corex.assets.DelegateXMLLoader;
import sg.atom.corex.assets.sprite.SpriteSheetLoader;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.managers.WorldManager;
import sg.atom.gameplay.managers.LevelManager;

/**
 * An optimized version of AtomMain which hard coded the Managers and the Cycle.
 */
public class AtomMain extends SimpleApplication implements IGameCycle {

    @Inject
    protected AtomAssetManager exAssetManager;
    @Inject
    protected StageManager stageManager;
    @Inject
    protected GUIManager guiManager;
    @Inject
    protected GamePlayManager gamePlayManager;
    @Inject
    protected WorldManager worldManager;
    @Inject
    protected SoundManager soundManager;
    @Inject
    protected ServiceManager serviceManager;
    @Inject
    protected MaterialManager materialManager;
    @Inject
    protected EffectManager effectManager;
    @Inject
    protected EntityManager entityManager;
    @Inject
    protected NetworkManager networkManager;
    @Inject
    protected GameStateManager gameStateManager;
    @Inject
    protected LevelManager levelManager;
    protected DeviceInfo deviceInfo;
    protected boolean customCycle = false;
    protected boolean injected = false;
    protected ArrayList<IGameCycle> cycles = new ArrayList<IGameCycle>();

    @Inject
    protected EventBus eventBus = new EventBus("AtomMain");
    protected Stopwatch stopwatch = Stopwatch.createStarted();

    @Inject
    protected ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    @Inject
    protected ListeningScheduledExecutorService scheduler = MoreExecutors.listeningDecorator(new ScheduledThreadPoolExecutor(2));
    @Inject
    protected IGameCycle currentCycle;

    protected class EmptyCycle implements IGameCycle {

        public void init() {
        }

        public void load() {
        }

        public void config(Configuration props) {
        }

        public void update(float tpf) {
        }

        public void finish() {
        }
    };

    protected class SimpleCycle implements IGameCycle {

        public void init() {
            initConfigurations();
            registerServices();
            initServices();
            registerManagers();
            initManagers();
            activesManagers();
            initStates();
        }

        public void initConfigurations() {

        }

        public void initManagers() {

            assetManager.registerLoader(SpriteSheetLoader.class, "sprites");
            assetManager.registerLoader(DelegateJSONLoader.class, "json");
            assetManager.registerLoader(DelegateXMLLoader.class, "xml");

            if (entityManager != null) {
                entityManager.init();
            }
            if (materialManager != null) {
                materialManager.init();
            }
            if (soundManager != null) {
                soundManager.init();
            }
            if (stageManager != null) {
                stageManager.init();
            }
            if (worldManager != null) {
                worldManager.init();
            }
            if (gamePlayManager != null) {
                gamePlayManager.init();
            }
            if (guiManager != null) {
                guiManager.init();
            }
            if (effectManager != null) {
                effectManager.init();
            }

        }

        public void activesManagers() {

            if (entityManager != null) {
                stateManager.attach(entityManager);
            }
            if (materialManager != null) {
                stateManager.attach(materialManager);
            }
            if (soundManager != null) {
                stateManager.attach(soundManager);
            }
            if (stageManager != null) {
                stateManager.attach(stageManager);
            }
            if (worldManager != null) {
                stateManager.attach(worldManager);
            }
            if (gamePlayManager != null) {
                stateManager.attach(gamePlayManager);
            }
            if (guiManager != null) {
                stateManager.attach(guiManager);
            }
            if (effectManager != null) {
                stateManager.attach(effectManager);
            }
            getEventBus().register(worldManager);
        }

        public void load() {
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

        public void config(Configuration configuration) {
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

        public void update(float tpf) {
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

        public void finish() {
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
    };

    @Override
    public void simpleInitApp() {
        deviceInfo = new DeviceInfo(this);
        currentCycle = new SimpleCycle();
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

    protected void registerManagers(){
        registerDefaultManagers();
    }
    
    protected void registerDefaultManagers() {
        materialManager = new MaterialManager(this);
        soundManager = new SoundManager(this);
        levelManager = new LevelManager(this);
        worldManager = new WorldManager(this);
        stageManager = new StageManager(this);
        guiManager = new GUIManager(this);
        gamePlayManager = new GamePlayManager(this);
    }

    public void init() {
        currentCycle.init();
    }

    public void load() {
        currentCycle.load();
    }

    public void config(Configuration props) {
        currentCycle.config(props);
    }

    public void update(float tpf) {
        currentCycle.update(tpf);
    }

    public void initStates() {
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
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

    public void finish() {
        currentCycle.finish();
    }

    public void quit() {
        this.stop();
    }
    //CYCLE --------------------------------------------------------

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

    public LevelManager getLevelManager() {
        return levelManager;
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
