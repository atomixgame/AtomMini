/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import sg.atom.gameplay.managers.ItemManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Shop {

    ArrayList<Item> items;
    /**
     * Singleton reference of Shop.
     */
    private static Shop defaultInstance;

    /**
     * Constructs singleton instance of Shop.
     */
    private Shop() {
        items = new ArrayList<Item>();
        loadItems();
    }

    /**
     * Provides reference to singleton object of Shop.
     *
     * @return Singleton instance of Shop.
     */
    public static synchronized final Shop getInstance() {
        if (defaultInstance == null) {
            defaultInstance = new Shop();
        }
        return defaultInstance;
    }

    public void loadItems() {
        items = ItemManager.getInstance().getItems();
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
