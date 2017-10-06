package different_data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Locations implements Map<Integer, Location> {

    private static Map<Integer, Location> locations = new HashMap<>();
    // only one instance shard across all instances

    // static initialisation block executed only once
    static {
        locations.put(0, new Location(0, "This is the description of the first Location", null));

        Map<String, Integer> tempExit = new HashMap<>();
        tempExit.put("W", 2);
        tempExit.put("E", 3);
        tempExit.put("S", 4);
        tempExit.put("N", 5);
        locations.put(1, new Location(1, "This is the description of the second Location", tempExit));

        tempExit.clear();
        tempExit.put("N", 5);
        locations.put(2, new Location(2, "This is the description of the third Location", tempExit));

        tempExit.clear();
        tempExit.put("W", 1);
        locations.put(3, new Location(3, "This is the description of the fourth Location", tempExit));
    }

    public static void main(String[] args) throws IOException {
        // try with resources - makes code more streamlined
        // ensures that the first error is the one thrown back
        // most likely that that error is the one to address

        try (FileWriter fw = new FileWriter("locations.txt")) {
            for (Location l : locations.values()) {
                fw.write(l.getLocationID() + ", " + l.getDescription());
            }
        }
    }

    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {
        locations.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
