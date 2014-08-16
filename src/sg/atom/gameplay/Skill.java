package sg.atom.gameplay;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.List;
import static sg.atom.corex.math.Tweenf.QUAD_IN;
import static sg.atom.corex.math.Tweenf.QUAD_IN_OUT;
import static sg.atom.gameplay.Skill.SKILL_ATTACK_REMOTE;
import static sg.atom.gameplay.Skill.SKILL_EFFECT_EFFECTED;
import static sg.atom.gameplay.Skill.SKILL_EFFECT_NONE;
import static sg.atom.gameplay.Skill.SKILL_MOTION_STRAIGHT;
import sg.atom.gameplay.controls.SkillControl;
import sg.atom.stage.fx.EffectControl;
import sg.atom.stage.fx.EffectEvent;
import sg.atom.stage.EffectManager;
import sg.atom.stage.fx.interpolators.Vector3fInterpolator;

/**
 * Skill.
 *
 * FIXME: Should be parameterize enough to become data.
 *
 * @author CuongNguyen
 */
public class Skill {

    public static final int SKILL_STATUS_NONE = 0;
    public static final int SKILL_STATUS_ENABLED = 1;
    public static final int SKILL_STATUS_DISABLED = 2;
    public static final int SKILL_EFFECT_NONE = 0;
    public static final int SKILL_EFFECT_EFFECTED = 1;
    public static final int SKILL_EFFECT_MISSED = 2;
    public static final int SKILL_ATTACK_CLOSE = 0;
    public static final int SKILL_ATTACK_REMOTE = 1;
    public static final int SKILL_TYPE_DAMAGE = 0;
    public static final int SKILL_TYPE_HEALING = 1;
    public static final int SKILL_MOTION_STRAIGHT = 0;
    public static final int SKILL_MOTION_CURVE = 0;
    public static final int SKILL_MOTION_SHAPE = 0;
    //Attributes
    public int id;
    public String name;
    public int cost = 10;
    public int costGem = 10;
    public int costMana = 10;
    public int costHP = 0;
    public int type;
    public int requiredLevel;
    public int[] requireItems;
    public int[] requireSkills;
    public Object[] params;
    public String icon;
    public String description;
    public boolean staged;
    //Effects
    public int damage = 10;
    public int attack = 0;
    public int defend = 0;
    public int gainHP = 0;
    public int gainMana = 0;
    public int status = SKILL_EFFECT_NONE;
    public int effected = SKILL_EFFECT_NONE;
    public int motionType = SKILL_MOTION_STRAIGHT;
    public float range = 4;
    public int rangeType;
    public int attackType = SKILL_ATTACK_REMOTE;
    //Timing
    public float activedTime = 0;
    public float activeTime = 10;
    public float activeDelayTime = 0;
    public float refreshTime = 0;
    public float percentRefeshed = 0;
    public boolean missed = false;
    public String skillEffect = "fire";
//    public String skillEffects;
//    public String skillModel;
//    public String skillSteering;
    public Spatial model;
    public String animName;
    private GameCharacter owner;
    private GameCharacter target;

    public Skill(int id, String name, int cost, String icon) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.icon = icon;
        this.skillEffect = "fire";
    }

    public void calculate(GameCharacter owner, GameCharacter target) {
        if ((owner.attack + this.damage > target.defend) || // Defend more than attack
                (costMana > owner.mana) || // Not enough mana
                (range < GameCharacter.distance(owner, target)) // Range too short
                ) {
            this.missed = false;
        } else {
            this.missed = true;
        }
    }

    public void applySkill(GameCharacter owner) {
        this.owner = owner;
        applyCost(owner);
    }

    public void applySkill(GameCharacter owner, GameCharacter target) {
        this.owner = owner;
        applyCost(owner);
        applyDamage(owner, target);
        this.staged = true;
        this.effected = SKILL_EFFECT_EFFECTED;
        this.target = target;
    }

    public void applySkill(GameCharacter owner, GameCharacter... targets) {
    }

    public void applySkill(GameCharacter owner, List<GameCharacter> targets) {
    }

    public void applyCost(GameCharacter owner) {
        owner.mana -= costMana;
    }

    public void applyDamage(GameCharacter owner, GameCharacter target) {
        if (target.isNpc) {
            damage = 1;
        } else {
            damage = 100;
        }
        target.health -= damage;
    }

    public void refresh() {
        activeDelayTime = refreshTime;
        this.missed = false;
    }

    /**
     * Should be called from Skill controls?
     *
     * @param tpf
     */
    public void update(float tpf) {
        // Refreshing time...
        if (activeDelayTime > 0) {
            activeDelayTime = ((activeDelayTime - tpf > 0) ? (activeDelayTime - tpf) : 0);
        } else {
        }

        // showing time...
    }

    public boolean isMissed() {
        return missed;
    }

    public void showSkill(GameCharacter gameCharacter) {
        showSkill(gameCharacter, null);
    }

    public void showSkill(GameCharacter gameCharacter, GameCharacter target) {
        EffectManager effectManager = gameCharacter.getStageManager().getEffectManager();
        Vector2f skillPos2d = gameCharacter.getLocationOnScreen();


        if (attackType == SKILL_ATTACK_REMOTE) {
            Spatial effectSpatial = effectManager.getEffect(skillEffect).clone();
            if (gameCharacter.isNpc) {
                effectManager.setColor(effectSpatial, ColorRGBA.Green);
            } else {
            }
            SkillControl skillControl = new SkillControl(this);
            effectSpatial.addControl(skillControl);
            skillControl.onStart(gameCharacter.getLocation(), target.getLocation());
            effectManager.attachEffect(gameCharacter.getLocation(), effectSpatial);
        } else {
            //Jump back and forth
//            gameCharacter.getCharacterControl().skillAttack(target);


        }

    }

//    public void showSkill(GameCharacter owner, List<GameCharacter> targets) {
//    }
//    public String toString() {
//        return new ToStringBuilder(this)
//                .append(name)
//                .append(damage)
//                .append(costMana)
//                .toString();
//    }
    // GETTER & SETTER
    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    public int getAttack() {
        return attack;
    }

    public int getDamage() {
        return damage;
    }

    public int getDefend() {
        return defend;
    }

    public int getCostGem() {
        return costGem;
    }

    public int getCostHP() {
        return costHP;
    }

    public int getCostMana() {
        return costMana;
    }

    public float getActiveTime() {
        return activeTime;
    }

    public float getActiveDelayTime() {
        return activeDelayTime;
    }

    public float getActivedTime() {
        return activedTime;
    }

    public int getEffected() {
        return effected;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }
}
