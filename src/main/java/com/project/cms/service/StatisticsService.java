package com.project.cms.service;

import com.project.cms.dao.contact.ContactDao;
import com.project.cms.model.Contact;
import com.project.cms.util.ConsoleColors;
import com.project.cms.util.ConsoleTable;
import java.util.List;

/**
 * Shows contact statistics using tables and simple animations.
 * Uses ContactDao to fetch data and display results.
 *
 * @author Simay
 */


public class StatisticsService {

    private final ContactDao contactDao;

    // dynamic label width for alignment
    private int maxLabelWidth = 0;

   /**
    * Creates a new StatisticsService.
    * @param contactDao - the DAO used to fetch statistics
    */
    public StatisticsService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    // ----------------------------------------------------------------------
    // Loading Animation (header)
    // ----------------------------------------------------------------------

    /**
     *  Plays a simple animated loading effect
     * @param text 
     * @param cycles
     * @param delay
     */
    private void playLoadingAnimation(String text, int cycles, int delay) {
        for (int i = 0; i < cycles; i++) {
            System.out.print("\r" + ConsoleColors.YELLOW_BOLD + text + ".".repeat(i % 4) + ConsoleColors.RESET);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.print("\r" + " ".repeat(60) + "\r");
    }

    // ----------------------------------------------------------------------
    // Update dynamic label width
    // ----------------------------------------------------------------------
    /**
     * Updates the maximum label width for aligning bar chart labels.
     * @param data
     */
    private void updateMaxLabelWidth(List<String[]> data) {
        maxLabelWidth = 0;
        for (String[] row : data) {
            maxLabelWidth = Math.max(maxLabelWidth, row[0].length());
        }
    }

    // ----------------------------------------------------------------------
    // Animated bar chart
    // ----------------------------------------------------------------------
    /**
     * Prints an animated horizontal bar for charts.
     * @param label
     * @param value
     * @param maxValue
     * @param color
     */
    private void printAnimatedBar(String label, int value, int maxValue, String color) {

        int totalLength = (int) ((value / (double) maxValue) * 40);

        // label formatting (aligned)
        String formattedLabel =
                ConsoleColors.CYAN_BOLD +
                        String.format("%-" + maxLabelWidth + "s", label) +
                        ConsoleColors.RESET;

        // Start line
        System.out.print(formattedLabel + " | ");

        // Fill bar gradually
        for (int i = 0; i < totalLength; i++) {
            System.out.print(color + "█" + ConsoleColors.RESET);
            try {
                Thread.sleep(20); // animation smoothness
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // print count
        System.out.println(" " + ConsoleColors.YELLOW_BOLD + "(" + value + ")" + ConsoleColors.RESET);
    }

    // ----------------------------------------------------------------------
    // Console Table Section
    // ----------------------------------------------------------------------
    /**
     * Prints a formatted table section using ConsoleTable.
     * @param title
     * @param data
     */
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
    // MAIN DASHBOARD
    // ----------------------------------------------------------------------
    
    /**
     * Shows the full statistics dashboard.
     * Includes general info, charts, tables, and animations.
     */
    public void showStatistics() {
        // Loading animation before stats
        playLoadingAnimation("Loading statistics", 25, 80);

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
        // CITY DISTRIBUTION (ANIMATED GRAPH)
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.PURPLE + "\n------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "[ CITY DISTRIBUTION ]" + ConsoleColors.RESET);

        List<String[]> cityData = contactDao.getCityDistribution();

        updateMaxLabelWidth(cityData);
        int maxCity = cityData.stream().mapToInt(c -> Integer.parseInt(c[1])).max().orElse(1);

        for (String[] row : cityData) {
            printAnimatedBar(row[0], Integer.parseInt(row[1]), maxCity, ConsoleColors.BLUE_BRIGHT);
        }


        // ------------------------------------------------------------------
        // AGE GROUP (TABLE)
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.PURPLE + "\n------------------------------------------------------------" + ConsoleColors.RESET);
        printTableSection("[ AGE GROUP DISTRIBUTION ]", contactDao.getAgeGroupDistribution());


        // ------------------------------------------------------------------
        // TOP FIRST NAMES
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.PURPLE + "\n------------------------------------------------------------" + ConsoleColors.RESET);
        printTableSection("[ TOP 5 FIRST NAMES ]", contactDao.getTopFirstNames());


        // ------------------------------------------------------------------
        // TOP LAST NAMES
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.PURPLE + "\n------------------------------------------------------------" + ConsoleColors.RESET);
        printTableSection("[ TOP 5 LAST NAMES ]", contactDao.getTopLastNames());


        // ------------------------------------------------------------------
        // BIRTH MONTH DISTRIBUTION (ANIMATED GRAPH)
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.RED_BOLD + "\n------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD + "[ BIRTH MONTH DISTRIBUTION ]" + ConsoleColors.RESET);

        List<String[]> birthData = contactDao.getBirthMonthDistribution();

        updateMaxLabelWidth(birthData);
        int maxMonth = birthData.stream().mapToInt(m -> Integer.parseInt(m[1])).max().orElse(1);

        for (String[] row : birthData) {
            printAnimatedBar(row[0], Integer.parseInt(row[1]), maxMonth, ConsoleColors.ORANGE);
        }


        // ------------------------------------------------------------------
        // END OF DASHBOARD
        // ------------------------------------------------------------------
        System.out.println(ConsoleColors.BLUE_BOLD +
                "\n======================== END OF DASHBOARD ========================\n" +
                ConsoleColors.RESET);
    }
}
