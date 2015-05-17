package net.sickill.off.jedit;

import net.sickill.off.common.Settings;
import net.sickill.off.common.Wildcard;
import org.gjt.sp.jedit.jEdit;

/**
 *
 * @author kill
 */
public class JEditSettings extends Settings {
    public static final String PREFIX = "OffPlugin.";
    private static JEditSettings settings;

    public static JEditSettings getInstance() {
        if (settings == null) {
            settings = new JEditSettings();
        }
        return settings;
    }

    @Override
    public void setBoolean(String prop, boolean b) {
        jEdit.setBooleanProperty(PREFIX + prop, b);
    }

    @Override
    public boolean getBoolean(String prop, boolean def) {
        return jEdit.getBooleanProperty(PREFIX + prop, def);
    }

    @Override
    public void setString(String prop, String s) {
        jEdit.setProperty(PREFIX + prop, s);
    }

    @Override
    public String getString(String prop, String def) {
        return jEdit.getProperty(PREFIX + prop, def);
    }

    @Override
    public void setInt(String prop, int i) {
        jEdit.setIntegerProperty(PREFIX + prop, i);
    }

    @Override
    public int getInt(String prop, int def) {
        return jEdit.getIntegerProperty(PREFIX + prop, def);
    }

    @Override
    public void setFloat(String prop, float f) {
        jEdit.setDoubleProperty(PREFIX + prop, f);
    }

    @Override
    public float getFloat(String prop, float def) {
        return (float)jEdit.getDoubleProperty(PREFIX + prop, def);
    }

    @Override
    public Wildcard getIgnoreWildcard() {
        if (ignoreWildcard == null) {
            ignoreWildcard = new Wildcard("*.svn/*;*.git/*;" + getIgnoreMask().trim());
        }
        return ignoreWildcard;
    }
}
