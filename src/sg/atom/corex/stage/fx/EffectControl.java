package sg.atom.corex.stage.fx;

import sg.atom.corex.managers.EffectManager;
import com.google.common.base.Function;
import com.jme3.cinematic.KeyFrame;
import com.jme3.cinematic.TimeLine;
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
 * @author cuongnguyen
 */
public class EffectControl extends AbstractControl implements CinematicEventListener {

    private EffectManager effectManager;
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
    private boolean selfTiming = false;
    private boolean started = false;
    private boolean startImmediately = true;
    protected TimeLine timeLine;
    private int lastFetchedKeyFrame = -1;

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
        super.setSpatial(spatial);
        if (spatial != null) {
        }
        if (startImmediately) {
            start();
        }
    }

    public void start() {
        System.out.println("Start effect!");
        if (this.effectManager == null) {
            this.selfTiming = true;
            this.timeLine = new TimeLine();
            for (EffectEvent ee : events) {
                addEffectEvent(ee.getStartTime(), ee);
            }
        } else {
            this.effectManager.addKeys(this.events);
        }
        effectTime = 0;
        this.started = true;
    }

    public KeyFrame addEffectEvent(float timeStamp, EffectEvent cinematicEvent) {
        KeyFrame keyFrame = timeLine.getKeyFrameAtTime(timeStamp);
        if (keyFrame == null) {
            keyFrame = new KeyFrame();
            timeLine.addKeyFrameAtTime(timeStamp, keyFrame);
        }
        keyFrame.getCinematicEvents().add(cinematicEvent);

        return keyFrame;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (started) {
            effectTime += tpf;

            if (effectTime - lastTime > stepTime) {
                lastTime = effectTime;
            }

            if (selfTiming) {
                updateSelfEvents(tpf);
            }
            if (effectTime > durationTime) {
                if (!continuePlay) {
                    if (removeSpatialOnEnd) {
                        this.spatial.removeFromParent();
                    }
                    if (removeOnEnd) {
                        this.setEnabled(false);
                        this.spatial.removeControl(this);
                        this.dispose();
                    }
                    onEndEffect();
                }
            }
        } else {
            
        }
    }

    void updateSelfEvents(float tpf) {
        for (int i = 0; i < events.size(); i++) {
            CinematicEvent ce = events.get(i);
            ce.internalUpdate(tpf);
        }

        int keyFrameIndex = timeLine.getKeyFrameIndexFromTime(effectTime);

        //iterate to make sure every key frame is triggered
        for (int i = lastFetchedKeyFrame + 1; i <= keyFrameIndex; i++) {
            KeyFrame keyFrame = timeLine.get(i);
            if (keyFrame != null) {
                keyFrame.trigger();
            }
        }

        lastFetchedKeyFrame = keyFrameIndex;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void onPlay(CinematicEvent event) {
    }

    public void onPause(CinematicEvent event) {
    }

    public void onStop(CinematicEvent event) {
//        if (events.indexOf(event) == events.size() - 1) {
        // onEndEffect():
        //effectTime = durationTime;
//        }
    }

    public void onEndEffect() {
        if (callBackFunc != null) {
            callBackFunc.apply(spatial);
        }
    }

    public float getEffectTime() {
        return effectTime;
    }

    public float getDurationTime() {
        return durationTime;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public float getStepTime() {
        return stepTime;
    }

    public boolean isStartImmediately() {
        return startImmediately;
    }

    public boolean isContinuePlay() {
        return continuePlay;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isSelfTiming() {
        return selfTiming;
    }

    private void dispose() {
        for (CinematicEvent event : events) {
            event.dispose();
        }
        events.clear();
        timeLine.clear();
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

        public Builder withStartImmediately(final boolean state) {
            this.item.startImmediately = state;
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
            for (EffectEvent e : events) {
                e.effectControl = this.item;
            }
            return this;
        }

        public Builder withEvents(final EffectEvent... effectEvents) {
            withEvents(Arrays.asList(effectEvents));
            return this;
        }

        public Builder withEvents(final Collection<EffectEvent> effectEvents) {
            withEvents(effectEvents);
            return this;
        }

        public EffectControl build() {
            return this.item;
        }
    }
}
