package net.sickill.off.common;

import java.util.regex.Matcher;

/**
 * @author mjomble
 */
public class Highlighter {

  public static String highlight(String string, Matcher matcher) {
    StringBuilder label = new StringBuilder();
    int lastEnd = 0;

    for (int i = 1; i <= matcher.groupCount(); i++) {
      label.append(string, lastEnd, matcher.start(i));
      label.append("<b>");
      label.append(string.charAt(matcher.start(i)));
      label.append("</b>");
      lastEnd = matcher.end(i);
    }

    label.append(string, lastEnd, string.length());
    return label.toString();
  }

}
