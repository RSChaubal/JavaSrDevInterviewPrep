package org.rsc.practice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 *  LRUCache is a simple implementation of a Least Recently Used (LRU) cache.
 *  It uses a HashMap for O(1) access time and a LinkedList to maintain the order of access.
 *  LinkedList allows us to efficiently remove the least recently used item when the cache
 *  exceeds its capacity.
 *
 */
public class LRUCache {

    protected int capacity ;
    protected final Map<Integer, Integer> cache ;
    protected final LinkedList<Integer> order ; // LinkedList for maintaining insertion and access order

    public LRUCache(int capacity) {
        this.capacity = capacity ;
        cache = new HashMap<>() ;
        order = new LinkedList<>() ;
    }

    public int get(int key) {
        if (capacity <= 0 || !cache.containsKey(key)) {
            return -1 ; // Return -1 if cache is empty or key does not exist
        }
        order.remove((Object)key) ; // Move the accessed key to the end of the LRU map / access order
        order.addFirst(key) ;
        return cache.get(key) ;
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }
        if (cache.size() >= capacity) {
            Integer leastUsedKey = order.removeLast() ; // Get the last element in the order list
            cache.remove(leastUsedKey) ; // Remove it from the cache
        }
        // Add the new key-value pair
        cache.put(key, value) ;
        order.addFirst(key) ; // Update the value so that it moves to the end of the LRU map
    }

    public static void main(String [] args) {
        // Test 1: Accessing non-existent key
        LRUCache cache = new LRUCache(2);
        assert cache.get(1) == -1 : "Test 1 failed";

        // Test 2: Adding elements up to capacity
        cache.put(1, 10);
        cache.put(2, 20);
        assert cache.get(1) == 10 : "Test 2 failed";
        assert cache.get(2) == 20 : "Test 2 failed";

        // Test 3: Adding element beyond capacity (eviction)
        cache.put(3, 30); // Should evict key 1
        assert cache.get(1) == -1 : "Test 3 failed";
        assert cache.get(2) == 20 : "Test 3 failed";
        assert cache.get(3) == 30 : "Test 3 failed";

        // Test 4: Updating existing key
        cache.put(2, 200);
        assert cache.get(2) == 200 : "Test 4 failed";

        // Test 5: Accessing a key to make it most recently used
        cache.get(3); // Now 2 is LRU
        cache.put(4, 40); // Should evict key 2
        assert cache.get(2) == -1 : "Test 5 failed";
        assert cache.get(3) == 30 : "Test 5 failed";
        assert cache.get(4) == 40 : "Test 5 failed";

        // Test 6: Repeatedly accessing the same key
        cache.get(3);
        cache.get(3);
        cache.put(5, 50); // Should evict key 4
        assert cache.get(4) == -1 : "Test 6 failed";
        assert cache.get(3) == 30 : "Test 6 failed";
        assert cache.get(5) == 50 : "Test 6 failed";

        // Test 7: Capacity 1
        LRUCache singleCache = new LRUCache(1);
        singleCache.put(1, 100);
        assert singleCache.get(1) == 100 : "Test 7 failed";
        singleCache.put(2, 200);
        assert singleCache.get(1) == -1 : "Test 7 failed";
        assert singleCache.get(2) == 200 : "Test 7 failed";

        // Test 8: Capacity 0 (should not store anything)
        LRUCache zeroCache = new LRUCache(0);
        zeroCache.put(1, 1);
        assert zeroCache.get(1) == -1 : "Test 8 failed";

        System.out.println("All tests passed!");
    }
}
