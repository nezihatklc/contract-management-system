package com.project.cms.app;

import com.project.cms.dao.contact.ContactDao;
import com.project.cms.dao.contact.ContactDaoImpl;
import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TestDb {

    public static void main(String[] args) {

        ContactDao dao = new ContactDaoImpl();

        System.out.println("===== DB TEST BAŞLIYOR =====");

        // 1) ADD TEST
        Contact c = new Contact();
        c.setFirstName("Test");
        c.setMiddleName("X");
        c.setLastName("Person");
        c.setNickname("tester123");
        c.setCity("Istanbul");
        c.setPhonePrimary("+905555555555");
        c.setPhoneSecondary(null);
        c.setEmail("test.person@example.com");
        c.setLinkedinUrl(null);
        c.setBirthDate(LocalDate.of(2000, 1, 1));

        System.out.println("→ addContact() deniyoruz...");
        dao.addContact(c);
        System.out.println("✓ Contact başarıyla eklendi.");

        // 2) FIND ALL
        System.out.println("\n→ findAll() sonucu:");
        List<Contact> all = dao.findAll();
        all.forEach(System.out::println);

        // 3) FIND BY ID
        System.out.println("\n→ findById(1) sonucu:");
        Contact byId = dao.findById(1);
        System.out.println(byId);

        // 4) SEARCH TEST
        System.out.println("\n→ search() sonucu:");
        SearchCriteria criteria = new SearchCriteria();
        criteria.add("first_name", "Ahmet");
        criteria.add("city", "İstanbul");

        List<Contact> searchResults = dao.search(criteria);
        searchResults.forEach(System.out::println);

        // 5) SORT TEST
        System.out.println("\n→ findAllSorted('first_name', true) sonucu:");
        List<Contact> sorted = dao.findAllSorted("first_name", true);
        sorted.forEach(System.out::println);

        // 6) DELETE SINGLE
        System.out.println("\n→ deleteContactById(1) deniyoruz...");
        dao.deleteContactById(1);
        System.out.println("✓ Contact ID 1 silindi.");

        // 7) DELETE MULTIPLE
        System.out.println("\n→ deleteContactsByIds(Arrays.asList(2,3)) deniyoruz...");
        dao.deleteContactsByIds(Arrays.asList(2, 3));
        System.out.println("✓ Contact ID 2 ve 3 silindi.");

        System.out.println("\n===== TEST BİTTİ =====");
    }
}
