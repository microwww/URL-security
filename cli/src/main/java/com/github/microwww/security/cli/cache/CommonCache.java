
package com.github.microwww.security.cli.cache;

import java.io.Serializable;

/**
 * @author changshu.li
 */
public interface CommonCache {

    public void cache(String key, Serializable o);

    public void cache(String key, Serializable o, long expSeconds);

    public <T extends Serializable> T getCacheSerializable(String key, Class<T> clazz);

    public <T> T getCache(String key, Class<T> clazz);

    public void removeCache(String key);

    public boolean isCached(String key);
}
