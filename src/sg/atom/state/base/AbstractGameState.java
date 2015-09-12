/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.state.base;

import com.jme3.app.state.AbstractAppState;
import sg.atom.corex.managers.GUIManager;

/**
 *
 * @author CuongNguyen
 */
public abstract class AbstractGameState extends AbstractAppState {

    int id;
    int type;
    int[] dependencies;
    boolean uiBound;
    int transistionType;

    public void bindUI(GUIManager guiManager, Object ui) {
    }

    public void unbindUI(GUIManager guiManager, Object ui) {
    }

    public void toState(Class<? extends AbstractAppState> newState) {
    }
}
