package sg.atom.ui.tonegod.layouts;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeTraverser;
import com.google.common.eventbus.EventBus;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 * Layout is Helper for Layout operations.
 *
 * Concepts: Layout, LayoutData, LayoutBuilder, LayoutEvent
 *
 * Element, Screen.
 *
 * @author CuongNguyen
 */
public class Layout {

    protected Ordering order;
    protected TreeTraverser<Element> elementTreeTraverser;
    protected TreeTraverser<LayoutData> layoutTreeTraverser;
    protected EventBus eventBus;
    protected boolean disodered = false;
    protected boolean allowDisordered = false;
    private Screen screen;
    private Object topMostElement;
    private boolean smartActive = true;
    HashMap<Element, LayoutData> layoutDatas = new HashMap<Element, LayoutData>();
    public static Layout $;
    private static ListLayoutBuilder listLayoutBuilder;
    private static LayoutBuilder layoutBuilder;

    public static class LayoutData {

        public static int UNIT_PERCENT = 0;
        public static int UNIT_PIXEL = 1;
//        public static int UNIT_DPI = 2;
//        public static int UNIT_METTER = 3;
        //Runtime data
        Vector2f pos;
        Vector2f absPos;
        Vector2f size;
        Vector2f scale;
        //Prototyping data
        Vector4f boundary;
        Vector4f padding;
        Vector4f margin;
        Vector4f constraints;
        int[] constraintsRef;
        //Units
        int posUnit = UNIT_PIXEL;
        int sizeUnit = UNIT_PIXEL;
        int[] constraintUnit;
        int[] boundaryUnit;
        int[] paddingUnit;
        int[] marginUnit;
        boolean naturalContainment;
        boolean specificContainment;
        boolean smartActive = true;
        LayoutData linked;

        public LayoutData() {
            this.pos = new Vector2f();
            this.size = new Vector2f();
        }

        public LayoutData(LayoutData original) {
//            this.pos = new Vector2f();
//            this.size = new Vector2f();
        }

        public Vector2f getPos() {
            return pos;
        }

        public Vector2f getSize() {
            return size;
        }

        public void setPos(Vector2f pos) {
            this.pos = pos;
        }

        public void setSize(Vector2f size) {
            this.size = size;
        }

        public String toString() {
            return pos.toString() + " " + size.toString();
        }
    }

    public Layout(Screen screen) {
        this.screen = screen;
        this.eventBus = new EventBus("LayoutManager");
        this.layoutDatas = new LinkedHashMap<Element, LayoutData>();
    }

    public Layout(Object topMost) {
        this.topMostElement = topMost;
        this.eventBus = new EventBus("LayoutManager");

    }

    public Layout(Screen screen, Object topMostElement, EventBus eventBus) {
        this.eventBus = eventBus;
        this.screen = screen;
        this.topMostElement = topMostElement;
    }

    /**
     * LayoutBuilder build LayoutData for single element.
     */
    public static class LayoutBuilder implements Supplier<LayoutData> {

        Element element;
        Element container;
        LayoutData item;
        Layout layout;
        static LayoutData global;

        public LayoutBuilder() {
            item = new LayoutData();
        }

        public LayoutBuilder(LayoutData newData) {
            item = newData;
        }

        public LayoutBuilder(Element element, Layout layout, LayoutData item) {
            this();
            this.element = element;
//            this.item = item;
            //Clone the item
        }

        public LayoutBuilder(Element element, Element container, Layout layout) {
            this();
            this.element = element;
            this.container = container;
            this.layout = layout;

        }

        public LayoutBuilder(Element element, Layout layout) {
            this();
            this.element = element;
            this.layout = layout;
        }

        //Pos------------------------------------------------------------------
        public LayoutBuilder pos(Vector2f pos) {
            item.pos.set(pos);
            return this;
        }

        public LayoutBuilder pos(Vector2f pos, int unit) {
            item.pos.set(pos);
            item.posUnit = unit;
            return this;
        }

        public LayoutBuilder pos(float x, float y) {
            item.pos.set(x, y);
            return this;
        }

