package idv.code.cache;

import com.google.common.cache.*;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CacheLoaderTest {

//    http://www.baeldung.com/guava-cache
//    Finally, here are a few additional quick notes about the Guava cache implementation:
//
//    it is thread-safe
//    you can insert values manually into the cache using put(key,value)
//    you can measure your cache performance using CacheStats ( hitRate(), missRate(), ..)


    //Eviction by Size
    @Test
    public void whenCacheMiss_thenValueIsComputed() {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder().build(loader);

        assertEquals(0, cache.size());
        assertEquals("HELLO", cache.getUnchecked("hello"));
        assertEquals(1, cache.size());
    }

    //Eviction by Weight

    @Test
    public void whenCacheReachMaxSize_thenEviction() {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };
        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder().maximumSize(3).build(loader);

        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("forth");
        assertEquals(3, cache.size());
        assertNull(cache.getIfPresent("first"));
        assertEquals("FORTH", cache.getIfPresent("forth"));
    }

    @Test
    public void whenCacheReachMaxWeight_thenEviction() {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        Weigher<String, String> weighByLength;
        weighByLength = new Weigher<String, String>() {
            @Override
            public int weigh(String key, String value) {
                return value.length();
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder()
                .maximumWeight(16)
                .weigher(weighByLength)
                .build(loader);

        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("last");
        assertEquals(3, cache.size());
        assertNull(cache.getIfPresent("first"));
        assertEquals("LAST", cache.getIfPresent("last"));
    }

    //Eviction by Time

    //remove records that have been idle for 2ms:
    @Test
    public void whenEntryIdle_thenEviction()
            throws InterruptedException {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.MILLISECONDS)
                .build(loader);

        cache.getUnchecked("hello");
        assertEquals(1, cache.size());

        cache.getUnchecked("hello");
        Thread.sleep(300);

        cache.getUnchecked("test");
        assertEquals(1, cache.size());
        assertNull(cache.getIfPresent("hello"));
    }

    //remove the records after 2ms of being stored:
    @Test
    public void whenEntryLiveTimeExpire_thenEviction()
            throws InterruptedException {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MILLISECONDS)
                .build(loader);

        cache.getUnchecked("hello");
        assertEquals(1, cache.size());
        Thread.sleep(300);
        cache.getUnchecked("test");
        assertEquals(1, cache.size());
        assertNull(cache.getIfPresent("hello"));
    }

    //Weak Keys
    @Test
    public void whenWeakKeyHasNoRef_thenRemoveFromCache() {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder().weakKeys().build(loader);
    }

    //Soft Values
    @Test
    public void whenSoftValue_thenRemoveFromCache() {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder().softValues().build(loader);
    }

    //RemovalNotification
    @Test
    public void whenEntryRemovedFromCache_thenNotify() {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(final String key) {
                return key.toUpperCase();
            }
        };

        RemovalListener<String, String> listener;
        listener = new RemovalListener<String, String>() {
            @Override
            public void onRemoval(RemovalNotification<String, String> n) {
                if (n.wasEvicted()) {
                    String cause = n.getCause().name();
                    assertEquals(RemovalCause.SIZE.toString(), cause);
                }
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener)
                .build(loader);

        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("last");
        assertEquals(3, cache.size());
    }
}