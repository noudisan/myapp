package com.common.parse;

import android.content.Context;
import com.common.util.Constants;
import com.common.util.Result;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParseProxy implements IJsonParse {
    private IJsonParse jsonParse;

    public JsonParseProxy(IJsonParse jsonParse) {
        this.jsonParse = jsonParse;
    }

    @Override
    public Result parse(final Context context, String jsonString) throws JSONException {
        JSONObject dataJson = new JSONObject(jsonString);
        int code = Integer.parseInt(dataJson.getString("code"));
        String message = dataJson.getString("msg");
        if (code == Constants.ErrorCode.SUCCESS) {
            if (jsonParse != null) {
                return jsonParse.parse(context, jsonString);
            }
        }
        return new Result(code, message);
    }
}
