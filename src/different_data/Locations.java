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
        try (FileReader fr = new FileReader("locations_big.txt");
             BufferedReader br = new BufferedReader(fr)
        ) {
            String input;
            while ((input = br.readLine()) != null) {

                String[] data = input.split(",");
                int loc = Integer.parseInt(data[0]);
                String description = data[1];

                System.out.println("Imported Location: " + loc + ": " + description);

                Map<String, Integer> tempExit = new LinkedHashMap<>();
                locations.put(loc, new Location(loc, description, tempExit));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader fr = new FileReader("directions_big.txt");
             BufferedReader br = new BufferedReader(fr)
        ) {
            String input;

            while ((input = br.readLine()) != null) {

                String[] data = input.split(",");
                int loc = Integer.parseInt(data[0]);
                String direction = data[1];
                int destination = Integer.parseInt(data[2]);

                System.out.println(loc + space() + direction + space() + destination);
                Location location = locations.get(loc);
                location.addExit(direction, destination);
            }

        } catch (IOException e) {
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
                locFile.write(location.getLocationID());
                locFile.writeUTF(location.getDescription());

                // sout's for debugging info
                System.out.println("Writing location " + location.getLocationID() + colon() + location.getDescription());
                System.out.println("Writing " + (location.getExits().size() - 1 + " exits"));
                locFile.writeInt(location.getExits().size() - 1);

                for (String direction : location.getExits().keySet()) {
                    if (!direction.equalsIgnoreCase("Q")) {
                        System.out.println("\t\t" + direction + "-" + location.getExits().get(direction));
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
