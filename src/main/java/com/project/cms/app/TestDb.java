package com.project.cms.app;


import com.project.cms.util.PasswordHasher;

public class TestDb {
    public static void main(String[] args) {
        System.out.println("tt  = " + PasswordHasher.hashPassword("tt"));
        System.out.println("jd  = " + PasswordHasher.hashPassword("jd"));
        System.out.println("sd  = " + PasswordHasher.hashPassword("sd"));
        System.out.println("man = " + PasswordHasher.hashPassword("man"));
    }
}
