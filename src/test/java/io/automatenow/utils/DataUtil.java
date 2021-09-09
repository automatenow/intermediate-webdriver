package io.automatenow.utils;

import io.automatenow.tests.BaseTest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class DataUtil extends BaseTest {

    @DataProvider
    public Object[] dataProvider1() {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        // Read JSON file
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/testData.json"));
            jsonObject = (JSONObject) obj;
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }

        // Array to store JSON data
        Object[] data = new Object[1];

        // Store JSON data as key/value pairs in a hashMap
        HashMap<String, String> hashMap = new LinkedHashMap<>();
        if (jsonObject != null) {
            Set<String> jsonObjKeys = jsonObject.keySet();
            for (String jsonObjKey : jsonObjKeys) {
                hashMap.put(jsonObjKey, (String) jsonObject.get(jsonObjKey));
            }
        } else {
            log.error("Error retrieving JSON data");
            throw new RuntimeException();
        }

        // Store HashMap in an array and return array
        data[0] = hashMap;
        return data;
    }
}
