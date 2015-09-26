/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.common.ui.nifty;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.List;
import sg.atom.gameplay.info.PlayerInfo;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.ui.NiftyGUIManager;

/**
 *
 * @author CuongNguyen
 */
public class UILeaderBoardScreen implements ScreenController {

    private Screen screen;
    NiftyGUIManager guiManager;
    public static final String INFO_LIST_ID = "playerInfoList";

    public UILeaderBoardScreen() {
    }

    public UILeaderBoardScreen(GUIManager guiManager) {
        this.guiManager = (NiftyGUIManager) guiManager;
    }

    /**
     * Fill the listbox with items. In this case with Strings.
     */
    public void createPlayerInfoList() {
        ListBox listBox = screen.findNiftyControl(INFO_LIST_ID, ListBox.class);
        listBox.clear();
        
        //
        listBox.addItem(new PlayerInfo(1, "Atomix", "2425.png", 800));
        listBox.addItem(new PlayerInfo(2, "Kelly", "2426.png", 400));
        listBox.addItem(new PlayerInfo(3, "Hato", "2427.png", 200));
    }

    /**
     * When the selection of the ListBox changes this method is called.
     */
    @NiftyEventSubscriber(id = INFO_LIST_ID)
    public void onListBoxSelectionChanged(final String id, final ListBoxSelectionChangedEvent<String> event) {
        List<String> selection = event.getSelection();
        for (String selectedItem : selection) {
            //System.out.println("listbox selection [" + selectedItem + "]");
        }
    }

    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;

    }

    public void onStartScreen() {
        createPlayerInfoList();
    }

    public void onEndScreen() {
    }

    public void back() {
        guiManager.goToScreen("MainMenuScreen");
    }
}
