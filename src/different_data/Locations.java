package different_data;

import java.io.*;
import java.util.*;

public class Locations implements Map<Integer, Location> {

    private static Map<Integer, Location> locations = new HashMap<>();
    // only one instance shard across all instances

    // static initialisation block executed only once
    static {
        try(FileInputStream fi = new FileInputStream("locations_big.txt");
            BufferedInputStream bi = new BufferedInputStream(fi);
            Scanner sc = new Scanner(bi)
        ) {
            sc.useDelimiter(",");

            while (sc.hasNextLine()) {
                int loc = sc.nextInt();
                sc.skip(sc.delimiter());
                String description = sc.nextLine();

                System.out.println("Imported Location: " + loc + ": " + description);

                Map<String, Integer> tempExit = new HashMap<>();
                locations.put(loc, new Location(loc, description, tempExit));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileInputStream fis = new FileInputStream("directions_big.txt");
             BufferedInputStream bis = new BufferedInputStream(fis);
             Scanner scanner = new Scanner(bis)
        ) {

            scanner.useDelimiter(", ");

            while (scanner.hasNextLine()) {

                String string = scanner.nextLine(); // read entire line.
                String[] data = string.split(",");
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

    private static String space(){
        return  ": ";
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
