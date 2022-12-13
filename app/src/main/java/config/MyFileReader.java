package config;

import game.KeyMovement;
import javafx.scene.input.KeyCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyFileReader {
    private final String path;

    public MyFileReader(String path) {
        this.path = path;
    }

    public Map<String, Map<KeyMovement, KeyCode>> readBindings() throws IOException, ParseException {
        JSONArray ja = (JSONArray) new JSONParser().parse(new FileReader(path));
        Map<String, Map<KeyMovement, KeyCode>> map = new HashMap<>();
        for (Object o : ja) {
            JSONObject jo = (JSONObject) o;
            map.put((String) jo.get("id"), parseStringToMap((JSONObject) jo.get("map")));
        }
        return map;
    }

    private Map<KeyMovement, KeyCode> parseStringToMap(JSONObject map) {
        Map<KeyMovement,KeyCode> toReturn = new HashMap<>();
        toReturn.put(KeyMovement.ACCELERATE, KeyCode.getKeyCode((String) map.get("ACCELERATE")));
        toReturn.put(KeyMovement.TURN_LEFT, KeyCode.getKeyCode((String) map.get("TURN_LEFT")));
        toReturn.put(KeyMovement.TURN_RIGHT, KeyCode.getKeyCode((String) map.get("TURN_RIGHT")));
        toReturn.put(KeyMovement.SHOOT, KeyCode.getKeyCode((String) map.get("SHOOT")));
        toReturn.put(KeyMovement.POWERUP, KeyCode.getKeyCode((String) map.get("POWERUP")));
        return toReturn;
    }
}
