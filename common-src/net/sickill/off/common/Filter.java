package net.sickill.off.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kill
 */
public class Filter {
    private String stringPattern;
    private Pattern pattern;

    public Filter(String filter, Settings settings) {
        stringPattern = filter;

        if (!settings.isMatchFromStart()) {
            filter = "*" + filter;
        }
        filter = filter.toLowerCase().replaceAll("\\*{2,}", "*");

        String regex = "(?:[_])?";

        if (settings.isSmartMatch()) {
            String[] chars = filter.split("");
            for (String c : chars) {
                if (c.equals(""))
                    continue;
                if (c.equals("*")) {
                    regex += ".*?";
                } else {
                    regex += "(\\Q" + c + "\\E)[^\\/]*?"; // \Q \E quoting
                }
            }
        } else {
            regex += (filter + "*").replaceAll("([^\\*]+)", "\\\\Q$1\\\\E").replaceAll("\\*", "[^\\/]*?");
        }

        pattern = Pattern.compile(regex);
    }

    public boolean matches(String s) {
        return matcher(s).matches();
    }

    public String toString() {
        return stringPattern;
    }

    Matcher matcher(String txt) {
        return pattern.matcher(txt);
    }

}
