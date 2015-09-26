package sg.atom.common.ui.nifty;

import com.google.common.base.Function;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.dragndrop.builder.DraggableBuilder;
import de.lessvoid.nifty.controls.dragndrop.builder.DroppableBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.HashMap;
import sg.atom.gameplay.CommonGameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.gameplay.Inventory;
import sg.atom.gameplay.Skill;
import sg.atom.common.state.InGameState;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.ui.NiftyGUIManager;
import sg.atom.corex.ui.hud.HealthBarNode;

/**
 *
 * @author CuongNguyen
 */
public class UIInGameScreen implements ScreenController {

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
    private Inventory inventory;
    private HashMap<CommonGameCharacter, HealthBarNode> healthBars;
    private Node healthBarNode;
    private Element skillListPanel;
    private HashMap<String, Skill> skillMap;
    private Element effectLayer;
    private int ITEM_WIDTH;
    private int ITEM_HEIGHT;

    public UIInGameScreen(GUIManager guiManager) {
        this.guiManager = (NiftyGUIManager) guiManager;
    }

    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
        this.nifty = nifty;
        inGameState = guiManager.getApp().getStateManager().getState(InGameState.class);

        if (screen.getScreenId().equals("InGameScreen")) {
            alertText = screen.findElementById("alertSmall");
            healthBarValue = screen.findElementById("healthBarValue");
            manaBarValue = screen.findElementById("manaBarValue");
            healthPoint = screen.findElementById("healthPoint");
            manaPoint = screen.findElementById("manaPoint");

//            inventoryUI = screen.findElementById("inventory");
//            inventoryItemListPanel = screen.findElementById("inventoryItemList");
//
//            GameCharacter playerMainCharacter = guiManager.getApp().getStageManager().getGamePlayManager().getMainPlayer().getMainCharacter();
//            createInventory(playerMainCharacter.getInventory());
//            closeInventory();
            skillListPanel = screen.findElementById("quickActionPanel");
            //createSkills(playerMainCharacter);
//            CustomHint.createHintLayer(nifty, screen);

        }
    }

    public void onStartScreen() {
        inGameState.bindUI(guiManager, this);
    }

    public void onEndScreen() {
        inGameState.unbindUI(guiManager, this);
        healthBarNode.detachAllChildren();
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
        healthBars = new HashMap<CommonGameCharacter, HealthBarNode>();
        for (CommonGameCharacter character : gamePlayManager.getCharacters()) {
            createCharacterInfoExtra(character);
        }
    }

    public void setCharacterInfo(String name, String type, int levels) {
    }

    public void createCharacterInfoExtra(CommonGameCharacter character) {
        HealthBarNode uiHealthBar = new HealthBarNode(character, guiManager);
        healthBars.put(character, uiHealthBar);
        healthBarNode.attachChild(uiHealthBar);
//        System.out.println("Create");
    }

    public void setCharacterInfoExtra(CommonGameCharacter character) {
        HealthBarNode healthBar = healthBars.get(character);
        if (healthBar != null) {
            healthBar.updateBar(guiManager.getApp().getStageManager().getCurrentCamera());
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

    void createSkills(CommonGameCharacter character) {
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
            Element e = screen.findElementById("skill" + slotId);
            if (e != null) {

            }
        }

        effectLayer = new LayerBuilder("") {
            {
                childLayoutAbsolute();
            }
        }.build(nifty, screen, screen.getRootElement());
    }

    public void onSkillAction(String name) {
        CommonGameCharacter playerMainCharacter = guiManager.getApp().getGamePlayManager().getMainPlayer().getMainCharacter();
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
    // Dialogue ----------------------------------------------------------------

    // Console -----------------------------------------------------------------
}
