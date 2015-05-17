package net.sickill.off.common;

/**
 *
 * @author kill
 */
public abstract class AbstractProject {
    protected OffListModel model;

    public abstract String getProjectRootPath(); // return path WITH trailing slash

    public void init(OffListModel model) {
        this.model = model;
    }

    public OffListModel getModel() {
      return model;
    }
}
