package tdd.lru;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LruMap<K, V> {

    private HashMap<K, V> map = new HashMap<K, V>();
    private List<K> keyList = new ArrayList<K>();
    private int limit;
    private ScheduledExecutorService executor;

    public LruMap(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Invalid limit: " + limit);
        }
        this.limit = limit;
        this.executor = Executors.newScheduledThreadPool(1);
    }

    public synchronized void put(K key, V value) {
        assert map.size() == keyList.size() : "キーリストとマップのサイズは常に同じ";
        if (size() >= limit) {
            if (!map.containsKey(key)) {
                map.remove(keyList.remove(0));
            }
        }
        updateKey(key);
        map.put(key, value);
    }

    public synchronized void put(final K key, V value, final long expiring, final TimeUnit timeUnit) {
        put(key, value);
        Runnable terminaterKey = new Runnable() {
            @Override
            public void run() {
                remove(key);
            }
        };
        executor.schedule(terminaterKey, expiring, timeUnit);
    }

    private void updateKey(K key) {
        keyList.remove(key);
        keyList.add(key);
    }

    public synchronized V get(K key) {
        assert map.size() == keyList.size() : "キーリストとマップのサイズは常に同じ";
        if (!map.containsKey(key)) {
            return null;
        }
        updateKey(key);
        return map.get(key);
    }

    public synchronized int size() {
        assert map.size() == keyList.size() : "キーリストとマップのサイズは常に同じ";
        return map.size();
    }

    public synchronized void setLimit(int limit) {
        this.limit = limit;
        for (int i = 0; i <= size() - limit; i++) {
            K key = keyList.remove(0);
            map.remove(key);
        }
    }

    @Override
    public String toString() {
        return String.format("%s [limit:%d]", map.toString(), limit);
    }

    public synchronized V remove(K key) {
        keyList.remove(key);
        return map.remove(key);
    }
}
