/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.map;

/**
 *
 * @author CuongNguyen
 */
public interface MapChangeListener {

    void regionActive(int id);
    
    void regionAdded(int id);

    void regionRemoved(int id);
    
}
