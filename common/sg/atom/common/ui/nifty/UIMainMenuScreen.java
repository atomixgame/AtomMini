/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.common.ui.nifty;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import sg.atom.common.state.CreditState;
import sg.atom.common.state.InGameState;
import sg.atom.common.state.MainMenuState;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.ui.NiftyGUIManager;

/**
 *
 * @author CuongNguyen
 */
public class UIMainMenuScreen implements ScreenController {

    private Screen screen;
    NiftyGUIManager guiManager;
    private MainMenuState mainMenuState;

    public UIMainMenuScreen(GUIManager guiManager) {
        this.guiManager = (NiftyGUIManager) guiManager;
    }

    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
        this.guiManager.getInputManager().setCursorVisible(true);
        mainMenuState = guiManager.getApp().getStateManager().getState(MainMenuState.class);
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }
//Interactions -----------------------------------------------------------------
    public void goInGame() {
        mainMenuState.toState(InGameState.class);

    }

    public void joinGame() {
        goInGame();
    }

    public void back() {
        guiManager.goToScreen("MainMenuScreen");
    }

    public void goCredit() {
        mainMenuState.toState(CreditState.class);
    }

    public void goSocial() {
        guiManager.goToScreen("LeaderBoardScreen");
    }

    public void goOptions() {
        guiManager.goToScreen("OptionsScreen");
    }

    public void goShop() {
        guiManager.goToScreen("ShopScreen");
    }

    public void quitGame() {
        guiManager.getApp().quit();
    }
}
