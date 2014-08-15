/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.input.InputManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.lifecycle.ManagableObject;

/**
 * Helper is facilities of system which always there to help. It meant to be
 * "on-screen" facilities helping the designer work.
 *
 * <p>[DONE] Helper is no contracted to be heavy lifted as Manager. Helper going
 * to be first class in Atom in the next version!
 *
 * @author hungcuong
 */
public abstract class AbstractHelper extends AbstractControl implements ManagableObject {

    protected int id;
    protected String name;

    public AbstractHelper(String name) {
        this.name = name;
    }

    public abstract void initHelper();

    public abstract Node getHelperNode();

    public abstract List<Spatial> getSelectableList();

    //Events--------------------------------------------------------------------
    public void onBind(Object manager) {
    }

    public void onBind(InputManager inputManager) {
    }

    public void doCommand(String command) {
    }

    public void doTask(Callable task) {
    }

    //Cycle---------------------------------------------------------------------
    public void init(Application app, Object... managers) {
    }

    public void load(AssetManager assetManager) {
    }

    public void config(Configuration configuration) {
    }

    public void update(float tpf) {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    }

    public void finish() {
    }

    public void write(JmeExporter ex) throws IOException {
        super.write(ex); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        super.read(im); //To change body of generated methods, choose Tools | Templates.
    }

    //GETTER & SETTER-----------------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHelperNode(Spatial sp) {
        return getHelperNode().hasChild(sp);
    }

    public int getId() {
        return id;
    }
}
