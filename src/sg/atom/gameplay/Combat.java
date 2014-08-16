package sg.atom.gameplay;

import sg.atom.gameplay.state.CombatFightGamePlay;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

/**
 * Combat is a predesigned match between player team and enemies or two players.
 * 
 * @author CuongNguyen
 */
public class Combat {

    private GamePlayManager gamePlayManager;
    private CombatFightGamePlay combatFightGamePlay;
    private Player player;
    private List<GameCharacter> enemies;
    private int playerScore;
    private int resultScore;
    private int enviroment;
    private boolean finished;
    
    public Combat(GamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
//        this.combatFightGamePlay = gamePlayManager.getCombatGamePlay();
    }

    public void start(Player player, List<GameCharacter> npc) {
        this.player = player;
        this.enemies = npc;
        this.combatFightGamePlay.onFightStart(this);
        this.finished = false;
    }

    public void update(float tpf) {
    }

    public void end(boolean playerWin) {
        this.finished = true;
        System.out.println("Combat end "+playerWin);
    }

    public List<GameCharacter> getCharacters() {
        ArrayList<GameCharacter> result = Lists.newArrayList(getPlayerCharacters());
        result.addAll(enemies);
        return result;
    }

    public List<GameCharacter> getPlayerCharacters() {
        return player.getCombatCharacters();
    }

    public List<GameCharacter> getNonPlayerCharacters() {
        return enemies;
    }

    public List<GameCharacter> getEnemies() {
        return enemies;
    }

    public boolean isDead(GameCharacter pc) {
        return pc.isDead();
    }

    boolean isBanned(GameCharacter pc) {
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
    
    
}
