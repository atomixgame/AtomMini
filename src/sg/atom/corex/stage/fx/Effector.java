/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage.fx;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface Effector<T> {

    void affect(T object, String propertyName, Object value);
}
