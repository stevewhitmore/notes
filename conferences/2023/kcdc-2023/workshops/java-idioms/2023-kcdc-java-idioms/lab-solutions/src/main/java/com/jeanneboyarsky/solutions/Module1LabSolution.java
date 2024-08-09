package com.jeanneboyarsky.solutions;

import java.util.ArrayList;
import java.util.List;

public class Module1LabSolution {
    // START pipeSeparatedValuesOfAllNoneWhitespaceStrings()
    public String pipeSeparatedValuesOfAllNoneWhitespaceStrings(String... strings) {
        List<String> values = new ArrayList<>();
        for(String current: strings) {
            if (! current.isBlank()) {
                values.add(current);
            }
        }
        return String.join("|", values);
    }
    // END pipeSeparatedValuesOfAllNoneWhitespaceStrings()

    // START threeCopiesOfFirstStringContainingKC()
    public String threeCopiesOfFirstStringContainingKC(List<String> strings) {
        for(String current: strings) {
            if (current.contains("KC")) {
                return current.repeat(3);
            }
        }
        return "";
    }
    // END threeCopiesOfFirstStringContainingKC()

    // START allCaseInsensitiveMatchesOfTree()
    public List<String> allCaseInsensitiveMatchesOfTree(List<String> strings) {
        List<String> result = new ArrayList<>();
        for(String current: strings) {
            if (current.equalsIgnoreCase("Tree")) {
                result.add(current);
            }
        }
        return result;
    }
    // END allCaseInsensitiveMatchesOfTree()

    // START formatConstant()
    public String formatConstant(String label, double constant) {
        return "%s=%5.3f".formatted(label, constant);
    }
    // END formatConstant()

    // START createTicTacToeBoardWithTextBlock()
    public String createTicTacToeBoardWithTextBlock(char[][] values) {
        return """
                --------------
                | %s | %s | %s |
                --------------
                | %s | %s | %s |
                --------------
                | %s | %s | %s |
                --------------""".formatted(
                values[0][0], values[0][1], values[0][2],
                values[1][0], values[1][1], values[1][2],
                values[2][0], values[2][1], values[2][2]);
    }
    // END createTicTacToeBoardWithTextBlock()

    // START createBlankTicTacToeBoardWithoutTextBlock()
    public String createBlankTicTacToeBoardWithoutTextBlock() {
        String solidLine = "-".repeat(10);
        String lineWithCells = "|  ".repeat(3) + "|";

        List<String> lines = new ArrayList<>();
        lines.add(solidLine);
        lines.add(lineWithCells);
        lines.add(solidLine);
        lines.add(lineWithCells);
        lines.add(solidLine);
        lines.add(lineWithCells);
        lines.add(solidLine);

        return String.join("\n", lines);
    }
    // END createBlankTicTacToeBoardWithoutTextBlock()

}
