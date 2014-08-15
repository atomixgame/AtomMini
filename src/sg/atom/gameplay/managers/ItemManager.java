/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.managers;

import com.jme3.asset.AssetManager;
import java.util.ArrayList;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.gameplay.Item;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ItemManager  implements IGameCycle{

    /**
     * Singleton reference of ItemManager.
     */
    private static ItemManager defaultInstance;
    private AssetManager assetManager;
    private ArrayList<Item> items;
    private int gid = 0;
    public static String IMAGE_DIR = "Interface/Images/Icons/Items/";

    /**
     * Constructs singleton instance of ItemManager.
     */
    private ItemManager() {
        loadItems();
    }

    /**
     * Provides reference to singleton object of ItemManager.
     *
     * @return Singleton instance of ItemManager.
     */
    public static synchronized final ItemManager getInstance() {
        if (defaultInstance == null) {
            defaultInstance = new ItemManager();
        }
        return defaultInstance;
    }

    public Item getAwardItem() {
        return getItem(gid - 1);
    }

    public void loadItems() {
        items = new ArrayList<Item>();
//        assetManager = CuteHeroesMain.getInstance().getAssetManager();
//        assetManager.registerLoader(TextLineLoader.class, "txt");
//        ArrayList<String> lines = (ArrayList<String>) assetManager.loadAsset("Data/Items/Sword.txt");
//        
//        for (String line:lines){
//            String[] elements = line.split(",");
//            Item item = new Item(gid++, elements[0]);
//            item.cost = elements[1];
//            items.add(item);
//            
//        }
        //FIXME: For testing purpose
        items.add(new Item(gid++, "Sword-001", 100, "Sword ", IMAGE_DIR + "sword/Sword-001.png"));
        items.add(new Item(gid++, "Sword-002", 200, "Sword ", IMAGE_DIR + "sword/Sword-002.png"));
        items.add(new Item(gid++, "Sword-003", 300, "Sword ", IMAGE_DIR + "sword/Sword-003.png"));
        items.add(new Item(gid++, "Sword-004", 100, "Sword ", IMAGE_DIR + "sword/Sword-001.png"));
        items.add(new Item(gid++, "Sword-005", 100, "Sword ", IMAGE_DIR + "sword/Sword-001.png"));
        items.add(new Item(gid++, "Sword-006", 100, "Sword ", IMAGE_DIR + "sword/Sword-001.png"));
        items.add(new Item(gid++, "Sword-007", 100, "Sword ", IMAGE_DIR + "sword/Sword-001.png"));
        items.add(new Item(gid++, "Sword-008", 100, "Sword ", IMAGE_DIR + "sword/Sword-001.png"));
        items.add(new Item(gid++, "Sword-009", 100, "Sword ", IMAGE_DIR + "sword/Sword-001.png"));
        items.add(new Item(gid++, "Sword-010", 100, "Sword ", IMAGE_DIR + "sword/Sword-001.png"));
        items.add(new Item(gid++, "Sword-011", 100, "Sword ", IMAGE_DIR + "sword/Sword-001.png"));
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
