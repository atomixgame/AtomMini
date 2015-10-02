package sg.atom.gameplay;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;
import sg.atom.gameplay.info.PlayerInfo;

/**
 * Player represent the Player Model and data.
 *
 * @author CuongNguyen
 */
public class Player implements ManagableObject {

    protected AtomMain app;
    int id;
    String uid;
    String name;
    PlayerInfo playerInfo;
    CommonGameCharacter character;
    ArrayList<CommonGameCharacter> characters;
    protected HashMap<String, Object> userData;
    protected boolean isBot = false;

    public Player(AtomMain app, String name) {
        this.app = app;
        this.name = name;
        this.uid = name;
    }

    public Player(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public Player(String name) {
        this.name = name;
        this.uid = "";
    }

    public CommonGameCharacter getMainCharacter() {
        return character;
    }

    public void setCharacter(CommonGameCharacter character) {
        this.character = character;
        character.isNpc = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CommonGameCharacter> getCharacters() {
        return characters;
    }

    public List<CommonGameCharacter> getCombatCharacters() {
        return characters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void init(Application app) {
        this.app = (AtomMain) app;
        this.characters = new ArrayList<CommonGameCharacter>();
        if (this.playerInfo == null) {
            playerInfo = new PlayerInfo(id, name, null);
        }
    }

    public void initManagers(IGameCycle... managers) {
    }

    public void load(AssetManager assetManager) {
    }

    public void config(Configuration configuration) {
    }

    public void update(float tpf) {
    }

    public void finish() {
    }

    public AtomMain getApp() {
        return app;
    }

    public HashMap<String, Object> getUserData() {
        return userData;
    }

    public boolean isBot() {
        return isBot;
    }
}
