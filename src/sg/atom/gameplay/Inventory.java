/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import sg.atom.gameplay.managers.ItemManager;
import java.util.ArrayList;
import java.util.List;

/**
 * A Collection and a Map2D of id (int) which offer slot and record the change over it. FIXME: Replace with Table?
 * 
 * <p>If linked with an ItemManager, can give the list of the real "Item" in it.
 *
 * @author cuong.nguyenmanh2
 */
public class Inventory {

    public static final int NO_ITEM = -1;
    protected ArrayList<Integer> itemIdList = new ArrayList<Integer>();
    protected int width = 10;
    protected int height = 1;
    protected int[][] itemIdMap;
    protected int[][] slotCategoryMap;
    protected Player owner;
    protected ItemManager itemManager;

    public Inventory() {
        itemIdMap = new int[width][height];
        clearInventory();
    }

    public Inventory(int width, int height) {
        this.width = width;
        this.height = height;
        itemIdMap = new int[this.width][this.height];
        clearInventory();
    }

    public void clearInventory() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                itemIdMap[x][y] = NO_ITEM;
            }
        }
    }

    public boolean checkItemRange(int x, int y, Item item) {
        return false;
    }

    public void placeItem(int x, int y, Item item) {
    }

    public void removeItemById(int id) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                if (itemIdMap[x][y] == id) {
                    removeItem(x, y);
                }
            }
        }
    }

    public Item removeItem(int x, int y) {
        Item removedItem = getItemAt(x, y);
        setItemAt(x, y, NO_ITEM);
        return removedItem;
    }

    public Item removeItem(int xy) {
        Item removedItem = getItemAt(xy);
        setItemAt(xy, NO_ITEM);
        return removedItem;
    }

    private boolean isFilled(int xy) {
        int x = xy % height;
        int y = xy / height;

        return itemIdMap[x][y] != NO_ITEM;

    }

    public boolean isFilled(int x, int y) {
        return itemIdMap[x][y] != NO_ITEM;
    }

    public boolean isCompletelyFilled() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                if (itemIdMap[x][y] != NO_ITEM) {
                    return false;
                }
            }
        }
        return true;
    }

    //    public void addToInventory(Entity entity) {
    //
    //
    //        if (isCompletelyFilled()) {
    //            return;
    //        }
    //
    //
    //    }
    public void setItemAt(int x, int y, int id) {
        itemIdMap[x][y] = id;
    }

    public void setItemAt(int x, int y, Item item) {
        setItemAt(x, y, item.id);
    }

    public void setItemAt(int xy, int id) {
        int x = xy % height;
        int y = xy / height;

        setItemAt(x, y, id);
    }

    public void setItemAt(int xy, Item item) {
        setItemAt(xy, item.id);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getOwner() {
        return owner;
    }

    public List<Integer> getItemIdList() {
        return itemIdList;
    }

    public Item getItemAt(int x, int y) {
        return ItemManager.getInstance().getItem(itemIdMap[x][y]);
    }

    public Item getItemAt(int xy) {
        int x = xy % height;
        int y = xy / height;
        return getItemAt(x, y);
    }

    public void addAll(ArrayList<Item> items) {
        int xy = 0;
        int total = width * height;
        for (Item item : items) {
            xy++;
            if (!isFilled(xy) && xy < total) {
                setItemAt(xy, item);
            }
            if (isCompletelyFilled()) {
                throw new RuntimeException("Inventory full!");
            }
        }
    }

    public void add(Item item) {
        int xy = 0;
        if (isCompletelyFilled()) {
            throw new RuntimeException("Inventory full!");
        }
        int total = width * height;
        while (isFilled(xy) && xy < total) {
            xy++;
        }
        if (xy < total) {
            setItemAt(xy, item);
        } else {
            throw new RuntimeException("Inventory full!");
        }
    }
    
    public void swap(Item item1, Item item2){
        
    }
}
