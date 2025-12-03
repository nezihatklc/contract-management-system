package com.project.cms.app;

import com.project.cms.dao.contact.ContactDao;
import com.project.cms.dao.contact.ContactDaoImpl;

import com.project.cms.service.UserService;
import com.project.cms.service.UserServiceImpl;

import com.project.cms.service.ContactService;
import com.project.cms.service.ContactServiceImpl;

import com.project.cms.service.StatisticsService;

import com.project.cms.ui.animation.AsciiAnimator;
import com.project.cms.ui.menu.MainMenu;
import com.project.cms.ui.input.ConsolePrinter;

public class ApplicationInitializer {

    public static void start() {
        try {
            // DAO needed for the StatisticsService
            ContactDao contactDao = new ContactDaoImpl();

            // Services
            UserService userService = new UserServiceImpl();
            ContactService contactService = new ContactServiceImpl();
            StatisticsService statisticsService = new StatisticsService(contactDao);

            // Welcome animation
            AsciiAnimator.showWelcome();

            // Main menu
            MainMenu mainMenu = new MainMenu(
                    userService,
                    contactService,
                    statisticsService
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
