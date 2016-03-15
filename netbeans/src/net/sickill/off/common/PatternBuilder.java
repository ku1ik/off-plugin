package net.sickill.off.common;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author mjomble
 */
public class PatternBuilder {

    private final String wildcards;
    private boolean inText = false;
    private final List<StringBuilder> parts = new LinkedList<>();
    private StringBuilder currentPart = null;

    public static Pattern build(String wildcards) {
        return new PatternBuilder(wildcards).build();
    }

    private PatternBuilder(String wildcards) {
        this.wildcards = wildcards;
    }

    private void beginTextIfNeeded() {
        if (!inText) {
            currentPart.append("\\Q");
            inText = true;
        }
    }

    private void endTextIfNeeded() {
        if (inText) {
            currentPart.append("\\E");
            inText = false;
        }
    }

    private void newPart() {
        currentPart = new StringBuilder();
        parts.add(currentPart);
    }

    private Pattern build() {
        newPart();

        for (int i = 0; i < wildcards.length(); i++) {
            char c = wildcards.charAt(i);

            switch (c) {
                case '\n':
                    endTextIfNeeded();
                    newPart();
                    break;

                case '*':
                    endTextIfNeeded();
                    currentPart.append(".*");
                    break;

                case '/':
                case '\\':
                    endTextIfNeeded();
                    currentPart.append("(\\\\|\\/)");
                    break;

                default:
                    beginTextIfNeeded();
                    currentPart.append(c);
                    break;
            }
        }

        endTextIfNeeded();

        StringBuilder regex = new StringBuilder();

        for (StringBuilder part : parts) {
            if (part.length() > 0) {
                if (regex.length() > 0) {
                    regex.append('|');
                }

                regex.append(part);
            }
        }

        return Pattern.compile(regex.toString());
    }

}
