package com.thechallengers.psagame.Leaderboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by VanHoang on 10/14/2017.
 */

public class JSONParser {

    public static HashMap<String, Object> parse(String json) {
        return new Gson().fromJson(
                json, new TypeToken<HashMap<String, Object>>() {}.getType()
        );
    }

    public static String toJSON(Map<String, String> map) {
        if (map == null)
            map = new HashMap<String, String>();
        return new Gson().toJson(map);
    }

}
