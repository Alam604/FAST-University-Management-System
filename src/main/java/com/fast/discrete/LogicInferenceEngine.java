package com.fast.discrete;

import java.util.*;

/**
 * Logic & Inference Engine Module
 * Parse and verify rules, perform inference, detect conflicts
 */
public class LogicInferenceEngine {
    private List<Rule> rules;
    private Map<String, Boolean> facts;
    
    public LogicInferenceEngine() {
        this.rules = new ArrayList<>();
        this.facts = new HashMap<>();
    }
    
    /**
     * Add a rule: "If A then B" or "If A and B then C"
     */
    public void addRule(String premise, String conclusion) {
        rules.add(new Rule(premise, conclusion));
    }
    
    /**
     * Add a fact to the knowledge base
     */
    public void addFact(String fact, boolean value) {
        facts.put(fact, value);
    }
    
    /**
     * Forward chaining: derive new facts from existing facts and rules
     */
    public void forwardChain() {
        boolean changed = true;
        
        while (changed) {
            changed = false;
            
            for (Rule rule : rules) {
                if (evaluatePremise(rule.premise) && !facts.getOrDefault(rule.conclusion, false)) {
                    facts.put(rule.conclusion, true);
                    changed = true;
                }
            }
        }
    }
    
    /**
     * Backward chaining: prove a goal using rules and facts
     */
    public boolean backwardChain(String goal) {
        if (facts.getOrDefault(goal, false)) {
            return true;
        }
        
        for (Rule rule : rules) {
            if (rule.conclusion.equals(goal)) {
                if (evaluatePremise(rule.premise)) {
                    facts.put(goal, true);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Evaluate a premise (can be simple fact or compound with "AND", "OR")
     */
    private boolean evaluatePremise(String premise) {
        if (premise.contains(" AND ")) {
            String[] parts = premise.split(" AND ");
            for (String part : parts) {
                if (!evaluatePremise(part.trim())) {
                    return false;
                }
            }
            return true;
        } else if (premise.contains(" OR ")) {
            String[] parts = premise.split(" OR ");
            for (String part : parts) {
                if (evaluatePremise(part.trim())) {
                    return true;
                }
            }
            return false;
        } else {
            return facts.getOrDefault(premise, false);
        }
    }
    
    /**
     * Detect conflicts: check if contradictory rules/facts exist
     */
    public List<String> detectConflicts() {
        List<String> conflicts = new ArrayList<>();
        
        // Check for contradictory facts
        for (String fact : facts.keySet()) {
            String negation = "NOT_" + fact;
            if (facts.containsKey(negation) && facts.get(negation)) {
                conflicts.add("Contradiction: " + fact + " and " + negation);
            }
        }
        
        // Check for conflicting rule conclusions
        for (int i = 0; i < rules.size(); i++) {
            for (int j = i + 1; j < rules.size(); j++) {
                if (rules.get(i).conclusion.equals(rules.get(j).conclusion)) {
                    if (!rules.get(i).premise.equals(rules.get(j).premise)) {
                        // Same conclusion with different premises could cause conflicts
                        conflicts.add("Potential conflict: Multiple rules lead to " + rules.get(i).conclusion);
                    }
                }
            }
        }
        
        return conflicts;
    }
    
    /**
     * Apply modus ponens: if we have P and P→Q, then conclude Q
     */
    public void modusPonens(String premise, String rule) {
        if (facts.getOrDefault(premise, false)) {
            for (Rule r : rules) {
                if (r.premise.equals(premise)) {
                    facts.put(r.conclusion, true);
                }
            }
        }
    }
    
    /**
     * Apply modus tollens: if we have ¬Q and P→Q, then conclude ¬P
     */
    public void modusTollens(String negatedConclusion, String rule) {
        String conclusion = negatedConclusion.replace("NOT_", "");
        
        for (Rule r : rules) {
            if (r.conclusion.equals(conclusion)) {
                facts.put("NOT_" + r.premise, true);
            }
        }
    }
    
    public Map<String, Boolean> getFacts() {
        return facts;
    }
    
    public List<Rule> getRules() {
        return rules;
    }
    
    public static class Rule {
        public String premise;
        public String conclusion;
        
        public Rule(String premise, String conclusion) {
            this.premise = premise;
            this.conclusion = conclusion;
        }
        
        @Override
        public String toString() {
            return premise + " → " + conclusion;
        }
    }
}
