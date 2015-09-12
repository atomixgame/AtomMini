package sg.atom.corex.ui.nifty;

import com.jme3.asset.AssetManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import java.util.ArrayList;
import sg.atom.core.io.TextLoader;
import sg.atom.state.CreditState;
import sg.atom.state.MainMenuState;
import sg.atom.corex.managers.GUIManager;

/**
 *
 * @author CuongNguyen
 */
public class UICreditScreen implements ScreenController {

    Screen screen;
    GUIManager guiManager;
    Nifty nifty;
    Element layer;
    ArrayList<Element> scrollElements;
    Element scrollPanel;
    AssetManager assetManager;
    CreditState creditState;
    ArrayList<String> creditTexts;
    int topY = 0;
    int speed = 10;
    int gapY = 40;

    public UICreditScreen(GUIManager guiManager) {
        this.guiManager = guiManager;
        this.assetManager = guiManager.getAssetManager();

        assetManager.registerLoader(TextLoader.class, "txt");
        creditTexts = (ArrayList<String>) assetManager.loadAsset("Story/Credits.txt");
        this.scrollElements = new ArrayList<Element>();
    }

    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
        this.nifty = nifty;
        this.layer = screen.findElementById("scrollLayer");
        this.scrollPanel = screen.findElementById("scrollPanel");
    }

    public void onStartScreen() {
        creditState = guiManager.getApp().getStateManager().getState(CreditState.class);
        topY = 500;
        creditState.bindUI();
        createScroll();
    }

    public void onEndScreen() {
    }

    public void createScroll() {
        int id = 0;
        if (!scrollElements.isEmpty()) {
            for (Element el : scrollElements) {
                el.markForRemoval();
            }
        }
        for (String line : creditTexts) {
            id++;
            scrollElements.add(createTextElement(line, id));
        }

    }

    public void scroll() {
        int y = 0;
        int count = 0;
        topY -= speed;

//        for (Element el : scrollElements) {
//            count++;
//            y = topY + count * gapY;
//            el.setConstraintY(new SizeValue(y + "px"));
//        }
        scrollPanel.setConstraintY(new SizeValue(topY + "px"));
        scrollPanel.getParent().layoutElements();
    }

    public Element createTextElement(final String text, int id) {
        final int y = topY + id * gapY;
        TextBuilder textBuilder = new TextBuilder("line" + id) {
            {
                alignCenter();

                y(y + "px");
                if (!text.startsWith("**")) {
                    text(text);
                    style("defaultFont");
                } else {
                    text(text.substring(2));
                    style("bigFont");
                }
                height("40px");
                textVAlignCenter();
            }
        };
        return textBuilder.build(nifty, screen, scrollPanel);
    }

    public void back() {
        creditState.toState(MainMenuState.class);
        //guiManager.goToScreen("MainMenuScreen");
    }
}
