package com.project.cms.util;

import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.util.ConsoleColors;

import java.util.List;


public class ConsoleTable {

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


    private static void printSeparator(int[] colWidths) {
        System.out.print(ConsoleColors.PURPLE + "+");
        for (int width : colWidths) {
            System.out.print("=".repeat(width + 2) + "+");
        }
        System.out.println(ConsoleColors.RESET);
    }


    private static void printRow(List<String> row, int[] colWidths, boolean isHeader) {

        System.out.print(ConsoleColors.PURPLE + "|" + ConsoleColors.RESET);

        for (int i = 0; i < row.size(); i++) {

            String cell = row.get(i);

            String content = " "
                    + (isHeader ? ConsoleColors.BOLD : "")
                    + ConsoleColors.GREEN
                    + cell
                    + ConsoleColors.RESET
                    + " ".repeat(colWidths[i] - cell.length() + 1);

            System.out.print(content + ConsoleColors.PURPLE + "|" + ConsoleColors.RESET);
        }

        System.out.println();
    }
}






    
}
