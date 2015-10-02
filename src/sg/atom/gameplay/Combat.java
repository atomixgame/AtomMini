package sg.atom.gameplay;

import com.google.common.collect.Lists;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;

/**
 * Combat is a predesigned match between player team and enemies or two players.
 *
 * @author CuongNguyen
 */
public class Combat implements ManagableObject {

    protected AtomMain app;
    protected GamePlayManager gamePlayManager;
    protected Player player;
    protected List<CommonGameCharacter> enemies;
    protected int playerScore;
    protected int resultScore;
    protected int enviroment;
    protected boolean finished;

    public Combat() {
    }

    public Combat(GamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
//        this.combatFightGamePlay = gamePlayManager.getCombatGamePlay();
    }

    public void init(Application app) {
        this.app = (AtomMain) app;
    }

    public void start(Player player, List<CommonGameCharacter> npc) {
        this.player = player;
        this.enemies = npc;
        this.finished = false;
    }

    public void update(float tpf) {
    }

    public void end(boolean playerWin) {
        this.finished = true;
    }

    public List<CommonGameCharacter> getCharacters() {
        ArrayList<CommonGameCharacter> result = Lists.newArrayList(getPlayerCharacters());
        result.addAll(enemies);
        return result;
    }

    public List<CommonGameCharacter> getPlayerCharacters() {
        return player.getCombatCharacters();
    }

    public List<CommonGameCharacter> getNonPlayerCharacters() {
        return enemies;
    }

    public List<CommonGameCharacter> getEnemies() {
        return enemies;
    }

    public boolean isDead(CommonGameCharacter pc) {
        return pc.isDead();
    }

    boolean isBanned(CommonGameCharacter pc) {
        return false;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getResultScore() {
        return resultScore;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isFinished() {
        return finished;
    }

    public void initManagers(IGameCycle... managers) {
    }

    public void load(AssetManager assetManager) {
    }

    public void config(Configuration configuration) {
    }

    public void finish() {
    }

}
