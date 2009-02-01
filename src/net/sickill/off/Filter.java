/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

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

        String regex;
		if (settings.isSmartMatch()) {
			String[] chars = filter.split("");
			regex = "";
			for (String c : chars) {
				if (!c.equals("")) {
                    if (c.equals("."))
                        c = "\\.";

                    if (c.equals("*")) {
                        regex += ".*?";
                    } else {
                        regex += c + "([^\\/]*?)";
                    }
				}
			}
		} else {
			regex = (filter + "*").replaceAll("\\.", "\\\\.").replaceAll("\\*", "[^\\/]*?");
		}

        pattern = Pattern.compile(regex);
    }

    public boolean matches(String s) {
        return matcher(s).matches();
    }

//    int length() {
//        return stringPattern.length();
//    }

    public String toString() {
        return stringPattern;
    }

    Matcher matcher(String txt) {
        return pattern.matcher(txt);
    }

}
