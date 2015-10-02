package sg.atom.ai;

import java.util.List;

/**
 * Has abilities, aweaness, and events.
 *
 * @author CuongNguyen
 */
public interface Agent {

    long getId();

    List<Behaviour> getBehaviours();

    void update(float tpf);

    void react(Object signal);

}
