/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import sg.atom.gameplay.managers.SkillManager;
import sg.atom.gameplay.managers.ItemManager;
import sg.atom.stage.GameAction;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sg.atom.gameplay.ai.Agent;
import sg.atom.gameplay.controls.BaseCharacterControl;
import sg.atom.stage.StageManager;
import sg.atom.stage.select.CursorControl;

/**
 *
 * @author CuongNguyen
 */
public class GameCharacter implements Savable, Agent, Cloneable {

    StageManager stageManager;
    GamePlayManager gamePlayManager;
    // Attributes
    protected String name;
    protected Player owner;
    protected boolean isNpc = true;
    protected int health = 800;
    protected int maxHP = 1000;
    protected int mana = 100;
    protected int maxMana = 100;
    protected int attack = 100;
    protected int defend = 100;
    protected int turn = 1;
    protected int speed = 1;
    protected int intelligent = 100;
    protected int status = 0;
    protected int rank = 1;
    protected int skillStatus = 0;
    protected int skillEffected = 0;
    protected float range = 40;
    //Turn
    protected float turnDelay = 0;
    protected float turnDelayMax = 5;
    protected int turnOrder;
    protected int turnNum;
    protected float turnRefreshRate = 1;
    protected Spatial model;
    //Gameplay
    protected ArrayList<Skill> skills;
    protected ArrayList<Item> items;
    protected ArrayList<GameAction> actions;
    protected Inventory inventory;
    protected boolean staged = false;
    protected Skill mainSkill;
    protected Item currentItem;
    protected Combat touchedCombat;
    protected Control characterControl;
    protected CursorControl cursorControl;
    protected Quest currentQuest;
    protected Combat currentCombat;

    public GameCharacter(String name) {
        this.name = name;
        skills = new ArrayList<Skill>();
    }

    public GameCharacter(String name, Spatial model) {
        this.name = name;
        this.model = model;
        this.skills = new ArrayList<Skill>();
        this.isNpc = true;
        this.turnDelayMax = 8;
        createSkills();
    }

    public GameCharacter(String name, Player owner, Spatial model) {
        this(name, model);
        this.isNpc = false;
        this.owner = owner;
        this.owner.getTeam().add(this);
        this.turnDelayMax = 2;
        createInventory();
    }

    public void createSkills() {
        SkillManager skillManager = SkillManager.getInstance();
        this.mainSkill = skills.get(0);
    }

    public void createInventory() {
        inventory = new Inventory(5, 5);
        items = new ArrayList<Item>();
        ItemManager itemManager = ItemManager.getInstance();
        inventory.addAll(items);

        this.currentItem = items.get(0);
    }
    // Events -------------------------------------

    public void onStage(StageManager stageManager) {
        this.stageManager = stageManager;
        this.gamePlayManager = stageManager.getGamePlayManager();
        this.staged = true;
    }

    public void onOutStage(StageManager stageManager) {
        this.staged = false;
    }

    public void onDead() {
        System.out.println(" " + name + " dead!");
//        characterControl.let("Die");
        cursorControl.setShow3DCussor(true);
        cursorControl.setColor(ColorRGBA.Gray);
    }

    public void onFightStart(Combat combat) {
        this.currentCombat = combat;

        //Cursor control
        cursorControl = new CursorControl(stageManager);
        cursorControl.createCursor(stageManager.getApp().getAssetManager(), stageManager.getWorldNode());
        cursorControl.placeCursor(model.getLocalTranslation());
        cursorControl.setShow3DCussor(false);
        cursorControl.setAnimated(false);

        if (this.isNpc) {
//            this.turnDelayMax = 8;
        } else {
//            this.turnDelayMax = 2;
        }
        this.turnDelay = turnDelayMax;
    }

    public void onFightEnd() {
    }

    public void onTurn() {
//        System.out.println("On turn :" + name);
//        //Find target automaticly
//        List<GameCharacter> nearBy = getGamePlayManager().getCombatGamePlay().getBy(byRange(this, range), byEnemy(this), byAlive(true));
//        if (!nearBy.isEmpty()) {
//            GameCharacter target = nearBy.get(0);
//
//            //And use main Skill
//            if (cursorControl != null) {
//                cursorControl.setShow3DCussor(true);
//                cursorControl.setColor(ColorRGBA.Orange);
//
//                //FIXME: Should be in Animation finish!
//                stageManager.queueAction(this, new Runnable() {
//                    public void run() {
//                        if (!isDead()) {
//                            cursorControl.setShow3DCussor(false);
//                        }
//                    }
//                }, 2f);
//            }
//            useSkill(mainSkill, target);
//            this.turnDelay = turnDelayMax;
//        }
    }

    public void onEndTurn() {
    }

    public void onStartQuest() {
    }

    public void onEndQuest() {
    }
//Actions-----------------------------------------------------------------------

    public void addItem(Item item) {
//        inventory.add(item);
    }

    public void useItem() {
    }
    // FIXME: Improve this

