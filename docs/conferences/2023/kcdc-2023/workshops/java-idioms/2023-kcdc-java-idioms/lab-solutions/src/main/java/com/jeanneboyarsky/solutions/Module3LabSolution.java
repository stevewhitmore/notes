package com.jeanneboyarsky.solutions;

import java.util.*;
import java.util.stream.Collectors;

public class Module3LabSolution {

    public static class Workshop {
        private String title;
        private int roomNumber;
        private List<String> presenters;

        public Workshop(String title, int roomNumber, String... presenters) {
            this.title = title;
            this.roomNumber = roomNumber;
            this.presenters = new ArrayList<>(Arrays.asList(presenters));
        }

        public String getTitle() {
            return title;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public List<String> getPresenters() {
            return List.copyOf(presenters);
        }
    }

    // START getOptionalByKey()
    public Optional<Workshop> getOptionalByKey(Map<String, Workshop> map, String key) {
        Workshop workshop = map.getOrDefault(key, null);
        return Optional.ofNullable(workshop);
    }
    // END getOptionalByKey()

    // START getPresentersNoStream()
    public List<String> getPresentersNoStream(Map<String, Workshop> map, String title) {
        List<Workshop> values = new ArrayList<>(map.values());
        values.removeIf(w -> ! w.getTitle().equals(title));
        return values.isEmpty() ?
                List.of() :
                values.get(0).getPresenters();
    }
    // END getPresentersNoStream()

    // START getPresentersStream()
    public List<String> getPresentersStream(Map<String, Workshop> map, String title) {
        return map.values().stream()
                .filter(w -> w.getTitle().equals(title))
                .map(Workshop::getPresenters)
                .findFirst()
                .orElse(Collections.emptyList());
    }
    // END getPresentersStream()


    // START getSessionKeysWithMultiplePresentersNoStream()
    public List<String> getSessionKeysWithMultiplePresentersNoStream(Map<String, Workshop> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        keys.removeIf(k ->map.get(k).presenters.size() == 1);
        keys.sort(Comparator.reverseOrder());
        return keys;
    }
    // END getSessionKeysWithMultiplePresentersNoStream()

    // START getSessionKeysWithMultiplePresentersStream()
    public List<String> getSessionKeysWithMultiplePresentersStream(Map<String, Workshop> map) {
        return map.entrySet().stream()
                .filter(e -> e.getValue().getPresenters().size() > 1)
                .map(Map.Entry::getKey)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
    // END getSessionKeysWithMultiplePresentersStream()

    // START largestRoomNumberWithOddNumber()
    public Optional<Integer> largestRoomNumberWithOddNumber(Map<String, Workshop> map) {
        return map.values().stream()
                .map(Workshop::getRoomNumber)
                .filter(r -> r % 2 == 1)
                .max(Comparator.naturalOrder());
    }
    // END largestRoomNumberWithOddNumber()

    // START titleOfRoomOneLowerThan()
    public Optional<String> titleOfRoomOneLowerThan(Map<String, Workshop> map, int roomNumber) {
        return map.values().stream()
                .filter(w -> w.getRoomNumber() < roomNumber)
                .sorted(Comparator.comparing(Workshop::getRoomNumber).reversed())
                .map(Workshop::getTitle)
                .findFirst();
    }
    // END titleOfRoomOneLowerThan()

    // START getTitlesAsCsvWithPrefix()
    public String getTitlesAsCsvWithPrefix(Map<String, Workshop> map, String prefix) {
        return map.values().stream()
                .map(Workshop::getTitle)
                .filter(t -> t.startsWith(prefix))
                .sorted()
                .collect(Collectors.joining(","));
    }
    // END getTitlesAsCsvWithPrefix()

}
