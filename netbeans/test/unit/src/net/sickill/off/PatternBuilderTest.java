package net.sickill.off;

import net.sickill.off.common.PatternBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author mjomble
 */
public class PatternBuilderTest {

    @Test
    public void testMatches() {
        assertPattern("*gems/*", ".*\\Qgems\\E(\\\\|\\/).*");
        assertPattern("jola*", "\\Qjola\\E.*");
        assertPattern("*.jp*g", ".*\\Q.jp\\E.*\\Qg\\E");

        assertPattern(
                "*.jpg\n*.bmp\njola.*\nmisio/*\n",
                ".*\\Q.jpg\\E|.*\\Q.bmp\\E|\\Qjola.\\E.*|\\Qmisio\\E(\\\\|\\/).*"
        );
    }

    private void assertPattern(String original, String expected) {
        String actual = PatternBuilder.build(original).pattern();
        Assert.assertEquals(expected, actual);
    }

}
