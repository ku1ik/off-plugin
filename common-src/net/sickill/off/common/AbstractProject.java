/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.common;

/**
 *
 * @author kill
 */
public interface AbstractProject {

    public void init(OffListModel model);
    public String getProjectRootPath(); // return path WITH trailing slash
}
