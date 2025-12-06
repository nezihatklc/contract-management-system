package com.project.cms.util;

import java.util.List;

/**
 * Utility class for printing formatted tables in the console.
 * <p>
 * This class supports dynamic column sizing, colored headers,
 * and visually separated rows using ASCII table-like formatting.
 * It is mainly used to display data sets in a structured and readable format.
 * @author Zeynep Sıla Şimşek
 */


public class ConsoleTable {

       /**
        * Prints a table to the console using the provided headers and row data.
        * <p>
        * Column widths are calculated dynamically based on the longest
        * header or cell value. A colored header row and separators are
        * included to improve readability.
        *
        * @param headers the list of column titles
        * @param rows    a list of data rows, each row represented as a list of strings
        */

        public static void printTable(List<String> headers, List<List<String>> rows) {

        // Determine max width for each column
        int[] colWidths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            colWidths[i] = headers.get(i).length();
        }

        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                colWidths[i] = Math.max(colWidths[i], row.get(i).length());
            }
        }

        printSeparator(colWidths);

        printRow(headers, colWidths, true);

        printSeparator(colWidths);

        for (List<String> row : rows) {
            printRow(row, colWidths, false);
        }

        printSeparator(colWidths);
    }

    /**
     * Prints a separator line based on the column widths.
     * <p>
     * The separator is constructed with '=' characters and
     * visually divides table sections such as headers and rows.
     *
     * @param colWidths an array representing each column's width
     */


    private static void printSeparator(int[] colWidths) {
        System.out.print(ConsoleColors.PURPLE + "+");
        for (int width : colWidths) {
            System.out.print("=".repeat(width + 2) + "+");
        }
        System.out.println(ConsoleColors.RESET);
    }

    /**
     * Prints a single row of the table.
     * <p>
     * Header rows are printed with bold blue styling, while data rows use
     * regular green text. Each cell is padded based on its column’s width.
     *
     * @param row       the list of cell values for this row
     * @param colWidths the calculated widths for each column
     * @param isHeader  whether this row is the table header
     */


    private static void printRow(List<String> row, int[] colWidths, boolean isHeader) {

        System.out.print(ConsoleColors.PURPLE + "|" + ConsoleColors.RESET);

        for (int i = 0; i < row.size(); i++) {

            String cell = row.get(i);

            String content = " "
                    + (isHeader ? ConsoleColors.BLUE_BOLD : "")
                    + ConsoleColors.GREEN
                    + cell
                    + ConsoleColors.RESET
                    + " ".repeat(colWidths[i] - cell.length() + 1);

            System.out.print(content + ConsoleColors.PURPLE + "|" + ConsoleColors.RESET);
        }

        System.out.println();
    }
}






    

