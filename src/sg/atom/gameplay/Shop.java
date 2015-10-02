/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import sg.atom.gameplay.managers.ItemManager;
import java.util.ArrayList;
import java.util.List;
import sg.atom.state.base.BaseGameState;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Shop extends BaseGameState {

    ArrayList<Item> items;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        items = new ArrayList<Item>();
        loadItems();
    }

    public void loadItems() {
        items = getApp().getStateManager().getState(ItemManager.class).getItems();
    }

    public List<Item> getItems() {
        return items;
    }

    public void updateItems() {
    }

    public void buy(Player player, Item item) {
        //player.
    }

    public Item getItemAt(int xy) {
        return items.get(xy);
    }
}
