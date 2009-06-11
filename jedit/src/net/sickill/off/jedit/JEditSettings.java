/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.jedit;

import net.sickill.off.common.Settings;
import org.gjt.sp.jedit.jEdit;

/**
 *
 * @author kill
 */
public class JEditSettings extends Settings {
    private static JEditSettings settings;

    public static JEditSettings getInstance() {
        if (settings == null) {
            settings = new JEditSettings();
        }
        return settings;
    }

    @Override
    public void setBoolean(String prop, boolean b) {
        prop = "OffPlugin." + prop;
        jEdit.setBooleanProperty(prop, b);
    }

    @Override
    public boolean getBoolean(String prop, boolean def) {
        prop = "OffPlugin." + prop;
        return jEdit.getBooleanProperty(prop, def);
    }

    @Override
    public void setString(String prop, String s) {
        prop = "OffPlugin." + prop;
        jEdit.setProperty(prop, s);
    }

    @Override
    public String getString(String prop, String def) {
        prop = "OffPlugin." + prop;
        return jEdit.getProperty(prop, def);
    }

    @Override
    public void setInt(String prop, int i) {
        prop = "OffPlugin." + prop;
        jEdit.setIntegerProperty(prop, i);
    }

    @Override
    public int getInt(String prop, int def) {
        prop = "OffPlugin." + prop;
        return jEdit.getIntegerProperty(prop, def);
    }

    @Override
    public void setFloat(String prop, float f) {
        prop = "OffPlugin." + prop;
        jEdit.setDoubleProperty(prop, f);
    }

    @Override
    public float getFloat(String prop, float def) {
        prop = "OffPlugin." + prop;
        return (float)jEdit.getDoubleProperty(prop, def);
    }

    @Override
    public String getIgnoreMask() {
        return "(^|.*\\/)(\\.git|\\.svn)\\/.*" + super.getIgnoreMask();
    }
}
