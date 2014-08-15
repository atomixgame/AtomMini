/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.nifty;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import sg.atom.ui.GUIManager;

/**
 *
 * @author CuongNguyen
 */
public class UICharacterSelectScreen implements ScreenController {

    private Screen screen;
    GUIManager guiManager;

    public UICharacterSelectScreen(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
    }

    public void onStartScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onEndScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
