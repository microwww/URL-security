package com.github.microwww.security.cli.cache;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakCache implements CommonCache {

    private final Map map = Collections.synchronizedMap(new WeakHashMap());

    @Override
    public void cache(String key, Serializable o) {
        map.put(key, o);
    }

    @Override
    public void cache(String key, String o) {
        map.put(key, o);
    }

    @Override
    public void cacheJson(String key, Object o) {
        map.put(key, o);
    }

    @Override
    public String getCacheString(String key) {
        return (String) map.get(key);
    }

    @Override
    public Serializable getCacheSerializable(String key) {
        return (Serializable) map.get(key);
    }

    @Override
    public Object getCacheJson(String key) {
        return map.get(key);
    }

    @Override
    public boolean isCached(String key) {
        return map.containsKey(key);
    }

    @Override
    public void removeCache(String key) {
        map.remove(key);
    }
}
