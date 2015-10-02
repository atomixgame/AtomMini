package sg.atom.ai;

import com.jme3.scene.control.Control;
import sg.atom.corex.logic.Trigger;

/**
 * Exposed signals, become input signals for interaction between Agents.
 *
 * @author cuong.nguyen
 */
public interface Behaviour extends Trigger<Agent> {

    Control getControl();
}
