package com.common.parse;

import android.content.Context;
import com.common.util.Constants;
import com.common.util.Result;
import com.common.util.Utils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhoutaotao on 6/5/15.
 */
public class LoginParse implements IJsonParse {
    @Override
    public Result parse(Context context, String jsonString) throws JSONException {
        JSONObject jsonStringObj = new JSONObject(jsonString);
        JSONObject data = jsonStringObj.optJSONObject("data");
        if (!Utils.judgeJsonObject(data)) {
            String sectionName = data.optString("sectionName");
            int onSiteFlag = data.optInt("onSiteFlag");
            String downloadUrl = data.optString("downloadUrl");
            if (downloadUrl != "null" && downloadUrl.trim().length() > 2) {
                return new Result(Constants.ErrorCode.SUCCESS, downloadUrl);
            } else {
                /*String wuyouSource = data.optString("wuyouSourceName");
                UserConfig userConfig = UserConfig.getInstance(context);
                userConfig.setSectionName(sectionName);
                userConfig.setOnSiteFlag(onSiteFlag);
                userConfig.setWuyouSource(wuyouSource);
                userConfig.save(context);*/
            }
        }
        return new Result(Constants.ErrorCode.SUCCESS, null);
    }
}
