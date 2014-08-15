package sg.atom.ui.nifty;

import com.google.common.base.Function;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
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
import de.lessvoid.nifty.screen.ScreenController;
import java.util.HashMap;
import sg.atom.gameplay.GameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.gameplay.Inventory;
import sg.atom.gameplay.Item;
import sg.atom.gameplay.Skill;
import sg.atom.state.InGameState;
import sg.atom.ui.GUIManager;
import sg.atom.ui.NiftyGUIManager;
import sg.atom.ui.hud.HealthBarNode;

/**
 *
 * @author CuongNguyen
 */
public class UIInGameScreen implements ScreenController, DroppableDropFilter {

    
    NiftyGUIManager guiManager;
    private InGameState inGameState;
    
    //Nifty elements
    private Screen screen;
    private Nifty nifty;
    //Elements
    private Element alertText;
    private Element healthBarValue;
    private Element manaBarValue;
    private Element healthPoint;
    private Element manaPoint;
    private Element inventoryUI;
    private Element dialogueText1;
    private Element dialogueText2;
    private Element dialoguePanel1;
    private Element dialoguePanel2;
    
    private Inventory inventory;
    private boolean inventoryOpen = true;
//    float lastTime = 0;
//    float timeLimit = 0.001f;
//    boolean lastTimePressed = false;
    private Element inventoryItemListPanel;
    private HashMap<GameCharacter, HealthBarNode> healthBars;
    private Node healthBarNode;
    private Element skillListPanel;
    private HashMap<String, Skill> skillMap;
    private Element effectLayer;

    public UIInGameScreen(GUIManager guiManager) {
        this.guiManager = (NiftyGUIManager) guiManager;
    }

    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
        this.nifty = nifty;
        inGameState = guiManager.getApp().getStateManager().getState(InGameState.class);

