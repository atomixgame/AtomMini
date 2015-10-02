package sg.atom.gameplay;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import java.util.List;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.stage.select.CursorControl;
import sg.atom.ai.Behaviour;
import sg.atom.gameplay.managers.ItemManager;
import sg.atom.gameplay.managers.SkillManager;

/**
 *
 * @author CuongNguyen
 */
public class CommonGameCharacter extends AbstractGameCharacter {

    protected boolean dead;
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
    protected AnimChannel animChannel;
    protected AnimControl animControl;
    protected CursorControl cursorControl;
    //Gameplay
    protected Quest currentQuest;
    protected Combat currentCombat;
    protected ArrayList<Item> items;
    protected Skill mainSkill;
    protected ArrayList<Skill> skills;
    protected Inventory inventory;
    protected Item currentItem;
    protected boolean isNpc;

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
        this.owner.getCharacters().add(this);
    }

    public void onStage(StageManager stageManager) {
        this.stageManager = stageManager;
        this.gamePlayManager = stageManager.getApp().getGamePlayManager();

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
        //        characterControl.let("Die");
        cursorControl.setShow3DCussor(true);
        cursorControl.setColor(ColorRGBA.Gray);
    }

    public void useSkill(Skill skill, CommonGameCharacter target) {
        if (target != null) {
            if (characterControl != null) {
                //                characterControl.face(target);
            }
            // Is there any calculation?
            skill.calculate(this, target);
            if (skill.isMissed()) {
            } else {
                target.letDamaged(skill.getDamage());
                skill.applySkill(this, target);
                skill.showSkill(this, target);
            }
        } else {
            skill.applySkill(this);
            skill.showSkill(this);
        }
        skill.refresh();
    }

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

    public List<Behaviour> getBehaviours() {
        return null;
    }

    public void react(Object signal) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        return this;
    }

    public void setSpatial(Spatial spatial) {
        this.model = spatial;
    }

    public void render(RenderManager rm, ViewPort vp) {
    }

    public long getId() {
        return id;
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

    public AnimChannel getAnimChannel() {
        return animChannel;
    }

    public AnimControl getAnimControl() {
        return animControl;
    }

    public void letDamaged(int damage) {
        if (this.health > damage) {
            this.health -= damage;
        } else {
            this.health = 0;
            letDie();
        }
    }

    protected void letDie() {
        onDead();
    }

    public void createInventory() {
        inventory = new Inventory(5, 5);
        items = new ArrayList<Item>();
        ItemManager itemManager = getApp().getStateManager().getState(ItemManager.class);
        inventory.addAll(items);
        this.currentItem = items.get(0);
    }

    public void createSkills() {
        SkillManager skillManager = getApp().getStateManager().getState(SkillManager.class);
        this.mainSkill = skills.get(0);
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Item getItem(int id) {
        return items.get(id);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Skill getMainSkill() {
        return mainSkill;
    }

    public Skill getSkill(int id) {
        return skills.get(id);
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }

    public void removeSkill(int id) {
        skills.remove(id);
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public void setEntity(SpatialEntity entity) {
        this.entity = entity;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setMainSkill(Skill mainSkill) {
        this.mainSkill = mainSkill;
    }

    public void addItem(Item item) {
        //        inventory.add(item);
    }

    public void useItem(Item item) {
    }
    // FIXME: Improve this

    public void useItem() {
    }

    @Override
    public boolean isNpc() {
        return super.isNpc(); //To change body of generated methods, choose Tools | Templates.
    }

    public void setIsNpc(boolean isNpc) {
        this.isNpc = isNpc;
    }

}
