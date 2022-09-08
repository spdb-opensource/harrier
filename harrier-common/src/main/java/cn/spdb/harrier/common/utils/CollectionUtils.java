
package cn.spdb.harrier.common.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException("Construct CollectionUtils");
    }

    /**
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Returns a new {@link Collection} containing <i>a</i> minus a subset of
     * <i>b</i>.  Only the elements of <i>b</i> that satisfy the predicate
     * condition, <i>p</i> are subtracted from <i>a</i>.
     *
     * <p>The cardinality of each element <i>e</i> in the returned {@link Collection}
     * that satisfies the predicate condition will be the cardinality of <i>e</i> in <i>a</i>
     * minus the cardinality of <i>e</i> in <i>b</i>, or zero, whichever is greater.</p>
     * <p>The cardinality of each element <i>e</i> in the returned {@link Collection} that does <b>not</b>
     * satisfy the predicate condition will be equal to the cardinality of <i>e</i> in <i>a</i>.</p>
     *
     * @param a the collection to subtract from, must not be null
     * @param b the collection to subtract, must not be null
     * @param <T> T
     * @return a new collection with the results
     * @see Collection#removeAll
     */
    public static <T> Collection<T> subtract(Set<T> a, Set<T> b) {
        return org.apache.commons.collections4.CollectionUtils.subtract(a, b);
    }

    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Collection coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * String to map
     *
     * @param str string
     * @param separator separator
     * @return string to map
     */
    public static Map<String, String> stringToMap(String str, String separator) {
        return stringToMap(str, separator, "");
    }

    /**
     * String to map
     *
     * @param str string
     * @param separator separator
     * @param keyPrefix prefix
     * @return string to map
     */
    public static Map<String, String> stringToMap(String str, String separator, String keyPrefix) {

        Map<String, String> emptyMap = new HashMap<>(0);
        if (StringUtils.isEmpty(str)) {
            return emptyMap;
        }
        if (StringUtils.isEmpty(separator)) {
            return emptyMap;
        }
        String[] strings = str.split(separator);
        int initialCapacity = (int)(strings.length / DEFAULT_LOAD_FACTOR) + 1;
        Map<String, String> map = new HashMap<>(initialCapacity);
        for (int i = 0; i < strings.length; i++) {
            String[] strArray = strings[i].split("=");
            if (strArray.length != 2) {
                return emptyMap;
            }
            //strArray[0] KEY  strArray[1] VALUE
            if (StringUtils.isEmpty(keyPrefix)) {
                map.put(strArray[0], strArray[1]);
            } else {
                map.put(keyPrefix + strArray[0], strArray[1]);
            }
        }
        return map;
    }

    /**
     * Transform item in collection
     *
     * @param collection origin collection
     * @param transformFunc transform function
     * @param <R> origin item type
     * @param <T> target type
     * @return transform list
     */
    public static <R, T> List<T> transformToList(Collection<R> collection, Function<R, T> transformFunc) {
        if (isEmpty(collection)) {
            return new ArrayList<>();
        }
        return collection.stream().map(transformFunc).collect(Collectors.toList());
    }

    /**
     * Collect collection to map
     *
     * @param collection origin collection
     * @param keyTransformFunction key transform function
     * @param <K> target k type
     * @param <V> value
     * @return map
     */
    public static <K, V> Map<K, V> collectionToMap(Collection<V> collection, Function<V, K> keyTransformFunction) {
        if (isEmpty(collection)) {
            return new HashMap<>();
        }
        return collection.stream().collect(Collectors.toMap(keyTransformFunction, Function.identity()));
    }

    /**
     * Helper class to easily access cardinality properties of two collections.
     *
     * @param <O> the element type
     */
    private static class CardinalityHelper<O> {

        /**
         * Contains the cardinality for each object in collection A.
         */
        final Map<O, Integer> cardinalityA;

        /**
         * Contains the cardinality for each object in collection B.
         */
        final Map<O, Integer> cardinalityB;

        /**
         * Create a new CardinalityHelper for two collections.
         *
         * @param a the first collection
         * @param b the second collection
         */
        public CardinalityHelper(final Iterable<? extends O> a, final Iterable<? extends O> b) {
            cardinalityA = CollectionUtils.getCardinalityMap(a);
            cardinalityB = CollectionUtils.getCardinalityMap(b);
        }

        /**
         * Returns the frequency of this object in collection A.
         *
         * @param obj the object
         * @return the frequency of the object in collection A
         */
        public int freqA(final Object obj) {
            return getFreq(obj, cardinalityA);
        }

        /**
         * Returns the frequency of this object in collection B.
         *
         * @param obj the object
         * @return the frequency of the object in collection B
         */
        public int freqB(final Object obj) {
            return getFreq(obj, cardinalityB);
        }

        private int getFreq(final Object obj, final Map<?, Integer> freqMap) {
            final Integer count = freqMap.get(obj);
            if (count != null) {
                return count;
            }
            return 0;
        }
    }

    /**
     * returns {@code true} iff the given {@link Collection}s contain
     * exactly the same elements with exactly the same cardinalities.
     *
     * @param a the first collection
     * @param b the second collection
     * @return Returns true iff the given Collections contain exactly the same elements with exactly the same cardinalities.
     * That is, iff the cardinality of e in a is equal to the cardinality of e in b, for each element e in a or b.
     */
    public static boolean equalLists(Collection<?> a, Collection<?> b) {
        if (a == null && b == null) {
            return true;
        }

        if (a == null || b == null) {
            return false;
        }

        return isEqualCollection(a, b);
    }

    /**
     * Returns {@code true} iff the given {@link Collection}s contain
     * exactly the same elements with exactly the same cardinalities.
     * <p>
     * That is, iff the cardinality of <i>e</i> in <i>a</i> is
     * equal to the cardinality of <i>e</i> in <i>b</i>,
     * for each element <i>e</i> in <i>a</i> or <i>b</i>.
     *
     * @param a the first collection, must not be null
     * @param b the second collection, must not be null
     * @return <code>true</code> iff the collections contain the same elements with the same cardinalities.
     */
    public static boolean isEqualCollection(final Collection<?> a, final Collection<?> b) {
        if (a.size() != b.size()) {
            return false;
        }
        final CardinalityHelper<Object> helper = new CardinalityHelper<>(a, b);
        if (helper.cardinalityA.size() != helper.cardinalityB.size()) {
            return false;
        }
        for (final Object obj : helper.cardinalityA.keySet()) {
            if (helper.freqA(obj) != helper.freqB(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a {@link Map} mapping each unique element in the given
     * {@link Collection} to an {@link Integer} representing the number
     * of occurrences of that element in the {@link Collection}.
     * <p>
     * Only those elements present in the collection will appear as
     * keys in the map.
     *
     * @param <O> the type of object in the returned {@link Map}. This is a super type of O
     * @param coll the collection to get the cardinality map for, must not be null
     * @return the populated cardinality map
     */
    public static <O> Map<O, Integer> getCardinalityMap(final Iterable<? extends O> coll) {
        final Map<O, Integer> count = new HashMap<>();
        for (final O obj : coll) {
            count.put(obj, count.getOrDefault(obj, 0) + 1);
        }
        return count;
    }

}
