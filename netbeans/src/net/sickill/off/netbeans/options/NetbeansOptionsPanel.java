package net.sickill.off.netbeans.options;

import net.sickill.off.common.OffOptionsPanel;
import net.sickill.off.netbeans.NetbeansProject;
import net.sickill.off.netbeans.NetbeansSettings;

/**
 * @author sickill
 */
public class NetbeansOptionsPanel extends OffOptionsPanel {

    public NetbeansOptionsPanel() {
        super();
        s = NetbeansSettings.getInstance();
    }

    @Override
    public void store() {
        super.store();
        NetbeansProject.getInstance().fetchProjectFiles();
    }

}
