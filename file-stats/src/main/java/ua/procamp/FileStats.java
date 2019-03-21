package ua.procamp;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * {@link FileStats} provides an API that allow to get character statistic based on TEXT file. All whitespace characters
 * are ignored.
 */
public class FileStats {

    private final String TEXT;

    private FileStats(String fileName) {
        TEXT = Optional
                .ofNullable(FileReaders.readWholeFile(fileName))
                .orElseThrow(() -> new FileStatsException("Can't get file"));
    }

    /**
     * Creates a new immutable {@link FileStats} objects using data from TEXT file received as a parameter.
     *
     * @param fileName input TEXT file name
     * @return new FileStats object created from TEXT file
     */
    public static FileStats from(String fileName) {
        return new FileStats(fileName);
    }

    /**
     * Returns a number of occurrences of the particular character.
     *
     * @param character a specific character
     * @return a number that shows how many times this character appeared in a TEXT file
     */
    public int getCharCount(char character) {
        return (int) TEXT.chars()
                .filter(ch -> ch == character)
                .count();
    }

    /**
     * Returns a character that appeared most often in the TEXT.
     *
     * @return the most frequently appeared character
     */
    public char getMostPopularCharacter() {
        String ch = Stream.of(TEXT.toLowerCase().split(""))
                .filter(x -> x.matches("[A-Za-z]"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .findAny()
                .map(entry -> entry.getKey())
                .orElseThrow(() -> new FileStatsException("File is empty"));
        return ch.charAt(0);
    }

    /**
     * Returns {@code true} if this character has appeared in the TEXT, and {@code false} otherwise
     *
     * @param character a specific character to check
     * @return {@code true} if this character has appeared in the TEXT, and {@code false} otherwise
     */
    public boolean containsCharacter(char character) {
        Objects.requireNonNull(character);
        return TEXT.toLowerCase()
                .chars()
                .filter(ch -> ch >= 'a' && ch <= 'z')
                .anyMatch(ch -> ch == character);
    }

}