    public void useSkill(Skill skill, GameCharacter target) {
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
    // For Collections of Characters!-----------------------------------------

    public static List<GameCharacter> getBy(List<GameCharacter> characters, Predicate<GameCharacter> predicate) {
        ArrayList<GameCharacter> result = new ArrayList<GameCharacter>();
        for (GameCharacter gc : characters) {
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public static List<GameCharacter> getBy(List<GameCharacter> characters, Predicate<GameCharacter>... filters) {
        ArrayList<GameCharacter> result = new ArrayList<GameCharacter>();
        for (GameCharacter gc : characters) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public Vector2f getLocationOnScreen() {
        Vector3f screenCoordinates = getStageManager().getCurrentActiveCamera().getScreenCoordinates(getLocation().add(0, -2, 0));
        return new Vector2f(screenCoordinates.x, screenCoordinates.y);
    }

    public boolean isStaged() {
        return staged;
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public static float distance(GameCharacter owner, GameCharacter target) {
        return owner.getLocation().distance(target.getLocation());
    }

    public void setCursorControl(CursorControl cursorControl) {
        this.cursorControl = cursorControl;
    }
//
//    private void face(GameCharacter target) {
//        
//    }

    public static Predicate<GameCharacter> byRange(final GameCharacter centerCharacter, final float checkRange) {
        return new Predicate<GameCharacter>() {
            public boolean apply(GameCharacter gameCharacter) {
                Vector3f center = centerCharacter.getLocation();
                boolean inRange = gameCharacter.getLocation().distance(center) < checkRange;
                return inRange;
            }
        };
    }

    public static Predicate<GameCharacter> byAlive(final boolean isAlive) {
        return new Predicate<GameCharacter>() {
            public boolean apply(GameCharacter gameCharacter) {
                return gameCharacter.isDead() != isAlive;
            }
        };
    }

    public static Predicate<GameCharacter> byRank(final int rank) {
        return new Predicate<GameCharacter>() {
            public boolean apply(GameCharacter gameCharacter) {
                return gameCharacter.rank == rank;
            }
        };
    }

    public static Predicate<GameCharacter> byEnemy(final GameCharacter centerCharacter) {
        return new Predicate<GameCharacter>() {
            public boolean apply(GameCharacter gameCharacter) {
                return centerCharacter.isNpc != gameCharacter.isNpc;
            }
        };
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class GetByRankPredicate implements Predicate<GameCharacter> {

        int rank;

        public GetByRankPredicate(int rank) {
            this.rank = rank;
        }

        public boolean apply(GameCharacter character) {
            return character.rank == rank;
        }
    }

    public static class GetByRangePredicate implements Predicate<GameCharacter> {

        Vector3f center;
        float checkRange;

        public GetByRangePredicate(Vector3f center, float range) {
            this.center = center;
            this.checkRange = range;
        }

        public GetByRangePredicate(GameCharacter centerCharacter, float range) {
            this(centerCharacter.getLocation(), range);
        }

        public boolean apply(GameCharacter gameCharacter) {
            boolean inRange = gameCharacter.getLocation().distance(center) < checkRange;
            //System.out.println("InRange " + checkRange + " : " + inRange);
            return inRange;
        }
    }

    //GETTER & SETTER-----------------------------------------------------------
    public Node getModelNode() {
        return (Node) model;
    }

    public Spatial getModel() {
        return model;
    }

    public void setPlayer(Player owner) {
        this.owner = owner;
    }

    public void setModel(Spatial playerModel) {
        this.model = playerModel;
    }

    public Vector3f getLocation() {
        return this.getModel().getLocalTranslation();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public Skill getSkill(int id) {
        return skills.get(id);
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }

    public void removeSkill(int id) {
        skills.remove(id);
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public Control getCharacterControl() {
        return characterControl;
    }

    public void setCharacterControl(Control characterControl) {
        this.characterControl = characterControl;
    }

    public ArrayList<GameAction> getActions() {
        return actions;
    }

    public Item getItem(int id) {
        return items.get(id);
    }

    public Item getCurrentItem() {
        return currentItem;
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

    public ArrayList<Item> getItems() {
        return items;
    }

    public Skill getMainSkill() {
        return mainSkill;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public String getName() {
        return name;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public Player getOwner() {
        return owner;
    }

    public float getRange() {
        return range;
    }

    public int getRank() {
        return rank;
    }

    public int getStatus() {
        return status;
    }

    public int getSpeed() {
        return speed;
    }

    public int getTurn() {
        return turn;
    }

    public float getTurnDelay() {
        return turnDelay;
    }

    public float getTurnDelayMax() {
        return turnDelayMax;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public float getTurnRefreshRate() {
        return turnRefreshRate;
    }

    public boolean isNpc() {
        return isNpc;
    }

    public void setTurnDelay(float turnDelay) {
        this.turnDelay = turnDelay;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
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

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setMainSkill(Skill mainSkill) {
        this.mainSkill = mainSkill;
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

    public void setTurnDelayMax(float turnDelayMax) {
        this.turnDelayMax = turnDelayMax;
    }
}
