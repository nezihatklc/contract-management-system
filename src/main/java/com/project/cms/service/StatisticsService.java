package com.project.cms.service;

import com.project.cms.dao.contact.ContactDao;
import com.project.cms.model.Contact;

public class StatisticsService {

    private final ContactDao contactDao;

    public StatisticsService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void showStatistics() {

        System.out.println("\n==========================================================");
        System.out.println("                 CONTACTS STATISTICS PANEL");
        System.out.println("==========================================================\n");

        // =========================
        // 1. GENERAL STATS
        // =========================
        int total = contactDao.countAllContacts();
        int withLinkedin = contactDao.countContactsWithLinkedin();
        int withoutLinkedin = contactDao.countContactsWithoutLinkedin();
        String commonFirst = contactDao.findMostCommonFirstName();
        String commonLast = contactDao.findMostCommonLastName();
        Contact youngest = contactDao.findYoungestContact();
        Contact oldest = contactDao.findOldestContact();
        double avgAge = contactDao.getAverageAge();

        System.out.println("[ GENERAL INFO ]");
        System.out.println("Total Contacts             : " + total);
        System.out.println("With LinkedIn              : " + withLinkedin);
        System.out.println("Without LinkedIn           : " + withoutLinkedin);
        System.out.println("Most Common Name           : " + commonFirst);
        System.out.println("Most Common Surname        : " + commonLast);
        System.out.println("Youngest Contact           : " +
                youngest.getFirstName() + " " + youngest.getLastName() +
                " (" + youngest.getBirthDate() + ")");
        System.out.println("Oldest Contact             : " +
                oldest.getFirstName() + " " + oldest.getLastName() +
                " (" + oldest.getBirthDate() + ")");
        System.out.println("Average Age                : " + String.format("%.1f", avgAge));

        System.out.println("\n----------------------------------------------------------");

        // =========================
        // 2. CITY DISTRIBUTION
        // =========================
        System.out.println("[ CITY DISTRIBUTION ]");
        for (String[] row : contactDao.getCityDistribution()) {
            System.out.printf("%-15s : %s\n", row[0], row[1]);
        }

        System.out.println("\n----------------------------------------------------------");

        // =========================
        // 3. AGE GROUPS
        // =========================
        System.out.println("[ AGE GROUPS ]");
        for (String[] row : contactDao.getAgeGroupDistribution()) {
            System.out.printf("%-8s : %s\n", row[0], row[1]);
        }

        System.out.println("\n----------------------------------------------------------");

        
        // =========================
        // 5. TOP FIRST & LAST NAMES
        // =========================
        System.out.println("[ TOP 5 FIRST NAMES ]");
        for (String[] row : contactDao.getTopFirstNames()) {
            System.out.printf("%-15s : %s\n", row[0], row[1]);
        }

        System.out.println("\n[ TOP 5 LAST NAMES ]");
        for (String[] row : contactDao.getTopLastNames()) {
            System.out.printf("%-15s : %s\n", row[0], row[1]);
        }

        System.out.println("\n----------------------------------------------------------");

        // =========================
        // 6. BIRTH MONTH DISTRIBUTION
        // =========================
        System.out.println("[ BIRTH MONTH DISTRIBUTION ]");
        for (String[] row : contactDao.getBirthMonthDistribution()) {
            System.out.printf("%-12s : %s\n", row[0], row[1]);
        }

        System.out.println("\n==========================================================\n");
    }
}
