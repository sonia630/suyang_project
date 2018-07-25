package com.o2o.massage.core.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author edwin
 * @since 14 Jun 2014
 */
public class CollectionUtils {

    private CollectionUtils(){
        throw new RuntimeException("No CollectionUtils instance for you");
    }

    public static <V> List<V> newArrayList(){
        return new ArrayList<V>();
    }

    public static <V> List<V> newArrayList(int size){
        return new ArrayList<V>(size);
    }

    public static <V> List<V> newLinkedList(){
        return new LinkedList<V>();
    }
    public static <V> Set<V> newHashSet(){
        return new HashSet<V>();
    }

    public static <K,V> Map<K,V> newHashMap(){
        return new HashMap<K,V>();
    }

    public static <K,V> Map<K,V> newHashMap(int size){
        return new HashMap<K,V>(size);
    }
    public static <K,V> Map<K,V> newConcurrentHashMap(){
        return new ConcurrentHashMap<K,V>();
    }

    public static <K,V> Map<K,V> newConcurrentHashMap(int size){
        return new ConcurrentHashMap<K,V>(size);
    }

    public static <V> List<V> trans2List(Collection<V> c){
        if(c == null) throw new NullPointerException();
        if(c.isEmpty()) return Collections.emptyList();
        List<V> l = newArrayList(c.size());
        l.addAll(c);
        return l;
    }

    public static <K,V> Map<K,V> newLinkedHashMap(){
        return new LinkedHashMap<K, V>();
    }
}
