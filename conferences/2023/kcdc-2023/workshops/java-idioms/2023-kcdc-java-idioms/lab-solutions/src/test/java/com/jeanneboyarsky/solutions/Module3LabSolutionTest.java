package com.jeanneboyarsky.solutions;

import com.jeanneboyarsky.rules.CodeRulesForMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.jeanneboyarsky.solutions.Module3LabSolution.*;
import static org.junit.jupiter.api.Assertions.*;

public class Module3LabSolutionTest {

    private Module3LabSolution target;
    private Map<String, Workshop> workshops;
    private CodeRulesForMethods codeRules;

    @BeforeEach
    void setUp() {
        target = new Module3LabSolution();
        Path folder = Paths.get("lab-solutions/src/main/java/");
        codeRules = new CodeRulesForMethods(folder, "com.jeanneboyarsky.solutions",
                "Module3LabSolution.java");
    }

    @BeforeEach
    void kcdcWorkshops() {
        workshops = Map.ofEntries(
                Map.entry("Performance",
                        new Workshop("Fundamentals of Web Performance", 2201,
                                "Todd Gardner")),
                Map.entry("Career",
                        new Workshop("Charting a Course to Your Dream Job", 2202
                                , "Cassandra Faris")),
                Map.entry("Terraform-Ansible",
                        new Workshop("Create a Cloud Environment with Terraform and Ansible", 2203,
                                "Gene Gotimer")),
                Map.entry("Data-Science",
                        new Workshop("Data Science: Zero to Hero", 2204,
                                "Gary Short")),
                Map.entry("Cypress",
                        new Workshop("End-to-End and Component Testing with Cypress", 2205,
                                "Mark Noonan")),
                Map.entry("GitHub",
                        new Workshop("End to End DevOps with GitHub", 2206,
                                "Damian Brady")),
                Map.entry("FERNI",
                        new Workshop("Freaky-Fast Full Stack with the FERNI Stack",
                                2207, "Keith Kurak")),
                Map.entry("Spring",
                        new Workshop("Getting Started with Spring", 2208,
                                "Dan Vega", "DaShaun Carter")),
                Map.entry("Security",
                        new Workshop("To the Left, to the Left: All your Security Shifted to the Left", 2210,
                                "Eddie Knight", "Theresa Mammarella")),
                Map.entry("Architecture",
                        new Workshop("Growing your career in architecture: 8 experiences to prepare you to be a professional architect", 2211,
                                "Brian Loomis")),
                Map.entry("Remix",
                        new Workshop("Remix Fundamentals", 2212,
                                "RJake Ginnivan")));
    }

    // ---------------------------------------------------------

    @Test
    void constructor() {
        Workshop actual = new Workshop("title", 999, "speaker");
        assertEquals("title", actual.getTitle());
        assertEquals(999, actual.getRoomNumber());
        List<String> presenters = actual.getPresenters();
        assertEquals(1, presenters.size(), "# presenters");
        assertEquals("speaker", presenters.get(0));
    }

    @Test
    void constructorParameterDoesNotAffectValue() {
        String[] array = new String[] { "original"};
        Workshop actual = new Workshop("title", 999, array);
        array[0] = "new";
        List<String> presenters = actual.getPresenters();
        assertEquals(1, presenters.size(), "# presenters");
        assertEquals("original", presenters.get(0));
    }

    @Test
    void getPresentersImmutable() {
        Workshop actual = new Workshop("title", 999, "speaker");
        List<String> presenters1 = actual.getPresenters();
        List<String> presenters2 = actual.getPresenters();
        assertNotSame(presenters1, presenters2);
    }

    // ---------------------------------------------------------

    @Test
    void getOptionalByKey_match() {
        Optional<Workshop> actual = target.getOptionalByKey(workshops, "Career");
        assertEquals("Charting a Course to Your Dream Job", actual.get().getTitle());
    }

    @Test
    void getOptionalByKey_noMatch() {
        Optional<Workshop> actual = target.getOptionalByKey(workshops, "Bad");
        assertFalse(actual.isPresent());
    }

    @Test
    void requirements_getOptionalByKey() {
        assertFalse(codeRules.containsIf("getOptionalByKey"),
                "cannot contain if statement");
        assertFalse(codeRules.containsLoop("getOptionalByKey"),
                "cannot contain a loop");
        assertFalse(codeRules.containsStream("getOptionalByKey"),
                "cannot contain stream");
    }

    // ---------------------------------------------------------

    @Test
    void getPresentersNoStream_foundSingle() {
        List<String> expected = List.of("Todd Gardner");
        List<String> actual = target.getPresentersNoStream(workshops, "Fundamentals of Web Performance");
        assertEquals(expected, actual);
    }

    @Test
    void getPresentersNoStream_foundTwo() {
        List<String> expected = List.of("Dan Vega", "DaShaun Carter");
        List<String> actual = target.getPresentersNoStream(workshops, "Getting Started with Spring");
        assertEquals(expected, actual);
    }

    @Test
    void getPresentersNoStream_foundNone() {
        List<String> actual = target.getPresentersNoStream(workshops, "Bad");
        assertEquals(0, actual.size());
    }

    @Test
    void requirements_getPresentersNoStream() {
        assertFalse(codeRules.containsIf("getPresentersNoStream"),
                "cannot contain if statement");
        assertFalse(codeRules.containsLoop("getPresentersNoStream"),
                "cannot contain a loop");
        assertFalse(codeRules.containsStream("getPresentersNoStream"),
                "cannot contain stream");
    }

    // ---------------------------------------------------------

    @Test
    void getPresentersStream_foundSingle() {
        List<String> expected = List.of("Todd Gardner");
        List<String> actual = target.getPresentersStream(workshops, "Fundamentals of Web Performance");
        assertEquals(expected, actual);
    }

