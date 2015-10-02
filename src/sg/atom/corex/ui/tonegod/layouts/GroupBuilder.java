/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.ui.tonegod.layouts;

import tonegod.gui.core.Element;

/**
 *
 * @author cuong.nguyen
 */
public class GroupBuilder extends ListLayoutBuilder {

    LayoutBuilder groupElementBuilder;

    public GroupBuilder(LayoutBuilder[] elements, Element element, QLayout layout) {
            super(elements, layout);
//            this.groupElementBuilder = new LayoutBuilder(element, QuickLayouts.this);
        this.addTo(element);
    }

    public LayoutBuilder panel() {
        return groupElementBuilder;
    }

    public void set() {
        this.groupElementBuilder.fill();
        this.groupElementBuilder.set();
        super.set();
    }
}
