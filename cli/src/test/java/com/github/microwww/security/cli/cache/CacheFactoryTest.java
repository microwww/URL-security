
package com.github.microwww.security.cli.cache;

import com.github.microwww.security.cli.dto.Account;
import org.junit.Test;

import java.io.Serializable;

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
        Serializable expResult = "vvvv" + System.currentTimeMillis();
        String key = expResult.toString();
        cache.cache(key, expResult);
        Object result = cache.getCache(key, String.class);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCacheSerializable() {
        System.out.println("getCommonCache");
        Object expResult = "vvvv" + System.currentTimeMillis();
        String key = expResult.toString();
        Account o = new Account();
        o.setAccount(expResult.toString());
        cache.cache(key, o);
        Account result = cache.getCacheSerializable(key, Account.class);
        assertEquals(expResult, result.getAccount());
    }

    @Test
    public void testIsCache() {
        System.out.println("getCommonCache");
        Object expResult = "vvvv" + System.currentTimeMillis();
        assertFalse(cache.isCached(expResult.toString()));

        String key = expResult.toString();

        Account o = new Account();
        o.setAccount(expResult.toString());
        cache.cache(key, o);
        Account result = cache.getCacheSerializable(key, Account.class);
        assertEquals(expResult, result.getAccount());
    }

}
