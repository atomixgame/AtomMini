/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.tonegod.layouts.old;

/**
 *
 * @author cuong.nguyenmanh2
 */
import java.util.logging.Level;
import java.util.logging.Logger;

import tonegod.gui.core.Element;
import com.jme3.math.Vector2f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public abstract class LayoutHelper extends AbstractControl {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final static Level LOG_LEVEL = Level.FINE;
    private Element element;

    protected void log(String msg, Object... params) {
        if (log.isLoggable(LOG_LEVEL)) {
            log.log(LOG_LEVEL, msg + " -> position: " + element.getPosition() + ", dimensions: " + element.getDimensions() + " on " + element, params);
        }
    }

    @Override
    public void setSpatial(Spatial spatial) {

        if (!(spatial instanceof Element)) {
            throw new RuntimeException("Wrong type of spatial");
        }

        this.element = (Element) spatial;
        super.setSpatial(spatial);

    }

    /**
     * Override to update element’s layout before the parent’s layout has been
     * updated. Use this when the parent’s layout depends on the layout of the
     * child (e.g. size panel to fit children).
     */
    protected void beforeParent() {
    }

    /**
     * Override to update element’s layout to reflect changes in child layout
     * but before the parent’s layout is updated.
     */
    protected void afterChildren() {
    }

    /**
     * Override to update element’s layout after the parent’s layout has been
     * updated. Use this when the child’s layout depends on the layout of the
     * parent (e.g. size children to fit in panel).
     */
    protected void afterParent() {
    }

    /**
     * Width of parent element or screen.
     */
    public float getContainerWidth() {
        Element parent = element.getElementParent();
        if (parent != null) {
            return parent.getWidth();
        } else {
            return element.getScreen().getWidth();
        }
    }

    /**
     * Height of parent element or screen.
     */
    public float getContainerHeight() {
        Element parent = element.getElementParent();
        if (parent != null) {
            return parent.getHeight();
        } else {
            return element.getScreen().getHeight();
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public static void layoutForScreen(Element element) {

        if (element.getElementParent() != null) {
            throw new RuntimeException("Not a root element");
        }

        // call afterParent (the parent is the screen) for current element
        for (int i = 0; i < element.getNumControls(); ++i) {
            Control control = element.getControl(i);
            if (control instanceof LayoutHelper) {
                ((LayoutHelper) control).afterParent();
            }
        }

        // call beforeParent and afterChildren for child elements recursively
        for (Spatial spatial : element.getChildren()) {
            if (spatial instanceof Element) {
                visitBeforeParent((Element) spatial);
            }
        }

        // call afterChildren for current element
        for (int i = 0; i < element.getNumControls(); ++i) {
            Control control = element.getControl(i);
            if (control instanceof LayoutHelper) {
                ((LayoutHelper) control).afterChildren();
            }
        }

        // call afterParent for child elements recursively
        for (Spatial spatial : element.getChildren()) {
            if (spatial instanceof Element) {
                visitAfterParent((Element) spatial);
            }
        }

    }

    private static void visitBeforeParent(Element element) {

        // beforeParent for current element
        for (int i = 0; i < element.getNumControls(); ++i) {
            Control control = element.getControl(i);
            if (control instanceof LayoutHelper) {
                ((LayoutHelper) control).beforeParent();
            }
        }

        // call beforeParent and afterChildren for child elements recursively
        for (Spatial spatial : element.getChildren()) {
            if (spatial instanceof Element) {
                visitBeforeParent((Element) spatial);
            }
        }

        // call afterChildren for current element
        for (int i = 0; i < element.getNumControls(); ++i) {
            Control control = element.getControl(i);
            if (control instanceof LayoutHelper) {
                ((LayoutHelper) control).afterChildren();
            }
        }

    }

    private static void visitAfterParent(Element element) {

        // afterParent for current element
        for (int i = 0; i < element.getNumControls(); ++i) {
            Control control = element.getControl(i);
            if (control instanceof LayoutHelper) {
                ((LayoutHelper) control).afterParent();
            }
        }

        // afterParent for child elements recursively
        for (Spatial spatial : element.getChildren()) {
            if (spatial instanceof Element) {
                visitAfterParent((Element) spatial);
            }
        }

    }

    public static Vector2f autoPosition() {
        return new Vector2f(Float.NaN, Float.NaN);
    }

    public static Vector2f autoSize() {
        return new Vector2f(Float.NaN, Float.NaN);
    }
}
