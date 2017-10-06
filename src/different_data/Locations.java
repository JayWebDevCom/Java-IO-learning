package different_data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Locations implements Map<Integer, Location> {

    private static Map<Integer, Location> locations = new HashMap<>();
    // only one instance shard across all instances

    // static initialisation block executed only once
    static {
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader("locations.txt"));
            sc.useDelimiter(", ");

            while (sc.hasNextLine()) {
                int loc = sc.nextInt();
                sc.skip(sc.delimiter());
                String description = sc.nextLine();
                System.out.println("Imported Location: " + loc + ": " + description);

                Map<String, Integer> tempExit = new HashMap<>();
                locations.put(loc, new Location(loc, description, tempExit));
            }

        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            if (sc != null) {
                sc.close(); // also closes the filereader (source) as long as the file reader implements closeable
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // try with resources - makes code more streamlined
        // ensures that the first error is the one thrown back
        // most likely that that error is the one to address

        try (FileWriter locationsFile = new FileWriter("locations.txt");
             FileWriter directionsFile = new FileWriter("directions.txt")) {
            for (Location location : locations.values()) {
                locationsFile.write(location.getLocationID() + ", " + location.getDescription() + "\n");
                for (String direction : location.getExits().keySet()) {
                    directionsFile.write(location.getLocationID() + ", " + direction + ", " + location.getExits().get(direction) + "\n");
                }
            }
        }
        // no longer necessary to close the FileWriter
        // much tidier
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
