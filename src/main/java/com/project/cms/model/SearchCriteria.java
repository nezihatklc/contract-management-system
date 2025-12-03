package com.project.cms.model;

import java.util.HashMap;
import java.util.Map;

public class SearchCriteria {

    private final Map<String, Object> criteria = new HashMap<>();

    
    public void add(String field, Object value) {
        if (value == null) return;

        String text = value.toString().trim();
        if (!text.isEmpty()) {
            criteria.put(field, value);
        }
    }

    
    public Map<String, Object> getCriteria() {
        return criteria;
    }

    
    public boolean hasCriteria() {
        return !criteria.isEmpty();
    }
}
