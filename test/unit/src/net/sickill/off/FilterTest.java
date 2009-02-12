/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

import java.util.regex.Pattern;
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
public class FilterTest {
    private Settings settings;

    public FilterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        settings = new FakeSettings(); // smart = true, matchFromStart = true
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testNormalMatch() {
        settings.setSmartMatch(false);
        assertTrue(new Filter("foo", settings).matches("foobar.rb"));
        assertFalse(new Filter("foo", settings).matches("foob/ar.rb"));
        assertTrue(new Filter("Foo", settings).matches("foobar.rb"));
        assertFalse(new Filter("foo", settings).matches("floobar"));
        assertTrue(new Filter("*bar", settings).matches("foobar.rb"));
        assertTrue(new Filter("ba*r", settings).matches("bazaar"));
        assertTrue(new Filter("ba\\", settings).matches("ba\\"));
        assertTrue(new Filter("ba\\nk", settings).matches("ba\\nk"));
        assertFalse(new Filter("ba\\", settings).matches("bank"));

        // with dir
        assertTrue(new Filter("app/mo", settings).matches("app/model1"));

        settings.setMatchFromStart(false);
        assertTrue(new Filter("bar", settings).matches("foobar.rb"));
        assertTrue(new Filter("Bar", settings).matches("foobar"));
        assertFalse(new Filter("bar", settings).matches("fublar"));
        assertTrue(new Filter("ba*r", settings).matches("thebazaar"));
    }

    @Test
    public void testSmartMatch() {
        assertTrue(new Filter("fbr", settings).matches("foobar.rb"));
        assertTrue(new Filter("f*b*r*", settings).matches("foobar.rb"));
        assertTrue(new Filter("FBR", settings).matches("foob.rb"));
        assertTrue(new Filter("kab.c", settings).matches("kab.c"));
        assertTrue(new Filter("kab.c", settings).matches("kabal.c"));
        assertFalse(new Filter("blan.cak", settings).matches("blanecak"));
        assertTrue(new Filter("*br2", settings).matches("foobar2000"));
        assertTrue(new Filter("ba\\nk", settings).matches("bla\\ncak"));

        // with dir
        assertTrue(new Filter("a/m/us", settings).matches("app/models/user.rb"));
        assertFalse(new Filter("a/m/us", settings).matches("app.models.user.rb"));

        settings.setMatchFromStart(false);
        assertTrue(new Filter("br2", settings).matches("foobar2000"));
        assertTrue(new Filter("b*r*2*", settings).matches("foobar2000"));

    }

}