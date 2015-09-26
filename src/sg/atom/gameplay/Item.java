/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author CuongNguyen
 */
public class Item extends AbstractControl{

    int id;
    String name;
    int cost = 100;
    int gem = 100;
    int[] requireItems;
    int version = 0;
    int rank = 1;
    String updateState;
    String versionState;
    String description = "";
    CommonGameCharacter owner;
    String icon;
    int width = 1;
    int height = 1;

    public Item(int id, String name) {
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    protected void controlUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
