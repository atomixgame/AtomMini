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

public class PixelLayout {

    public static void position(final Element element, final float left, final float bottom) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(left);
                element.setY(bottom);
                log("position - left: {0}, bottom: {1}", left, bottom);
            }
        });
    }

    public static void left(final Element element, final float left) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(left);
                log("left: {0}", left);
            }
        });
    }

    public static void bottom(final Element element, final float bottom) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setY(bottom);
                log("bottom: {0}", bottom);
            }
        });
    }

    public static void dimensions(final Element element, final float width, final float height) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setWidth(width);
                element.setHeight(height);
                log("dimensions - width: {0}, height: {1}", width, height);
            }
        });
    }

    public static void width(final Element element, final float width) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setWidth(width);
                log("width: {0}", width);
            }
        });
    }

    public static void height(final Element element, final float height) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(height);
                log("height: {0}", height);
            }
        });
    }

    public static void fillWidth(final Element element, final float leftMargin, final float rightMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(leftMargin);
                element.setWidth(getContainerWidth() - (leftMargin + rightMargin));
                log("fillWidth - leftMargin: {0}, rightMargin: {1}", leftMargin, rightMargin);
            }
        });
    }

    public static void fillWidth(final Element element, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(margin);
                element.setWidth(getContainerWidth() - (2 * margin));
                log("fillWidth - margin: {0}", margin);
            }
        });
    }

    public static void fillHeight(final Element element, final float bottomMargin, final float topMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setY(bottomMargin);
                element.setHeight(getContainerHeight() - (bottomMargin + topMargin));
                log("fillHeight - bottomMargin: {0}, topMaring: {1}", bottomMargin, topMargin);
            }
        });
    }

    public static void fillHeight(final Element element, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setY(margin);
                element.setHeight(getContainerHeight() - (2 * margin));
                log("fillHeight - margin: {0}", margin);
            }
        });
    }

    public static void fill(final Element element, final float leftMargin, final float bottomMargin, final float rightMargin, final float topMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(leftMargin);
                element.setY(bottomMargin);
                element.setWidth(getContainerWidth() - (leftMargin + rightMargin));
                element.setHeight(getContainerHeight() - (bottomMargin + topMargin));
                log("fill - leftMargin: {0}, bottomMargin: {1}, rightMargin: {2}, topMargin: {3}", leftMargin, bottomMargin, rightMargin, topMargin);
            }
        });
    }

    public static void fill(final Element element, final float horizontalMargin, final float verticalMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(horizontalMargin);
                element.setY(verticalMargin);
                element.setWidth(getContainerWidth() - (2 * horizontalMargin));
                element.setHeight(getContainerHeight() - (2 * verticalMargin));
                log("fill - horizontalMargin: {0}, verticalMargin: {1}", horizontalMargin, verticalMargin);
            }
        });
    }

    public static void fill(final Element element, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(margin);
                element.setY(margin);
                element.setWidth(getContainerWidth() - (2 * margin));
                element.setHeight(getContainerHeight() - (2 * margin));
                log("fill - margin: {0}", margin);
            }
        });
    }

    public static void extendUp(final Element element, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(getContainerHeight() - (element.getY() + margin));
                log("extendUp - margin: {0}", margin);
            }
        });
    }

    public static void extendUpTo(final Element element, final float margin, final Element target) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(target.getY() - element.getY() - margin);
                log("extendUpTo - margin: {0}, target: {1}", margin, target);
            }
        });
    }

    public static void extendRight(final Element element, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setWidth(getContainerWidth() - (element.getX() + margin));
                log("extendRight - margin: {0}", margin);
            }
        });
    }

    public static void extendRightTo(final Element element, final float margin, final Element target) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(target.getX() - element.getX() - margin);
                log("extendRightTo - margin: {0}, target: {1}", margin, target);
            }
        });
    }

    public static void centerVertical(final Element element) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setY((getContainerHeight() / 2) - (element.getHeight() / 2));
                log("centerVertical");
            }
        });
    }

    public static void centerHorizontal(final Element element) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX((getContainerWidth() / 2) - (element.getWidth() / 2));
                log("centerVertical");
            }
        });
    }
}
