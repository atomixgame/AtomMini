package sg.atom.corex.ui.tonegod.layouts;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeTraverser;
import com.google.common.eventbus.EventBus;
import com.jme3.math.Vector2f;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;
import tonegod.gui.core.layouts.LayoutHint;

/**
 * QLayout is Helper for Layout operations in Query style.
 *
 * Concepts: Layout, LayoutData, LayoutBuilder, LayoutEvent
 *
 * Target: Element, Screen.
 *
 * Create common layouts via DSL.
 *
 * Create base and placeholder elements.
 *
 * @author CuongNguyen
 */
public class QLayout {

    protected Ordering order;
    protected TreeTraverser<Element> elementTreeTraverser;
    protected TreeTraverser<LayoutData> layoutTreeTraverser;
    protected EventBus eventBus;
    protected Screen screen;
    protected Element topMostElement;
    HashMap<Element, LayoutData> layoutDatas = new HashMap<Element, LayoutData>();
//    public static QLayout $;
    protected static ListLayoutBuilder listLayoutBuilder;
    protected static LayoutBuilder layoutBuilder;
    protected static LayoutHint.SizeUnit defaultUnit = LayoutHint.SizeUnit.absolute;

    public QLayout(Screen screen) {
        this(screen, null, new EventBus("LayoutManager"));
        
    }

    public QLayout(Element topMostElement) {
        this(null, topMostElement, new EventBus("LayoutManager"));
    }

    public QLayout(Screen screen, Element topMostElement, EventBus eventBus) {
        this.eventBus = eventBus;
        this.screen = screen;
        this.topMostElement = topMostElement;
        this.layoutDatas = new LinkedHashMap<Element, LayoutData>();
    }

    public static LayoutBuilder create() {
        return new LayoutBuilder();

    }

    //Selectors
    public LayoutData $(String style) {
        return new LayoutData();
    }

    public void $(Element element, LayoutData layoutData) {
        this.layoutDatas.put(element, layoutData);
//        layoutData.element = element;
    }

    public LayoutBuilder $(Element element) {
        QLayout.layoutBuilder = new LayoutBuilder(element, this);
        return layoutBuilder;
    }

    public ListLayoutBuilder $(Element... elements) {
        QLayout.listLayoutBuilder = new ListLayoutBuilder(elements, this);
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
        if (layoutData.posUnit == LayoutHint.SizeUnit.absolute) {
            element.setPosition(layoutData.getPos());
            element.setDimensions(layoutData.getSize());
        } else if (layoutData.posUnit == LayoutHint.SizeUnit.percent) {
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
        this.screen = screen;
        refreshLayout();
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
