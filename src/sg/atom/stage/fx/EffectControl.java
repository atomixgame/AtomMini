package sg.atom.stage.fx;

import sg.atom.stage.EffectManager;
import com.google.common.base.Function;
import com.jme3.asset.AssetManager;
import com.jme3.cinematic.events.CinematicEvent;
import com.jme3.cinematic.events.CinematicEventListener;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Spawning and detaching...
 *
 * @author normenhansen
 */
public class EffectControl extends AbstractControl implements CinematicEventListener {

    private EffectManager effectManager;
    private AssetManager assetManager;
    private Effect originalEffect;
    //Timing
    float effectTime = 0;
    float lastTime = 0;
    float stepTime = 2;
    float durationTime = 1;
    float lifeTime = 2;
    boolean continuePlay = false;
    boolean removeOnEnd = true;
    boolean removeSpatialOnEnd = true;
    Function<Spatial, Void> callBackFunc;
    List<EffectEvent> events;

    public EffectControl() {
        this.events = new ArrayList<EffectEvent>();
    }

    public EffectControl(EffectManager effectManager) {
        this.effectManager = effectManager;
        this.events = new ArrayList<EffectEvent>();
        //this.assetManager = effectManager.getAssetManager();
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial); //To change body of generated methods, choose Tools | Templates.
        effectTime = 0;
        //this.effectManager.add
    }

    @Override
    protected void controlUpdate(float tpf) {
//        System.out.println("Effect update!");
        //count down
        effectTime += tpf;

        if (effectTime - lastTime > stepTime) {
            lastTime = effectTime;
        }

        if (effectTime > durationTime) {
            if (!continuePlay) {
                if (removeOnEnd) {
                    this.setEnabled(false);
                    this.spatial.removeControl(this);
                }
                if (removeSpatialOnEnd) {
                    this.spatial.removeFromParent();
                }
                onEndEffect();
            }
        }

//        System.out.println(" end size: "+((ParticleEmitter)spatial).getEndSize());
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void onPlay(CinematicEvent event) {
    }

    public void onPause(CinematicEvent event) {
    }

    public void onStop(CinematicEvent event) {
        if (events.indexOf(event) == events.size() - 1) {
            // onEndEffect():
            //effectTime = durationTime;
        }
    }

    public void onEndEffect() {
        if (callBackFunc != null) {
            callBackFunc.apply(spatial);
        }
    }

    public float getEffectTime() {
        return effectTime;
    }

    public static class Builder {

        public Builder() {
            this.item = new EffectControl();
        }
        private EffectControl item;

        public Builder withEffectManager(final EffectManager effectManager) {
            this.item.effectManager = effectManager;
            return this;
        }

        public Builder withEffectTime(final float effectTime) {
            this.item.effectTime = effectTime;
            return this;
        }

        public Builder withLastTime(final float lastTime) {
            this.item.lastTime = lastTime;
            return this;
        }

        public Builder withStepTime(final float stepTime) {
            this.item.stepTime = stepTime;
            return this;
        }

        public Builder withDurationTime(final float durationTime) {
            this.item.durationTime = durationTime;
            return this;
        }

        public Builder withLifeTime(final float lifeTime) {
            this.item.lifeTime = lifeTime;
            return this;
        }

        public Builder withRemoveOnEnd(final boolean remove) {
            this.item.removeOnEnd = remove;
            return this;
        }

        public Builder withRemoveSpatialOnEnd(final boolean remove) {
            this.item.removeSpatialOnEnd = remove;
            return this;
        }

        public Builder withContinuePlay(final boolean remove) {
            this.item.continuePlay = remove;
            return this;
        }

        public Builder withCallBackFunc(final Function<Spatial, Void> callBackFunc) {
            this.item.callBackFunc = callBackFunc;
            return this;
        }

        public Builder withEvent(final EffectEvent event) {
            this.item.events.add(event);
            return this;
        }

        public Builder withEvents(final List<EffectEvent> events) {
            this.item.events.addAll(events);
            return this;
        }

        public Builder withEvents(final EffectEvent... effectEvents) {
            this.item.events.addAll(Arrays.asList(effectEvents));
            return this;
        }

        public Builder withEvents(final Collection<EffectEvent> effectEvents) {
            this.item.events.addAll(effectEvents);
            return this;
        }

        public EffectControl build() {
            this.item.effectManager.addKeys(this.item.events);
//            System.out.println("Control Built!");
            return this.item;
        }
    }
}
