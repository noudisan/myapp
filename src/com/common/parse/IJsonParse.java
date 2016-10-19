package com.common.parse;

import android.content.Context;
import com.common.util.Result;
import org.json.JSONException;

/**
 * Created by zhoutaotao on 6/5/15.
 */
public interface IJsonParse {
    Result parse(Context context, String jsonString) throws JSONException;
}
