/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage.story;

import com.google.common.collect.TreeTraverser;
import java.util.HashMap;
import java.util.List;
import org.jgrapht.DirectedGraph;
import sg.atom.corex.stage.GameAction;
import sg.atom.gameplay.CommonGameCharacter;

/**
 *
 * @author CuongNguyen
 */
public class Dialogue {
    String title;
    int status;
    int line;
    HashMap<String, GameAction> actionMap = new HashMap<String, GameAction>();
    List<CommonGameCharacter> characters;
    
    // Dialogue as Graph
    DirectedGraph<DialogueNode, DialogueChoice> dialogueGraph;
    TreeTraverser<String> dialogueTraverser;
    
    public void showOptions() {
    }


    public void saveXML(String path) {
    }
}
