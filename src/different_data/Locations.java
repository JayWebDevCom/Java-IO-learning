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
        try(FileReader fr = new FileReader("locations_big.txt");
            BufferedReader br = new BufferedReader(fr)
        ) {
            String input;
            while ((input = br.readLine()) != null ) {

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

        try (BufferedWriter brLocations = new BufferedWriter(new FileWriter("locations.txt"));
             BufferedWriter brDirections = new BufferedWriter(new FileWriter("directions.txt"))
        ) {

            for (Location location : locations.values()) {
                brLocations.write(location.getLocationID() + space() + location.getDescription() + newLine());
                for (String direction : location.getExits().keySet()) {
                    if (!direction.equalsIgnoreCase("Q")) {
                        brDirections.write(location.getLocationID() + space() + direction + space() + location.getExits().get(direction) + newLine());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String newLine() {
        return "\n";
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
