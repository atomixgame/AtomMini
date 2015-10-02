/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.spatial;

import com.jme3.math.Vector2f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;

/**
 *
 * @author cuong.nguyen
 */
public class ExQuad extends Mesh {

    private float width;
    private float height;
    private Vector2f origin;

    /**
     * Serialization only. Do not use.
     */
    public ExQuad() {
    }

    /**
     * Create a quad with the given width and height. The quad is always created
     * in the XY plane.
     *
     * @param width The X extent or width
     * @param height The Y extent or width
     */
    public ExQuad(float width, float height) {
        updateGeometry(Vector2f.ZERO, width, height);
    }

    /**
     * Create a quad with the given width and height. The quad is always created
     * in the XY plane.
     *
     * @param width The X extent or width
     * @param height The Y extent or width
     * @param flipCoords If true, the texture coordinates will be flipped along
     * the Y axis.
     */
    public ExQuad(float width, float height, boolean flipCoords) {
        updateGeometry(Vector2f.ZERO, width, height, flipCoords);
    }

    /**
     * Create a quad with the given width and height. The quad is always created
     * in the XY plane.
     *
     * @param origin The origin of the quad
     * @param width The X extent or width
     * @param height The Y extent or width
     */
    public ExQuad(Vector2f origin, float width, float height) {
        updateGeometry(origin, width, height);
    }

    /**
     * Create a quad with the given width and height. The quad is always created
     * in the XY plane.
     *
     * @param origin The origin of the quad
     * @param width The X extent or width
     * @param height The Y extent or width
     * @param flipCoords If true, the texture coordinates will be flipped along
     * the Y axis.
     */
    public ExQuad(Vector2f origin, float width, float height, boolean flipCoords) {
        updateGeometry(origin, width, height, flipCoords);
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public Vector2f getOrigin() {
        return origin;
    }

    private void updateGeometry(Vector2f origin, float width, float height) {
        updateGeometry(origin, width, height, false);
    }

    private void updateGeometry(Vector2f origin, float width, float height, boolean flipCoords) {
        this.origin = origin;
        this.width = width;
        this.height = height;

        setBuffer(Type.Position, 3, new float[]{origin.x, origin.y, 0,
            origin.x + width, origin.y, 0,
            origin.x + width, origin.y + height, 0,
            origin.x, origin.y + height, 0});

        if (flipCoords) {
            setBuffer(Type.TexCoord, 2, new float[]{0, 1,
                1, 1,
                1, 0,
                0, 0});
        } else {
            setBuffer(Type.TexCoord, 2, new float[]{0, 0,
                1, 0,
                1, 1,
                0, 1});
        }
        setBuffer(Type.Normal, 3, new float[]{0, 0, 1,
            0, 0, 1,
            0, 0, 1,
            0, 0, 1});

        if (height < 0) {
            setBuffer(Type.Index, 3, new short[]{0, 2, 1,
                0, 3, 2});
        } else {
            setBuffer(Type.Index, 3, new short[]{0, 1, 2,
                0, 2, 3});
        }

        updateBound();
    }
}
