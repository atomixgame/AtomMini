/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.fx;

import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Primitives;
import com.google.common.reflect.TypeToken;
import com.jme3.cinematic.events.AbstractCinematicEvent;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sg.atom.corex.math.Interpolator;
import static sg.atom.corex.math.Tweenf.*;
import sg.atom.stage.fx.effectors.ReflectionEffector;
import sg.atom.stage.fx.interpolators.NullInterpolator;

/**
 * EffectEvent is keyframe for effects.
 *
 * @author cuong.nguyenmanh2
 */
public class EffectEvent<T> extends AbstractCinematicEvent {

    public static int EFFECT_STOPED = 0;
    public static int EFFECT_PAUSED = 1;
    public static int EFFECT_STARTED = 2;
    static Interpolator defaultConverter = new NullInterpolator(LINEAR);
    static ReflectionEffector defaultEffector = new ReflectionEffector();
    float startTime;
    T startValue;
    T endValue;
    boolean selfAnimated = true;
    float eventDuration;
    Easing easing = LINEAR;
    String propertyName;
    Spatial effect;
    EffectControl effectControl;
    Interpolator<T> interpolator;
    boolean useReflection = true;
    Effector effector;
    TypeToken type;
    TypeToken ownerType;
    int status;

    public EffectEvent() {
        super(1);
//        this.startValue = 0;
//        this.endValue = 0;
//        this.changeValue = 0;
        this.startTime = 0;
        this.eventDuration = 1;
        this.easing = LINEAR;
        this.effect = null;
        this.interpolator = null;
        this.propertyName = "";
    }

    private EffectEvent(EffectEvent<T> item) {
        super(item.eventDuration);
        this.startValue = item.startValue;
        this.endValue = item.endValue;
//        this.changeValue = item.changeValue;
        this.eventDuration = item.eventDuration;
        this.easing = item.easing;
        this.effect = item.effect;
        this.interpolator = item.interpolator;
        this.propertyName = item.propertyName;
    }

    @Override
    protected void onPlay() {
        this.status = EFFECT_STARTED;
        this.effectControl = effect.getControl(EffectControl.class);
        //this.effectControl.
    }

    @Override
    protected void onUpdate(float tpf) {
        Object value;

        if (this.status == EFFECT_STARTED) {
            if (selfAnimated) {
                if (interpolator == null) {
                    value = defaultConverter.interpolate(startValue, endValue, time / eventDuration);
                } else {
                    value = interpolator.interpolate(startValue, endValue, time / eventDuration);
                }
                //FIXME: Use effector instead.
//            System.out.println(" Event effect: " + value);
                if (useReflection) {
                    defaultEffector.affect(effect, propertyName, value);
                } else {

                    effector.affect(effect, propertyName, value);
                }
            } else {
            }
        }
    }

    @Override
    protected void onStop() {
        this.status = EFFECT_STOPED;

    }

    @Override
    public void onPause() {
        this.status = EFFECT_PAUSED;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(propertyName)
                .append(startTime)
                .append(eventDuration)
                .append(startValue)
                .append(endValue)
                //.append(type.toString())
                //.append(changeValue);
                .toString();
    }

    public float getStartTime() {
        return startTime;
    }

    public T getStartValue() {
        return startValue;
    }

    public int getStatus() {
        return status;
    }

    public static class Builder<T> {

        public int num;
        private List<T> values;
        private List<Float> eachs;
        private List<Float> durations;
        private List<Easing> easings;

        public Builder() {
            this.item = new EffectEvent();
        }
        private EffectEvent item;

        public Builder withStartValue(final T startValue) {
            this.item.startValue = startValue;
            return this;
        }

        public Builder withEndValue(final T endValue) {
            this.item.endValue = endValue;
            return this;
        }

        public Builder withDuration(final float duration) {
            this.item.eventDuration = duration;
            return this;
        }

        public Builder withEasing(final Easing easing) {
            this.item.easing = easing;
            return this;
        }

        public Builder withPropertyName(final String propertyName) {
            this.item.propertyName = propertyName;
            return this;
        }

        public Builder withEffect(final Spatial effect) {
            this.item.effect = effect;
            return this;
        }

        public Builder withType(final TypeToken<T> type) {
            this.item.type = type;
            return this;
        }

        public Builder withConverter(final Interpolator<T> converter) {
            this.item.interpolator = converter;
            return this;
        }

        public Builder withStartTime(float startTime) {
            this.item.startTime = startTime;
            return this;
        }

        public EffectEvent build() {
            EffectEvent newEvent = item;
            //newEvent.changeValue = (newEvent.endValue - newEvent.startValue) / newEvent.duration;
            return this.item;
        }

        public Builder withMultiValue(T... values) {
            if (Primitives.isWrapperType(values.getClass())) {
                List floatList = Lists.newArrayList();
                for (T v : values) {
                    floatList.add((Float) v);
                }
                this.values = floatList;
            } else {
                this.values = Arrays.asList(values);
            }
            return this;
        }

        public Builder withMultiDuration(float... durations) {
            this.durations = Floats.asList(durations);
            return this;
        }

        public Builder withMultiEasing(Easing... easings) {
            this.easings = Arrays.asList(easings);
            return this;
        }

        public List<EffectEvent> buildAll() {
            this.num = values.size();
//            System.out.println("Num of effect events :" + num);
            if (values != null && num > 1) {
                ArrayList<EffectEvent> events = new ArrayList<EffectEvent>(num);
                if ((durations != null) && (durations.size() != num - 1)
                        || (easings != null) && (easings.size() != num - 1)) {
                    throw new IllegalStateException("Different number of items: Duration, Easing should has one less item than values!");
                }
                float startTimeNow = item.startTime;

                for (int i = 0; i < num - 1; i++) {
                    EffectEvent newEvent = new EffectEvent(item);

                    newEvent.startValue = values.get(i);
                    newEvent.endValue = values.get(i + 1);
                    newEvent.eventDuration = (durations != null) ? durations.get(i) : item.eventDuration;
                    newEvent.easing = (easings != null) ? easings.get(i) : item.easing;
                    newEvent.startTime = startTimeNow;
                    //newEvent.converter.setE
                    //newEvent.type = TypeToken.of();
                    startTimeNow += newEvent.eventDuration;
                    newEvent.initialDuration = newEvent.eventDuration;
                    events.add(newEvent);

//                    System.out.println(" " + newEvent);
                }

                return events;
            } else {
                throw new IllegalStateException("Build All should has more than one time!");
            }

        }
    }
}
