package com.github.microwww.security.cli;

import com.github.microwww.security.cli.cache.CommonCache;
import com.github.microwww.security.cli.cache.WeakCache;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FindService {

    private static final Map map = new ConcurrentHashMap();

    private static final FindService find = new FindService();

    public static CommonCache loadCache() {
        return find.load(CommonCache.class, () -> new WeakCache());
    }

    private FindService() {
    }

    public <T> T load(Class<T> service, Supplier<T> supplier) {
        T t = (T) map.get(service);
        if (t == null) {
            synchronized (map) {
                t = (T) map.get(service);
                if (t == null) {
                    ServiceLoader<T> load = ServiceLoader.load(service);
                    Iterator<T> its = load.iterator();
                    if (its.hasNext()) {
                        t = its.next();
                    } else {
                        t = supplier.get();
                    }
                    t = (T) map.putIfAbsent(service, t);
                }
            }
        }
        return t;
    }
}
