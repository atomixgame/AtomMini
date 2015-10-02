package sg.atom.corex.ui.tonegod.layouts;

import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.jme3.math.Vector2f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;
import tonegod.gui.core.layouts.LayoutHint;

/**
 * ListLayoutBuilder build LayoutData for series of Elements.
 */
public class ListLayoutBuilder {
    public int _index;
    List<Element> elements;
    List<LayoutData> items;
    BiMap<Element, LayoutBuilder> builderMap;
    QLayout layout;
    static LayoutData defaultItemData;
    //        ListLayoutBuilder $$;
    Element container;

    protected ListLayoutBuilder(Element[] elements, QLayout layout) {
        this.layout = layout;
        this.elements = Arrays.asList(elements);
        this.items = new ArrayList<LayoutData>();
        for (Element element : elements) {
            LayoutData item = new LayoutData();
            items.add(item);
        }
    }

    protected ListLayoutBuilder(LayoutBuilder[] lbs, QLayout layout) {
        this.layout = layout;
        this.elements = new ArrayList<Element>(lbs.length);
        this.items = new ArrayList<LayoutData>(lbs.length);
        for (LayoutBuilder lb : lbs) {
            elements.add(lb.element);
            items.add(lb.item);
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
    public ListLayoutBuilder cellPos(Vector2f pos) {
        for (LayoutData item : items) {
            item.pos.set(pos);
        }
        return this;
    }

    public ListLayoutBuilder cellPos(Vector2f pos, LayoutHint.SizeUnit unit) {
        for (LayoutData item : items) {
            item.pos.set(pos);
            item.posUnit = unit;
        }
        return this;
    }

    public ListLayoutBuilder cellPos(float x, float y) {
        for (LayoutData item : items) {
            item.pos.set(x, y);
        }
        return this;
    }

    public ListLayoutBuilder cellPos(float x, float y, LayoutHint.SizeUnit unit) {
        for (LayoutData item : items) {
            item.pos.set(x, y);
            item.posUnit = unit;
        }
        return this;
    }

    public ListLayoutBuilder cellPos(int index, float x, float y, LayoutHint.SizeUnit unit) {
        LayoutData item = items.get(index);
        item.pos.set(x, y);
        item.posUnit = unit;
        return this;
    }

    //Pos with padding
    public ListLayoutBuilder posInc(float x, float y) {
        return posInc(x, y, 0, 0, LayoutHint.SizeUnit.absolute);
    }

    public ListLayoutBuilder posInc(float x, float y, LayoutHint.SizeUnit unit) {
        return posInc(x, y, 0, 0, unit);
    }

    public ListLayoutBuilder posInc(float x, float y, float padx, float pady) {
        return posInc(x, y, padx, pady, LayoutHint.SizeUnit.absolute);
    }

    public ListLayoutBuilder posInc(float x, float y, float padx, float pady, LayoutHint.SizeUnit unit) {
        if (items.isEmpty()) {
            return this;
        }
        LayoutData first = items.get(0);
        for (LayoutData item : items) {
            int index = items.indexOf(item);
            item.pos.set(first.pos.x + (padx + x) * index, first.pos.y + (pady + y) * index);
            item.posUnit = unit;
        }
        return this;
    }
    //Size------------------------------------------------------------------

    public ListLayoutBuilder cellSize(Vector2f size) {
        for (LayoutData item : items) {
            item.pos.set(size);
        }
        return this;
    }

    public ListLayoutBuilder cellSize(Vector2f size, LayoutHint.SizeUnit unit) {
        for (LayoutData item : items) {
            item.pos.set(size);
            item.sizeUnit = unit;
        }
        return this;
    }

    public ListLayoutBuilder cellSize(float x, float y) {
        for (LayoutData item : items) {
            item.size.set(x, y);
        }
        return this;
    }

    public ListLayoutBuilder cellSize(float x, float y, LayoutHint.SizeUnit unit) {
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
