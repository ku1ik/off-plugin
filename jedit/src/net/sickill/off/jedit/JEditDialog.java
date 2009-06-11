/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.jedit;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import net.sickill.off.common.OffDialog;
import net.sickill.off.common.OffPanel;
import net.sickill.off.common.Settings;
import org.gjt.sp.jedit.View;

/**
 *
 * @author kill
 */
public class JEditDialog extends OffDialog implements KeyListener {
    OffPanel off;

    public JEditDialog(View view, OffPanel off) {
        super(view, "Open File Fast");
        this.off = off;
        addKeyListener(this);
		getContentPane().add(off, BorderLayout.CENTER);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
            e.consume();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    void showDialog() {
		setVisible(true);
		off.focusOnDefaultComponent();
    }

    @Override
    protected Settings getSettings() {
        return JEditSettings.getInstance();
    }
}
