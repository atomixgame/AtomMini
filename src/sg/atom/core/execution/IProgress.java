package sg.atom.core.execution;

public interface IProgress {

    public static final float DEFAULT_RATE = 1;

    float getProgress();

    void setProgress(float progress);

    float getContributedRate();

    void addListener(IProgress.Listener listener);

    public interface Listener {

        void onProgress(float progress);
    }
}
