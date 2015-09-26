/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import java.util.logging.Logger;

/**
 *
 * @author CuongNguyen
 */
public class SplashState extends BaseGameState {
    
    private static final Logger logger = Logger.getLogger(SplashState.class.getName());
    float thisTime = 0, lastTime = 0, stepTime = 2;
    
    public SplashState() {
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        setEnabled(true);
        
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        guiManager.goToScreen("SplashScreen");
        bindUI();
    }
    
    public void bindUI() {
        
        this.guiManager.getInputManager().setCursorVisible(true);
    }
    
    @Override
    public void update(float tpf) {
        super.update(tpf);
        
        //count down
        thisTime += tpf;
        
        if (thisTime - lastTime > stepTime) {
            
            lastTime = thisTime;
            
            toState(MainMenuState.class);
        }
    }
    
    public void toState(Class<? extends AbstractAppState> newState) {
        if (newState.isAssignableFrom(MainMenuState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new MainMenuState());
        } else {
            throw new IllegalStateException("Can not change to new state!" + newState.getName());
        }
    }
}
