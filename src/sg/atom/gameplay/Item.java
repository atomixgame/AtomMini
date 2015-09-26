/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import org.apache.commons.configuration.Configuration;
import sg.atom.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;

/**
 *
 * @author CuongNguyen
 */
public class Item extends AbstractControl implements ManagableObject {

    protected int id;
    protected String name;

    protected int cost = 100;
    protected int gem = 100;
    protected int[] requireItems;
    protected int version = 0;
    protected int rank = 1;
    protected String updateState;
    protected String versionState;
    protected String description = "";
    protected CommonGameCharacter owner;
    protected String icon;
    private AtomMain app;

//    int width = 1;
//    int height = 1;
    public Item(String name) {
        this.name = name;
    }

    public Item(int id, String name, int cost, String description, String icon) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.icon = icon;
    }

    //Actions
    //Events -------------------------------------------------------------------
    public void onPick() {
    }

    public void onDrop() {
    }

    public void onPickInventory() {

    }

    public void onDropInventory() {

    }

    public void onHover() {

    }

    public void onUse(CommonGameCharacter character) {

    }

    //GETTER & SETTER -------------------------------------------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int[] getRequireItems() {
        return requireItems;
    }

    public void setRequireItems(int[] requireItems) {
        this.requireItems = requireItems;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommonGameCharacter getOwner() {
        return owner;
    }

    public void setOwner(CommonGameCharacter owner) {
        this.owner = owner;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

//    public int getWidth() {
//        return width;
//    }
//
//    public void setWidth(int width) {
//        this.width = width;
//    }
//
//    public int getHeight() {
//        return height;
//    }
//
//    public void setHeight(int height) {
//        this.height = height;
//    }
    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void init(Application app) {
        this.app = (AtomMain) app;
    }

    public void initManagers(IGameCycle... managers) {
    }

    public void load(AssetManager assetManager) {
    }

    public void config(Configuration configuration) {
    }

    public void finish() {
    }

    public AtomMain getApp() {
        return app;
    }

}
