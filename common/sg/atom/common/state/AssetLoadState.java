package sg.atom.common.state;

import com.google.common.util.concurrent.ListenableFuture;
import sg.atom.common.ui.nifty.UILoadingScreenController;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Logger;
import sg.atom.AtomMain;
import sg.atom.state.BaseGameState;

/**
 * AssetLoadState take request to load some asset or do some workload and handle
 * the progress, results and time taken.
 *
 * Accept request as:
 * <li> Runnable, Callable, Future hand them to TaskManager
 *
 * <li> Preloader hand them to ExAssetManager
 *
 * <li> Injections
 *
 * @author cuong.nguyenmanh2
 */
public class AssetLoadState extends BaseGameState {

    public static final Logger logger = Logger.getLogger(AssetLoadState.class.getName());
    protected ListenableFuture<Boolean> loadGUIAsset, loadDataTask, loadAssetTask, loadStageTask, configTask, socialTask;
    // GUI
    protected UILoadingScreenController guiController;
    protected Screen loadingScreen;
    protected float passedTime;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled) {
            initPhase();
        } else {
//            if (loadComplete) {
//                nextState();
//            } else {
//                throw new RuntimeException("Loading is not complete!");
//            }
        }
    }

    protected void initPhase() {
        guiManager.goToScreen("loadingScreen");
//        loadingScreen = guiManager.getNifty().getScreen("loadingScreen");
//        if (loadingScreen == null) {
//            throw new RuntimeException("May be: You didn't add the LoadingScreen in XML yet!");
//        } else {
//            guiController = (UILoadingScreenController) loadingScreen.getScreenController();
//        }
    }

    public void bindUI(ScreenController guiController) {
        this.guiController = (UILoadingScreenController) guiController;
    }

    protected void submitLoadTasks() {
//        loadGUIAsset = executor.submit(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                //guiManager.loadNiftyScreens();
//                return true;
//            }
//        });
//
//        loadStageTask = executor.submit(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
////                stageManager.loadLevels();
//                return true;
//            }
//        });
//
//        //AsyncFunction
//        ListenableFuture<List<Boolean>> finishedResults = Futures.successfulAsList(loadDataTask, loadGUIAsset, loadAssetTask, loadStageTask, socialTask, configTask);
//        Futures.addCallback(finishedResults, new FutureCallback<List<Boolean>>() {
//            public void onFailure(Throwable t) {
//                retry();
//            }
//
//            public void onSuccess(List<Boolean> result) {
//                nextState();
//            }
//        });
        //ListenableFuture<Boolean> queryFuture = Futures.transform(rowKeyFuture, configTask, executor);
    }

    //    protected void loadPhase() {
    //        stageManager.loadStage();
    //        System.out.println("Finish Loading !");
    //        stageManager.configStage();
    //    }
    //    }
    @Override
    public void update(float tpf) {
        super.update(tpf);

        onWatch(passedTime);
    }

    protected void onRetry() {
    }

    protected void onError() {
    }

    protected void onFinished() {
    }

    protected void onWatch(float passedTime) {
        // Wait for the GUI controller to finish screen changing
        if (guiController != null && loadingScreen.isBound()) {
//            if (stageManager.getProgressInfo().getCurrentProgressName() != null) {
//                float currentProcess = stageManager.getProgressInfo().getCurrentProgressPercent();
//                if (oldPercent != currentProcess) {
//                    guiController.setProgress(currentProcess, stageManager.getProgressInfo().getCurrentProgressName());
//                    oldPercent = currentProcess;
//                }
//                //System.out.println(currentProcess);
//            }
        }
    }

    protected void retry() {
    }

    protected void finish() {
    }

    protected void nextState() {
//        gameStateManager.goInGame();
    }
//    public void updateProgressBar(boolean hasError, String errorMsg) {
//        // Wait for the GUI controller to finish screen changing
//        if (guiController != null && gameGUIManager.getNifty().getCurrentScreen().getScreenId().equals("loadingScreen")) {
//            if (hasError) {
//                //
//                guiController.setProgress(0, "Error ! Press Esc to back to main menu :" + errorMsg);
//            } else {
//                if (stageManager.getProgressInfo().getCurrentProgressName() != null) {
//                    float currentProcess = stageManager.getProgressInfo().getCurrentProgressPercent();
//                    if (oldPercent != currentProcess) {
//                        guiController.setProgress(currentProcess, stageManager.getProgressInfo().getCurrentProgressName());
//                        oldPercent = currentProcess;
//                        
//                        System.out.println("Load :" + oldPercent);
//                    }
//                }
//            }
//        }
//    }
}
