package net.sickill.off.netbeans;

import net.sickill.off.common.OffListModel;
import org.openide.modules.ModuleInstall;

/**
 * Starts indexing on current project at NetBeans startup
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
      final NetbeansSettings settings = NetbeansSettings.getInstance();
      OffListModel listModel = new OffListModel(settings);
      NetbeansProject.getInstance().init(listModel);
    }
}
