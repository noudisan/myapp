package com.common.net;

import java.util.Hashtable;
import java.util.LinkedList;

public class RequestCache {
    private static final int CACHE_LIMIT = 30;

    private LinkedList<String> history;

    private Hashtable<String, String> cache;

    public RequestCache() {
        history = new LinkedList<String>();
        cache = new Hashtable<String, String>();
    }

    public void put(String url, String data) {
        history.add(url);
        if (history.size() > CACHE_LIMIT) {
            String old_url = (String) history.poll();
            cache.remove(old_url);
        }
        cache.put(url, data);
    }

    public String get(String url) {
        return cache.get(url);
    }
}
