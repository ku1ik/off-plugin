/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kill
 */
public class OffListModelTest {
    private Settings settings;
    private ProjectProvider projectProvider;
    private OffListModel model;

    public OffListModelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        settings = new TestSettings();
        projectProvider = new TestProjectProvider();
        model = new OffListModel(settings, projectProvider);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMinPatternLength() {
        assertTrue(model.setFilter(""));
        assertEquals(0, model.getSize());
        assertTrue(model.setFilter("u"));
        assertEquals(0, model.getSize());
        assertTrue(model.setFilter("us"));
        assertEquals(0, model.getSize());
        assertTrue(model.setFilter("use"));
        assertTrue(model.getSize() > 0);
    }

    @Test
    public void testFilterWithPath() {
        assertTrue(model.setFilter("a/m/ut"));
        assertEquals(1, model.getSize());
    }

    @Test
    public void testPopularitySorting() {
        model.setFilter("***");
        assertFalse(elementNameMatches(0, "Rakefile"));
        model.incrementAccessCounter(fileByName("Rakefile"));
        model.refresh();
        assertTrue(elementNameMatches(0, "Rakefile"));
    }
    
    @Test
    public void testNameSorting() {
        settings.setSmartMatch(false);
        model.setFilter("***");
        assertTrue(elementNameMatches(0, "helper.rb"));
        assertTrue(elementNameMatches(model.getSize()-1, "zone.cfg"));
    }
    
    @Test
    public void testNoSorting() {
        settings.setSmartMatch(false);
        settings.setNameSorting(false);
        model.setFilter("***");
        assertTrue(elementNameMatches(0, "README"));
        assertTrue(elementNameMatches(model.getSize()-1, "user_topic.rb"));
    }

    @Test
    public void testDistanceSorting() {
        model.setFilter("rae");
        assertTrue(model.getSize() == 2);
        assertTrue(elementNameMatches(0, "Rakefile"));
        assertTrue(elementNameMatches(1, "README"));

        model.setFilter("ust");
        assertTrue(model.getSize() == 3);
        assertTrue(elementNameMatches(0, "user_test.rb"));
        assertTrue(elementNameMatches(1, "user_topic.rb"));
        assertTrue(elementNameMatches(2, "users_controller.rb"));

        model.setFilter("hlr");
        assertTrue(model.getSize() == 2);
        assertTrue(elementNameMatches(0, "hlr.rb"));
        assertTrue(elementNameMatches(1, "helper.rb"));

        model.setFilter("index");
        assertTrue(model.getSize() == 3);
        assertTrue(elementPathMatches(0, "app/views/elements/index.html"));
        assertTrue(elementPathMatches(1, "app/views/topics/index.html"));
        assertTrue(elementPathMatches(2, "app/views/users/index.html"));
    }

    @Test
    public void testPrioritySorting() {
    }
    
    @Test
    public void testSortingOrder() {
    } 

    
    
    // -----------------------------------------------------------------------------------
    
    // helpers

    private boolean elementNameMatches(int index, String n) {
        OffListElement ole = (OffListElement)model.getElementAt(index);
        return ole.getFile().getName().equals(n);
    }

    private boolean elementPathMatches(int index, String n) {
        OffListElement ole = (OffListElement)model.getElementAt(index);
        return ole.getFile().getPathInProject().equals(n);
    }

    private ProjectFile fileByName(String n) {
      return ((TestProjectProvider)projectProvider).getFileByName(n);
    }
}