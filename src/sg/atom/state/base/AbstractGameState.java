/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.state.base;

import com.jme3.app.state.AbstractAppState;

/**
 *
 * @author CuongNguyen
 */
public abstract class AbstractGameState extends AbstractAppState {

    int id;
    int type;
    int[] dependencies;
    boolean uiBound;
    int transistionType;
}
