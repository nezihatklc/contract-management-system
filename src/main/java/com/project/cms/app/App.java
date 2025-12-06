package com.project.cms.app;

/**
 * Entry point of the Contract Management System application.
 * <p>
 * This class is responsible for starting the program by invoking the
 * {@link ApplicationInitializer}, which prepares all required services,
 * database connections, and UI components before the system becomes usable.
 * </p>
 * @author Zeynep Sıla Şimşek
 */

public class App {
    /**
     * Launches the application.
     *
     * @param args Not used, but required by the Java runtime.
     */
    public static void main(String[] args) {
        ApplicationInitializer.start();
    }
}

