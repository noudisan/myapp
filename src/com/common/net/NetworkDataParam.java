package com.common.net;

import java.util.HashMap;

/**
 * Created by zhoutaotao on 6/5/15.
 */
public class NetworkDataParam {
    String url;
    HashMap<String, Object> params;
    // HashMap<String, File> files;
    HashMap<String, byte[]> files;

    public NetworkDataParam(String url, HashMap<String, Object> params) {
        this(url, params, null);
    }

    public NetworkDataParam(String url, HashMap<String, Object> params, HashMap<String, byte[]> files) {
        this.url = url;
        this.params = params;
        this.files = files;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public HashMap<String, byte[]> getFiles() {
        return files;
    }

    public void setFiles(HashMap<String, byte[]> files) {
        this.files = files;
    }
}