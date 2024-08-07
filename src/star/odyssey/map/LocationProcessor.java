package star.odyssey.map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LocationProcessor {

    // INSTANCE VARIABLES
    private HashMap<String, Integer> finalGrid;

    // CONSTRUCTORS

    // METHODS
    public HashMap<String, Integer> processLocations() {
        try {
            Gson gson = new Gson();

            // Read the JSON file into a JsonObject
            JsonObject json = gson.fromJson(new FileReader("./data/locations.json"), JsonObject.class);
            JsonArray locations = json.getAsJsonArray("locations");

            // create a hashmap with room indices as keys and string as values
            HashMap<String, String> roomList = new HashMap<>();

            // Iterate through locations array
            for (int i = 0; i < locations.size(); i++) {
                JsonObject location = locations.get(i).getAsJsonObject();

                // Get the index (room name) from the location
                String roomName = location.get("index").getAsString();
//                System.out.println("Room name: " + roomName);

                // Add the room name to the hashmap with value "11" if the hashmap is empty, else get the value from the hashmap
                if (roomList.isEmpty()) {
                    roomList.put(roomName, "11");
                } else {
                    roomList.put(roomName, roomList.get(roomName));
                }

                // Now get the value of the room from the hashmap
                String roomValue = roomList.get(roomName);
//                System.out.println("Room value: " + roomValue);
//                System.out.println();

                // Get the connections for the room
                JsonObject connections = location.getAsJsonObject("connections");
//                System.out.println("Connections: " + connections);

                // code for handling connections (east, west, north, south) goes here
                boolean westActivated = false;
                if (connections.has("east") && connections.get("east").isJsonPrimitive()) {
                    String connectedRoom = connections.get("east").getAsString();
//                    System.out.println("Connected room: " + connectedRoom);
                    int roomValueInt = Integer.parseInt(roomValue);
                    roomValueInt += 1;
                    roomList.put(connectedRoom, String.valueOf(roomValueInt));
                }
//                System.out.println("Room values: " + roomList);
                if (connections.has("west") && connections.get("west").isJsonPrimitive()) {
                    String connectedRoom = connections.get("west").getAsString();
//                    System.out.println("Connected room: " + connectedRoom);
                    int roomValueInt = Integer.parseInt(roomValue);
                    roomValueInt -= 1;
                    // if roomValueInt has the second number of the 2 digit value as 0, then make the increase the value of all other rooms by 1
                    if (roomValueInt % 10 == 0) {
                        roomValueInt = Integer.parseInt(roomValue);
                        for (String room : roomList.keySet()) {
                            roomList.put(room, String.valueOf(Integer.parseInt(roomList.get(room)) + 1));
                        }
                        westActivated = true;
                    }
                    roomList.put(connectedRoom, String.valueOf(roomValueInt));
                }
//                System.out.println("Room values: " + roomList);
                if (connections.has("south") && connections.get("south").isJsonPrimitive()) {
                    String connectedRoom = connections.get("south").getAsString();
//                    System.out.println("Connected room: " + connectedRoom);
                    int roomValueInt = Integer.parseInt(roomValue);
                    if (westActivated) {
                        roomValueInt += 1;
                    }
                    roomValueInt += 10;
                    roomList.put(connectedRoom, String.valueOf(roomValueInt));
                }
//                System.out.println("Room values: " + roomList);
                if (connections.has("north") && connections.get("north").isJsonPrimitive()) {
                    String connectedRoom = connections.get("north").getAsString();
//                    System.out.println("Connected room: " + connectedRoom);
                    int roomValueInt = Integer.parseInt(roomValue);
                    roomValueInt -= 10;
                    // if roomValueInt is 1 then set it to 11 and then add 10 to all other rooms values currently in the map
                    if (roomValueInt >= 0 && roomValueInt <= 9) {
                        roomValueInt = Integer.parseInt(roomValue);
                        if (westActivated) {
                            roomValueInt += 1;
                        }
                        for (String room : roomList.keySet()) {
                            roomList.put(room, String.valueOf(Integer.parseInt(roomList.get(room)) + 10));
                        }

                    }
                    roomList.put(connectedRoom, String.valueOf(roomValueInt));
                }

                finalGrid = new HashMap<>();
                for (String key : roomList.keySet()) {
                    finalGrid.put(key, Integer.parseInt(roomList.get(key)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalGrid;
    }

    private JsonObject loadJson() throws IOException {
        return JsonParser.parseReader(new FileReader("./data/locations.json")).getAsJsonObject();
    }

    private boolean checkDirection(String room, String direction) {
        try {
            JsonObject json = loadJson();
            JsonArray locations = json.getAsJsonArray("locations");

            for (int i = 0; i < locations.size(); i++) {
                JsonObject location = locations.get(i).getAsJsonObject();
                String roomName = location.get("index").getAsString();

                if (room.equals(roomName)) {
                    JsonObject connections = location.getAsJsonObject("connections");
                    return connections.has(direction) && connections.get(direction).isJsonPrimitive();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // GETTERS AND SETTERS
    public String getRoomNameByIndex(String roomIndex) {
        try {
            // Read the JSON file into a JsonObject
            JsonObject json = loadJson();
            JsonArray locations = json.getAsJsonArray("locations");

            // Iterate through locations array
            for (int i = 0; i < locations.size(); i++) {
                JsonObject location = locations.get(i).getAsJsonObject();
                String index = location.get("index").getAsString();
                String name = location.get("name").getAsString();

                if (roomIndex.equals(index)) {
                    return name;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return null if the room index is not found
        return null;
    }

    public boolean hasNorth(String room) {
        return checkDirection(room, "north");
    }

    public boolean hasSouth(String room) {
        return checkDirection(room, "south");
    }

    public boolean hasWest(String room) {
        return checkDirection(room, "west");
    }

    public boolean hasEast(String room) {
        return checkDirection(room, "east");
    }
}
