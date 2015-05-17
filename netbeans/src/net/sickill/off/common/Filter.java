package net.sickill.off.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sickill
 */
public class Filter {

  private String stringPattern;
  private Pattern pattern;

  public Filter(String stringPattern, Settings settings) {
    this.stringPattern = stringPattern;
    String filter = stringPattern;

    if (!settings.isMatchFromStart()) {
      filter = "*" + filter;
    }

    filter = filter.toLowerCase().replaceAll("\\*+", "*");

    StringBuilder regex = new StringBuilder("_?");

    if (settings.isSmartMatch()) {
      for (int i = 0; i < filter.length(); i++) {
        char c = filter.charAt(i);

        if (c == '*') {
          regex.append(".*?");
        }
        else {
          String charPattern = "\\Q" + c + "\\E";

          // Consider slashes and backslashes interchangeable
          if (c == '\\' || c == '/') {
            charPattern = "\\\\|\\/";
          }

          regex.append('(').append(charPattern).append(")[^\\/\\\\]*?");
        }
      }
    }
    else {
      String filterPattern = (filter + "*")
        .replaceAll("([^\\*]+)", "\\\\Q$1\\\\E")
        .replaceAll("\\*", Matcher.quoteReplacement("[^/\\\\]*?"))
      ;

      regex.append(filterPattern);
    }

    pattern = Pattern.compile(regex.toString());
  }

  public boolean matches(String s) {
    return matcher(s).matches();
  }

  @Override
  public String toString() {
    return stringPattern;
  }

  public Matcher matcher(String txt) {
    return pattern.matcher(txt);
  }

}
