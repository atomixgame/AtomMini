/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage.fx;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Arrays;
import java.util.Collection;

/**
 * Effect.
 *
 * @author CuongNguyen
 */
public class ComposableSpatialEffect extends Node {

    public int id;
    public String name;
    public int status;
    private EffectControl control;
    private Spatial spatial;

    public ComposableSpatialEffect() {
        this.control = new EffectControl();
    }

    public Spatial getSpatial() {
        return spatial;
    }

    public int getStatus() {
        return status;
    }

    public EffectControl getControl() {
        return control;
    }

    public static class Builder {

        public Builder() {
            this.item = new ComposableSpatialEffect();
        }
        private ComposableSpatialEffect item;

        public Builder withControl(final EffectControl control) {
            this.item.control = control;
            return this;
        }

        public Builder withSpatial(final Spatial spatial) {
            this.item.spatial = spatial;
            return this;
        }

        public Builder key(final EffectEvent effectEvent) {
            this.item.control.events.add(effectEvent);
            return this;
        }

        public Builder keys(final EffectEvent... effectEvents) {
            this.item.control.events.addAll(Arrays.asList(effectEvents));
            return this;
        }

        public Builder keys(final Collection<EffectEvent> effectEvents) {
            this.item.control.events.addAll(effectEvents);
            return this;
        }

        public ComposableSpatialEffect build() {
            return this.item;
        }
    }
}
