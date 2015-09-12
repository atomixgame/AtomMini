/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.ui.tonegod;

import com.jme3.app.Application;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import sg.atom.AtomMain;
import sg.atom.state.FightState;
import sg.atom.state.InGameState;
import sg.atom.corex.managers.GUIManager;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Screen;

/**
 *
 * @author CuongNguyen
 */
public class UITFightScreen extends Screen {

    private GUIManager guiManager;
    private FightState fightState;

    public UITFightScreen(Application app) {
        super(app);
        this.guiManager = ((AtomMain) app).getGUIManager();
        bind();
        createElements();

    }

    private void createElements() {
        // create button and add to window
        ButtonAdapter newButton = new ButtonAdapter(this, "btn1", new Vector2f(15, 55)) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                fightState.toState(InGameState.class);
            }
        };
        newButton.setText("SingleGame");
        this.addElement(newButton);
    }

    protected void bind() {
        fightState = guiManager.getApp().getStateManager().getState(FightState.class);
    }
}