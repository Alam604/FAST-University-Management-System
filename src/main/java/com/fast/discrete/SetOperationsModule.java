package com.fast.discrete;

import java.util.*;

/**
 * Set Operations Module
 * Handles set theory operations: union, intersection, difference, power sets, subsets
 */
public class SetOperationsModule {
    
    /**
     * Union of two sets
     */
    public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }
    
    /**
     * Intersection of two sets
     */
    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }
    
    /**
     * Difference: elements in set1 but not in set2
     */
    public static <T> Set<T> difference(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.removeAll(set2);
        return result;
    }
    
    /**
     * Symmetric difference: elements in either set but not both
     */
    public static <T> Set<T> symmetricDifference(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(union(set1, set2));
        result.removeAll(intersection(set1, set2));
        return result;
    }
    
    /**
     * Check if set1 is a subset of set2
     */
    public static <T> boolean isSubset(Set<T> set1, Set<T> set2) {
        return set2.containsAll(set1);
    }
    
    /**
     * Check if set1 is a proper subset of set2
     */
    public static <T> boolean isProperSubset(Set<T> set1, Set<T> set2) {
        return isSubset(set1, set2) && set1.size() < set2.size();
    }
    
    /**
     * Generate power set (all subsets)
     */
    public static <T> Set<Set<T>> powerSet(Set<T> set) {
        Set<Set<T>> result = new HashSet<>();
        List<T> list = new ArrayList<>(set);
        int n = list.size();
        
        // Generate all 2^n subsets
        for (int i = 0; i < (1 << n); i++) {
            Set<T> subset = new HashSet<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subset.add(list.get(j));
                }
            }
            result.add(subset);
        }
        
        return result;
    }
    
    /**
     * Generate all k-combinations (subsets of size k)
     */
    public static <T> Set<Set<T>> combinations(Set<T> set, int k) {
        Set<Set<T>> result = new HashSet<>();
        List<T> list = new ArrayList<>(set);
        combinationsHelper(list, k, 0, new HashSet<>(), result);
        return result;
    }
    
    private static <T> void combinationsHelper(List<T> list, int k, int start, 
                                                Set<T> current, Set<Set<T>> result) {
        if (current.size() == k) {
            result.add(new HashSet<>(current));
            return;
        }
        
        for (int i = start; i < list.size(); i++) {
            current.add(list.get(i));
            combinationsHelper(list, k, i + 1, current, result);
            current.remove(list.get(i));
        }
    }
    
    /**
     * Cardinality (size) of a set
     */
    public static <T> int cardinality(Set<T> set) {
        return set.size();
    }
    
    /**
     * Check if two sets are disjoint (no common elements)
     */
    public static <T> boolean isDisjoint(Set<T> set1, Set<T> set2) {
        return intersection(set1, set2).isEmpty();
    }
}
