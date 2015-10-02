package sg.atom.corex.ui.tonegod.layouts;

import com.google.common.base.Supplier;
import com.jme3.math.Vector2f;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;
import tonegod.gui.core.layouts.LayoutHint;

/**
 * LayoutBuilder build LayoutData for single element.
 */
public class LayoutBuilder implements Supplier<LayoutData> {
    protected Element element;
    protected Element container;
    protected LayoutData item;
    protected QLayout layout;
    protected static LayoutData global;

    public LayoutBuilder() {
        item = new LayoutData();
    }

    public LayoutBuilder(LayoutData newData) {
        item = newData;
    }

    public LayoutBuilder(Element element, QLayout layout, LayoutData item) {
        this();
        this.element = element;
        //            this.item = item;
        //Clone the item
    }

    public LayoutBuilder(Element element, Element container, QLayout layout) {
        this();
        this.element = element;
        this.container = container;
        this.layout = layout;
    }

    public LayoutBuilder(Element element, QLayout layout) {
        this();
        this.element = element;
        this.layout = layout;
    }

    //Pos------------------------------------------------------------------
    public LayoutBuilder pos(Vector2f pos) {
        item.pos.set(pos);
        return this;
    }

    public LayoutBuilder pos(Vector2f pos, LayoutHint.SizeUnit unit) {
        item.pos.set(pos);
        item.posUnit = unit;
        return this;
    }

    public LayoutBuilder pos(float x, float y) {
        item.pos.set(x, y);
        return this;
    }

    public LayoutBuilder pos(float x, float y, LayoutHint.SizeUnit unit) {
        item.pos.set(x, y);
        item.posUnit = unit;
        return this;
    }

    public LayoutBuilder fill() {
        item.pos.set(0, 0);
        item.posUnit = LayoutHint.SizeUnit.percent;
        item.size.set(100, 100);
        return this;
    }

    public LayoutBuilder autoSize() {
        return this;
    }

    public LayoutBuilder alignCenter(float x) {
        item.pos.x = (100 - x) / 2;
        return this;
    }

    public LayoutBuilder vAlignCenter(float y) {
        item.pos.y = (100 - y) / 2;
        return this;
    }
    //Size------------------------------------------------------------------

    public LayoutBuilder size(Vector2f size) {
        item.pos.set(size);
        return this;
    }

    public LayoutBuilder size(Vector2f size, LayoutHint.SizeUnit unit) {
        item.pos.set(size);
        item.sizeUnit = unit;
        return this;
    }

    public LayoutBuilder size(float x, float y) {
        item.size.set(x, y);
        return this;
    }

    public LayoutBuilder size(float x, float y, LayoutHint.SizeUnit unit) {
        item.size.set(x, y);
        item.sizeUnit = unit;
        return this;
    }

    public LayoutBuilder unit(LayoutHint.SizeUnit unit1) {
        item.sizeUnit = unit1;
        item.posUnit = unit1;
        return this;
    }

    public LayoutBuilder convert() {
        if (element != null) {
            item.size.set(element.getDimensions());
            item.pos.set(element.getPosition());
        }
        return this;
    }

    public LayoutBuilder convertSize(LayoutHint.SizeUnit unit1, LayoutHint.SizeUnit unit2) {
        if (element != null) {
            item.size.set(element.getDimensions());
        }
        item.sizeUnit = unit2;
        return this;
    }
    //Static methods----------------------------------------------------------------

    public static void pos(LayoutData item, Vector2f pos) {
        item.pos.set(pos);
    }

    public static void pos(LayoutData item, Vector2f pos, LayoutHint.SizeUnit unit) {
        item.pos.set(pos);
        item.posUnit = unit;
    }

    public static void pos(LayoutData item, float x, float y) {
        item.pos.set(x, y);
    }

    public static void pos(LayoutData item, float x, float y, LayoutHint.SizeUnit unit) {
        item.pos.set(x, y);
        item.posUnit = unit;
    }

    public static void size(LayoutData item, Vector2f size) {
        item.pos.set(size);
    }

    public static void size(LayoutData item, Vector2f size, LayoutHint.SizeUnit unit) {
        item.pos.set(size);
        item.sizeUnit = unit;
    }

    public static void size(LayoutData item, float x, float y) {
        item.size.set(x, y);
    }

    public static void size(LayoutData item, float x, float y, LayoutHint.SizeUnit unit) {
        item.size.set(x, y);
        item.sizeUnit = unit;
    }
    // Global switch -------------------------------------------------------
    //        public LayoutBuilder left() {
    //            return this;
    //        }
    //
    //        public LayoutBuilder right() {
    //            return this;
    //        }

    //        public LayoutBuilder unitPercentage() {
    //            return this;
    //        }
    //
    //        public LayoutBuilder unitPixel() {
    //            return this;
    //        }
    public LayoutData build() {
        if (element != null && layout != null) {
            if (container == null) {
                layout.$(element, item);
            } else {
                container.addChild(element);
                layout.$(element, item, container);
            }
        }
        return item;
    }

    public LayoutData get() {
        return build();
    }

    public void set() {
        build();
        if (layout != null) {
            layout.getEventBus().post(new LayoutEvent(item));
        }
    }

    public void set(Element container) {
        this.container = container;
        set();
    }
    //Hierarchy------------------------------------------------------------------

    public LayoutBuilder addTo(Screen screen) {
        screen.addElement(element);
        container = null;
        return this;
    }

    public LayoutBuilder addTo(Element container) {
        container.addChild(element);
        this.container = container;
        return this;
    }

    public LayoutBuilder link(LayoutData parent, LayoutData child) {
        return this;
    }
    //Additional -----------------------------------------------------------

    public LayoutBuilder text(String string) {
        element.setText(string);
        return this;
    }

    public Element getElement() {
        return element;
    }

    public QLayout getLayout() {
        return layout;
    }

    public Element getContainer() {
        return container;
    }
    
}
