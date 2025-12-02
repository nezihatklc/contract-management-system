package com.project.cms.service;

import com.project.cms.dao.contact.ContactDao;
import com.project.cms.model.Contact;
import com.project.cms.util.ConsoleColors;
import com.project.cms.util.ConsoleTable;
import java.util.List;

public class StatisticsService {

    private final ContactDao contactDao;

    // Dynamic label width for perfect alignment
    private int maxLabelWidth = 0;

    public StatisticsService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    // ----------------------------------------------------------------------
    // Update maximum label width based on data labels (for perfect alignment)
    // ----------------------------------------------------------------------
    private void updateMaxLabelWidth(List<String[]> data) {
        maxLabelWidth = 0;
        for (String[] row : data) {
            maxLabelWidth = Math.max(maxLabelWidth, row[0].length());
        }
    }

    // ----------------------------------------------------------------------
    // Draw aligned and colored ASCII bar chart
    // ----------------------------------------------------------------------
    private void printAlignedBar(String label, int value, int maxValue, String color) {

        // Determine bar length (max 40 chars)
        int barLength = (int) ((value / (double) maxValue) * 40);
        StringBuilder bar = new StringBuilder();

        for (int i = 0; i < barLength; i++) {
            bar.append(color).append("█").append(ConsoleColors.RESET);
        }

        // Format label with dynamic width
        String formattedLabel =
                ConsoleColors.CYAN_BOLD +
                        String.format("%-" + maxLabelWidth + "s", label) +
                        ConsoleColors.RESET;

        // Final aligned output
        System.out.printf(
                "%s | %-40s %s(%d)%s\n",
                formattedLabel,
                bar.toString(),
                ConsoleColors.YELLOW_BOLD,
                value,
                ConsoleColors.RESET
        );
    }

    // ----------------------------------------------------------------------
    // Display a formatted table section (using ConsoleTable)
    // ----------------------------------------------------------------------
    private void printTableSection(String title, List<String[]> data) {

        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\n" + title + ConsoleColors.RESET);

        List<String> headers = List.of("Category", "Count");
        List<List<String>> rows = new java.util.ArrayList<>();

        for (String[] row : data) {
            rows.add(List.of(row[0], row[1]));
        }

        ConsoleTable.printTable(headers, rows);
    }

    // ----------------------------------------------------------------------
    // MAIN DASHBOARD METHOD
    // ----------------------------------------------------------------------
    public void showStatistics() {

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT +
                "\n============================================================" +
                "\n                   CONTACTS STATISTICS DASHBOARD            " +
                "\n============================================================\n" +
                ConsoleColors.RESET);

        // ------------------------------------------------------------------
        // GENERAL INFORMATION
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.YELLOW_BOLD + "[ GENERAL INFORMATION ]" + ConsoleColors.RESET);

        int total = contactDao.countAllContacts();
        int withLinkedin = contactDao.countContactsWithLinkedin();
        int withoutLinkedin = contactDao.countContactsWithoutLinkedin();
        String commonFirst = contactDao.findMostCommonFirstName();
        String commonLast = contactDao.findMostCommonLastName();
        Contact youngest = contactDao.findYoungestContact();
        Contact oldest = contactDao.findOldestContact();
        double avgAge = contactDao.getAverageAge();

        System.out.printf("%-22s : %s%d%s\n",
                "Total Contacts", ConsoleColors.GREEN_BOLD, total, ConsoleColors.RESET);

        System.out.printf("%-22s : %s%d%s\n",
                "With LinkedIn", ConsoleColors.CYAN_BOLD, withLinkedin, ConsoleColors.RESET);

        System.out.printf("%-22s : %s%d%s\n",
                "Without LinkedIn", ConsoleColors.RED_BOLD, withoutLinkedin, ConsoleColors.RESET);

        System.out.printf("%-22s : %s%s%s\n",
                "Most Common Name", ConsoleColors.YELLOW_BOLD, commonFirst, ConsoleColors.RESET);

        System.out.printf("%-22s : %s%s%s\n",
                "Most Common Surname", ConsoleColors.YELLOW_BOLD, commonLast, ConsoleColors.RESET);

        System.out.printf("%-22s : %s%s %s (%s)%s\n",
                "Youngest Contact",
                ConsoleColors.GREEN_BOLD, youngest.getFirstName(), youngest.getLastName(),
                youngest.getBirthDate(), ConsoleColors.RESET);

        System.out.printf("%-22s : %s%s %s (%s)%s\n",
                "Oldest Contact",
                ConsoleColors.GREEN_BOLD, oldest.getFirstName(), oldest.getLastName(),
                oldest.getBirthDate(), ConsoleColors.RESET);

        System.out.printf("%-22s : %s%.1f%s\n",
                "Average Age", ConsoleColors.PURPLE_BOLD, avgAge, ConsoleColors.RESET);

        // ------------------------------------------------------------------
        // CITY DISTRIBUTION (GRAPH)
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.PURPLE + "\n------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD + "[ CITY DISTRIBUTION ]" + ConsoleColors.RESET);

        List<String[]> cityData = contactDao.getCityDistribution();

        updateMaxLabelWidth(cityData);
        int maxCity = cityData.stream().mapToInt(c -> Integer.parseInt(c[1])).max().orElse(1);

        for (String[] row : cityData) {
            printAlignedBar(row[0], Integer.parseInt(row[1]), maxCity, ConsoleColors.BLUE_BRIGHT);
        }

        // ------------------------------------------------------------------
        // AGE GROUP DISTRIBUTION (TABLE)
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.PURPLE + "\n------------------------------------------------------------" + ConsoleColors.RESET);
        printTableSection("[ AGE GROUP DISTRIBUTION ]", contactDao.getAgeGroupDistribution());

        // ------------------------------------------------------------------
        // TOP FIRST NAMES (TABLE)
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.PURPLE + "\n------------------------------------------------------------" + ConsoleColors.RESET);
        printTableSection("[ TOP 5 FIRST NAMES ]", contactDao.getTopFirstNames());

        // ------------------------------------------------------------------
        // TOP LAST NAMES (TABLE)
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.PURPLE + "\n------------------------------------------------------------" + ConsoleColors.RESET);
        printTableSection("[ TOP 5 LAST NAMES ]", contactDao.getTopLastNames());

        // ------------------------------------------------------------------
        // BIRTH MONTH DISTRIBUTION (GRAPH)
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.PURPLE + "\n------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD + "[ BIRTH MONTH DISTRIBUTION ]" + ConsoleColors.RESET);

        List<String[]> birthData = contactDao.getBirthMonthDistribution();

        updateMaxLabelWidth(birthData);
        int maxMonth = birthData.stream().mapToInt(m -> Integer.parseInt(m[1])).max().orElse(1);

        for (String[] row : birthData) {
            printAlignedBar(row[0], Integer.parseInt(row[1]), maxMonth, ConsoleColors.ORANGE);
        }

        // ------------------------------------------------------------------
        // END OF DASHBOARD
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.BLUE_BOLD +
                "\n======================== END OF DASHBOARD ========================\n" +
                ConsoleColors.RESET);
    }
}
