package different_data;

import java.io.*;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Locations implements Map<Integer, Location> {

    private static Map<Integer, Location> locations = new LinkedHashMap<>();
    // only one instance shard across all instances

    // static initialisation block executed only once
    static {
        try (FileInputStream fis = new FileInputStream("locations.dat");
             BufferedInputStream bis = new BufferedInputStream(fis);
             DataInputStream locFile = new DataInputStream(bis)
        ) {
            while (true) { // IOException will cause while loop to be broken

                Map<String, Integer> exits = new LinkedHashMap<>();
                int locId = locFile.readInt();
                String description = locFile.readUTF();
                System.out.println("description is " + description);

                int numExits = locFile.readInt();
                System.out.println("Read location " + locId + colon() + description);
                System.out.println("Found " + numExits + " exits");

                for (int i = 0; i < numExits; i++) {
                    String direction = locFile.readUTF();
                    int destination = locFile.readInt();
                    exits.put(direction, destination);
                    System.out.println("\t\t" + direction + space() + description);
                }

                locations.put(locId, new Location(locId, description, exits));
            }

        } catch (IOException e) {
            System.out.println("IOException... ");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {

        // save the data read in in a binary format
        // static initialization block can then read from the binary data bile
        // adv - data no longer needs to br parsed because bytestream can write all binary/primitive types
        // buffer the data using appropriate classes

        try (FileOutputStream fos = new FileOutputStream("locations.dat");
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             DataOutputStream locFile = new DataOutputStream(bos)
             // automatically closes our resources
             // no need to catch because method throws

        ) {

            for (Location location : locations.values()) {
                locFile.writeInt(location.getLocationID());
                locFile.writeUTF(location.getDescription());

                // sout's for debugging info
                System.out.println("Writing location " + location.getLocationID() + colon() + location.getDescription());
                System.out.println("Writing " + (location.getExits().size() - 1 + " exits"));
                locFile.writeInt(location.getExits().size() - 1);

                for (String direction : location.getExits().keySet()) {
                    if (!direction.equalsIgnoreCase("Q")) {
                        System.out.println("\t\t" + direction + " - " + location.getExits().get(direction));
                        locFile.writeUTF(direction);
                        locFile.writeInt(location.getExits().get(direction));
                    }
                }
            }
        }
    }

    private static String newLine() {
        return "\n";
    }

    private static String colon() {
        return " : ";
    }

    private static String space() {
        return ": ";
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
