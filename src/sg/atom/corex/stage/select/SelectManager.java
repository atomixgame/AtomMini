package sg.atom.corex.stage.select;

import com.google.common.base.Predicate;
import com.google.common.eventbus.EventBus;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.es.EntityId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.logic.Command;
import sg.atom.corex.world.collision.CollisionChecker;
import sg.atom.corex.world.physics.PhysicCollisionChecker;

/**
 * General SelectManager to intergrate with Zay-ES and other Atom components.
 *
 * @author cuong.nguyenmanh2
 */
public class SelectManager extends AbstractManager implements RawInputListener {

    Command<Spatial> selectFunction;
    Command<Spatial> deselectFunction;
    Command<Spatial> hoverFunction;
    Command<Spatial> dehoverFunction;
    Predicate<Spatial> selectCondition;
    Predicate<Spatial> hoverCondition;
//    Predicate<Spatial> deselectCondition;
//    Predicate<Spatial> dehoverCondition;
    protected int firstButtonIndex = MouseInput.BUTTON_LEFT;
    protected int secondButtonIndex = MouseInput.BUTTON_RIGHT;
    //FIXME: This should be some lookup, oservable collection? like EventList?
    protected ArrayList<Spatial> currentSelection;
    protected ArrayList<EntityId> currentEntitiesSelection;
    private boolean physicalBase = false;
    private boolean debugEnabled = false;
    private CollisionChecker collisionChecker;
    private PhysicCollisionChecker physicCollisionChecker;
    private EventBus eventBus;
    private SelectOperationUI currentUI;
    private Camera currentCam;

    public static interface SelectOperationUI {

        void setOriginPoint(Vector3f point);

        void addDynamicPoint(Vector3f point);

        Vector3f getOriginPoint();

        Vector4f getBound();

        List<Vector3f> getPolygon();

        void setFinished(boolean finish);

        boolean isFinished();

        void setVisible(boolean visibility);

        boolean isVisible();

        void update(float tpf);
    }

    public static enum SelectOperation {

        Single() {
            void execute(SelectManager manager, float tpf) {
                CollisionResults collisionResults = manager.doShoot();
//                CollisionResults collisionResults = manager.collisionChecker.checkRayCollision(manager.currentUI.getOriginPoint(), manager.getCollidable());
                manager.changes(collisionResults);
            }

            @Override
            void button(SelectManager manager, MouseButtonEvent evt) {
                super.button(manager, evt);
                if (isPressed) {
                    execute(manager, 0);
                }
            }

            @Override
            void move(SelectManager manager, MouseMotionEvent evt) {
                super.move(manager, evt);
                SelectOperationUI ui = manager.currentUI;
                ui.setOriginPoint(new Vector3f(currentX, currentY, 0));
            }
        }, Multi() {
            void execute(SelectManager manager, float tpf) {
                CollisionResults collisionResults = manager.collisionChecker.checkBoundCollision(manager.currentUI.getBound(), manager.getCollidable());
            }

            @Override
            void button(SelectManager manager, MouseButtonEvent evt) {
                super.button(manager, evt);
                if (!isPressed) {
                    execute(manager, 0);
                }
            }

            @Override
            void move(SelectManager manager, MouseMotionEvent evt) {
                super.move(manager, evt);
                if (isDragged) {
                    SelectOperationUI ui = manager.currentUI;
                    ui.setVisible(true);
                    if (ui.isFinished()) {
                        ui.setOriginPoint(new Vector3f(lastX, lastY, 0));
                        ui.setFinished(false);
                    }
                    ui.addDynamicPoint(new Vector3f(currentX, currentY, 0));
                }

            }
        }, Switchable() {
            void execute(SelectManager manager, float tpf) {
            }
        };
        int lastX, lastY, currentX, currentY;
        protected boolean isPressed = false;
        protected boolean isDragged = false;

        abstract void execute(SelectManager manager, float tpf);

        void button(SelectManager manager, MouseButtonEvent evt) {
            if (evt.getButtonIndex() == manager.firstButtonIndex) {
                if (evt.isPressed()) {
                    isPressed = true;
                    lastX = evt.getX();
                    lastY = evt.getY();
                } else {
                    isPressed = false;
                    isDragged = false;
                }
            }
        }

        void move(SelectManager manager, MouseMotionEvent evt) {
            if (isPressed) {
                currentX = evt.getX();
                currentY = evt.getY();

                if (lastX != evt.getX() || lastY != evt.getY()) {
                    isDragged = true;
                } else {
                    //isDragged = false;
                }
            }
        }
    }
    protected SelectOperation selectOperation = SelectOperation.Single;

    public static enum SelectOperationType {

