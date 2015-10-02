package sg.atom.common.ui.tonegod;

import com.jme3.app.Application;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import sg.atom.AtomMain;
import sg.atom.common.state.CreditState;
import sg.atom.common.state.InGameState;
import sg.atom.common.state.MainMenuState;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.ui.tonegod.layouts.QLayout;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Screen;
import tonegod.gui.core.Element;
import tonegod.gui.core.layouts.LayoutHint.SizeUnit;

/**
 *
 * @author CuongNguyen
 */
public class UITMainMenuScreen extends Screen {

    private GUIManager guiManager;
    private MainMenuState mainMenuState;
    private Element background;

    public UITMainMenuScreen(Application app) {
        super(app);
        this.guiManager = ((AtomMain) app).getGUIManager();
        bind();
        createElements();

    }

    private void createElements() {

        // create button and add to window
        ButtonAdapter btnInGame = new ButtonAdapter(this, "btnInGame", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                mainMenuState.toState(InGameState.class);
            }
        };
        ButtonAdapter btnCredit = new ButtonAdapter(this, "btnCredit", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                mainMenuState.toState(InGameState.class);
            }
        };
        ButtonAdapter btnSocial = new ButtonAdapter(this, "btnSocial", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                mainMenuState.toState(InGameState.class);
            }
        };
        ButtonAdapter btnOption = new ButtonAdapter(this, "btnOption", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                mainMenuState.toState(InGameState.class);
            }
        };
        ButtonAdapter btnShop = new ButtonAdapter(this, "btnShop", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                mainMenuState.toState(InGameState.class);
            }
        };
        ButtonAdapter btnQuit = new ButtonAdapter(this, "btnQuit", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                quitGame();
            }
        };
        background = new Element(this, "imgBackground", new Vector2f(0, 0), new Vector2f(100, 100), Vector4f.ZERO, "Interface/Images/MainMenu/MainMenuBg.png");
        Element logo = new Element(this, "imgLogo", new Vector2f(0, 0), new Vector2f(50, 20), Vector4f.ZERO, "Interface/Images/brand/Logo/TitleLogo.png");

        //Now resolve the layout, IOC style!
        QLayout layout = new QLayout(this);
        layout.$(background).convert().unit(SizeUnit.percent).addTo(this).set();
        layout.$(logo).convert().unit(SizeUnit.percent).addTo(this).alignCenter(40).vAlignCenter(0).set();

        layout.active(this);
    }

    public void endScreen() {
        this.removeElement(background);
    }

    protected void bind() {
        mainMenuState = guiManager.getApp().getStateManager().getState(MainMenuState.class);
    }
    //Interactions -----------------------------------------------------------------

    public void goInGame() {
        mainMenuState.toState(InGameState.class);

    }

    public void joinGame() {
        goInGame();
    }

    public void back() {
        guiManager.goToScreen("MainMenuScreen");
    }

    public void goCredit() {
        mainMenuState.toState(CreditState.class);
    }

    public void goSocial() {
        guiManager.goToScreen("LeaderBoardScreen");
    }

    public void goOptions() {
        guiManager.goToScreen("OptionsScreen");
    }

    public void goShop() {
        guiManager.goToScreen("ShopScreen");
    }

    public void quitGame() {
        guiManager.getApp().quit();
    }
}
