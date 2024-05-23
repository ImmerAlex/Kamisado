package model;

import java.util.ArrayList;
import java.util.List;

public class EntryFileContainer {
    public static List<String> entryFromFile;

    public EntryFileContainer() {
        this.entryFromFile = new ArrayList<>();
    }

    public void addEntry(String entry) {
        entryFromFile.add(entry);
    }

    public static String getFirstEntry() {
        return entryFromFile.getFirst();
    }

    public static void removeFirstEntry() {
        entryFromFile.removeFirst();
    }
}
