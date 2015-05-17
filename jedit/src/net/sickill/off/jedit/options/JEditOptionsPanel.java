package net.sickill.off.jedit.options;

import java.awt.Component;
import net.sickill.off.common.OffOptionsPanel;
import net.sickill.off.jedit.JEditSettings;
import org.gjt.sp.jedit.OptionPane;

// use tokens highlighting
public class JEditOptionsPanel extends OffOptionsPanel implements OptionPane {

  public JEditOptionsPanel() {
    super();
    s = JEditSettings.getInstance();
  }

  @Override
  public void init() {
    load();
  }

  @Override
  public void save() {
    store();
  }

  @Override
  public Component getComponent() {
    return this;
  }

}
