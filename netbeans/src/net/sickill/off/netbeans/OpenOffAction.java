/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sickill.off.netbeans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class OpenOffAction implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        NetbeansDialog dialog = NetbeansDialog.getInstance();
        dialog.showDialog();
    }
}
