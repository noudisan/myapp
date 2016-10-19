package com.example.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private Context context;
    private final String FILENAME = "myShared";
    private SharedPreferences sharedPreferences;

    public MySharedPreferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(FILENAME,
                Context.MODE_PRIVATE);
    }

    public boolean putData(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public boolean removeData(String key) {
        boolean flag = false;
        if (sharedPreferences.contains(key)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.commit();
            flag = true;
        }
        return flag;
    }

    public List<String> queryAllKey() {
        Map<String, String> map = (Map<String, String>) sharedPreferences.getAll();
        List<String> keyValue = new ArrayList<String>();

        if (map != null && map.size() != 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                keyValue.add("key:" + entry.getKey() + "-value:" + entry.getValue());
            }
        }
        return keyValue;

    }

    public String getValue(String key) {
        String value = null;
        if (sharedPreferences.contains(key)) {
            value = sharedPreferences.getString(key, "");
        }
        return value;

    }
}
