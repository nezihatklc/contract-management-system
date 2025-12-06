package com.project.cms.model;

import java.util.HashMap;
import java.util.Map;
/**
 * Encapsulates filtering criteria for dynamic search operations.
 * <p>
 * This class allows the collection of multiple field-value pairs to construct
 * flexible search queries (e.g., search by Name AND City). It supports the
 * project requirement for both single-field and multi-field searches.
 * @author Zeynep Sıla Şimşek
 */
public class SearchCriteria {
// Stores field names and their corresponding search values
    private final Map<String, Object> criteria = new HashMap<>();

    /**
     * Adds a new search criterion to the list.
     * <p>
     * The value is added only if it is not null and, when converted to string, is not empty.
     *
     * @param field The name of the field to search (e.g., "firstName", "phonePrimary").
     * @param value The value to search for.
     */
    public void add(String field, Object value) {
        if (value == null) return;

        String text = value.toString().trim();
        if (!text.isEmpty()) {
            criteria.put(field, value);
        }
    }

    /**
     * Retrieves the map of all collected search criteria.
     *
     * @return A map containing field names as keys and search values as values.
     */
    public Map<String, Object> getCriteria() {
        return criteria;
    }

    /**
     * Checks whether any search criteria have been added.
     *
     * @return {@code true} if the criteria map is not empty; {@code false} otherwise.
     */
    public boolean hasCriteria() {
        return !criteria.isEmpty();
    }
}
