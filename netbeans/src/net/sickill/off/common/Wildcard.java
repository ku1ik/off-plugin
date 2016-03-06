package net.sickill.off.common;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sickill
 */
public class Wildcard {

    private String wildcard;
    private Pattern pattern;

    public Wildcard(String wildcard) {
        this.wildcard = wildcard.replaceAll("[;,]", "\n");
        pattern = PatternBuilder.build(this.wildcard);
    }

    private Matcher matcher(String subject) {
        return pattern.matcher(subject);
    }

    public boolean matches(String subject) {
        return matcher(subject).matches();
    }

    public boolean matches(Path subject) {
        String string = subject.toString();
        return matcher(string).matches();
    }

}
