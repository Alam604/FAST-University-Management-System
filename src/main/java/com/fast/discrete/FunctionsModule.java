package com.fast.discrete;

import java.util.*;

/**
 * Functions Module
 * Maps students → courses, courses → faculty, faculty → rooms
 * Tests injectivity, surjectivity, and bijectivity
 */
public class FunctionsModule {
    private Map<String, String> mapping; // domain -> codomain
    private Set<String> domain;
    private Set<String> codomain;
    
    public FunctionsModule() {
        this.mapping = new HashMap<>();
        this.domain = new HashSet<>();
        this.codomain = new HashSet<>();
    }
    
    /**
     * Add a function mapping: element from domain maps to element in codomain
     */
    public void addMapping(String from, String to) {
        if (mapping.containsKey(from) && !mapping.get(from).equals(to)) {
            throw new IllegalArgumentException("Function violation: " + from + " cannot map to multiple elements");
        }
        mapping.put(from, to);
        domain.add(from);
        codomain.add(to);
    }
    
    /**
     * Check if function is injective (one-to-one)
     * Each element in domain maps to different element in codomain
     */
    public boolean isInjective() {
        Set<String> images = new HashSet<>();
        for (String value : mapping.values()) {
            if (images.contains(value)) {
                return false; // Two different domain elements map to same codomain element
            }
            images.add(value);
        }
        return true;
    }
    
    /**
     * Check if function is surjective (onto)
     * Every element in codomain is mapped to by some element in domain
     */
    public boolean isSurjective(Set<String> expectedCodomain) {
        for (String element : expectedCodomain) {
            if (!mapping.values().contains(element)) {
                return false; // Codomain element not mapped
            }
        }
        return true;
    }
    
    /**
     * Check if function is bijective (both injective and surjective)
     */
    public boolean isBijective(Set<String> expectedCodomain) {
        return isInjective() && isSurjective(expectedCodomain);
    }
    
    /**
     * Get the image of an element
     */
    public String getImage(String element) {
        return mapping.get(element);
    }
    
    /**
     * Get all pre-images of an element in codomain
     */
    public Set<String> getPreImages(String codomainElement) {
        Set<String> preImages = new HashSet<>();
        for (Map.Entry<String, String> entry : mapping.entrySet()) {
            if (entry.getValue().equals(codomainElement)) {
                preImages.add(entry.getKey());
            }
        }
        return preImages;
    }
    
    /**
     * Compose two functions: f ∘ g means apply g first, then f
     */
    public FunctionsModule compose(FunctionsModule other) {
        FunctionsModule result = new FunctionsModule();
        
        for (String element : this.domain) {
            String intermediate = this.getImage(element);
            if (intermediate != null && other.mapping.containsKey(intermediate)) {
                String finalImage = other.getImage(intermediate);
                if (finalImage != null) {
                    result.addMapping(element, finalImage);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Get inverse function (if it exists and function is bijective)
     */
    public FunctionsModule getInverse(Set<String> expectedCodomain) {
        if (!isBijective(expectedCodomain)) {
            return null; // Inverse doesn't exist
        }
        
        FunctionsModule inverse = new FunctionsModule();
        for (Map.Entry<String, String> entry : mapping.entrySet()) {
            inverse.addMapping(entry.getValue(), entry.getKey());
        }
        
        return inverse;
    }
    
    /**
     * Get the range (image) of the function
     */
    public Set<String> getRange() {
        return new HashSet<>(mapping.values());
    }
    
    public Map<String, String> getMapping() {
        return mapping;
    }
    
    public Set<String> getDomain() {
        return domain;
    }
    
    public Set<String> getCodomain() {
        return codomain;
    }
}
