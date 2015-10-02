/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.managers;

import com.jme3.asset.AssetKey;
import java.util.ArrayList;
import java.util.Collection;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.gameplay.GameLevel;

/**
 *
 * @author cuongnguyen
 */
public class LevelManager extends AbstractManager {

    protected ArrayList<GameLevel> levels;
    protected GameLevel currentLevel;

    public LevelManager() {
        super();
    }

    public LevelManager(AtomMain app) {
        super(app);
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

    public ArrayList<GameLevel> getLevels() {
        return levels;
    }

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(GameLevel level) {
        this.currentLevel = level;
    }

}
