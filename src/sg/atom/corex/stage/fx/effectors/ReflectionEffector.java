/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage.fx.effectors;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import sg.atom.corex.stage.fx.Effector;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ReflectionEffector implements Effector {

    public void affect(Object object, String propertyName, Object value) {
        if (propertyName.contains(",")) {
            String[] split = StringUtils.split(propertyName, ",");
            for (String pName : split) {
                setProperty(object, pName, value);
            }
        } else {
            String pName = propertyName;
            setProperty(object, pName, value);
        }
    }
    
    
    public static void setProperty(Object target, String propertyName, Object value) {
//        try {
//            BeanUtils.setProperty(value, propertyName, value);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(ReflectionEffector.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvocationTargetException ex) {
//            Logger.getLogger(ReflectionEffector.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            FieldUtils.writeField(target, propertyName, value, true);
        } catch (IllegalAccessException ex) {
            setPropertySetter(target, propertyName, value);
        } catch (IllegalArgumentException ex) {
            setPropertySetter(target, propertyName, value);
        }
//        BeanUtil.setPropertyForced(value, propertyName, value);

    }

    public static void setPropertySetter(Object target, String propertyName, Object value) {
        try {
            String mName = "set" + StringUtils.capitalize(propertyName);
            MethodUtils.invokeExactMethod(target, mName, value);
        } catch (NoSuchMethodException ex1) {
        } catch (IllegalAccessException ex1) {
        } catch (InvocationTargetException ex1) {
        }
    }
}
