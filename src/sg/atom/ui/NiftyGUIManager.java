package sg.atom.ui;

import com.google.common.collect.TreeTraverser;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.UUID;
import sg.atom.AtomMain;
import sg.atom.ui.nifty.UICreditScreen;
import sg.atom.ui.nifty.UIFightScreen;
import sg.atom.ui.nifty.UIInGameScreen;
import sg.atom.ui.nifty.UILeaderBoardScreen;
import sg.atom.ui.nifty.UIMainMenuScreen;
import sg.atom.ui.nifty.UIShopScreen;

/**
 *
 * @author CuongNguyen
 */
public class NiftyGUIManager extends GUIManager implements ScreenController {
    // Nifty

    protected NiftyJmeDisplay niftyDisplay;
    protected Nifty nifty;
    protected boolean niftyStarted = false;
    protected boolean disableNifty = false;

    public NiftyGUIManager(AtomMain app) {
        super(app);
        instance = this;
    }

    public void setupGUI() {
        super.setupGUI();
        setupNifty();
    }

    public void loadNiftyScreens() {
        //Screens

//        UIInGameScreen inGameScreenController = new UIInGameScreen(this);
//        UICreditScreen creditScreenController = new UICreditScreen(this);
//        UIMainMenuScreen mainMenuScreenController = new UIMainMenuScreen(this);
//        UILeaderBoardScreen leaderBoardScreenController = new UILeaderBoardScreen(this);
//        UIShopScreen shopScreenController = new UIShopScreen(this);
//        nifty.registerScreenController(mainMenuScreenController,
//                inGameScreenController,
//                creditScreenController,
//                leaderBoardScreenController,
//                shopScreenController);

        nifty.registerScreenController(new UIInGameScreen(this),
                new UICreditScreen(this),
                new UIMainMenuScreen(this),
                new UILeaderBoardScreen(this),
                new UIShopScreen(this),
                new UIFightScreen(this));
        //nifty.addXml("Interface/Screens/SplashScreen.xml");
        nifty.addXml("Interface/Screens/MainMenu.xml");
//        nifty.addXml("Interface/Screens/CreditScreen.xml");
        nifty.addXml("Interface/Screens/InGame.xml");
//        nifty.addXml("Interface/Screens/LeaderBoard.xml");
//        nifty.addXml("Interface/Screens/IngameScreens/Dialogue.xml");
//        nifty.addXml("Interface/Screens/Shop.xml");
    }

    public void setupNifty() {
        niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();

        nifty.registerEffect("customHint", "sg.games.cuteheroes.ui.effects.CustomHint");
        nifty.registerEffect("motionAxis", "sg.games.cuteheroes.ui.effects.MotionAxis");
        nifty.registerEffect("imageOverlayPulsateBlend", "sg.games.cuteheroes.ui.effects.ImageOverlayPulsateBlend");

        loadNiftyScreens();
        inputManager.addMapping("toggleNifty", new KeyTrigger(KeyInput.KEY_F10));
        inputManager.addListener(new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                if (name.equals("toggleNifty") && isPressed) {
                    toggleNifty();
                }
            }
        }, "toggleNifty");
        attachNifty();
    }

    public void attachNifty() {
        guiViewPort.addProcessor(niftyDisplay);

    }

    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    // ===Ultilites that Nifty should have!============================================
    /**
     * Ultility method to create a NiftyImage from a JME3's Texture
     *
     * @param tex
     * @param textureName
     * @param iR
     */
    public void setNiftyImage(Texture tex, String textureName, ImageRenderer iR) {
        ((DesktopAssetManager) assetManager).addToCache(new TextureKey(textureName), tex);
        NiftyImage img = nifty.getRenderEngine().createImage(null, textureName, false);
        iR.setImage(img);
    }

    public NiftyImage createNiftyImage(String path) {
        Texture tex = assetManager.loadTexture(path);
        String textureName = path.substring(path.lastIndexOf("/"));
        textureName = textureName.concat(UUID.randomUUID().toString());
        ((DesktopAssetManager) assetManager).addToCache(new TextureKey(textureName), tex);
        NiftyImage img = nifty.getRenderEngine().createImage(null, textureName, false);
        return img;
    }

    /**
     * Ultility to safety setText in Nifty Elements
     */
    public static void setText(Element el, String text) {
        el.getRenderer(TextRenderer.class).setText(text);
    }

    public static void setText(Element el, int num) {
        el.getRenderer(TextRenderer.class).setText(Integer.toString(num));
    }

    public static void setText(Element el, float num) {
        el.getRenderer(TextRenderer.class).setText(Float.toString(num));
    }

    public void setText(String elId, String text) {
        nifty.getCurrentScreen().findElementByName(elId).getRenderer(TextRenderer.class).setText(text);
    }

    public static void setImage(Element el, NiftyImage image) {
        el.getRenderer(ImageRenderer.class).setImage(image);
    }

    public TreeTraverser<Element> getTreeTraverser() {
        return new TreeTraverser<Element>() {
            @Override
            public Iterable<Element> children(Element root) {
                return root.getElements();
            }
        };
    }

    public Element getElementByPath(String path) {
        return null;
    }

    /**
     * Ultility to safety navigate through screens.
     *
     * It can be intercept for tesing purpose.
     *
     * @param screenName
     */
    public void goToScreen(String screenName) {
        //FIXME: Intercept goto

        if (nifty.getScreen(screenName) != null) {
            nifty.gotoScreen(screenName);
        } else {
            //loadAndGotoScreen(screenName);
        }
    }

    public boolean isCurrentScreen(String screenName) {
        return nifty.getCurrentScreen().getScreenId().equals(screenName);
    }

    public Nifty getNifty() {
        return nifty;
    }

    public ScreenController getScreenController() {
        return this.nifty.getCurrentScreen().getScreenController();
    }

    public Node getGuiNode() {
        return guiNode;
    }

    public void detachNifty() {

        guiViewPort.removeProcessor(niftyDisplay);
    }

    public void toggleNifty() {
        if (disableNifty) {
            attachNifty();
            disableNifty = false;
        } else {
            detachNifty();
            disableNifty = true;
        }
    }

    public static NiftyGUIManager getInstance() {
        return (NiftyGUIManager) instance;

    }
}
