
package com.github.microwww.security.cli.cache;

import com.github.microwww.security.cli.dto.Employee;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author changshu.li
 */
public class CacheFactoryTest {

    CommonCache cache = new WeakCache();

    /**
     * Test of getCommonCache method, of class CacheFactory.
     */
    @Test
    public void testGetCacheJson() {
        System.out.println("getCommonCache");
        Object expResult = "vvvv" + System.currentTimeMillis();
        String key = expResult.toString();
        cache.cacheJson(key, expResult);
        Object result = cache.getCacheJson(key);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCacheSerializable() {
        System.out.println("getCommonCache");
        Object expResult = "vvvv" + System.currentTimeMillis();
        String key = expResult.toString();
        Employee o = new Employee();
        o.setAccount(expResult.toString());
        cache.cache(key, o);
        Employee result = (Employee) cache.getCacheSerializable(key);
        assertEquals(expResult, result.getAccount());
    }

    @Test
    public void testIsCache() {
        System.out.println("getCommonCache");
        Object expResult = "vvvv" + System.currentTimeMillis();
        assertFalse(cache.isCached(expResult.toString()));

        String key = expResult.toString();

        Employee o = new Employee();
        o.setAccount(expResult.toString());
        cache.cache(key, o);
        Employee result = (Employee) cache.getCacheSerializable(key);
        assertEquals(expResult, result.getAccount());
    }

}
