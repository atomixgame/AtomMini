/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.ui.tonegod.layouts.old;

/**
 *
 * @author cuong.nguyenmanh2
 */
import com.jme3.math.Vector2f;

import tonegod.gui.core.Element;

public class ScaleLayout {

    public static void position(final Element element, final Vector2f scale, final float left, final float bottom) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(left * scale.x);
                element.setY(bottom * scale.y);
                log("position - scale: {0}, left: {1}, bottom: {2}", left, bottom);
            }
        });
    }

    public static void left(final Element element, final Vector2f scale, final float left) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(left * scale.x);
                log("left - scale: {0}, left: {1}", scale, left);
            }
        });
    }

    public static void bottom(final Element element, final Vector2f scale, final float bottom) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setY(bottom * scale.y);
                log("bottom - scale: {0}, bottom: {1}", scale, bottom);
            }
        });
    }

    public static void dimensions(final Element element, final Vector2f scale, final float width, final float height) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setWidth(width * scale.x);
                element.setHeight(height * scale.y);
                log("dimensions - scale: {0}, width: {1}, height: {2}", scale, width, height);
            }
        });
    }

    public static void width(final Element element, final Vector2f scale, final float width) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setWidth(width * scale.x);
                log("width - scale: {0}, width: {1}", scale, width);
            }
        });
    }

    public static void height(final Element element, final Vector2f scale, final float height) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(height * scale.y);
                log("height - scale: {0}, height: {1}", scale, height);
            }
        });
    }

    public static void fillWidth(final Element element, final Vector2f scale, final float leftMargin, final float rightMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(leftMargin * scale.x);
                element.setWidth(getContainerWidth() - ((leftMargin + rightMargin) * scale.x));
                log("fillWidth - scale: {0}, leftMargin: {1}, rightMargin: {2}", scale, leftMargin, rightMargin);
            }
        });
    }

    public static void fillWidth(final Element element, final Vector2f scale, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(margin * scale.x);
                element.setWidth(getContainerWidth() - (2 * margin * scale.x));
                log("fillWidth - scale: {0}, margin: {1}", scale, margin);
            }
        });
    }

    public static void fillHeight(final Element element, final Vector2f scale, final float bottomMargin, final float topMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setY(bottomMargin * scale.y);
                element.setHeight(getContainerHeight() - ((bottomMargin + topMargin) * scale.y));
                log("fillHeight - scale: {0}, bottomMargin: {1}, topMaring: {2}", scale, bottomMargin, topMargin);
            }
        });
    }

    public static void fillHeight(final Element element, final Vector2f scale, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setY(margin * scale.y);
                element.setHeight(getContainerHeight() - (2 * margin * scale.y));
                log("fillHeight - scale: {0}, margin: {1}", scale, margin);
            }
        });
    }

    public static void fill(final Element element, final Vector2f scale, final float leftMargin, final float bottomMargin, final float rightMargin, final float topMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(leftMargin * scale.x);
                element.setY(bottomMargin * scale.y);
                element.setWidth(getContainerWidth() - (leftMargin + rightMargin) * scale.x);
                element.setHeight(getContainerHeight() - (bottomMargin + topMargin) * scale.y);
                log("fill - scale: {0}, leftMargin: {1}, bottomMargin: {2}, rightMargin: {3}, topMargin: {4}", scale, leftMargin, bottomMargin, rightMargin, topMargin);
            }
        });
    }

    public static void fill(final Element element, final Vector2f scale, final float horizontalMargin, final float verticalMargin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(horizontalMargin * scale.x);
                element.setY(verticalMargin * scale.y);
                element.setWidth(getContainerWidth() - (2 * horizontalMargin * scale.x));
                element.setHeight(getContainerHeight() - (2 * verticalMargin * scale.y));
                log("fill - scale: {0}, horizontalMargin: {1}, verticalMargin: {2}", scale, horizontalMargin, verticalMargin);
            }
        });
    }

    public static void fill(final Element element, final Vector2f scale, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setX(margin * scale.x);
                element.setY(margin * scale.y);
                element.setWidth(getContainerWidth() - (2 * margin * scale.x));
                element.setHeight(getContainerHeight() - (2 * margin * scale.y));
                log("fill - scale: {0}, margin: {1}", scale, margin);
            }
        });
    }

    public static void extendUp(final Element element, final Vector2f scale, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(getContainerHeight() - (element.getY() + margin * scale.y));
                log("extendUp - scale: {0}, margin: {1}", scale, margin);
            }
        });
    }

    public static void extendUpTo(final Element element, final Vector2f scale, final float margin, final Element target) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(target.getY() - element.getY() - margin * scale.y);
                log("extendUpTo - scale: {0}, margin: {1}, target: {2}", scale, margin, target);
            }
        });
    }

    public static void extendRight(final Element element, final Vector2f scale, final float margin) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setWidth(getContainerWidth() - (element.getX() + margin * scale.x));
                log("extendRight - scale: {0}, margin: {1}", scale, margin);
            }
        });
    }

    public static void extendRightTo(final Element element, final Vector2f scale, final float margin, final Element target) {
        element.addControl(
                new LayoutHelper() {
            @Override
            public void afterParent() {
                element.setHeight(target.getX() - element.getX() - margin * scale.x);
                log("extendRightTo - scale: {0}, margin: {1}, target: {2}", scale, margin, target);
            }
        });
    }
}
