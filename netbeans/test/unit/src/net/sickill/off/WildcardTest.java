package net.sickill.off;

import net.sickill.off.common.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author sickill
 */
public class WildcardTest {

    @Test
    public void testMatches() {
        Wildcard w = new Wildcard("jola*");
        assertTrue(w.matches("jola1"));
        assertTrue(w.matches("jola/misio"));

        w = new Wildcard("*jola");
        assertTrue(w.matches("misio/jola"));
        assertFalse(w.matches("misio/jola1"));

        w = new Wildcard("*.jp*g");
        assertTrue(w.matches("jola.jpg"));
        assertTrue(w.matches("jola.jpeg"));
        assertTrue(w.matches("misiojpg.jpg"));
        assertFalse(w.matches("misio.jpg.txt"));

        w = new Wildcard("*.jpg;*.bmp,jola.*\nmisio/*,");
        assertTrue(w.matches("1.jpg"));
        assertTrue(w.matches("22.bmp"));
        assertTrue(w.matches("jola.foo"));
        assertTrue(w.matches("misio/bar"));
    }

}
