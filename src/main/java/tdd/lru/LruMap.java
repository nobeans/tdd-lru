package tdd.lru;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LruMap<K, V> {

    private HashMap<K, V> map = new HashMap<K, V>();
    private List<K> keyList = new ArrayList<K>();
    private int limit;

    public LruMap(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Invalid limit: " + limit);
        }
        this.limit = limit;
    }

    public void put(K key, V value) {
        assert map.size() == keyList.size() : "キーリストとマップのサイズは常に同じ";
        if (size() >= limit) {
            map.remove(keyList.remove(0));
        }
        map.put(key, value);
        keyList.add(key);
    }

    public V get(K key) {
        assert map.size() == keyList.size() : "キーリストとマップのサイズは常に同じ";
        if (!map.containsKey(key)) {
            return null;
        }
        assert keyList.contains(key) : "マップに含まれるならばキーリストにも含まれる";
        keyList.remove(key);
        keyList.add(key);
        return map.get(key);
    }

    public int size() {
        return map.size();
    }

}