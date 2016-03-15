package net.sickill.off;

import java.util.regex.Matcher;
import net.sickill.off.common.Filter;
import net.sickill.off.common.Highlighter;
import net.sickill.off.common.Settings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mjomble
 */
public class HighlighterTest {

    private Settings settings;

    @Before
    public void setUp() {
        settings = new FakeSettings(); // smart = true, matchFromStart = true
    }

    @Test
    public void test() {
        settings.setMatchFromStart(false);
        assertHighlight("foobar2000", "bar*2*", "foo<b>b</b><b>a</b><b>r</b><b>2</b>000");
        assertHighlight("foobar2000", "b*r*2*", "foo<b>b</b>a<b>r</b><b>2</b>000");
        assertHighlight("fo/bar", "o/b", "f<b>o</b><b>/</b><b>b</b>ar");
        assertHighlight("fo\\bar", "o/b", "f<b>o</b><b>\\</b><b>b</b>ar");
        assertHighlight("rakefile", "rae", "<b>r</b><b>a</b>k<b>e</b>file");
        assertHighlight("_partial", "r", "_pa<b>r</b>tial");
        assertHighlight("javafoo.java", "java", "<b>j</b><b>a</b><b>v</b><b>a</b>foo.java");
    }

    private void assertHighlight(String input, String filterString, String expected) {
        Filter filter = new Filter(filterString, settings);
        Matcher matcher = filter.matcher(input);
        matcher.matches();
        String actual = Highlighter.highlight(input, matcher);
        Assert.assertEquals(expected, actual);
    }

}
