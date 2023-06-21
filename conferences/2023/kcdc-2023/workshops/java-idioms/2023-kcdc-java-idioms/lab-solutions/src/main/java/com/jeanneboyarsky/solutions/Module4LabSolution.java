package com.jeanneboyarsky.solutions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Module4LabSolution {

    public record Workshop(String title, int roomNumber, List<String> presenters) {

        public Workshop(String title, int roomNumber, String... presenters) {
            this(title, roomNumber, new ArrayList<>(Arrays.asList(presenters)));
        }

        @Override
        public List<String> presenters() {
            return List.copyOf(presenters);
        }

        public String toCsv() {
            // tip: use library commons csv in real code
            StringBuilder result = new StringBuilder();
            result.append(roomNumber);
            result.append(',');
            result.append('"');
            result.append(title);
            result.append('"');
            result.append(',');
            result.append('"');
            result.append(String.join(",", presenters));
            result.append('"');
            return result.toString();
        }
    }

    // format: key,room,"title","presenters"
    // START writeWorkshopsSortedByRoomNumber()
    public void writeWorkshopsSortedByRoomNumber(Map<String, Workshop> map, Path outputPath) {
        List<String> lines = map.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getValue().roomNumber()))
                .map(e -> e.getKey() + "," + e.getValue().toCsv())
                .collect(Collectors.toList());
        try {
            Files.write(outputPath, lines);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    // END writeWorkshopsSortedByRoomNumber()

    // re-order so all lines with odd room numbers are first (keeping sort order within odds)
    // followed by all even room numbers (keeping sort order within evens)
    // write the result back to the file
    // START musicalRooms()
    public void musicalRooms(Path path) throws IOException {
        List<String> initialData = Files.readAllLines(path);
        List<String> oddRoomNumbers = new ArrayList<>(initialData);
        oddRoomNumbers.removeIf(s -> ! isOddRoomNumber(s));
        List<String> evenRoomNumbers = new ArrayList<>(initialData);
        evenRoomNumbers.removeIf(s -> isOddRoomNumber(s));

        List<String> result = new ArrayList<>(oddRoomNumbers);
        result.addAll(evenRoomNumbers);
        Files.write(path, result);
    }
    // END musicalRooms()

    private boolean isOddRoomNumber(String line) {
        String roomCell = line.split(",")[1];
        return Integer.parseInt(roomCell) %2 != 0;
    }

    // START shortestLine()
    public Optional<String> shortestLine(Path path) throws IOException {
        try (var stream = Files.lines(path)) {
            return stream.min(Comparator.comparing(String::length));
        }
    }
    // END shortestLine()

    // START musicalRoomsStreamVersion()
    public void musicalRoomsStreamVersion(Path path) throws IOException {
        List<String> odd;
        List<String> even;
        try (var stream = Files.lines(path)) {
            odd = stream.filter(l -> isOddRoomNumber(l))
                    .collect(Collectors.toList());
        }
        try (var stream = Files.lines(path)) {
            even = stream.filter(l -> !isOddRoomNumber(l))
                    .collect(Collectors.toList());
        }
        Files.write(path, odd);
        Files.write(path, even, StandardOpenOption.APPEND);
    }
    // END musicalRoomsStreamVersion()

    // START deleteFileIfNotAbsolutePath()
    public boolean deleteFileIfNotAbsolutePath(Path path) throws IOException {
        if (! path.isAbsolute()) {
            Files.delete(path);
            return true;
        }
        return false;
    }
    // END deleteFileIfNotAbsolutePath()

    // challenge
    // START absolutePathOfLargestLabSolutionFile()
    public Optional<String> absolutePathOfLargestLabSolutionFile() throws IOException {
        Path path = Paths.get("lab-solutions");
        try (var stream = Files.walk(path)) {
            return stream.filter(p -> p.toString().endsWith("LabSolution.java"))
                    .sorted(Comparator.reverseOrder())
                    .limit(1)
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .findAny();
        }
    }
    // END absolutePathOfLargestLabSolutionFile()

    // build the date for today: June 21, 2023
    // START startOfKcdc()
    public LocalDate startOfKcdc() {
        return LocalDate.of(2023, Month.JUNE, 21);
    }
    // END startOfKcdc()

    // add days to start to get last day of KCDC
    // START endOfKcdc()
    public LocalDate endOfKcdc() {
        return startOfKcdc().plusDays(2);
    }
    // END endOfKcdc()

    // START roomNumbersSmallerThan()
    public List<Integer> roomNumbersSmallerThan(Map<String, Workshop> map, int max) {
        return map.values().stream()
                .map(Workshop::roomNumber)
                .filter(n -> Math.min(n, max) == n)
                .sorted()
                .collect(Collectors.toList());
    }
    // END roomNumbersSmallerThan()

    // START getFiveRandomSessions()
    public List<Integer> getFiveRandomSessions(Map<String, Workshop> map) {
        List<Workshop> workshops = new ArrayList<>(map.values());
        Random random = new Random();
        return Stream.generate(() -> random.nextInt(workshops.size()))
                .map(n -> workshops.get(n))
                .map(Workshop::roomNumber)
                .distinct()
                .limit(5)
                .collect(Collectors.toList());
    }
    // END getFiveRandomSessions()

}
