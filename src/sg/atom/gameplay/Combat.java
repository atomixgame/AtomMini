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
    private List<CommonGameCharacter> enemies;
    private int playerScore;
    private int resultScore;
    private int enviroment;
    private boolean finished;

    public Combat(GamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
//        this.combatFightGamePlay = gamePlayManager.getCombatGamePlay();
    }

    public void start(Player player, List<CommonGameCharacter> npc) {
        this.player = player;
        this.enemies = npc;
        this.combatFightGamePlay.onFightStart(this);
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

}
