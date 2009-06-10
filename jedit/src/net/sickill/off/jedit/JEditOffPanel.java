/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.jedit;

import net.sickill.off.common.AbstractProject;
import net.sickill.off.common.IDE;
import net.sickill.off.common.OffPanel;
import net.sickill.off.common.Settings;
import org.gjt.sp.jedit.gui.DefaultFocusComponent;

/**
 *
 * @author kill
 */
public class JEditOffPanel extends OffPanel implements DefaultFocusComponent {
    public JEditOffPanel(IDE i, Settings s, AbstractProject p) {
        super(i, s, p);
    }
}
