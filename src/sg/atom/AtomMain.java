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
import sg.atom.net.NetworkManager;
import sg.atom.corex.managers.EffectManager;
import sg.atom.corex.managers.SoundManager;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.managers.WorldManager;
import sg.atom.cycle.SimpleCycle;

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
    protected DeviceInfo deviceInfo;
    protected boolean customCycle = false;
    protected boolean injected = false;
    protected ArrayList<IGameCycle> cycles = new ArrayList<IGameCycle>();

    @Inject
    protected EventBus eventBus = new EventBus("AtomMain");
    protected Stopwatch stopwatch = Stopwatch.createStarted();

    @Inject
    protected ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    protected ListeningScheduledExecutorService scheduler = MoreExecutors.listeningDecorator(new ScheduledThreadPoolExecutor(2));
    protected IGameCycle currentCycle;

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

    protected void registerManagers() {
        registerDefaultManagers();
    }

    protected void registerDefaultManagers() {
        materialManager = new MaterialManager(this);
        soundManager = new SoundManager(this);
        worldManager = new WorldManager(this);
        stageManager = new StageManager(this);
        guiManager = new GUIManager(this);
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
        if (currentCycle != null) {
            currentCycle.finish();
        }
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

    public SoundManager getSoundManager() {
        return soundManager;
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
