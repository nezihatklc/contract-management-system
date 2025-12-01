package com.project.cms.model;

import java.util.HashMap;
import java.util.Map;

public class SearchCriteria {

    private Map<String, String> criteria = new HashMap<>();

    public void add(String field, String value) {
        criteria.put(field, value);
    }

    public Map<String, String> getCriteria() {
        return criteria;
    }
}
