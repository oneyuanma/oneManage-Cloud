package com.oym.commons.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合工具类
 *
 * @author oneyuanma
 * @data 2021/06/26
 */
public class CollectionUtils {

    public CollectionUtils() {
    }

    public static <T, R> List<R> convert(List<T> list, Function<T, R> converter) {
        return list == null ? null : (List) stream(list).map(converter).collect(Collectors.toList());
    }

    public static <K, T, R> Map<K, R> convert(Map<K, T> map, Function<T, R> converter) {
        if (map == null) {
            return null;
        } else {
            Map<K, R> result = new HashMap(map.size());
            map.forEach((k, t) -> {
                result.put(k, converter.apply(t));
            });
            return result;
        }
    }

    public static <D> boolean hasOne(List<D> sourceList, List<D> checkList) {
        if (sourceList != null && !sourceList.isEmpty() && checkList != null && !checkList.isEmpty()) {
            Iterator var2 = checkList.iterator();

            Object check;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                check = var2.next();
            } while (!sourceList.contains(check));

            return true;
        } else {
            return false;
        }
    }

    public static <E, V> E findOne(Collection<E> collection, V v, Function<E, V> function) {
        if (collection == null) {
            return null;
        } else {
            Iterator var3 = collection.iterator();

            Object e;
            while (true) {
                if (!var3.hasNext()) {
                    return null;
                }

                e = var3.next();
                if (v == null) {
                    if (function.apply((E) e) == null) {
                        break;
                    }
                } else if (v.equals(function.apply((E) e))) {
                    break;
                }
            }

            return (E) e;
        }
    }

    private static <T> Stream<T> stream(List<T> list) {
        return list.parallelStream();
    }

    public static <T, R> List<R> asList(Collection<T> collection, Function<T, R> function) {
        return collection == null ? null : (List) collection.parallelStream().map(function).collect(Collectors.toList());
    }

    public static <T, R> Set<R> asSet(Collection<T> collection, Function<T, R> function) {
        return collection == null ? null : (Set) collection.parallelStream().map(function).collect(Collectors.toSet());
    }

    public static <R> List<R> filter(List<R> list, Predicate<R> predicate) {
        return list == null ? null : (List) stream(list).filter(predicate).collect(Collectors.toList());
    }

    public static <T, R> List<R> filterAndConvert(List<T> list, Predicate<T> predicate, Function<T, R> function) {
        return list == null ? null : (List) stream(list).filter(predicate).map(function).collect(Collectors.toList());
    }

    public static <T, R> List<R> toUniqueList(List<T> list, Function<T, R> function) {
        return list == null ? null : (List) stream(list).map(function).distinct().collect(Collectors.toList());
    }

    public static <T> boolean anyMatch(List<T> list, Predicate<T> predicate) {
        return list == null ? false : stream(list).anyMatch(predicate);
    }

    public static <T> boolean allMatch(List<T> list, Predicate<T> predicate) {
        return list == null ? false : stream(list).allMatch(predicate);
    }

    public static <R> Set<R> filterSet(List<R> list, Predicate<R> predicate) {
        return list == null ? null : (Set) stream(list).filter(predicate).collect(Collectors.toSet());
    }

    public static <T, R> Set<R> filterSetAndConvert(List<T> list, Predicate<T> predicate, Function<T, R> function) {
        return list == null ? null : (Set) stream(list).filter(predicate).map(function).collect(Collectors.toSet());
    }

    public static <T, R> List<T> distinctByProperty(List<T> list, Function<T, R> propertyExtractor) {
        if (list == null) {
            return null;
        } else {
            Set<Object> seen = ConcurrentHashMap.newKeySet();
            return (List) stream(list).filter((t) -> {
                return seen.add(propertyExtractor.apply(t));
            }).collect(Collectors.toList());
        }
    }

    public static <T, R> Set<R> toSet(List<T> list, Function<T, R> function) {
        return list == null ? null : (Set) stream(list).map(function).collect(Collectors.toSet());
    }

    public static <T, K> Map<K, T> toMap(List<T> list, Function<T, K> keyMapper) {
        return list == null ? null : toMap(list, keyMapper, (t) -> {
            return t;
        });
    }

    public static <T, K, V> Map<K, V> toMap(List<T> list, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return list == null ? null : toMap(list, keyMapper, valueMapper, (v, v2) -> {
            return v;
        });
    }

    public static <T, K> Map<K, T> toMap(List<T> list, Function<T, K> keyMapper, BinaryOperator<T> mergeFunction) {
        return list == null ? null : toMap(list, keyMapper, (t) -> {
            return t;
        }, mergeFunction);
    }

    public static <T, K, V> Map<K, V> toMap(List<T> list, Function<T, K> keyMapper, Function<T, V> valueMapper, BinaryOperator<V> mergeFunction) {
        return list == null ? null : (Map) stream(list).collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }

    public static <K, T> Map<K, List<T>> group(List<T> list, Function<T, K> function) {
        return list == null ? null : (Map) stream(list).collect(Collectors.groupingBy(function));
    }

    public static <K, T, R> Map<K, List<R>> groupAndConvert(List<T> list, Function<T, K> function, Function<T, R> convert) {
        return list == null ? null : (Map) stream(list).collect(Collectors.groupingBy(function, Collectors.mapping(convert, Collectors.toList())));
    }

    public static <K, T> Map<K, Set<T>> groupSet(List<T> list, Function<T, K> function) {
        return list == null ? null : (Map) stream(list).collect(Collectors.groupingBy(function, Collectors.toSet()));
    }

    public static <K, T, R> Map<K, Set<R>> groupSetAndConvert(List<T> list, Function<T, K> function, Function<T, R> convert) {
        return list == null ? null : (Map) stream(list).collect(Collectors.groupingBy(function, Collectors.mapping(convert, Collectors.toSet())));
    }
}
