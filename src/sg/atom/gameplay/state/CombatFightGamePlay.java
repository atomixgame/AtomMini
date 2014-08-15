package sg.atom.gameplay.state;

import com.google.common.base.Predicate;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import sg.atom.gameplay.BaseGamePlay;
import sg.atom.gameplay.Combat;
import sg.atom.gameplay.GameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.stage.StageManager;
import sg.atom.state.FightState;

/**
 *
 * @author CuongNguyen
 */
public class CombatFightGamePlay extends BaseGamePlay {

    private FightState fightState;
    private List<Vector3f> stageConner; // Left Top first!
    private Combat currentCombat;
    public static float stageSizeX = 20;
    public static float stageSizeY = 12;
    public static int stageSeparateSize = 5;
    public static int numOfRank = 3;
    public static float pcTurnRefreshRate = 1;
    public static float npcTurnRefreshRate = 1.5f;
    //private Turn currentTurn;
    private boolean automaticFight;

    public CombatFightGamePlay(GamePlayManager gamePlayManager) {
        super(gamePlayManager);
        //this.currentTurn = new Turn();
        this.automaticFight = true;
    }

    public void startGamePlay() {
    }

    public void onFightStart(Combat combat) {
        this.fightState = stageManager.getApp().getStateManager().getState(FightState.class);
        //this.stageConner = fightState.getStageConner();
        this.currentCombat = combat;
        setupCharacter();
        for (GameCharacter gc : currentCombat.getCharacters()) {
            gc.onFightStart(currentCombat);
        }
    }

    public void onFightEnd() {
    }

    public void setupCharacter() {
        //Layout
        layout(currentCombat.getPlayerCharacters(), false);
        layout(currentCombat.getEnemies(), true);
        getGamePlayManager().setupCharacters(currentCombat.getCharacters());

        //Scheduled skills

        //Enviromental


        //Banned skill
    }

    public void setupInput() {
    }

//    public void layout(GameCharacter gameCharacter) {
//    }
//
//    public void layout(List<GameCharacter> characters) {
//    }
    public void layout(List<GameCharacter> characters, boolean isNPC) {

        float direction = isNPC ? 1 : -1;
        for (int rank = 0; rank < numOfRank; rank++) {
            List<GameCharacter> charactersInRank = getBy(characters, GameCharacter.byRank(rank));

            for (GameCharacter gc : charactersInRank) {
                int row = charactersInRank.indexOf(gc);
                int numOfRow = charactersInRank.size();

                Spatial model = gc.getModel();
                float cx = (stageSeparateSize / 2 + stageSizeX / 2 / numOfRank * rank) * -direction;
                float cz = (-stageSizeY / 2) + stageSizeY / numOfRow * row;
                model.setLocalTranslation(cx, 0, cz);
                model.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.PI * direction, Vector3f.UNIT_Y));
                stageManager.placeCharacter(gc);

                // Increase the delay
                if (gc.isNpc()) {
                    gc.setTurnDelayMax( gc.getTurnDelayMax() + row);
                }
            }
        }
    }

    public void endGamePlay() {
    }

    public List<GameCharacter> getBy(Predicate<GameCharacter>... filters) {
        return GameCharacter.getBy(currentCombat.getCharacters(), filters);
    }

    public List<GameCharacter> getBy(List<GameCharacter> list, Predicate<GameCharacter>... filters) {
        return GameCharacter.getBy(list, filters);
    }

    public void update(float tpf) {
        if (currentCombat != null && !currentCombat.isFinished()) {
            currentCombat.update(tpf);
            //Decrease all the wait time of characters;

            //Continue the combat
            for (GameCharacter gc : currentCombat.getCharacters()) {
                if (gc.isStaged()) {
                    if (currentCombat.isDead(gc)) {
                    } else {
                        // Update staged characters
                        // update turns
//                        if (!gc.isNpc()) {
//                            gc.turnDelay -= pcTurnRefreshRate * gc.turnRefreshRate * tpf;
//                        } else {
//                            gc.turnDelay -= npcTurnRefreshRate * gc.turnRefreshRate * tpf;
//                        }
//                        if (gc.turnDelay <= 0) {
//                            onTurn(gc);
//                        }
                    }
                } else {
//
//                    if (gc.staged) {
//                    } else {
//                        // Prepare to spawn
//                    }
                }
            }

            //Check the win condition
            boolean pcAllDead = true;
            boolean npcAllDead = true;

            for (GameCharacter gc : currentCombat.getCharacters()) {
                if (!currentCombat.isDead(gc)) {
                    if (gc.isNpc()) {
                        npcAllDead = false;
                    } else {
                        pcAllDead = false;
                    }
                } else {
                    if (gc.isStaged()) {
                        onDead(gc);
                    }
                }
            }
            if (pcAllDead) {
                currentCombat.end(false);
            } else if (npcAllDead) {
                currentCombat.end(true);
                gamePlayManager.award(currentCombat.getPlayer(), currentCombat.getResultScore());
            }
        } else {
            //Move out
        }
    }
    //Turn----------------------------------------------------------------------

    private void onTurn(GameCharacter gameCharacter) {
        gameCharacter.onTurn();
    }

    private void onDead(GameCharacter gameCharacter) {
        gameCharacter.onDead();
        gameCharacter.onOutStage(stageManager);
        //stageManager.removeCharacter(pc, 1f);
    }
//    private void nextTurn() {
//    }
//
//    private void missTurn() {
//    }
    //Enviromental--------------------------------------------------------------

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
