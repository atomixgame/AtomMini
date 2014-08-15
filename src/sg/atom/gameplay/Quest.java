/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import java.util.ArrayList;
import java.util.List;
import sg.atom.corex.logic.Condition;

/**
 *
 * @author CuongNguyen
 */
public class Quest {

    int id;
    String title;
    String description;
    String cinematicName;
    String scene;
    int status;
    boolean finished;
    List<Condition<Quest>> questChecks;

    public Quest(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questChecks = new ArrayList<Condition<Quest>>();
    }
    public void takeQuest(GameCharacter gameCharacter){
        
    }
    public void addCheck(Condition<Quest> check){
        this.questChecks.add(check);
    }

    public List<Condition<Quest>> getQuestChecks() {
        return questChecks;
    }
    
    public void onStart(Player player){
        
    }
    
    public void oneEnd(){
        
    }
}
