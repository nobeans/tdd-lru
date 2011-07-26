package tdd.lru;

import java.util.HashMap;

public class LruMap<K, V> {

    private final HashMap<K, V> map = new HashMap<K, V>();

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(String key) {
        return map.get(key);
    }

    public int size() {
        return map.size();
    }

}
