package com.project.cms.app;

import com.project.cms.dao.user.UserDao;
import com.project.cms.dao.user.UserDaoImpl;
import com.project.cms.model.Role;
import com.project.cms.model.User;
import java.time.LocalDate;

public class TestDb {

    public static void main(String[] args) {

        UserDao userDao = new UserDaoImpl();

        // -------------------------------
        // 1) TEST: Find user by username
        // -------------------------------
        System.out.println("==== FIND BY USERNAME ====");
        User u = userDao.findByUsername("tt"); // var olan bir user gir
        System.out.println(u != null ? u.getName() + " " + u.getSurname() : "User not found");

        // -------------------------------
        // 2) TEST: Get user by ID
        // -------------------------------
        System.out.println("\n==== GET USER BY ID ====");
        User u2 = userDao.getUserById(1);
        System.out.println(u2 != null ? u2.getUsername() : "User not found");

        // -------------------------------
        // 3) TEST: List all users
        // -------------------------------
        System.out.println("\n==== GET ALL USERS ====");
        for (User usr : userDao.getAllUsers()) {
            System.out.println(usr.getUserId() + " - " + usr.getUsername());
        }

        // -------------------------------
        // 4) TEST: Add new user
        // -------------------------------
        System.out.println("\n==== ADD NEW USER ====");
        User newUser = new User();
        newUser.setUsername("newuser123");
        newUser.setPasswordHash("pass123");
        newUser.setName("Yeni");
        newUser.setSurname("Kullanici");
        newUser.setPhone("00000000000");
        newUser.setBirthDate(LocalDate.of(2000, 1, 1));
        newUser.setRole(Role.TESTER);

        userDao.addUser(newUser);
        System.out.println("New user added!");

        // -------------------------------
        // 5) TEST: Update user
        // -------------------------------
        System.out.println("\n==== UPDATE USER ====");
        User toUpdate = userDao.findByUsername("newuser123");
        if (toUpdate != null) {
            toUpdate.setName("Guncel");
            toUpdate.setSurname("Isim");
            toUpdate.setPhone("11111111111");
            toUpdate.setRole(Role.JUNIOR_DEVELOPER);

            userDao.updateUser(toUpdate);
            System.out.println("User updated!");
        }

        // -------------------------------
        // 6) TEST: Delete user
        // -------------------------------
        System.out.println("\n==== DELETE USER ====");
        User toDelete = userDao.findByUsername("newuser123");
        if (toDelete != null) {
            userDao.deleteUser(toDelete.getUserId());
            System.out.println("User deleted!");
        }
    }
}
