package com.project.cms.app;

import com.project.cms.dao.contact.ContactDao;
import com.project.cms.dao.contact.ContactDaoImpl;
import com.project.cms.service.ContactService;
import com.project.cms.service.ContactServiceImpl;
import com.project.cms.service.StatisticsService;
import com.project.cms.service.UndoService;
import com.project.cms.service.UndoServiceImpl;
import com.project.cms.service.UserService;
import com.project.cms.service.UserServiceImpl;
import com.project.cms.ui.animation.AsciiAnimator;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.menu.MainMenu;

/**
 * Initializes and starts the entire CMS application.
 * <p>
 * This class is responsible for creating DAO and Service layer instances,
 * wiring dependencies, preparing undo-support connections, launching
 * startup animations, and finally opening the main menu. It also ensures
 * that a goodbye animation is shown even if an unexpected error occurs.
 * </p>
 * @author Zeynep Sıla Şimşek
 */

public class ApplicationInitializer {

    /**
     * Starts the CMS application by setting up all required components.
     * <p>
     * Responsibilities:
     * <ul>
     *     <li>Creates DAO instances required by services.</li>
     *     <li>Initializes UserService, ContactService, UndoService, and StatisticsService.</li>
     *     <li>Links UndoService back into UserService and ContactService.</li>
     *     <li>Displays welcome animation and launches the main menu UI.</li>
     *     <li>Always plays the goodbye animation on exit.</li>
     * </ul>
     * </p>
     */

    public static void start() {
        try {
            // DAO needed for the StatisticsService
            ContactDao contactDao = new ContactDaoImpl();

            // Services
            UserService userService = new UserServiceImpl(null);
            ContactService contactService = new ContactServiceImpl(null, userService);

        
            UndoService undoService = new UndoServiceImpl(contactService, userService);

            // 3) UndoService’i servislere geri bağla
            ((UserServiceImpl) userService).setUndoService(undoService);
            ((ContactServiceImpl) contactService).setUndoService(undoService);

            // 4) Statistics normal bağımsız
            StatisticsService statisticsService = new StatisticsService(contactDao);
            // Welcome animation
            AsciiAnimator.showWelcome();

            // Main menu
            MainMenu mainMenu = new MainMenu(
                    userService,
                    contactService,
                    statisticsService,
                    undoService
            );

            mainMenu.start();

        } catch (Exception e) {
            ConsolePrinter.error("A critical error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Goodbye animation always runs
            AsciiAnimator.showGoodbye();
        }
    }
}
