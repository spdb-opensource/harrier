package cn.spdb.harrier.api.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 缓存类（最近最少未使用）.
 * 
 * @param <K>
 *            键
 * @param <V>
 *            值 <K,V> 键值对
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> implements Serializable {
    /**
     * 缓存默认大小.
     */
    public static final int DEFAULT_CAPASITY = 20;
    /**
     * 线程同步锁.
     */
    private static final Lock LOCK = new ReentrantLock();
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * 缓存实际大小.
     */
    public int cacheCapasity = DEFAULT_CAPASITY;

    /**
     * Constructor.
     * 
     */
    public LRUCache() {
        super(DEFAULT_CAPASITY);
        cacheCapasity = DEFAULT_CAPASITY;
    }

    /**
     * Constructor.
     * 
     * @param size
     *            缓存大小
     * 
     */
    public LRUCache(int size) {
        super(size);
        cacheCapasity = size;
    }

    /**
     * 清空緩存.
     * 
     * @see java.util.LinkedHashMap#clear()
     */
    @Override
    public void clear() {
        try {
            LOCK.lock();
            super.clear();
        } finally {
            /***/
            LOCK.unlock();
        }
    }

    /**
     * 判断缓存中是否包含key.
     * 
     * @see java.util.HashMap#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key) {
        try {
            LOCK.lock();
            return super.containsKey(key);
        } finally {
            /***/
            LOCK.unlock();
        }
    }

    /**
     * 判断是否包含对象.
     * 
     * @see java.util.LinkedHashMap#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        try {
            LOCK.lock();
            return super.containsValue(value);
        } finally {
            /***/
            LOCK.unlock();
        }
    }

    /**
     * 从缓存中查询对象.
     * 
     * @see java.util.LinkedHashMap#get(java.lang.Object)
     */
    @Override
    public V get(Object key) {
        try {
            LOCK.lock();
            return super.get(key);
        } finally {
            /***/
            LOCK.unlock();
        }
    }

    /**
     * 判断缓存是否为空.
     * 
     * @see java.util.HashMap#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        try {
            LOCK.lock();
            return super.isEmpty();
        } finally {
            /***/
            LOCK.unlock();
        }
    }

    /**
     * 放入缓存.
     * 
     * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public V put(K key, V value) {
        try {
            LOCK.lock();
            return super.put(key, value);
        } finally {
            /***/
            LOCK.unlock();
        }
    }

    /**
     * 从缓存中删除.
     * 
     * @see java.util.HashMap#remove(java.lang.Object)
     */
    @Override
    public V remove(Object key) {
        try {
            LOCK.lock();
            return super.remove(key);
        } finally {
            /***/
            LOCK.unlock();
        }
    }

    /**
     * 是否删除最早未使用缓存对象.
     * 
     * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
     */
    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        try {
            LOCK.lock();
            return this.size() > cacheCapasity;
        } finally {
            /***/
            LOCK.unlock();
        }
    }

    /**
     * 缓存大小.
     * 
     * @see java.util.HashMap#size()
     */
    @Override
    public int size() {
        try {
            LOCK.lock();
            return super.size();
        } finally {
            /***/
            LOCK.unlock();
        }
    }
}
