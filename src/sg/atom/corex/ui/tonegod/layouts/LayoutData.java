package sg.atom.corex.ui.tonegod.layouts;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import tonegod.gui.core.layouts.LayoutHint;
import tonegod.gui.core.layouts.LayoutHint.SizeUnit;

/**
 * Layout data vs Layout hint?
 *
 * @author cuong.nguyen
 */
public class LayoutData {

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
    SizeUnit posUnit = LayoutHint.SizeUnit.absolute;
    SizeUnit sizeUnit = LayoutHint.SizeUnit.absolute;
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
