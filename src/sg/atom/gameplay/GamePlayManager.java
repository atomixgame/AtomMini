/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

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
import java.util.Collection;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.gameplay.controls.NonPlayerCharacterControl;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.ui.nifty.UIFightScreen;
import sg.atom.corex.ui.nifty.UIInGameScreen;

/**
 * GamePlayManager is Manager for GamePlay, Player, Character, Level, Score.
 *
 * @author CuongNguyen
 */
public class GamePlayManager extends AbstractAppState implements IGameCycle {

    protected final AtomMain app;
    protected final AssetManager assetManager;
    protected final ViewPort viewPort;
    protected final InputManager inputManager;
    protected final AudioRenderer audioRenderer;
    protected final Node rootNode;
    protected final StageManager stageManager;
    // Game related
    protected ArrayList<CommonGameCharacter> characters;
    protected Player player;
    protected Player mainPlayer;
    protected ArrayList<Player> players;
    // Gameplay Mode! FIXME: State plz!
    protected int playMode = -1;
    protected ArrayList<GameLevel> levels;
    protected GameLevel currentLevel;
    protected ArrayList<BaseGamePlay> gamePlayList;
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

    public void loadLevels(AssetKey assetKey) {
        ArrayList<GameLevel> newLevels = (ArrayList<GameLevel>) assetManager.loadAsset(assetKey);
        this.levels.addAll(newLevels);
    }

    public void loadLevels() {
    }

    public void addLevel(GameLevel level) {
        this.levels.add(level);
    }

    public void addLevels(Collection<GameLevel> newLevels) {
        this.levels.addAll(newLevels);
    }

    public void startLevel(GameLevel level) {
    }

    public void initLevel(GameLevel currentLevel) {
    }

    public void setCurrentLevel(GameLevel level) {
        this.currentLevel = level;
    }

    public void startGame() {
    }

    public void setupInput() {
    }

    public void setupPlayer() {
    }

    public void initStates() {
    }

    public void update(float tpf) {
    }

    public void updateGUI(ScreenController screenController, float tpf) {
        

    }

    public void award(Player player, int score) {
        ItemManager itemManager = ItemManager.getInstance();
        Item item = itemManager.getAwardItem();
    }

    public void onState(Class<? extends AbstractAppState> newState) {
    }

    public void toState(Class<? extends AbstractAppState> newState) {
        
    }
    //Cycle---------------------------------------------------------------------
    public void init() {
    }

    public void load() {
    }

    public void config(Configuration props) {
    }

    public void finish() {
    }

    //GETTER & SETTER
    public void setupCharacters(List<CommonGameCharacter> characters) {
        this.characters = Lists.newArrayList(characters);
    }

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    public List<CommonGameCharacter> getCharacters() {
        return characters;
    }

    public CommonGameCharacter getCharacter(final String name) {
        return Iterables.find(characters, new Predicate<CommonGameCharacter>() {
            public boolean apply(CommonGameCharacter gc) {
                return gc.name.equals(name);
            }
        });
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public AtomMain getApp() {
        return app;
    }
}
