/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import net.sickill.off.common.Settings;
import net.sickill.off.common.OffPanel;
import org.openide.util.NbPreferences;

/**
 *
 * @author kill
 */
public class NetbeansSettings extends Settings {
    private static NetbeansSettings settings;

    public static NetbeansSettings getInstance() {
        if (settings == null) {
            settings = new NetbeansSettings();
        }
        return settings;
    }

    @Override
    public void setBoolean(String prop, boolean b) {
        NbPreferences.forModule(OffPanel.class).putBoolean(prop, b);
    }

    @Override
    public boolean getBoolean(String prop, boolean def) {
        return NbPreferences.forModule(OffPanel.class).getBoolean(prop, def);
    }

    @Override
    public void setString(String prop, String s) {
        NbPreferences.forModule(OffPanel.class).put(prop, s);
    }

    @Override
    public String getString(String prop, String def) {
        return NbPreferences.forModule(OffPanel.class).get(prop, def);
    }

    @Override
    public void setInt(String prop, int i) {
        NbPreferences.forModule(OffPanel.class).putInt(prop, i);
    }

    @Override
    public int getInt(String prop, int def) {
        return NbPreferences.forModule(OffPanel.class).getInt(prop, def);
    }

    @Override
    public void setFloat(String prop, float f) {
        NbPreferences.forModule(OffPanel.class).putFloat(prop, f);
    }

    @Override
    public float getFloat(String prop, float def) {
        return NbPreferences.forModule(OffPanel.class).getFloat(prop, def);
    }

}
