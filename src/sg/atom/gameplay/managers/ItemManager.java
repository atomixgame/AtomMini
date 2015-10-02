package sg.atom.gameplay.managers;

import java.util.ArrayList;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.gameplay.Item;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ItemManager extends AbstractManager{
    
    private int gid = 0;
    private ArrayList<Item> items;
    
    public ItemManager(AtomMain app) {
        super(app);
    }

    public Item getAwardItem() {
        return getItem(gid - 1);
    }

    public void loadItems() {
        items = new ArrayList<Item>();
    }

    public Item getItem(int i) {
        return items.get(i);
    }

    public Item getItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void config(Configuration props) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
