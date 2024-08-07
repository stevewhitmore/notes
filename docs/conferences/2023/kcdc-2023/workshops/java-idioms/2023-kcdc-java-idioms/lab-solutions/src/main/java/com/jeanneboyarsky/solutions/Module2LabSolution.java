package com.jeanneboyarsky.solutions;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Module2LabSolution {

    // START getMiddleName()
    public Optional<String> getMiddleName(String text) {
        String[] segments = text.split(" ");
        if (segments.length != 3) {
            return Optional.empty();
        }
        return Optional.of(segments[1]);
    }
    // END getMiddleName()

    // START getAllDistinctNumbers()
    public Collection<Integer> getAllDistinctNumbers(String text) {
        Set<Integer> result = new TreeSet<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            String match = matcher.group();
            result.add(Integer.parseInt(match));
        }
        return result;
    }
    // END getAllDistinctNumbers()

    // START getAllDistinctNumbersEvenUnderscores()
    public Collection<Integer> getAllDistinctNumbersEvenUnderscores(String text) {
        Set<Integer> result = new TreeSet<>();
        Pattern pattern = Pattern.compile("[0-9_]+");
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            String match = matcher.group();
            String withoutUnderscores = match.replace("_", "");
            result.add(Integer.parseInt(withoutUnderscores));
        }
        return result;
    }
    // END getAllDistinctNumbersEvenUnderscores()

    // START removeNameInOneLine()
    public String removeNameInOneLine(String text) {
        return text.replaceFirst("(?m)Name:\\s*.*$","Name:");
    }
    // END removeNameInOneLine()

    // START removeNameWithPattern()
    public String removeNameWithPattern(String text) {
        Pattern pattern = Pattern.compile("Name:\\s*.*\n");
        Matcher matcher = pattern.matcher(text);
        StringBuilder result = new StringBuilder();
        while(matcher.find()) {
            matcher.appendReplacement(result, "Name:\n");
        }
        matcher.appendTail(result);
        return result.toString();
    }
    // END removeNameWithPattern()

    /*
     * Challenge: Tricky regex
     */
    // START removeStarsFromFrontAndBack()
    public String removeStarsFromFrontAndBack(String text) {
        String withoutLeading = text.replaceFirst(
                "^\\*+", "");
        String withoutTrailing = withoutLeading.replaceFirst(
                "\\*+$", "");
        return withoutTrailing.trim();
    }
    // END removeStarsFromFrontAndBack()
}
