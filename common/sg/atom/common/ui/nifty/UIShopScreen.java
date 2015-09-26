/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.common.ui.nifty;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.Draggable;
import de.lessvoid.nifty.controls.Droppable;
import de.lessvoid.nifty.controls.DroppableDropFilter;
import de.lessvoid.nifty.controls.DroppableDroppedEvent;
import de.lessvoid.nifty.controls.dragndrop.DroppableControl;
import de.lessvoid.nifty.controls.dragndrop.builder.DraggableBuilder;
import de.lessvoid.nifty.controls.dragndrop.builder.DroppableBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import sg.atom.gameplay.Item;
import sg.atom.gameplay.Shop;
import sg.atom.common.state.ShopState;
import sg.atom.corex.managers.GUIManager;

/**
 *
 * @author CuongNguyen
 */
public class UIShopScreen implements ScreenController, DroppableDropFilter {

    private Screen screen;
    private NiftyGUIManager guiManager;
    private Nifty nifty;
    private ShopState shopState;
    private Element itemListPanel;
    private Shop shop;
    int ITEM_WIDTH = 60;
    int ITEM_HEIGHT = 60;
    int SHOP_WIDTH = 7;
    int SHOP_HEIGHT = 7;

    public UIShopScreen(GUIManager guiManager) {
        this.guiManager = (NiftyGUIManager) guiManager;
    }

    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
        this.nifty = nifty;
        this.shop = Shop.getInstance();
        itemListPanel = nifty.getCurrentScreen().findElementById("itemList");

    }

    public void onStartScreen() {
        createList(shop);
    }

    public void onEndScreen() {
    }

    public void back() {
        guiManager.goToScreen("MainMenuScreen");
    }

    void createGridItem(final String slotId, final Item item, Element parentPanel) {
        new PanelBuilder("itemPanel" + slotId) {
            {
                width(ITEM_WIDTH + "px");

                childLayoutVertical();
                control(new DroppableBuilder("itemDrop" + slotId) {
                    {
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
                });

                text(new TextBuilder("itemPrice" + slotId) {
                    {
                        style("defaultFont");
                        height("40px");
                        text("100$");
                        color("#ff0f");
                    }
                });
            }
        }.build(nifty, screen, parentPanel);
    }

    void createList(Shop shop) {
        this.shop = shop;
        final int cellHeight = 100 / SHOP_HEIGHT;
        for (int by = 0; by < SHOP_HEIGHT; by++) {
            Element rowPanel = new PanelBuilder("Row" + by) {
                {
                    width("100%");
                    height(cellHeight + "%");
                    childLayoutHorizontal();
                }
            }.build(nifty, screen, itemListPanel);
            for (int bx = 0; bx < SHOP_WIDTH; bx++) {
                int num = by * SHOP_WIDTH + bx;

                if (num < shop.getItems().size()) {
                    String slotId = bx + "_" + by;
                    createGridItem(slotId, shop.getItemAt(num), rowPanel);
                } else {
                    break;
                }
            }
        }

        // For i x j items
        for (int by = 0; by < SHOP_HEIGHT; by++) {
            for (int bx = 0; bx < SHOP_WIDTH; bx++) {
                String slotId = bx + "_" + by;
                Element e = screen.findElementById("itemDrop" + slotId);
                if (e != null) {
                    DroppableControl droppableControl = e.getControl(DroppableControl.class);
                    droppableControl.addFilter(this);
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
