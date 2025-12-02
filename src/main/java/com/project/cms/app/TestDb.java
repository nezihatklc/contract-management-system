package com.project.cms.app;


import com.project.cms.dao.contact.ContactDao;
import com.project.cms.dao.contact.ContactDaoImpl;
import com.project.cms.service.StatisticsService;

public class TestDb{

    public static void main(String[] args) {

        // DAO oluştur
        ContactDao contactDao = new ContactDaoImpl();

        // Servisi oluştur
        StatisticsService service = new StatisticsService(contactDao);

        // Test başlıyor
        System.out.println("\n====== TEST DB STATISTICS TEST ======\n");

        service.showStatistics();

        System.out.println("\n====== END OF TEST ======\n");
    }
}