        Add() {
            void execute(SelectManager manager, Spatial... objs) {
                manager.currentSelection.addAll(Arrays.asList(objs));
            }

            void execute(SelectManager manager, Collection<Spatial> objs) {
                manager.currentSelection.addAll(objs);
            }
        }, Substract() {
            void execute(SelectManager manager, Spatial... objs) {
                manager.currentSelection.removeAll(Arrays.asList(objs));
            }

            void execute(SelectManager manager, Collection<Spatial> objs) {
                manager.currentSelection.removeAll(objs);
            }
        }, Intersect() {
            void execute(SelectManager manager, Spatial... objs) {
                manager.currentSelection.retainAll(Arrays.asList(objs));
            }

            void execute(SelectManager manager, Collection<Spatial> objs) {
                manager.currentSelection.retainAll(objs);
            }
        };

        abstract void execute(SelectManager manager, Spatial... objs);

        abstract void execute(SelectManager manager, Collection<Spatial> objs);
    }
    protected SelectOperationType selectOperationType = SelectOperationType.Add;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        app.getInputManager().addRawInputListener(this);
        currentCam = app.getCamera();
        currentSelection = new ArrayList<Spatial>();
        currentEntitiesSelection = new ArrayList<EntityId>();
        currentUI = new SelectRectUI(app, Vector4f.ZERO);
    }

    public static class SelectionAdd {

        Spatial sp;

        private SelectionAdd(Spatial sp) {
            this.sp = sp;
        }
    }

    public static class SelectionRemove {

        Spatial sp;

        private SelectionRemove(Spatial sp) {
            this.sp = sp;
        }
    }

    private void changes(CollisionResults collisionResults) {
//        Set<Spatial> result = filter(collisionResults);
        Set<Spatial> result = new HashSet<Spatial>();
        
        for (CollisionResult cr : collisionResults) {
            Geometry geo = cr.getGeometry();
            if (selectCondition.apply(geo)) {
                result.add(geo);
                selectFunction.execute(geo);
                eventBus.post(new SelectionAdd(geo));
            } else {
                result.remove(geo);
                deselectFunction.execute(geo);
            }
        }
        
//                        if (currentSelection.contains(geo)){
//                    eventBus.post(new SelectionRemove(geo));
//                    currentSelection.remove(geo);
//                }
        
        selectOperationType.execute(this, result);
    }

    private Collidable getCollidable() {
        //FIXME: Replace with supllier!
        return app.getWorldManager().getWorldNode();
    }

//    private Set<Spatial> filter(CollisionResults collisionResults) {
//        return null;
//    }

    public void beginInput() {
    }

    public void endInput() {
    }

    public void onJoyAxisEvent(JoyAxisEvent evt) {
    }

    public void onJoyButtonEvent(JoyButtonEvent evt) {
    }

    public void onMouseMotionEvent(MouseMotionEvent evt) {
        selectOperation.move(this, evt);
    }

    public void onMouseButtonEvent(MouseButtonEvent evt) {
        selectOperation.button(this, evt);
    }

    public void onKeyEvent(KeyInputEvent evt) {
    }

    public void onTouchEvent(TouchEvent evt) {
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

//        Set<Spatial> result = selectOperation.execute(this, tpf);
//        selectOperationType.execute(this, result);
        currentUI.update(tpf);
    }

    public void setCurrentMode(String mode) {
        if (mode.equals("SelectUnit")) {
//            setSelectFunction(unitSelect);
        } else if (mode.equals("SelectMulti")) {
//            setSelectFunction(switchableSelectFunc);
        } else {
//            setSelectFunction(null);
        }
    }

    public Ray createRay(Camera cam) {
        Vector3f origin = cam.getWorldCoordinates(app.getInputManager().getCursorPosition(), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(app.getInputManager().getCursorPosition(), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();

        // Convert screen click to 3d position
//        Vector2f click2d = app.getInputManager().getCursorPosition();
//        Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
//        Vector3f direction = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
        // Aim the ray from the clicked spot forwards.

        return new Ray(origin, direction);
    }

    public CollisionResults doShoot() {
        return doShoot(currentCam, getCollidable());
    }

    public CollisionResults doShoot(Camera cam, Collidable shootables) {
        Ray ray = createRay(cam);
        return collisionChecker.checkRayCollision(ray, shootables);
    }

    public PhysicsRayTestResult doShootPhysic(Camera cam, Node shootables) {
        Ray ray = createRay(cam);
        return physicCollisionChecker.checkRayCollision(ray, shootables);
    }

//    public boolean inRange() {
//        return true;
//    }
    public void setFirstButtonIndex(int normalButtonIndex) {
        this.firstButtonIndex = normalButtonIndex;
    }

    public int getFirstButtonIndex() {
        return firstButtonIndex;
    }

    public void setSecondButtonIndex(int secondButtonIndex) {
        this.secondButtonIndex = secondButtonIndex;
    }

    public int getSecondButtonIndex() {
        return secondButtonIndex;
    }

    public SelectOperationType getSelectOperationType() {
        return selectOperationType;
    }

    public SelectOperation getSelectOperation() {
        return selectOperation;
    }
}
