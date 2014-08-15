/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui;

import com.google.common.collect.TreeTraverser;
import com.jme3.scene.Node;
import sg.atom.AtomMain;
import sg.atom.ui.tonegod.UITFightScreen;
import sg.atom.ui.tonegod.UITInGameScreen;
import sg.atom.ui.tonegod.UITMainMenuScreen;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 *
 * @author CuongNguyen
 */
public class ToneGodGUIManager extends GUIManager {

    private Screen currentScreen;
    Node screenNode;

    public ToneGodGUIManager(AtomMain app) {
        super(app);
    }

    @Override
    public void setupGUI() {
        super.setupGUI();
        createScreens();
    }

    public void createScreens() {
        commonScreens.put("MainMenuScreen", new UITMainMenuScreen(app));
        commonScreens.put("InGameScreen", new UITInGameScreen(app));
        commonScreens.put("FightScreen", new UITFightScreen(app));
    }

    @Override
    public void goToScreen(String screenName) {
        Screen nextScreen = (Screen) commonScreens.get(screenName);
        if (nextScreen != null) {
            if (nextScreen != currentScreen) {
                if (currentScreen != null) {
                    screenNode.removeControl(currentScreen);
                    screenNode.removeFromParent();
                }
                screenNode = new Node(screenName);
                guiNode.attachChild(screenNode);
                screenNode.addControl(nextScreen);
                this.currentScreen = nextScreen;
            }
        } else {
            throw new IllegalArgumentException("No screen existed :" + screenName);
        }
    }

    @Override
    public TreeTraverser<Element> getTreeTraverser() {
        return new TreeTraverser<Element>() {
            @Override
            public Iterable<Element> children(Element root) {
                return root.getElements();
            }
        };
    }

    private void clearScreen(Screen currentScreen) {
//        Iterator<Element> iterator = currentScreen.getElements().iterator();
//        while (iterator.hasNext()) {
//            iterator.remove();
//        }
//        currentScreen.
    }
}