package net.sickill.off;

import net.sickill.off.common.Filter;
import net.sickill.off.common.Settings;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author sickill
 */
public class FilterTest {

  private Settings settings;

  @Before
  public void setUp() {
    settings = new FakeSettings(); // smart = true, matchFromStart = true
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

    // from start, name with underscore
    assertTrue(new Filter("partial", settings).matches("_partial"));

    // with dir
    assertTrue(new Filter("app/mo", settings).matches("app/model1"));

    settings.setMatchFromStart(false);
    assertTrue(new Filter("bar", settings).matches("foobar.rb"));
    assertTrue(new Filter("Bar", settings).matches("foobar"));
    assertFalse(new Filter("bar", settings).matches("fublar"));
    assertTrue(new Filter("ba*r", settings).matches("thebazaar"));

    // anywhere, name with underscore
    assertTrue(new Filter("partial", settings).matches("_partial"));
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

    // from start, name with underscore
    assertTrue(new Filter("partial", settings).matches("_partial"));

    // with dir
    assertTrue(new Filter("a/m/us", settings).matches("app/models/user.rb"));
    assertFalse(new Filter("a/m/us", settings).matches("app.models.user.rb"));

    settings.setMatchFromStart(false);
    assertTrue(new Filter("br2", settings).matches("foobar2000"));
    assertTrue(new Filter("b*r*2*", settings).matches("foobar2000"));

    // Searching by dir name, with and without slash in filter
    assertFalse(new Filter("mo", settings).matches("app/models/user.rb"));
    assertFalse(new Filter("mo", settings).matches("app\\models\\user.rb"));
    assertTrue(new Filter("mo/", settings).matches("app/models/user.rb"));
    assertTrue(new Filter("mo/", settings).matches("app\\models\\user.rb"));
    assertTrue(new Filter("mo\\", settings).matches("app/models/user.rb"));
    assertTrue(new Filter("mo\\", settings).matches("app\\models\\user.rb"));

    // anywhere, name with underscore
    assertTrue(new Filter("partial", settings).matches("_partial"));
  }

}
