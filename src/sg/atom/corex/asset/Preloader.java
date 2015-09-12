package sg.atom.corex.asset;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.core.execution.IProgress;

/**
 * Multi-mainThread preloader load asset in paralel. The preloader can depend in
 * other preloader, the tree is called recusively. In the loading progress it
 * handle progresses automaticly. After finish it offer a Runnable callback.
 *
 * <p>Usage:
 *
 * <p>
 * <code>
 * add(IPreload);
 * start();
 * ...
 * join();
 * </code>
 *
 * <p>FIXME: This implementation is pretty raw. Should be replace with Cache and
 * Executor.
 *
 * @author mulova, atomix
 *
 */
@Deprecated
public final class Preloader implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Preloader.class);
    String name;
    Thread mainThread;
    //IErrorHandler errorHandler;
    IPreload current;
    final ArrayList<IPreload> pool; //FIXME: Replace with a real Pool!
    final List<Preloader> prerequisites = new LinkedList<Preloader>();
    //Timing & Progress
    final ArrayList<Float> progressContribution;
    float currentContribution;
    long yieldTime;
    IProgress progress;
    int priority = Thread.NORM_PRIORITY;

    public Preloader(String name) {
        this.name = name;
        this.pool = new ArrayList<IPreload>();
        this.progressContribution = new ArrayList<Float>();
    }

    public void add(IPreload loader, float rate) {
        if (loader == null) {
            return;
        }
        pool.add(loader);
        progressContribution.add(rate);
    }

    /**
     * start thread
     */
    public void start() {
        if (this.mainThread != null && this.mainThread.isAlive()) {
            return;
        }
        this.mainThread = new Thread(this);
        this.mainThread.setName(name);
        this.mainThread.setPriority(priority);
        this.mainThread.start();
    }

    public void setPriority(int priority) {
        this.priority = priority;
        if (mainThread != null) {
            mainThread.setPriority(priority);
        }
    }

    public void interrupt() {
        if (this.mainThread != null) {
            this.mainThread.interrupt();
        }
    }

    public boolean isAlive() {
        return mainThread.isAlive();
    }

    /**
     * @param pre extenal depended preloader
     */
    public void addPrerequisite(Preloader pre) {
        this.prerequisites.add(pre);
    }

    public void join() {
        try {
            if (this.mainThread != null) {
                this.mainThread.join();
            } else {
                start();
                this.mainThread.join();
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }
    /*
     public void setErrorHandler(IErrorHandler handler) {
     errorHandler = handler;
     }
     */

    public void setProgress(IProgress progress) {
        if (progress != null) {
            this.progress = progress;
        } else {
//            this.progress = new NullProgress();
        }
        if (current != null) {
            current.setProgress(progress);
//            if (progress instanceof AtomicProgress) {
//                ((AtomicProgress) progress).setEnd(currentContribution);
//            }
        }
    }

    @Override
    public void run() {
        for (Preloader p : prerequisites) {
            p.join();
        }
        for (int i = 0; i < pool.size(); i++) {
            try {
                current = pool.get(i);
                currentContribution = progressContribution.get(i);
//                log.info(current.getName(), " loading start...");
                float time = System.currentTimeMillis();
                setProgress(progress);
                current.preload();
//                log.info("{} loading ends. {} msec", current.getName(), System.currentTimeMillis() - time);
                if (Thread.interrupted()) {
                    break;
                }
            } catch (Throwable t) {
                /*
                 if (errorHandler != null) {
                 errorHandler.handleError(t);
                 }
                 */
//                log.warn("Preloading fails", t);
            }
        }
        mainThread = null;
    }

    @Override
    public String toString() {
        return name;
    }
}