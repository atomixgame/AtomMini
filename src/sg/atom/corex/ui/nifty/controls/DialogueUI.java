package sg.atom.corex.ui.nifty.controls;

import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author cuongnguyen
 */
public class DialogueUI {

    private Element dialogueText1;
    private Element dialogueText2;
    private Element dialoguePanel1;
    private Element dialoguePanel2;

    public void create(Screen screen) {
        dialogueText1 = screen.findElementById("dialogueText1");
        dialogueText2 = screen.findElementById("dialogueText2");
        dialoguePanel1 = screen.findElementById("dialoguePanel1");
        dialoguePanel2 = screen.findElementById("dialoguePanel2");
    }

    public void setDialogue(String text, int id) {
        if (id == 1) {
//            guiManager.setText(dialogueText1, text);
            //dialoguePanel1.set
        } else {
//            guiManager.setText(dialogueText2, text);
        }
    }
}
