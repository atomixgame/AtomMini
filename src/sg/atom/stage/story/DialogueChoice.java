/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.story;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class DialogueChoice extends DialogueNode {

    List<DialogueNode> options = new ArrayList<DialogueNode>();
    
    public List<DialogueNode> getOptions() {
        return options;
    }

    public void setOptions(List<DialogueNode> options) {
        this.options = options;
    }

    public void addOption(DialogueNode node) {
        options.add(node);
    }

    public void removeOptions(DialogueNode node) {
        options.remove(node);
    }

    public int getNumOfOptions() {
        return options.size();
    }
}
