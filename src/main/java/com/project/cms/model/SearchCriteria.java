package com.project.cms.model;

import java.util.HashMap;
import java.util.Map;

public class SearchCriteria {
    
    private Map<String, String> criteria = new HashMap<>();

    // fixed as "add" :
    public void add(String field, String value) {
        if (value != null && !value.trim().isEmpty()) {
            criteria.put(field, value);
        }
    }

    public Map<String, String> getCriteria() {
        return criteria;
    }
    
    public boolean hasCriteria() {
        return !criteria.isEmpty();
    }
}