package net.sickill.off.common;

/**
 * @author sickill
 */
public abstract class AbstractProject {

    protected OffListModel model;

    public void init(OffListModel model) {
        this.model = model;
    }

    public OffListModel getModel() {
        return model;
    }

}
