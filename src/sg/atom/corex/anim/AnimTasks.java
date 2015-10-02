package sg.atom.corex.anim;


/**
 *
 *  @author    Paul Speed
 */
public class AnimTasks {
 
 /*
    Seems like an interpolator works better in many cases
    and can be curved, reversed, etc....
       
    public static Task playAnimation( double duration, Spatial target, String name, int channel, double speed ) {
        return new PlayAnimationTask(target, name, channel, speed, duration);       
    }
    
    private static class PlayAnimationTask implements Task {
    
        private Spatial target;
        private AnimControl anim;        
        private String name;
        private int channel;
        private double speed;
        private double duration;
        private double time;
        private AnimChannel chan;
                
        public PlayAnimationTask( Spatial target, String name, int channel, 
                                  double speed, double duration ) {
            this.target = target;
            this.name = name;
            this.channel = channel;
            this.speed = speed;
            this.duration = duration;
            
            this.anim = target.getControl(AnimControl.class);
            if( anim == null ) {
                throw new IllegalArgumentException("Target spatial has no AnimControl");
            }
        }

        private AnimChannel getChannel() {
            if( chan != null ) {
                return chan;
            }
            chan = anim.getChannel(channel);
            if( chan != null ) {
                return chan;
            }
            chan = anim.createChannel();
            return chan;
        }

        public TaskStatus execute( double tpf ) {
                            
            time += tpf;
            AnimChannel c = getChannel();
            
            if( time >= duration ) {
                // Stop
                c.reset(false);
                return TaskStatus.Done;
            } else {
                // Keep going
                if( !name.equals(c.getAnimationName()) ) {
                    c.setAnim(name);
                }
                double relativeTime = time % c.getAnimMaxTime();
                c.setTime((float)relativeTime);                
                return TaskStatus.Continue;
            }                      
        }

        public void pausing() {
            AnimChannel c = getChannel();
            c.reset(false);
        }

        public void stopping() {
            AnimChannel c = getChannel();
            c.reset(true);
            time = 0;
        }
        
        @Override
        public String toString() {
            return "PlayAnimationTask[name=" + name + ", channel=" + channel + ", speed=" + speed + "]";
        }
    }*/
    
}
