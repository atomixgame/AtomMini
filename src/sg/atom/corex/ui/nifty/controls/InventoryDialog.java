/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.ui.nifty.controls;

import com.jme3.input.controls.ActionListener;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.Draggable;
import de.lessvoid.nifty.controls.Droppable;
import de.lessvoid.nifty.controls.DroppableDropFilter;
import de.lessvoid.nifty.controls.DroppableDroppedEvent;
import de.lessvoid.nifty.controls.dragndrop.DroppableControl;
import de.lessvoid.nifty.controls.dragndrop.builder.DraggableBuilder;
import de.lessvoid.nifty.controls.dragndrop.builder.DroppableBuilder;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import sg.atom.gameplay.Inventory;
import sg.atom.gameplay.Item;

/**
 *
 * @author cuongnguyen
 */
public class InventoryDialog extends ElementBuilder implements DroppableDropFilter {

    private boolean inventoryOpen = true;
    private Element inventoryItemListPanel;
    private Inventory inventory;
    private Element inventoryUI;
    private Screen screen;
    private Nifty nifty;

    public InventoryDialog(Inventory inventory, Screen screen, Nifty nifty) {
        super(null);
        this.inventory = inventory;
        this.screen = screen;
        this.nifty = nifty;
    }

    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("inventory") && keyPressed) {
                if (!inventoryOpen) {
                    openInventory();
                } else {
                    closeInventory();
                }
            }

        }
    };

    class InventoryUINotify implements EndNotify {

        boolean isOpen;

        InventoryUINotify(boolean isOpen) {
            this.isOpen = isOpen;
        }

        public void perform() {
            inventoryOpen = isOpen;
            //System.out.println(" inventory Change" + inventoryOpen);
        }
    }
    int ITEM_WIDTH = 60;
    int ITEM_HEIGHT = 60;

    void createInventoryItem(final String slotId, final Item item, Element parentPanel) {
        new DroppableBuilder("itemDrop" + slotId) {
            {
                width(ITEM_WIDTH + "px");
                height(ITEM_HEIGHT + "px");
                onActiveEffect(new EffectBuilder("border") {
                    {
                        effectParameter("color", "#0003");
                    }
                });

                control(new DraggableBuilder("itemDrag" + slotId) {
                    {
                        childLayoutCenter();
                        image(new ImageBuilder("itemIcon" + slotId) {
                            {
                                width(ITEM_WIDTH + "px");
                                height(ITEM_HEIGHT + "px");

                                if (!item.getIcon().contains(";")) {
                                    filename(item.getIcon());
                                } else {
                                    String[] split = item.getIcon().split(";");
                                    filename(split[0]);
                                    imageMode(split[1]);
                                }
//                                
                                onHoverEffect(new HoverEffectBuilder("") {
                                    {

                                        effectParameter("name", "colorBar");
                                        effectParameter("color", "#00ffff44");
                                        effectParameter("post", "true");
                                    }
                                });
//
//                                onHoverEffect(new HoverEffectBuilder("customHint") {
//                                    {
//                                        effectParameter("startDelay", "100");
//                                        effectParameter("targetElement", "hintPanel");
//                                        effectParameter("hintText", item.getDescription());
//                                        effectParameter("longText", item.getDescription());
//                                    }
//                                });

                            }
                        });
                    }
                });
            }
        }.build(nifty, screen, parentPanel);
    }

    void createInventory(Inventory inventory) {
        this.inventory = inventory;
        final int cellHeight = 100 / inventory.getHeight();
        for (int by = 0; by < inventory.getHeight(); by++) {
            Element rowPanel = new PanelBuilder("Row" + by) {
                {
                    width("100%");
                    height(cellHeight + "%");
                    childLayoutHorizontal();
                }
            }.build(nifty, screen, inventoryItemListPanel);
            for (int bx = 0; bx < inventory.getWidth(); bx++) {
                if (inventory.isFilled(bx, by)) {
                    String slotId = bx + "_" + by;
                    createInventoryItem(slotId, inventory.getItemAt(bx, by), rowPanel);
                }
            }
        }

        // For i x j items
        for (int by = 0; by < inventory.getHeight(); by++) {
            for (int bx = 0; bx < inventory.getWidth(); bx++) {
                String slotId = bx + "_" + by;
                Element e = screen.findElementById("itemDrop" + slotId);

                if (e != null) {
                    DroppableControl droppableControl = e.getControl(DroppableControl.class);
                    droppableControl.addFilter(
                            this);
                }
            }
        }
    }

    public void toogleInventory() {
        if (inventoryOpen) {
            closeInventory();
        } else {
            openInventory();
        }
    }

    public void closeInventory() {
        inventoryUI.setVisibleToMouseEvents(false);
        inventoryUI.resetAllEffects();
        inventoryUI.startEffect(EffectEventId.onCustom, new InventoryUINotify(false), "showInventory");
        for (int by = 0; by < inventory.getHeight(); by++) {
            for (int bx = 0; bx < inventory.getWidth(); bx++) {
                String slotId = bx + "_" + by;
                Element e = screen.findElementById("itemIcon" + slotId);
                if (e != null) {
                    e.setVisibleToMouseEvents(false);
                }
            }
        }
    }

    public void openInventory() {
        inventoryUI.setVisibleToMouseEvents(true);
        inventoryUI.resetAllEffects();
        inventoryUI.startEffect(EffectEventId.onCustom, new InventoryUINotify(true), "hideInventory");
        for (int by = 0; by < inventory.getHeight(); by++) {
            for (int bx = 0; bx < inventory.getWidth(); bx++) {
                String slotId = bx + "_" + by;
                Element e = screen.findElementById("itemIcon" + slotId);
                if (e != null) {
                    e.setVisibleToMouseEvents(true);
                }
            }
        }
    }

    @NiftyEventSubscriber(id = "item-*")
    public void onDrop(final String id, final DroppableDroppedEvent event) {
//        if ("key".equals(event.getDraggable().getId())) {
//            event.getDraggable().getElement().markForRemoval();
//      chestOpenElement.startEffect(EffectEventId.onCustom, new EndNotify() {
//        @Override
//        public void perform() {
//          dragAndDropDescription.setText("Well Done!");
//        }
//      }, "switchOpen");
//        }
    }

    public boolean accept(Droppable dropSource, Draggable draggedItem, Droppable droppedAt) {
        if (isFree(droppedAt)) {
            return true;
        } else {
            return false;
        }
    }

    boolean isFree(Droppable dropSource) {
        if (dropSource.getElement().getChildren().size() > 1) {
            return false;
        } else {
            return true;
        }
    }
}
