/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.tonegod.layouts.old;

/**
 *
 * @author cuong.nguyenmanh2
 */
import tonegod.gui.core.Element;

public class PercentLayout {

    public static void position(final Element element, final float left, final float bottom) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(left * getContainerWidth());
                element.setY(bottom * getContainerHeight());
                log("position - left: {0}, bottom: {1}", left, bottom);
            }
        });
    }

    public static void left(final Element element, final float left) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(left * getContainerWidth());
                log("left: {0}", left);
            }
        });
    }

    public static void bottom(final Element element, final float bottom) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setY(bottom * getContainerHeight());
                log("bottom: {0}", bottom);
            }
        });
    }

    public static void dimensions(final Element element, final float width, final float height) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setWidth(width * getContainerWidth());
                element.setHeight(height * getContainerHeight());
                log("dimensions - width: {0}, height: {1}", width, height);
            }
        });
    }

    public static void width(final Element element, final float width) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setWidth(width * getContainerWidth());
                log("width: {0}", width);
            }
        });
    }

    public static void height(final Element element, final float height) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(height * getContainerHeight());
                log("height: {0}", height);
            }
        });
    }

    public static void fillWidth(final Element element, final float leftMargin, final float rightMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(leftMargin * getContainerWidth());
                element.setWidth(getContainerWidth() - (leftMargin + rightMargin) * getContainerWidth());
                log("fillWidth - leftMargin: {0}, rightMargin: {1}", leftMargin, rightMargin);
            }
        });
    }

    public static void fillWidth(final Element element, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(margin * getContainerWidth());
                element.setWidth(getContainerWidth() - (2 * margin * getContainerWidth()));
                log("fillWidth - margin: {0}", margin);
            }
        });
    }

    public static void fillHeight(final Element element, final float bottomMargin, final float topMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setY(bottomMargin * getContainerHeight());
                element.setHeight(getContainerHeight() - (bottomMargin + topMargin) * getContainerHeight());
                log("fillHeight - bottomMargin: {0}, topMaring: {1}", bottomMargin, topMargin);
            }
        });
    }

    public static void fillHeight(final Element element, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setY(margin * getContainerHeight());
                element.setHeight(getContainerHeight() - (2 * margin * getContainerHeight()));
                log("fillHeight - margin: {0}", margin);
            }
        });
    }

    public static void fill(final Element element, final float leftMargin, final float bottomMargin, final float rightMargin, final float topMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(leftMargin * getContainerWidth());
                element.setY(bottomMargin * getContainerHeight());
                element.setWidth(getContainerWidth() - (leftMargin + rightMargin) * getContainerWidth());
                element.setHeight(getContainerHeight() - (bottomMargin + topMargin) * getContainerHeight());
                log("fill - leftMargin: {0}, bottomMargin: {1}, rightMargin: {2}, topMargin: {3}", leftMargin, bottomMargin, rightMargin, topMargin);
            }
        });
    }

    public static void fill(final Element element, final float horizontalMargin, final float verticalMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(horizontalMargin * getContainerWidth());
                element.setY(verticalMargin * getContainerHeight());
                element.setWidth(getContainerWidth() - (2 * horizontalMargin * getContainerWidth()));
                element.setHeight(getContainerHeight() - (2 * verticalMargin * getContainerHeight()));
                log("fill - horizontalMargin: {0}, verticalMargin: {1}", horizontalMargin, verticalMargin);
            }
        });
    }

    public static void fill(final Element element, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(margin * getContainerWidth());
                element.setY(margin * getContainerHeight());
                element.setWidth(getContainerWidth() - (2 * margin * getContainerWidth()));
                element.setHeight(getContainerHeight() - (2 * margin * getContainerHeight()));
                log("fill - margin: {0}", margin);
            }
        });
    }

    public static void extendUp(final Element element, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(getContainerHeight() - (element.getY() + margin * getContainerHeight()));
                log("extendUp - margin: {0}", margin);
            }
        });
    }

    public static void extendUpTo(final Element element, final float margin, final Element target) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(target.getY() - element.getY() - margin * getContainerHeight());
                log("extendUpTo - margin: {0}, target: {1}", margin, target);
            }
        });
    }

    public static void extendRight(final Element element, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setWidth(getContainerWidth() - (element.getX() + margin * getContainerWidth()));
                log("extendRight - margin: {0}", margin);
            }
        });
    }

    public static void extendRightTo(final Element element, final float margin, final Element target) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(target.getX() - element.getX() - margin * getContainerWidth());
                log("extendRightTo - margin: {0}, target: {1}", margin, target);
            }
        });
    }
}
