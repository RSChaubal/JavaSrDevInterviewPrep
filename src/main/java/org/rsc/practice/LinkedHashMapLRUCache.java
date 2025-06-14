package org.rsc.practice;

import java.util.LinkedHashMap;

public class LinkedHashMapLRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public LinkedHashMapLRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public static void main(String [] args) {

        // Test 1: Accessing non-existent key
        LinkedHashMapLRUCache<Integer, Integer> cache = new LinkedHashMapLRUCache<>(2);
        assert cache.get(1) == null : "Test 1 failed";

        // Test 2: Adding elements up to capacity
        cache.put(1, 10);
        cache.put(2, 20);
        assert cache.get(1) == 10 : "Test 2 failed";
        assert cache.get(2) == 20 : "Test 2 failed";

        // Test 3: Adding element beyond capacity (eviction)
        cache.put(3, 30); // Should evict key 1
        assert cache.get(1) == null : "Test 3 failed";
        assert cache.get(2) == 20 : "Test 3 failed";
        assert cache.get(3) == 30 : "Test 3 failed";

        // Test 4: Updating existing key
        cache.put(2, 200);
        assert cache.get(2) == 200 : "Test 4 failed";

        // Test 5: Accessing a key to make it most recently used
        cache.get(3); // Now 2 is LRU
        cache.put(4, 40); // Should evict key 2
        assert cache.get(2) == null : "Test 5 failed";
        assert cache.get(3) == 30 : "Test 5 failed";
        assert cache.get(4) == 40 : "Test 5 failed";

        // Test 6: Repeatedly accessing the same key
        cache.get(3);
        cache.get(3);
        cache.put(5, 50); // Should evict key 4
        assert cache.get(4) == null : "Test 6 failed";
        assert cache.get(3) == 30 : "Test 6 failed";
        assert cache.get(5) == 50 : "Test 6 failed";

        // Test 7: Capacity 1
        LinkedHashMapLRUCache<Integer, Integer> singleCache = new LinkedHashMapLRUCache<>(1);
        singleCache.put(1, 100);
        assert singleCache.get(1) == 100 : "Test 7 failed";
        singleCache.put(2, 200);
        assert singleCache.get(1) == null : "Test 7 failed";
        assert singleCache.get(2) == 200 : "Test 7 failed";

        // Test 8: Capacity 0 (should not store anything)
        LinkedHashMapLRUCache<Integer, Integer> zeroCache = new LinkedHashMapLRUCache<>(0);
        zeroCache.put(1, 1);
        assert zeroCache.get(1) == null : "Test 8 failed";

        // Test 9: Null key/value (if allowed)
        LinkedHashMapLRUCache<Integer, Integer> nullCache = new LinkedHashMapLRUCache<>(2);
        nullCache.put(null, 123);
        assert nullCache.get(null) == 123 : "Test 9 failed";
        nullCache.put(5, null);
        assert nullCache.get(5) == null : "Test 9 failed";

        System.out.println("All tests passed!");

    }

}