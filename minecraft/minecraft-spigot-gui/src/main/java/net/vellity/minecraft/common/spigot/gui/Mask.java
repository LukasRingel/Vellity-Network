package net.vellity.minecraft.common.spigot.gui;


import java.util.*;
import java.util.function.Function;

public class Mask {
  private final Map<Character, List<Slot>> slotMap = new HashMap<>();

  Mask(Gui gui, Builder builder) {
    builder.chars.forEach(
      (point, character) -> {
        List<Slot> slots =
          slotMap.computeIfAbsent(character, unused -> new LinkedList<>());
        slots.add(gui.getSlot(point.x, point.y));
      });
  }

  public static Function<Integer, Comparator<Point>> HORIZONTAL = rows -> Comparator.comparingInt(value -> value.x() + (value.y() * 9));
  public static Function<Integer, Comparator<Point>> VERTICAL = rows -> Comparator.comparingInt(value -> value.y() + (value.x() * rows));

  public static Builder builder(int rows) {
    return new Builder(rows, HORIZONTAL);
  }

  public static Builder builder(int rows, Function<Integer, Comparator<Point>> comparator) {
    return new Builder(rows, comparator);
  }

  /**
   * Returns a map with all mapped characters and a list of slots which have been mapped to this
   * character.
   *
   * @return The map
   */
  public Map<Character, List<Slot>> getSlots() {
    return slotMap;
  }

  /**
   * Retrieves all slots that have been mapped to the specified character.
   *
   * @param c The character to lookup
   * @return The slot list
   */
  public List<Slot> getSlots(char c) {
    return slotMap.getOrDefault(c, Collections.emptyList());
  }

  public static class Builder {
    private final Map<Point, Character> chars;
    private int rows = 0;

    Builder(int rows, Function<Integer, Comparator<Point>> slotComparator) {
      this.chars = new TreeMap<>(slotComparator.apply(rows));
    }

    /**
     * Returns the line counter.
     *
     * @return Amount of rows set
     */
    public int getRows() {
      return rows;
    }

    /**
     * Counts the number of occurrences of a character in the mask.
     *
     * @param lookupCharacter The character to lookup
     * @return The amount of characters
     */
    public int countChars(char lookupCharacter)   {
      int counter = 0;
      for (Character value : chars.values()) {
        if(value == lookupCharacter) counter++;
      }
      return counter;
    }

    /**
     * Sets the pattern for the current line and increments the line counter.
     *
     * @param pattern The pattern
     * @return The builder itself
     */
    public Builder pattern(String pattern) {
      char[] patternChars = pattern.toCharArray();
      for (int i = 0; i < patternChars.length; i++) {
        this.chars.put(new Point(i, rows), patternChars[i]);
      }

      rows++;
      return this;
    }

    /**
     * Builds the Mask for the given gui.
     *
     * @param gui The gui to use for building the mask
     * @return The mask
     */
    public Mask build(Gui gui) {
      return new Mask(gui, this);
    }
  }

  public record Point(int x, int y) {
  }
}
