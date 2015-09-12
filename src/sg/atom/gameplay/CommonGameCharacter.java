package sg.atom.gameplay;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import java.util.List;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.stage.select.CursorControl;

/**
 *
 * @author CuongNguyen
 */
public class CommonGameCharacter extends AbstractGameCharacter {

    //Health & energy
    protected int health = 800;
    protected int maxHP = 1000;
    protected int mana = 100;
    protected int maxMana = 100;
    //Power
    protected int attack = 100;
    protected int defend = 100;
    protected int turn = 1;
    protected int speed = 1;
    protected int intelligent = 100;
    protected int rank = 1;
    //Skill
    protected int skillStatus = 0;
    protected int skillEffected = 0;
    protected float range = 40;
    protected int rangeType = 0;
    //Some controls
    protected CursorControl cursorControl;
    //Gameplay
    protected Quest currentQuest;
    protected Combat currentCombat;

    public CommonGameCharacter(String name) {
        super(name, null);
        skills = new ArrayList<Skill>();
    }

    public CommonGameCharacter(String name, Spatial model) {
        super(name, model);
        this.name = name;
        this.model = model;
        this.skills = new ArrayList<Skill>();
        this.isNpc = true;

    }

    public CommonGameCharacter(String name, Player owner, Spatial model) {
        this(name, model);
        this.isNpc = false;
        this.owner = owner;
        this.owner.getTeam().add(this);

    }

    public void onStage(StageManager stageManager) {
        this.stageManager = stageManager;
        this.gamePlayManager = stageManager.getGamePlayManager();
        this.staged = true;
    }

    public void onOutStage(StageManager stageManager) {
        this.staged = false;
    }

    public void onSkillAffected(Skill skill, CommonGameCharacter caster) {
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void onDead() {
        System.out.println(" " + name + " dead!");
        //        characterControl.let("Die");
        cursorControl.setShow3DCussor(true);
        cursorControl.setColor(ColorRGBA.Gray);
    }

    public void useSkill(Skill skill, CommonGameCharacter target) {
        System.out.println(" Use skill :" + skill.toString() + " to: " + target);
        if (target != null) {
            if (characterControl != null) {
                //                characterControl.face(target);
            }
            // Is there any calculation?
            skill.calculate(this, target);
            if (skill.isMissed()) {
            } else {
                if (isNpc) {
                    skill.damage = 1;
                } else {
                    skill.damage = 100;
                }
                target.health -= skill.damage;
                skill.applySkill(this, target);
                skill.showSkill(this, target);
            }
        } else {
            //            this.attack += skill.attack;
            //            this.defend += skill.defend;
            //            this.health += skill.gainHP;
            //            this.mana += skill.gainMana;
            skill.applySkill(this);
            skill.showSkill(this);
        }
        skill.refresh();
    }
//Actions-----------------------------------------------------------------------

    public static List<CommonGameCharacter> getBy(List<CommonGameCharacter> characters, Predicate<CommonGameCharacter> predicate) {
        ArrayList<CommonGameCharacter> result = new ArrayList<CommonGameCharacter>();
        for (CommonGameCharacter gc : characters) {
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public static List<CommonGameCharacter> getBy(List<CommonGameCharacter> characters, Predicate<CommonGameCharacter>... filters) {
        ArrayList<CommonGameCharacter> result = new ArrayList<CommonGameCharacter>();
        for (CommonGameCharacter gc : characters) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public Vector2f getLocationOnScreen() {
        Vector3f screenCoordinates = getStageManager().getCurrentCamera().getScreenCoordinates(getLocation().add(0, -2, 0));
        return new Vector2f(screenCoordinates.x, screenCoordinates.y);
    }

    public boolean isStaged() {
        return staged;
    }

    public static float distance(CommonGameCharacter owner, CommonGameCharacter target) {
        return owner.getLocation().distance(target.getLocation());
    }

    public void setCursorControl(CursorControl cursorControl) {
        this.cursorControl = cursorControl;
    }
//
//    private void face(GameCharacter target) {
//        
//    }

    public static Predicate<CommonGameCharacter> byRange(final CommonGameCharacter centerCharacter, final float checkRange) {
        return new Predicate<CommonGameCharacter>() {
            public boolean apply(CommonGameCharacter gameCharacter) {
                Vector3f center = centerCharacter.getLocation();
                boolean inRange = gameCharacter.getLocation().distance(center) < checkRange;
                return inRange;
            }
        };
    }

    public static Predicate<CommonGameCharacter> byAlive(final boolean isAlive) {
        return new Predicate<CommonGameCharacter>() {
            public boolean apply(CommonGameCharacter gameCharacter) {
                return gameCharacter.isDead() != isAlive;
            }
        };
    }

    public static Predicate<CommonGameCharacter> byRank(final int rank) {
        return new Predicate<CommonGameCharacter>() {
            public boolean apply(CommonGameCharacter gameCharacter) {
                return gameCharacter.rank == rank;
            }
        };
    }

    public static Predicate<CommonGameCharacter> byEnemy(final CommonGameCharacter centerCharacter) {
        return new Predicate<CommonGameCharacter>() {
            public boolean apply(CommonGameCharacter gameCharacter) {
                return centerCharacter.isNpc != gameCharacter.isNpc;
            }
        };
    }

    public void load(AssetManager assetManager) {
    }

    public static class GetByRankPredicate implements Predicate<CommonGameCharacter> {

        int rank;

        public GetByRankPredicate(int rank) {
            this.rank = rank;
        }

        public boolean apply(CommonGameCharacter character) {
            return character.rank == rank;
        }
    }

    public static class GetByRangePredicate implements Predicate<CommonGameCharacter> {

        Vector3f center;
        float checkRange;

        public GetByRangePredicate(Vector3f center, float range) {
            this.center = center;
            this.checkRange = range;
        }

        public GetByRangePredicate(CommonGameCharacter centerCharacter, float range) {
            this(centerCharacter.getLocation(), range);
        }

        public boolean apply(CommonGameCharacter gameCharacter) {
            boolean inRange = gameCharacter.getLocation().distance(center) < checkRange;
            //System.out.println("InRange " + checkRange + " : " + inRange);
            return inRange;
        }
    }

    public Vector3f getLocation() {
        return this.getModel().getLocalTranslation();
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public Control getCharacterControl() {
        return characterControl;
    }

    public Combat getCurrentCombat() {
        return currentCombat;
    }

    public Quest getCurrentQuest() {
        return currentQuest;
    }

    public CursorControl getCursorControl() {
        return cursorControl;
    }

    public int getDefend() {
        return defend;
    }

    public int getAttack() {
        return attack;
    }

    public int getIntelligent() {
        return intelligent;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public float getRange() {
        return range;
    }

    public int getRank() {
        return rank;
    }

    public int getSpeed() {
        return speed;
    }

    public int getTurn() {
        return turn;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setIntelligent(int intelligent) {
        this.intelligent = intelligent;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public void setCurrentQuest(Quest currentQuest) {
        this.currentQuest = currentQuest;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public void setStaged(boolean staged) {
        this.staged = staged;
    }
}
