/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sickill.taz.netbeans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public final class OpenTazAction implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        JDialog dialog = new NetbeansDialog();
        dialog.setVisible(true);
    }
}