        public LayoutBuilder pos(float x, float y, int unit) {
            item.pos.set(x, y);
            item.posUnit = unit;
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

        public LayoutBuilder size(Vector2f size, int unit) {
            item.pos.set(size);
            item.sizeUnit = unit;
            return this;
        }

        public LayoutBuilder size(float x, float y) {
            item.size.set(x, y);
            return this;
        }

        public LayoutBuilder size(float x, float y, int unit) {
            item.size.set(x, y);
            item.sizeUnit = unit;
            return this;
        }

        public LayoutBuilder unit(int unit1) {
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

        public LayoutBuilder convertSize(int unit1, int unit2) {
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

        public static void pos(LayoutData item, Vector2f pos, int unit) {
            item.pos.set(pos);
            item.posUnit = unit;
        }

        public static void pos(LayoutData item, float x, float y) {
            item.pos.set(x, y);
        }

        public static void pos(LayoutData item, float x, float y, int unit) {
            item.pos.set(x, y);
            item.posUnit = unit;
        }

        public static void size(LayoutData item, Vector2f size) {
            item.pos.set(size);
        }

        public static void size(LayoutData item, Vector2f size, int unit) {
            item.pos.set(size);
            item.sizeUnit = unit;
        }

        public static void size(LayoutData item, float x, float y) {
            item.size.set(x, y);
        }

        public static void size(LayoutData item, float x, float y, int unit) {
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
    }

    /**
     * ListLayoutBuilder build LayoutData for series of Elements.
     */
    public static class ListLayoutBuilder {

        public int _index;
        List<Element> elements;
        List<LayoutData> items;
        BiMap<Element, LayoutBuilder> builderMap;
        Layout layout;
        static LayoutData global;
        ListLayoutBuilder $$;
        Element container;

        protected ListLayoutBuilder(Element[] elements, Layout layout) {
            this.layout = layout;
            this.elements = Arrays.asList(elements);
            this.items = new ArrayList<LayoutData>();
            for (Element element : elements) {
                LayoutData item = new LayoutData();
                items.add(item);
            }
        }

        public List<LayoutBuilder> toBuilderList() {
            return Lists.transform(ImmutableList.copyOf(elements), new Function<Element, LayoutBuilder>() {
                public LayoutBuilder apply(Element element) {
                    return new LayoutBuilder(element, layout);
                }
            });
        }

        //Pos------------------------------------------------------------------
        public ListLayoutBuilder pos(Vector2f pos) {
            for (LayoutData item : items) {
                item.pos.set(pos);
            }
            return this;
        }

        public ListLayoutBuilder pos(Vector2f pos, int unit) {
            for (LayoutData item : items) {
                item.pos.set(pos);
                item.posUnit = unit;
            }
            return this;
        }

        public ListLayoutBuilder pos(float x, float y) {
            for (LayoutData item : items) {
                item.pos.set(x, y);
            }
            return this;
        }

        public ListLayoutBuilder pos(float x, float y, int unit) {
            for (LayoutData item : items) {

                item.pos.set(x, y);
                item.posUnit = unit;
            }
            return this;
        }

        public ListLayoutBuilder pos(int index, float x, float y, int unit) {
            LayoutData item = items.get(index);
            item.pos.set(x, y);
            item.posUnit = unit;

            return this;
        }

        //Pos with padding
        public ListLayoutBuilder posInc(float x, float y) {
            return posInc(x, y, 0, 0, LayoutData.UNIT_PIXEL);
        }

        public ListLayoutBuilder posInc(float x, float y, int unit) {
            return posInc(x, y, 0, 0, unit);
        }

        public ListLayoutBuilder posInc(float x, float y, float padx, float pady) {

            return posInc(x, y, padx, pady, LayoutData.UNIT_PIXEL);
        }

        public ListLayoutBuilder posInc(float x, float y, float padx, float pady, int unit) {
            LayoutData first = items.get(0);
            for (LayoutData item : items) {
                int index = items.indexOf(item);
                item.pos.set(first.pos.x + (padx + x) * index, first.pos.y + (pady + y) * index);
                item.posUnit = unit;
            }
            return this;
        }
        //Size------------------------------------------------------------------

        public ListLayoutBuilder size(Vector2f size) {
            for (LayoutData item : items) {
                item.pos.set(size);
            }
            return this;
        }

        public ListLayoutBuilder size(Vector2f size, int unit) {
            for (LayoutData item : items) {
                item.pos.set(size);
                item.sizeUnit = unit;
            }
            return this;
        }

        public ListLayoutBuilder size(float x, float y) {
            for (LayoutData item : items) {
                item.size.set(x, y);
            }
            return this;
        }

        public ListLayoutBuilder size(float x, float y, int unit) {
            for (LayoutData item : items) {
                item.size.set(x, y);
                item.sizeUnit = unit;
            }
            return this;
        }
        //Hierachy--------------------------------------------------------------

        public ListLayoutBuilder addTo(Screen screen) {
            for (Element element : elements) {
                screen.addElement(element);
                container = null;
            }
            return this;
        }

        public ListLayoutBuilder addTo(Element container) {
            for (Element element : elements) {
                container.addChild(element);
                this.container = container;
            }
            return this;
        }
        //Build-----------------------------------------------------------------

        public void build() {
            for (Element element : elements) {
                int index = elements.indexOf(element);
                LayoutData item = items.get(index);
                if (element != null && layout != null) {
                    if (container == null) {
                        layout.$(element, item);
                    } else {
                        container.addChild(element);
                        layout.$(element, item, container);
                    }
                }
            }

        }

        public void set() {
            build();
        }
        //Branching-------------------------------------------------------------

        public LayoutBuilder _get(int index) {
            return new LayoutBuilder(items.get(index));
        }

        //Additional -----------------------------------------------------------
        public ListLayoutBuilder text(String string) {
            for (Element element : elements) {
                element.setText(string);
            }
            return this;
        }

        public ListLayoutBuilder text(String... string) {
            for (Element element : elements) {
                int index = elements.indexOf(element);
                element.setText(string[index]);
            }
            return this;
        }
    }

    public static class LayoutEvent {

        public LayoutEvent(Layout.LayoutData item) {
        }
    }

    public static LayoutBuilder create() {
        return new LayoutBuilder();

    }

    public LayoutData $(String style) {
        return new LayoutData();
    }

    public void $(Element element, LayoutData layoutData) {
        this.layoutDatas.put(element, layoutData);
//        layoutData.element = element;
    }

    public LayoutBuilder $(Element element) {
        Layout.layoutBuilder = new LayoutBuilder(element, this);
        return layoutBuilder;
    }

    public ListLayoutBuilder $(Element... elements) {
        Layout.listLayoutBuilder = new ListLayoutBuilder(elements, this);
        return listLayoutBuilder;
    }

    public LayoutBuilder $(Element element, Element container) {
        return new LayoutBuilder(element, container, this);
    }

    public void $(Element element, LayoutData layoutData, LayoutData containerLayoutData) {
        $(element, layoutData);
        layoutData.linked = containerLayoutData;

    }

    public void $(Element element, LayoutData layoutData, Element container) {
        //Assume that there is a link
        $(element, layoutData);
        LayoutData containerLayoutData = layoutDatas.get(container);

        if (containerLayoutData == null) {
            containerLayoutData = new LayoutData();
            layoutDatas.put(container, containerLayoutData);

            //Mark as dis-ordered operations?
        } else {
            layoutData.linked = containerLayoutData;
        }

    }

    public LayoutBuilder $() {
        return new LayoutBuilder();
    }

    /**
     * <p>Layout data should be resolve with aligment and containment
     * informations. The order usually are not easy to describle. The resolving
     * progress also not just top-down or bottom up only but mixed. So at first
     * it will try to travel from the root to get fixed attributes (size,
     * aligment) of the container which can be lend to calculate the attributes
     * of its children. If the calculating require some deep travel along the
     * hierarchy to complete, the linkage will be save and result will be
     * propagate through event mechnism.
     *
     * @param element
     * @param layoutData
     */
    public void layout(Element element, LayoutData layoutData) {
        if (layoutData.posUnit == LayoutData.UNIT_PIXEL) {
            element.setPosition(layoutData.getPos());
            element.setDimensions(layoutData.getSize());
        } else if (layoutData.posUnit == LayoutData.UNIT_PERCENT) {
            //Relative unit
            if (layoutData.linked == null) {
                Element elementParent = element.getElementParent();
                Vector2f pSize;
                if (elementParent != null) {
                    pSize = elementParent.getDimensions();

                } else {
                    pSize = new Vector2f(screen.getWidth(), screen.getHeight());
                }
                Vector2f cPos = layoutData.getPos().mult(0.01f).multLocal(pSize);
                Vector2f cSize = layoutData.getSize().mult(0.01f).multLocal(pSize);
                element.setPosition(cPos);
                element.setDimensions(cSize);
                System.out.println(" " + cPos + " " + cSize);
            } else {
            }
        }

    }
    //ordering
    Ordering<Element> naturalOrder = new Ordering<Element>() {
        @Override
        public int compare(Element left, Element right) {
            return 0;
        }
    };

    /**
     * Refresh layout is a progress of arrange element with its layout data in
     * an ordering.
     *
     */
    public void refreshLayout() {
        order = Ordering.natural();
        List<Element> sortedElements = ImmutableList.copyOf(layoutDatas.keySet());

        for (Element element : sortedElements) {
            System.out.println(" " + element.getUID() + " ->" + layoutDatas.get(element).toString());
            layout(element, layoutDatas.get(element));
        }
    }

    public void active(Screen screen) {
        //Find containment info.

        refreshLayout();
    }

    public void smartActive(Element element) {
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
