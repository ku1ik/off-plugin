/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans.options;

import net.sickill.off.common.OffOptionsPanel;
import net.sickill.off.netbeans.NetbeansProject;
import net.sickill.off.netbeans.NetbeansSettings;

/**
 *
 * @author kill
 */
public class NetbeansOptionsPanel extends OffOptionsPanel {
//    private final OffOptionsPanelController controller;

    public NetbeansOptionsPanel() {
        super();
        s = NetbeansSettings.getInstance();
    }

    public void store() {
        super.store();
        NetbeansProject.getInstance().fetchProjectFiles();
    }
}
