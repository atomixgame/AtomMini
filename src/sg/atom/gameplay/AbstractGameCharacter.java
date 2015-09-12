package sg.atom.gameplay;

import com.jme3.app.Application;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.configuration.Configuration;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;
import sg.atom.gameplay.ai.Agent;
import sg.atom.gameplay.managers.ItemManager;
import sg.atom.gameplay.managers.SkillManager;
import sg.atom.corex.stage.GameAction;
import sg.atom.corex.stage.GameActor;
import sg.atom.corex.managers.StageManager;

/**
 *
 * @author cuong.nguyen
 */
public abstract class AbstractGameCharacter implements Agent, Cloneable, GameActor, ManagableObject, Savable {
    protected GamePlayManager gamePlayManager;
    protected ArrayList<GameAction> actions;
    protected SpatialEntity entity;
    protected boolean isNpc = true;
    protected ArrayList<Item> items;
    protected Skill mainSkill;
    //Stage, appearance
    protected Spatial model;
    // Attributes
    protected String name;
    protected Player owner;
    //Skills and secondary properties
    protected ArrayList<Skill> skills;
    protected StageManager stageManager;
    protected boolean staged = false;
    protected int status = 0;
    protected Control characterControl;
    protected Inventory inventory;    
    protected Item currentItem;
    
    public AbstractGameCharacter(String name, Spatial model) {
                this.name = name;
                this.model = model;

    }

    public void act(int type, Object param) {
    }

    public void addItem(Item item) {
        //        inventory.add(item);
    }

    public void config(Configuration configuration) {
    }

    public void createInventory() {
        inventory = new Inventory(5, 5);
        items = new ArrayList<Item>();
        ItemManager itemManager = ItemManager.getInstance();
        inventory.addAll(items);
        this.currentItem = items.get(0);
    }

    public void createModel() {
    }
    // Events -------------------------------------

    public void createSkills() {
        SkillManager skillManager = SkillManager.getInstance();
        this.mainSkill = skills.get(0);
    }

    public void finish() {
    }

    public ArrayList<GameAction> getActions() {
        return actions;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public SpatialEntity getEntity() {
        return entity;
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
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

    public Spatial getModel() {
        return model;
    }

    //GETTER & SETTER-----------------------------------------------------------
    public Node getModelNode() {
        return (Node) model;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }

    public Skill getSkill(int id) {
        return skills.get(id);
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public int getStatus() {
        return status;
    }

    public void init(Application app) {
        //        createSkills();
        //        createInventory();
        //        createModel();
    }

    public void initManagers(IGameCycle... managers) {
    }

    public boolean isDead() {
        return false;
    }

    public boolean isNpc() {
        return isNpc;
    }

    public void onDead() {
    }

    public void read(JmeImporter im) throws IOException {
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }

    public void removeSkill(int id) {
        skills.remove(id);
    }

    public void setCharacterControl(Control characterControl) {
        this.characterControl = characterControl;
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

    public void setModel(Spatial playerModel) {
        this.model = playerModel;
    }

    public void setPlayer(Player owner) {
        this.owner = owner;
    }

    public void update(float tpf) {
    }

    public void useIteam(Item item) {
    }
    // FIXME: Improve this

    public void useItem() {
    }

    public void useSkill(Skill skill, CommonGameCharacter target) {
        skill.refresh();
    }
    // For Collections of Characters!-----------------------------------------

    public void write(JmeExporter ex) throws IOException {
    }
    
}
