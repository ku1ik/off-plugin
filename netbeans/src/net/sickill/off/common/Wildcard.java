package net.sickill.off.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kill
 */
public class Wildcard {
    private String wildcard;
    private Pattern pattern;

    public Wildcard(String wildcard) {
        this.wildcard = wildcard.replaceAll("[;,]", "\n");
    }

    public Pattern getPattern() {
        if (pattern == null) {
            String[] masks = wildcard.split("[\n;,]");
            String regex = "";
            for (String mask : masks) {
                if (!mask.equals("")) {
                    regex += "(" + Pattern.quote(mask).replaceAll("\\*", "\\\\E.*\\\\Q") + ")|";
                }
            }
            regex = regex.replaceFirst("\\|$", "").replaceAll("\\\\Q\\\\E", "");
            pattern = Pattern.compile(regex);
        }
        return pattern;
    }

    public Matcher matcher(String subject) {
        return getPattern().matcher(subject);
    }

    public boolean matches(String subject) {
        return matcher(subject).matches();
    }

}