    @Test
    void getPresentersStream_foundTwo() {
        List<String> expected = List.of("Dan Vega", "DaShaun Carter");
        List<String> actual = target.getPresentersStream(workshops, "Getting Started with Spring");
        assertEquals(expected, actual);
    }

    @Test
    void getPresentersStream_foundNone() {
        List<String> actual = target.getPresentersStream(workshops, "Bad");
        assertEquals(0, actual.size());
    }

    @Test
    void requirements_getPresentersStream() {
        assertTrue(codeRules.containsStream("getPresentersStream"),
                "must contains stream()");
        assertFalse(codeRules.containsIf("getPresentersStream"),
                "cannot contain if statement");
        assertFalse(codeRules.containsLoop("getPresentersStream"),
                "cannot contain a loop");
        assertFalse(codeRules.containsRemoveIf("getPresentersStream"),
                "cannot contain removeIf");
    }

    // ---------------------------------------------------------

    @Test
    void getSessionKeysWithMultiplePresentersNoStream() {
        List<String> expected = List.of("Spring", "Security");
        List<String> actual = target.getSessionKeysWithMultiplePresentersNoStream(workshops);
        assertEquals(expected, actual);
    }

    @Test
    void requirements_getSessionKeysWithMultiplePresentersNoStream() {
        assertFalse(codeRules.containsIf("getSessionKeysWithMultiplePresentersNoStream"),
                "cannot contain if statement");
        assertFalse(codeRules.containsLoop("getSessionKeysWithMultiplePresentersNoStream"),
                "cannot contain a loop");
        assertFalse(codeRules.containsStream("getSessionKeysWithMultiplePresentersNoStream"),
                "cannot contain stream");
    }

    // ---------------------------------------------------------

    @Test
    void getSessionKeysWithMultiplePresentersStream() {
        List<String> expected = List.of("Spring", "Security");
        List<String> actual = target.getSessionKeysWithMultiplePresentersStream(workshops);
        assertEquals(expected, actual);
    }

    @Test
    void requirements_getSessionKeysWithMultiplePresentersStream() {
        assertTrue(codeRules.containsStream("getSessionKeysWithMultiplePresentersStream"),
                "must contains stream()");
        assertFalse(codeRules.containsIf("getSessionKeysWithMultiplePresentersStream"),
                "cannot contain if statement");
        assertFalse(codeRules.containsLoop("getSessionKeysWithMultiplePresentersStream"),
                "cannot contain a loop");
        assertFalse(codeRules.containsRemoveIf("getSessionKeysWithMultiplePresentersStream"),
                "cannot contain removeIf");
    }

    // ---------------------------------------------------------
    @Test
    void largestRoomNumberWithOddNumber() {
        Optional<Integer> actual = target.largestRoomNumberWithOddNumber(workshops);
        assertEquals(2211, actual.get());
    }

    @Test
    void largestRoomNumberWithOddNumber_forNoWorkshops() {
        Optional<Integer> actual = target.largestRoomNumberWithOddNumber(Collections.emptyMap());
        assertFalse(actual.isPresent());
    }

    @Test
    void requirements_largestRoomNumberWithOddNumber() {
        assertTrue(codeRules.containsStream("largestRoomNumberWithOddNumber"),
                "must contains stream()");
        assertFalse(codeRules.containsIf("largestRoomNumberWithOddNumber"),
                "cannot contain if statement");
        assertFalse(codeRules.containsLoop("largestRoomNumberWithOddNumber"),
                "cannot contain a loop");
        assertFalse(codeRules.containsRemoveIf("largestRoomNumberWithOddNumber"),
                "cannot contain removeIf");
    }

    // ---------------------------------------------------------

    @Test
    void titleOfRoomOneLowerThan() {
        Optional<String> actual = target.titleOfRoomOneLowerThan(workshops,2204);
        assertEquals("Create a Cloud Environment with Terraform and Ansible", actual.get());
    }

    @Test
    void titleOfRoomOneLowerThan_lowest() {
        Optional<String> actual = target.titleOfRoomOneLowerThan(workshops,2201);
        assertFalse(actual.isPresent());
    }

    @Test
    void requirements_titleOfRoomOneLowerThan() {
        assertTrue(codeRules.containsStream("titleOfRoomOneLowerThan"),
                "must contains stream()");
        assertFalse(codeRules.containsIf("titleOfRoomOneLowerThan"),
                "cannot contain if statement");
        assertFalse(codeRules.containsLoop("titleOfRoomOneLowerThan"),
                "cannot contain a loop");
        assertFalse(codeRules.containsRemoveIf("titleOfRoomOneLowerThan"),
                "cannot contain removeIf");
    }

    // ---------------------------------------------------------

    @Test
    void getTitlesAsCsvWithPrefix() {
        String expected = "End to End DevOps with GitHub";
        String actual = target.getTitlesAsCsvWithPrefix(workshops, "End to End");
        assertEquals(expected, actual);
    }

    @Test
    void getTitlesAsCsvWithPrefix_noMatches() {
        String expected = "";
        String actual = target.getTitlesAsCsvWithPrefix(workshops, "X");
        assertEquals(expected, actual);
    }

    @Test
    void requirements_getTitlesAsCsvWithPrefix() {
        assertTrue(codeRules.containsStream("getTitlesAsCsvWithPrefix"),
                "must contains stream()");
        assertFalse(codeRules.containsIf("getTitlesAsCsvWithPrefix"),
                "cannot contain if statement");
        assertFalse(codeRules.containsLoop("getTitlesAsCsvWithPrefix"),
                "cannot contain a loop");
        assertFalse(codeRules.containsRemoveIf("getTitlesAsCsvWithPrefix"),
                "cannot contain removeIf");
    }

}
