package sg.atom.corex.stage;

import com.jme3.scene.Spatial;
import sg.atom.corex.managers.StageManager;

/**
 *
 * @author DaiPhongPC
 */
public interface VirtualGameActor {

    public Spatial getModel();

    public void onStage(StageManager aThis);
    
}
