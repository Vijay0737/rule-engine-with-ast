package com.yourname.ruleengine.data;

import java.util.HashMap;
import java.util.Map;

public class RuleRepository {
    private Map<Integer, String> policyStorage;
    private int policyIdCounter;

    public RuleRepository() {
        this.policyStorage = new HashMap<>();
        this.policyIdCounter = 1;
    }

    public int storePolicy(String policy) {
        policyStorage.put(policyIdCounter, policy);
        return policyIdCounter++;
    }

    public Map<Integer, String> retrievePolicies() {
        return policyStorage;
    }
}
