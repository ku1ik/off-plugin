/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.jedit;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import net.sickill.off.common.OffPanel;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.View;

/**
 *
 * @author kill
 */
public class JEditDialog extends JDialog implements ComponentListener, KeyListener {
    OffPanel off;

    public JEditDialog(View view, OffPanel off) {
        super(view, "Open File Fast");
        this.off = off;
        addComponentListener(this);
        addNotify();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        addKeyListener(this);
        setSize(554, 182);
        setLocationRelativeTo(null);
//		GUIUtilities.loadGeometry(this, "OffPlugin.window");
		getContentPane().add(off, BorderLayout.CENTER);
    }

    public void componentResized(ComponentEvent e) {
        GUIUtilities.saveGeometry(this, "OffPlugin.window");
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
//            closeMainWindow();
            dispose();
            e.consume();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    void showDialog() {
		setVisible(true);
		off.focusOnDefaultComponent();
		//dialog.addFocusListener(new TazFocusListener());
    }
}
