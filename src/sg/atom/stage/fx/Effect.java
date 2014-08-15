/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.fx;

import com.jme3.scene.Spatial;
import java.util.Arrays;
import java.util.Collection;

/**
 * Effect.
 *
 * @author CuongNguyen
 */
public class Effect {

    public int id;
    public String name;
    public int status;
    private EffectControl control;
    private Spatial spatial;

    public Effect() {
        this.control = new EffectControl();
    }

    public static class Builder {

        public Builder() {
            this.item = new Effect();
        }
        private Effect item;

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

        public Effect build() {
            return this.item;
        }
    }
}
