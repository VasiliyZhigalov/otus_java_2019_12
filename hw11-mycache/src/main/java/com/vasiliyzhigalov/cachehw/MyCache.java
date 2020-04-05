package com.vasiliyzhigalov.cachehw;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    private Map<K, V> cache;
    private Set<HwListener<K, V>> listeners;

    public MyCache() {
        this.cache = new WeakHashMap<>();
        this.listeners = new HashSet<>();
    }
//Надо реализовать эти методы

    @Override
    public void put(K key, V value) {
        checkNotNull(key);
        checkNotNull(value);
        cache.put(key, value);
        listeners.forEach(listener -> listener.notify(key, value, "put"));
    }

    @Override
    public void remove(K key) {
        checkNotNull(key);
        listeners.forEach(listener -> listener.notify(key, cache.get(key), "remove"));
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        checkNotNull(key);
        listeners.forEach(listener -> listener.notify(key, cache.get(key), "get"));
        return cache.get(key);

    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        this.listeners.remove(listener);
    }

    private void checkNotNull(Object object) {
        if (object == null) {
            throw new NullPointerException("cache key or value is null");
        }

    }
}
