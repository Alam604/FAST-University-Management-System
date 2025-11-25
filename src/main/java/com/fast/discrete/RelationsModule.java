package com.fast.discrete;

import java.util.*;

/**
 * Relations Module
 * Handles binary relations and their properties: reflexive, symmetric, transitive, equivalence
 */
public class RelationsModule {
    private Map<String, Set<String>> relation; // From -> To pairs
    private Set<String> domain;
    
    public RelationsModule() {
        this.relation = new HashMap<>();
        this.domain = new HashSet<>();
    }
    
    /**
     * Add a relation pair (from, to)
     */
    public void addRelation(String from, String to) {
        domain.add(from);
        domain.add(to);
        relation.computeIfAbsent(from, k -> new HashSet<>()).add(to);
    }
    
    /**
     * Check if relation is reflexive: every element relates to itself
     */
    public boolean isReflexive() {
        for (String element : domain) {
            if (!relation.getOrDefault(element, new HashSet<>()).contains(element)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Check if relation is symmetric: if (a,b) exists then (b,a) exists
     */
    public boolean isSymmetric() {
        for (String from : relation.keySet()) {
            for (String to : relation.get(from)) {
                Set<String> toRelations = relation.getOrDefault(to, new HashSet<>());
                if (!toRelations.contains(from)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Check if relation is transitive: if (a,b) and (b,c) exist then (a,c) exists
     */
    public boolean isTransitive() {
        for (String a : relation.keySet()) {
            for (String b : relation.get(a)) {
                for (String c : relation.getOrDefault(b, new HashSet<>())) {
                    if (!relation.getOrDefault(a, new HashSet<>()).contains(c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * Check if relation is an equivalence relation (reflexive, symmetric, transitive)
     */
    public boolean isEquivalenceRelation() {
        return isReflexive() && isSymmetric() && isTransitive();
    }
    
    /**
     * Find equivalence classes for an equivalence relation
     */
    public Set<Set<String>> findEquivalenceClasses() {
        if (!isEquivalenceRelation()) {
            return new HashSet<>();
        }
        
        Set<Set<String>> equivalenceClasses = new HashSet<>();
        Set<String> processed = new HashSet<>();
        
        for (String element : domain) {
            if (!processed.contains(element)) {
                Set<String> equivalenceClass = new HashSet<>();
                dfsEquivalenceClass(element, equivalenceClass);
                equivalenceClasses.add(equivalenceClass);
                processed.addAll(equivalenceClass);
            }
        }
        
        return equivalenceClasses;
    }
    
    private void dfsEquivalenceClass(String element, Set<String> equivalenceClass) {
        if (equivalenceClass.contains(element)) {
            return;
        }
        equivalenceClass.add(element);
        
        for (String related : relation.getOrDefault(element, new HashSet<>())) {
            if (!equivalenceClass.contains(related)) {
                dfsEquivalenceClass(related, equivalenceClass);
            }
        }
    }
    
    /**
     * Check if relation is a partial order (reflexive, transitive, antisymmetric)
     */
    public boolean isPartialOrder() {
        // Antisymmetric: if (a,b) and (b,a) exist then a == b
        for (String a : relation.keySet()) {
            for (String b : relation.get(a)) {
                if (!a.equals(b)) {
                    Set<String> bRelations = relation.getOrDefault(b, new HashSet<>());
                    if (bRelations.contains(a)) {
                        return false; // Not antisymmetric
                    }
                }
            }
        }
        
        return isReflexive() && isTransitive();
    }
    
    /**
     * Compose two relations: R1 ∘ R2
     */
    public RelationsModule compose(RelationsModule other) {
        RelationsModule result = new RelationsModule();
        
        for (String a : this.relation.keySet()) {
            for (String b : this.relation.get(a)) {
                for (String c : other.relation.getOrDefault(b, new HashSet<>())) {
                    result.addRelation(a, c);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Find transitive closure using Floyd-Warshall algorithm
     */
    public Map<String, Set<String>> transitiveCloseure() {
        Map<String, Set<String>> closure = new HashMap<>();
        
        // Initialize with original relation
        for (String element : domain) {
            closure.put(element, new HashSet<>(relation.getOrDefault(element, new HashSet<>())));
        }
        
        // Floyd-Warshall
        for (String k : domain) {
            for (String i : domain) {
                for (String j : domain) {
                    if (closure.get(i).contains(k) && closure.get(k).contains(j)) {
                        closure.get(i).add(j);
                    }
                }
            }
        }
        
        return closure;
    }
    
    public Map<String, Set<String>> getRelation() {
        return relation;
    }
    
    public Set<String> getDomain() {
        return domain;
    }
}
