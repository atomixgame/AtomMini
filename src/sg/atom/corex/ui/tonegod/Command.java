package sg.atom.corex.ui.tonegod;

import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 *
 * @author cuong.nguyen
 */
public interface Command {

    void execute(Screen screen, Element element);
    
}
