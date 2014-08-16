/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.state;

import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import java.util.ArrayList;
import org.apache.commons.configuration.Configuration;
import sg.atom.gameplay.BaseGamePlay;
import sg.atom.gameplay.GameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.gameplay.Player;
import sg.atom.gameplay.Quest;
import sg.atom.gameplay.Skill;
import sg.atom.gameplay.controls.PlayerCharacterControl;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class AdventureGamePlay extends BaseGamePlay {

    private ChaseCamera chaseCam;
    private Spatial playerModel;
    private PlayerCharacterControl playerCharacterControl;
    private ArrayList<GameCharacter> characters;
    private Quest currentQuest;

    public AdventureGamePlay(GamePlayManager gamePlayManager) {
        super(gamePlayManager);
    }

    public void startGamePlay() {
        System.out.println("Start gameplay");
        setupCharacters();
        setupInput();
    }

    public void setupInput() {
        InputManager inputManager = app.getInputManager();
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharDown", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CharJump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("CharShoot", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addMapping("CharWeapon", new KeyTrigger(KeyInput.KEY_V));

        inputManager.addListener(playerCharacterControl, "CharLeft", "CharRight", "CharUp", "CharDown", "CharJump", "CharShoot", "CharWeapon");
    }

    public void setupCharacters() {
        System.out.println("Create charaters!");
        characters = new ArrayList<GameCharacter>();

        // FIXME: Simplize this?
        // Main character
        Player mainPlayer = gamePlayManager.getMainPlayer();
        playerModel = app.getWorldManager().getModel("Skai");
        GameCharacter skai = new GameCharacter("Skai", mainPlayer, playerModel);
        playerCharacterControl = new PlayerCharacterControl(getStageManager(), skai);
        mainPlayer.setCharacter(skai);
        // Other characters
        GameCharacter aerith = new GameCharacter("Aerith", mainPlayer, app.getWorldManager().getModel("Aerith"));
        new PlayerCharacterControl(app.getStageManager(), aerith);
        // Place characters
        characters.add(skai);
        characters.add(aerith);
        getStageManager().placeCharacter(skai, new Vector3f());
        getStageManager().placeCharacter(aerith, new Vector3f(1, 0, 2));
        getGamePlayManager().setupCharacters(characters);

        //Misc
        skai.setMainSkill(skai.getSkills().get(1));
        skai.getSkills().get(1).attackType = Skill.SKILL_ATTACK_CLOSE;
    }

    public PlayerCharacterControl getCharacterControl() {
        return playerCharacterControl;
    }

    public void endGamePlay() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(float tpf) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //Dialouge------------------------------------------------------------------

    public void startDialogue(GameCharacter... gameCharacters) {
    }

    //Events--------------------------------------------------------------------
    public void onTouch(Vector3f pos) {
    }

    public void onSelect(Entity entity) {
    }

    public void onSelect(GameCharacter gameCharacter) {
    }

    public void onSelect(int option) {
    }

    public void onDialogue(int option) {
    }

    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void config(Configuration props) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
