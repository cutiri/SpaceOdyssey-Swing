package star.odyssey.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GameUtil {

    // INSTANCE VARIABLES

    // CONSTRUCTORS

    // METHODS (STATIC)
    public static Map<String, String> jsonToStringMap(String filePath, String desiredItem) {
        Map<String, String> map = null;
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();

            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject menuObject = jsonObject.getAsJsonObject(desiredItem);

            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            map = gson.fromJson(menuObject, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String jsonToString(String filePath, String desiredItem) {
        String s = null;
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();

            // reads the main_menu portion of gameText.json
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            s = jsonObject.get(desiredItem).getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static Integer jsonToInt(String filePath, String desiredItem) {
        int i = 0;
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();

            // reads the main_menu portion of gameText.json
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            i = jsonObject.get(desiredItem).getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static void IntToJson(String filePath, String desiredItem, int newValue) {
        int i = 0;
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            jsonObject.addProperty(desiredItem, newValue);
            String newValues = jsonObject.toString();

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(newValues);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String optionsValues(Map<String, String> optionsMap) {
        StringBuilder optionsStr = new StringBuilder();
        for (Map.Entry<String, String> option : optionsMap.entrySet()) {
            if (!optionsStr.toString().isEmpty()) {
                optionsStr.append(", ");
            }
            optionsStr.append(option.getValue());
        }

        return optionsStr.toString();
    }

    public static Set<String> getValueKey(Map<String, String> map, String value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}