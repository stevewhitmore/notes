package com.jeanneboyarsky.solutions;

import com.jeanneboyarsky.rules.CodeRulesForMethods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static com.jeanneboyarsky.solutions.Module4LabSolution.*;
import static org.junit.jupiter.api.Assertions.*;

public class Module4LabSolutionTest {

    private Module4LabSolution target;
    private Map<String, Workshop> workshops;
    private CodeRulesForMethods codeRules;

    private Path path;

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
                                "Jake Ginnivan")));
        target = new Module4LabSolution();
        path = Paths.get("lab/src/main/resources/workshops.csv");
        target.writeWorkshopsSortedByRoomNumber(workshops, path);

        Path folder = Paths.get("lab-solutions/src/main/java/");
        codeRules = new CodeRulesForMethods(folder, "com.jeanneboyarsky.solutions",
               "Module4LabSolution.java");
    }

    @AfterEach
    void tearDown() throws IOException {
        if (path != null) {
            Files.deleteIfExists(path);
        }
    }

    // ---------------------------------------------------------

    @Test
    void requirements_writeWorkshopsSortedByRoomNumber() {
        assertFalse(codeRules.containsLoop("writeWorkshopsSortedByRoomNumber"),
                "must not contain loop");
    }

    // ---------------------------------------------------------

    @Test
    void readFile() throws IOException {
        List<String> expected = List.of("Performance,2201,\"Fundamentals of Web Performance\",\"Todd Gardner\"",
                "Career,2202,\"Charting a Course to Your Dream Job\",\"Cassandra Faris\"",
                "Terraform-Ansible,2203,\"Create a Cloud Environment with Terraform and Ansible\",\"Gene Gotimer\"",
                "Data-Science,2204,\"Data Science: Zero to Hero\",\"Gary Short\"",
                "Cypress,2205,\"End-to-End and Component Testing with Cypress\",\"Mark Noonan\"",
                "GitHub,2206,\"End to End DevOps with GitHub\",\"Damian Brady\"",
                "FERNI,2207,\"Freaky-Fast Full Stack with the FERNI Stack\",\"Keith Kurak\"",
                "Spring,2208,\"Getting Started with Spring\",\"Dan Vega,DaShaun Carter\"",
                "Security,2210,\"To the Left, to the Left: All your Security Shifted to the Left\",\"Eddie Knight,Theresa Mammarella\"",
                "Architecture,2211,\"Growing your career in architecture: 8 experiences to prepare you to be a professional architect\",\"Brian Loomis\"",
                "Remix,2212,\"Remix Fundamentals\",\"Jake Ginnivan\"");
        List<String> actual = Files.readAllLines(path);
        assertEquals(expected, actual);
    }

    // ---------------------------------------------------------

    @Test
    void musicalRooms_sortedOddsFirst() throws IOException {
        List<String> lines = List.of("key,123,name", "key,456,name", "key,7,name");
        Files.write(path, lines);
        List<String> expected = List.of("key,123,name", "key,7,name", "key,456,name");
        target.musicalRooms(path);
        List<String> actual = Files.readAllLines(path);
        assertEquals(expected, actual);
    }

    @Test
    void musicalRooms() throws IOException {
        List<String> expected = List.of("Performance,2201,\"Fundamentals of Web Performance\",\"Todd Gardner\"",
                "Terraform-Ansible,2203,\"Create a Cloud Environment with Terraform and Ansible\",\"Gene Gotimer\"",
                "Cypress,2205,\"End-to-End and Component Testing with Cypress\",\"Mark Noonan\"",
                "FERNI,2207,\"Freaky-Fast Full Stack with the FERNI Stack\",\"Keith Kurak\"",
                "Architecture,2211,\"Growing your career in architecture: 8 experiences to prepare you to be a professional architect\",\"Brian Loomis\"",
                "Career,2202,\"Charting a Course to Your Dream Job\",\"Cassandra Faris\"",
                "Data-Science,2204,\"Data Science: Zero to Hero\",\"Gary Short\"",
                "GitHub,2206,\"End to End DevOps with GitHub\",\"Damian Brady\"",
                "Spring,2208,\"Getting Started with Spring\",\"Dan Vega,DaShaun Carter\"",
                "Security,2210,\"To the Left, to the Left: All your Security Shifted to the Left\",\"Eddie Knight,Theresa Mammarella\"",
                "Remix,2212,\"Remix Fundamentals\",\"Jake Ginnivan\"");
        target.musicalRooms(path);
        List<String> actual = Files.readAllLines(path);
        assertEquals(expected, actual);
    }

    @Test
    void requirements_musicalRooms() {
        assertFalse(codeRules.containsLoop("musicalRooms"),
                "must not contain loop");
    }

    // ---------------------------------------------------------
    @Test
    void shortestLine() throws IOException {
        String expected = "Remix,2212,\"Remix Fundamentals\",\"Jake Ginnivan\"";
        Optional<String> actual = target.shortestLine(path);
        assertEquals(expected, actual.get());
    }

    @Test
    void requirements_shortestLine() {
        assertTrue(codeRules.containsLines("shortestLine"),
                "must contains lines()");
    }

    // ---------------------------------------------------------

    @Test
    void musicalRoomsStreamVersion_sortedOddsFirst() throws IOException {
        List<String> lines = List.of("key,123,name", "key,456,name", "key,7,name");
        Files.write(path, lines);
        List<String> expected = List.of("key,123,name", "key,7,name", "key,456,name");
        target.musicalRoomsStreamVersion(path);
        List<String> actual = Files.readAllLines(path);
        assertEquals(expected, actual);
    }

    @Test
    void musicalRoomsStreamVersion() throws IOException {
        List<String> expected = List.of("Performance,2201,\"Fundamentals of Web Performance\",\"Todd Gardner\"",
                "Terraform-Ansible,2203,\"Create a Cloud Environment with Terraform and Ansible\",\"Gene Gotimer\"",
                "Cypress,2205,\"End-to-End and Component Testing with Cypress\",\"Mark Noonan\"",
                "FERNI,2207,\"Freaky-Fast Full Stack with the FERNI Stack\",\"Keith Kurak\"",
                "Architecture,2211,\"Growing your career in architecture: 8 experiences to prepare you to be a professional architect\",\"Brian Loomis\"",
                "Career,2202,\"Charting a Course to Your Dream Job\",\"Cassandra Faris\"",
                "Data-Science,2204,\"Data Science: Zero to Hero\",\"Gary Short\"",
                "GitHub,2206,\"End to End DevOps with GitHub\",\"Damian Brady\"",
                "Spring,2208,\"Getting Started with Spring\",\"Dan Vega,DaShaun Carter\"",
                "Security,2210,\"To the Left, to the Left: All your Security Shifted to the Left\",\"Eddie Knight,Theresa Mammarella\"",
                "Remix,2212,\"Remix Fundamentals\",\"Jake Ginnivan\"");
        target.musicalRoomsStreamVersion(path);
        List<String> actual = Files.readAllLines(path);
        assertEquals(expected, actual);
    }

    @Test
    void requirements_musicalRoomsStreamVersion() {
        assertTrue(codeRules.containsLines("musicalRoomsStreamVersion"),
                "must contains lines()");
    }

    // ---------------------------------------------------------

    @Test
    void deleteFileIfNotAbsolutePath_deleting() throws IOException {
        boolean actual = target.deleteFileIfNotAbsolutePath(path);
        assertTrue(actual, "deleted");
        assertFalse(Files.exists(path), "exists");
    }

    @Test
    void deleteFileIfNotAbsolutePath_notDeleting() throws IOException {
        Path absolute = Paths.get("/temp");
        boolean actual = target.deleteFileIfNotAbsolutePath(absolute);
        assertFalse(actual, "deleted");
    }

    @Test
    void absolutePathOfLargestLabSolutionFile() throws IOException {
        Optional<String> actual = target.absolutePathOfLargestLabSolutionFile();
        assertTrue(actual.get().endsWith("Module4LabSolution.java"));
    }

    // ---------------------------------------------------------

    @Test
    void requirements_absolutePathOfLargestLabSolutionFile() {
        assertTrue(codeRules.containsWalk("absolutePathOfLargestLabSolutionFile"),
                "must contains walk()");
    }

    // ---------------------------------------------------------

    @Test
    void startOfKcdc() {
        LocalDate actual = target.startOfKcdc();
        assertEquals(2023, actual.getYear());
        assertEquals(Month.JUNE, actual.getMonth());
        assertEquals(21, actual.getDayOfMonth());
    }

    @Test
    void endOfKcdc() {
        LocalDate actual = target.endOfKcdc();
        assertEquals(2023, actual.getYear());
        assertEquals(Month.JUNE, actual.getMonth());
        assertEquals(23, actual.getDayOfMonth());
    }

    @Test
    void roomNumbersSmallerThan() {
        List<Integer> expected = List.of(2201, 2202, 2203, 2204, 2205);
        List<Integer> actual = target.roomNumbersSmallerThan(workshops,2205);
        assertEquals(expected, actual);
    }

    @Test
    void requirements_roomNumbersSmallerThan() {
        assertTrue(codeRules.containsStream("roomNumbersSmallerThan"),
                "must contain stream");
        assertTrue(codeRules.containsMathMin("roomNumbersSmallerThan"),
                "must contain Math.min");
    }

    @RepeatedTest(100)
    void getFiveRandomSessions() {
        List<Integer> actual = target.getFiveRandomSessions(workshops);
        Set<Integer> unique = new HashSet<>(actual);
        assertEquals(5, unique.size());
    }
}

