package net.sickill.off;

import net.sickill.off.common.OffListModel;
import net.sickill.off.common.OffListElement;
import net.sickill.off.common.AbstractProject;
import net.sickill.off.common.Settings;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author sickill
 */
public class OffListModelTest {

    private Settings settings;
    private AbstractProject project;
    private OffListModel model;

    @Before
    public void setUp() {
        settings = new FakeSettings();
        project = new FakeProject();
        model = new OffListModel(settings);
        project.init(model);
    }

    @Test
    public void testMinPatternLength() {
        model.setFilter("");
        model.refilter();
        assertEquals(0, model.getSize());
        model.setFilter("u");
        model.refilter();
        assertEquals(0, model.getSize());
        model.setFilter("us");
        model.refilter();
        assertEquals(0, model.getSize());
        model.setFilter("use");
        model.refilter();
        assertTrue(model.getSize() > 0);
    }

    @Test
    public void testFilterWithPath() {
        model.setFilter("a/m/ut");
        model.refilter();
        assertEquals(1, model.getSize());

        model.setFilter("a\\m\\ut");
        model.refilter();
        assertEquals(1, model.getSize());

        model.setFilter("a/m\\ut");
        model.refilter();
        assertEquals(1, model.getSize());

        model.setFilter("a\\m/ut");
        model.refilter();
        assertEquals(1, model.getSize());
    }

    @Test
    public void testPopularitySorting() {
        model.setFilter("***");
        model.refilter();
        assertFalse(elementNameMatches(0, "Rakefile"));
        model.incrementAccessCounter(findFileInProject("Rakefile"));
        model.refilter();
        assertTrue(elementNameMatches(0, "Rakefile"));
    }

    @Test
    public void testNameSorting() {
        settings.setSmartMatch(false);
        model.setFilter("***");
        model.refilter();
        assertTrue(elementNameMatches(0, "helper.rb"));
        assertTrue(elementNameMatches(model.getSize() - 1, "zone.cfg"));
    }

    @Test
    public void testDistanceSorting() {
        model.setFilter("rae");
        model.refilter();
        assertTrue(model.getSize() == 2);
        assertTrue(elementNameMatches(0, "Rakefile"));
        assertTrue(elementNameMatches(1, "README"));

        model.setFilter("ust");
        model.refilter();
        assertTrue(model.getSize() == 3);
        assertTrue(elementNameMatches(0, "user_test.rb"));
        assertTrue(elementNameMatches(1, "user_topic.rb"));
        assertTrue(elementNameMatches(2, "users_controller.rb"));

        model.setFilter("hlr");
        model.refilter();
        assertTrue(model.getSize() == 2);
        assertTrue(elementNameMatches(0, "hlr.rb"));
        assertTrue(elementNameMatches(1, "helper.rb"));

        model.setFilter("index");
        model.refilter();
        assertTrue(model.getSize() == 3);
        assertTrue(elementPathMatches(0, "app/views/users/index.html"));
        assertTrue(elementPathMatches(1, "app/views/topics/index.html"));
        assertTrue(elementPathMatches(2, "app/views/elements/index.html"));
    }

    @Test
    public void testPrioritySorting() {
        settings.setLessPriorityMask("*inde*");
        model.setFilter("***");
        model.refilter();
        assertTrue(elementPathMatches(model.getSize() - 3, "app/views/users/index.html"));
        assertTrue(elementPathMatches(model.getSize() - 2, "app/views/topics/index.html"));
        assertTrue(elementPathMatches(model.getSize() - 1, "app/views/elements/index.html"));

        settings.setLessPriorityMask("*Thumbs*");
        model.refilter();
        assertTrue(elementPathMatches(model.getSize() - 1, "jola/Thumbs.db"));
    }

    @Test
    public void testIgnoreMask() {
        model.setFilter("***");
        model.refilter();
        assertTrue(findFileInResults("zone.cfg") != null);
        assertTrue(findFileInResults("Thumbs.db") != null);
        assertTrue(findFileInResults("jola.pl") == null);

        settings.setIgnoreMask("*.cfg");
        project.init(model);
        model.refilter();
        assertTrue(findFileInResults("zone.cfg") == null);

        settings.setIgnoreMask("*Thumbs.db*");
        project.init(model);
        model.refilter();
        assertTrue(findFileInResults("Thumbs.db") == null);
    }

    @Test
    public void testLabel() {
        // README
        // lib/tags.rb
        // app/models/user_topic.rb
        OffListElement ole;

        model.setFilter("readme");
        model.refilter();
        ole = model.getElementAt(0);
        ole.getHTMLHighlightedFileName();

        model.setFilter("tags");
        model.refilter();
        ole = model.getElementAt(0);
        ole.getHTMLHighlightedFileName();

        model.setFilter("user_topic");
        model.refilter();
        ole = model.getElementAt(0);
        ole.getHTMLHighlightedFileName();

        String[] filters = {"a/m/", "a/m/u"};

        for (String f : filters) {
            model.setFilter(f);
            model.refilter();
            ole = model.getElementAt(0);
            ole.getHTMLHighlightedFileName();
        }
    }

    // -----------------------------------------------------------------------------------
    // helpers
    private OffListElement findFileInResults(String name) {
        for (int i = 0; i < model.getSize(); i++) {
            final String fileName = model.getElementAt(i).getPlainFileName();

            if (fileName.equals(name)) {
                return model.getElementAt(i);
            }
        }

        return null;
    }

    private boolean elementNameMatches(int index, String name) {
        OffListElement ole = model.getElementAt(index);
        return ole.getPlainFileName().equals(name);
    }

    private boolean elementPathMatches(int index, String path) {
        OffListElement ole = model.getElementAt(index);
        return ole.getFileRelativeToProjectRoot().equals(path);
    }

    private String findFileInProject(String name) {
        return ((FakeProject) project).getFileByName(name).getFullPath();
    }

}
