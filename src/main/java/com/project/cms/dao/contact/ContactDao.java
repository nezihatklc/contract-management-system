package com.project.cms.dao.contact;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import java.util.List;

public interface ContactDao {

     // CREATE
    void addContact(Contact contact);
    void addContacts(List<Contact> contacts);

    // READ
    Contact findById(int id);
    List<Contact> findAll();

    // UPDATE
    void updateContact(Contact contact);

    // DELETE (single)
    void deleteContactById(int id);

    // DELETE (multiple)
    void deleteContactsByIds(List<Integer> ids);

    // SEARCH (single + multi field)
    List<Contact> search(SearchCriteria criteria);

    // SORT
    List<Contact> findAllSorted(String field, boolean ascending);

    //             STATISTICS METHODS (MANAGER ONLY)
    // ===========================================================
    int countAllContacts();
    int countContactsWithLinkedin();
    int countContactsWithoutLinkedin();

    String findMostCommonFirstName();
    String findMostCommonLastName();

    Contact findYoungestContact();
    Contact findOldestContact();

    double getAverageAge();

    List<String[]> getCityDistribution();          // (city, count)
    List<String[]> getAgeGroupDistribution();      // ("18-25", count)
    List<String[]> getTopFirstNames();             // (firstname, count)
    List<String[]> getTopLastNames();              // (lastname, count)
    List<String[]> getBirthMonthDistribution();    // ("January", count)

    
}
