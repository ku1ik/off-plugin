package net.sickill.off.jedit;

import net.sickill.off.common.AbstractProject;
import net.sickill.off.common.IDE;
import net.sickill.off.common.OffPanel;
import net.sickill.off.common.Settings;
import org.gjt.sp.jedit.gui.DefaultFocusComponent;

/**
 * @author sickill
 */
public class JEditOffPanel extends OffPanel implements DefaultFocusComponent {

  public JEditOffPanel(IDE i, Settings s, AbstractProject p) {
    super(i, s, p);
  }

}