        if (screen.getScreenId().equals("InGameScreen")) {
            alertText = screen.findElementByName("alertSmall");
            healthBarValue = screen.findElementByName("healthBarValue");
            manaBarValue = screen.findElementByName("manaBarValue");
            healthPoint = screen.findElementByName("healthPoint");
            manaPoint = screen.findElementByName("manaPoint");

//            inventoryUI = screen.findElementByName("inventory");
//            inventoryItemListPanel = screen.findElementByName("inventoryItemList");
//
//            GameCharacter playerMainCharacter = guiManager.getApp().getStageManager().getGamePlayManager().getMainPlayer().getMainCharacter();
//            createInventory(playerMainCharacter.getInventory());
//            closeInventory();

            skillListPanel = screen.findElementByName("quickActionPanel");
            //createSkills(playerMainCharacter);
//            CustomHint.createHintLayer(nifty, screen);

        } else if (screen.getScreenId().equals("DialogueScreen")) {
            dialogueText1 = screen.findElementByName("dialogueText1");
            dialogueText2 = screen.findElementByName("dialogueText2");
            dialoguePanel1 = screen.findElementByName("dialoguePanel1");
            dialoguePanel2 = screen.findElementByName("dialoguePanel2");
        }
    }

    public void onStartScreen() {
        inGameState.bindUI(guiManager,this);
        initKey(guiManager.getInputManager());
    }

    public void onEndScreen() {
        inGameState.unbindUI(guiManager,this);
        healthBarNode.detachAllChildren();
        clearKey(guiManager.getInputManager());
    }

    // Infos --------------------------------------------------------------
    public void setAlert(String text) {
        guiManager.setText(alertText, text);
    }

    public void setHealth(int value, int total) {
        //healthBarValue.setConstraintWidth(new SizeValue(value / total + "%"));
        int pw = healthBarValue.getParent().getWidth();
        healthBarValue.setWidth(value * pw / total);
        healthBarValue.getParent().resetLayout();
        guiManager.setText(healthPoint, value + "/" + total);
    }

    public void setMana(int value, int total) {
        //manaBarValue.setConstraintWidth(new SizeValue(value / total + "%"));
        int pw = manaBarValue.getParent().getWidth();
        manaBarValue.setWidth(value * pw / total);
        manaBarValue.getParent().resetLayout();
        guiManager.setText(manaPoint, value + "/" + total);
    }

    public void createCharacterInfos(GamePlayManager gamePlayManager) {
        //guiManager.getGuiNode().detachAllChildren();
        if (healthBarNode == null) {
            healthBarNode = new Node("characterHealthBars");
        } else {
            healthBarNode.detachAllChildren();
        }
        guiManager.getApp().getGuiNode().attachChild(healthBarNode);
        healthBars = new HashMap<GameCharacter, HealthBarNode>();
        for (GameCharacter character : gamePlayManager.getCharacters()) {
            createCharacterInfoExtra(character);
        }
    }

    public void setCharacterInfo(String name, String type, int levels) {
    }

    public void createCharacterInfoExtra(GameCharacter character) {
        HealthBarNode uiHealthBar = new HealthBarNode(character, guiManager);
        healthBars.put(character, uiHealthBar);
        healthBarNode.attachChild(uiHealthBar);
//        System.out.println("Create");
    }

    public void setCharacterInfoExtra(GameCharacter character) {
        HealthBarNode healthBar = healthBars.get(character);
        if (healthBar != null) {
            healthBar.updateBar(guiManager.getApp().getStageManager().getCurrentActiveCamera());
        } else {
        }
    }

    // Navigation --------------------------------------------------------------
    public void goOptions() {
    }

    public void goShop() {
        guiManager.goToScreen("ShopScreen");
    }

    public void goPlayerInfo() {
        guiManager.toggleNifty();
    }

    public void goMainMenu() {
        guiManager.goToScreen("MainMenuScreen");
    }
    // Input --------------------------------------------------------------

    public void initKey(InputManager inputManager) {
        inputManager.addMapping("inventory", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addListener(actionListener, "inventory");
    }

    public void clearKey(InputManager inputManager) {
        inputManager.deleteMapping("inventory");
        inputManager.removeListener(actionListener);
    }
//
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
    // Skills-------------------------------------------------------------------

    void createSkillItem(final String slotId, final Skill item, Element parentPanel) {
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
                                interactOnClick("onSkillAction(" + slotId + ")");

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

    void createSkills(GameCharacter character) {
        this.skillMap = new HashMap<String, Skill>();

        int num = character.getSkills().size();
        final int cellWidth = 100 / num;
        for (int by = 0; by < num; by++) {
            Element rowPanel = new PanelBuilder("skill" + by) {
                {
                    height("100%");
                    width(cellWidth + "%");
                    childLayoutHorizontal();
                }
            }.build(nifty, screen, skillListPanel);

            String slotId = "" + by;
            Skill skill = character.getSkills().get(by);
            this.skillMap.put(slotId, skill);
            createSkillItem(slotId, skill, rowPanel);

        }

        // For num skills
        for (int by = 0; by < num; by++) {
            String slotId = "" + by;
            Element e = screen.findElementByName("skill" + slotId);
            if (e != null) {
                DroppableControl droppableControl = e.getControl(DroppableControl.class);
                droppableControl.addFilter(this);
            }
        }

        effectLayer = new LayerBuilder("") {
            {
                childLayoutAbsolute();
            }
        }.build(nifty, screen, screen.getRootElement());
    }

    public void onSkillAction(String name) {
        GameCharacter playerMainCharacter = guiManager.getApp().getStageManager().getGamePlayManager().getMainPlayer().getMainCharacter();
        Skill skill = skillMap.get(name);

        if (skill != null) {
            playerMainCharacter.useSkill(skill, null);
            System.out.println(" " + skill.getName());
            showSkillDamage(1000, playerMainCharacter.getLocationOnScreen());
        }



    }

    public void updateSkills() {
    }

    public void showSkillDamage(final int damage, final Vector2f pos) {
        Element skillDamageText = new TextBuilder() {
            {
                String valueTxt = Integer.toString(damage);
                x(((int) pos.x - valueTxt.length() * 16) + "px");
                y(((int) pos.y) + "px");
                text(valueTxt);
                textHAlignCenter();

                //style("defaultFont");
                style("bigFont2");
//                onActiveEffect(new EffectBuilder("fontSize") {
//                    {
//                        effectParameter("color", "#0003");
//                        
//                    }
//                });
            }
        }.build(nifty, screen, effectLayer);
        effectLayer.layoutElements();

        guiManager.getApp().getStageManager().queueAction(skillDamageText, new Function<Element, Element>() {
            public Element apply(Element input) {
                input.markForRemoval();
                return null;
            }
        }, 2);
    }

    class SkillDamageEffectNotify implements EndNotify {

        private final Element element;

        SkillDamageEffectNotify(Element element) {
            this.element = element;
        }

        public void perform() {
            element.markForRemoval();
        }
    }
// Inventory ---------------------------------------------------------------

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
                Element e = screen.findElementByName("itemDrop" + slotId);


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
                Element e = screen.findElementByName("itemIcon" + slotId);
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
                Element e = screen.findElementByName("itemIcon" + slotId);
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
        if (dropSource.getElement().getElements().size() > 1) {
            return false;

        } else {
            return true;
        }
    }
    // Dialogue ----------------------------------------------------------------

    public void setDialogue(String text, int id) {
        if (id == 1) {
            guiManager.setText(dialogueText1, text);
            //dialoguePanel1.set
        } else {
            guiManager.setText(dialogueText2, text);
        }
    }
    // Console -----------------------------------------------------------------
}
