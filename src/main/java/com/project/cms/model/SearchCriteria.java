package com.project.cms.model;

import java.util.HashMap;
import java.util.Map;

public class SearchCriteria {
    private Map<String, String> criteria = new HashMap<>();

    // Kriter ekleme metodu
    public void addCriterion(String field, String value) {
        if (value != null && !value.trim().isEmpty()) {
            criteria.put(field, value);
        }
    }

    // Kriterleri okuma metodu
    public Map<String, String> getCriteria() {
        return criteria;
    }
    
    // Filtre var mı kontrolü
    public boolean hasCriteria() {
        return !criteria.isEmpty();
    }
    
}
