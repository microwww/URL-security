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
    public void cache(String key, Serializable o, long expSeconds) {
        this.cache(key, o);
    }

    @Override
    public <T extends Serializable> T getCacheSerializable(String key, Class<T> clazz) {
        return (T) map.get(key);
    }

    @Override
    public <T> T getCache(String key, Class<T> clazz) {
        return (T) map.get(key);
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
