/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import sg.atom.gameplay.state.AdventureGamePlay;
import sg.atom.gameplay.state.CombatFightGamePlay;
import sg.atom.gameplay.managers.ItemManager;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.gameplay.controls.NonPlayerCharacterControl;
import sg.atom.stage.StageManager;
import sg.atom.ui.nifty.UIFightScreen;
import sg.atom.ui.nifty.UIInGameScreen;

/**
 *
 * @author CuongNguyen
 */
public class GamePlayManager  implements IGameCycle{

    protected final AtomMain app;
    protected final AssetManager assetManager;
    protected final ViewPort viewPort;
    protected final InputManager inputManager;
    protected final AudioRenderer audioRenderer;
    protected final Node rootNode;
    protected final StageManager stageManager;
    // Game related
    protected ArrayList<GameCharacter> characters;
    protected Player player;
    protected Player mainPlayer;
    // Gameplay Mode! FIXME: State plz!
    protected int playMode = -1;
    protected ArrayList<GameLevel> levels;
    protected GameLevel currentLevel;
    protected CombatFightGamePlay combatGamePlay;
    protected AdventureGamePlay adventureGamePlay;
    protected BaseGamePlay currentGamePlay;
    protected boolean firstRun = true;

    public GamePlayManager(AtomMain app) {
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.inputManager = app.getInputManager();
        this.audioRenderer = app.getAudioRenderer();
        this.viewPort = app.getViewPort();
        this.rootNode = app.getRootNode();
        this.stageManager = app.getStageManager();
    }

    public void loadLevels(AssetKey assetKey){
        ArrayList<GameLevel> gameLevels = (ArrayList<GameLevel>) assetManager.loadAsset(assetKey);
    }
    public void goInGame() {
        setupPlayer();
        startLevel(getCurrentLevel());
        setupInput();
        this.adventureGamePlay = new AdventureGamePlay(this);
        this.combatGamePlay = new CombatFightGamePlay(this);
        //adventureGamePlay.startGamePlay();
    }

    public void startGamePlay() {
    }

    public void setupInput() {
    }

    public void setupPlayer() {
        mainPlayer = new Player("Atomix");
    }

    public void update(float tpf) {
        if (playMode == 1) {
            combatGamePlay.update(tpf);
        } else {
            adventureGamePlay.update(tpf);
        }
    }

    public void startLevel(GameLevel level) {
//        Vector3f startPos = currentLevel.getStartPos();
//        currentLevel.getLevelNode().attachChild(mainPlayer.getPlayerMainCharacter().getModelNode());
//        characterControl.setLocation(new Vector3f(0f, 8f, 0f));
//         steerManager = new SteerManager(this.stageManager.getApp());
//        createRandomNPC();
    }

    public void updateGUI(ScreenController screenController, float tpf) {
        if (screenController instanceof UIInGameScreen) {

            // Update onscreen info
            UIInGameScreen inGameScreen = (UIInGameScreen) screenController;
            //inGameScreen.setAlert("Hello World");
            GameCharacter mainCharacter = mainPlayer.getMainCharacter();
            inGameScreen.setHealth(mainCharacter.health, mainCharacter.maxHP);
            inGameScreen.setMana(mainCharacter.mana, mainCharacter.maxMana);

            for (GameCharacter character : characters) {
                inGameScreen.setCharacterInfoExtra(character);
            }
        } else if (screenController instanceof UIFightScreen) {
            UIFightScreen fightScreen = (UIFightScreen) screenController;
            for (GameCharacter character : characters) {
                fightScreen.setCharacterInfoExtra(character);
            }
        }

    }

    public List<GameCharacter> createRandomNPC() {
        ArrayList<GameCharacter> npcs = new ArrayList<GameCharacter>();
        for (int i = 0; i < 8; i++) {
            GameCharacter cerberus = new GameCharacter("Cerberus " + i, app.getWorldManager().getModel("Cerberus").clone());
            cerberus.setCharacterControl(new NonPlayerCharacterControl(stageManager, cerberus));
            cerberus.rank = i % 3;
            cerberus.maxHP = 1000 + i * 100;
            npcs.add(cerberus);
        }
        return npcs;
    }

    public void award(Player player, int score) {
        ItemManager itemManager = ItemManager.getInstance();
        Item item = itemManager.getAwardItem();
    }

    public void onState(Class<? extends AbstractAppState> newState) {
    }

//    public void goFightScene() {
//        this.playMode = 1;
//        if (!firstRun) {
//            adventureGamePlay.endGamePlay();
//            adventureGamePlay.getCharacterControl().setInputControlled(this.playMode != 1);
//        }
//        combatGamePlay.startGamePlay();
//        Combat exampleCombat = new Combat(this);
//        exampleCombat.start(mainPlayer, createRandomNPC());
//
//    }
//
//    public void goNormalScene() {
//        this.playMode = 1;
//        if (!firstRun) {
//            adventureGamePlay.getCharacterControl().setInputControlled(this.playMode != 1);
//            combatGamePlay.endGamePlay();
//        }
//        this.adventureGamePlay.startGamePlay();
//    }
    //Cycle---------------------------------------------------------------------
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

    public void initLevel(GameLevel currentLevel) {
    }
    //GETTER & SETTER
    
    public void setupCharacters(List<GameCharacter> characters) {
        this.characters = Lists.newArrayList(characters);
    }

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    public List<GameCharacter> getCharacters() {
        return characters;
    }

    public GameCharacter getCharacter(final String name) {
        return Iterables.find(characters, new Predicate<GameCharacter>() {
            public boolean apply(GameCharacter gc) {
                return gc.name.equals(name);
            }
        });
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public CombatFightGamePlay getCombatGamePlay() {
        return combatGamePlay;
    }

    public AdventureGamePlay getAdventureGamePlay() {
        return adventureGamePlay;
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }
}
