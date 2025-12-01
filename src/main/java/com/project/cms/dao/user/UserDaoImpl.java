package com.project.cms.dao.user;

import com.project.cms.model.Role;
import com.project.cms.model.User;
import com.project.cms.util.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) implementation for performing database operations
 * related to the User entity.
 *
 * This class implements the methods defined in the UserDao interface,
 * providing the actual database logic using JDBC.
 * Provides full CRUD operations for Manager and
 * authentication support (find by username).
 */


public class UserDaoImpl implements UserDao{

    //  FIND USER BY USERNAME
    @Override 
    public User findByUsername (String username){
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) { // to send data securely

            ps.setString(1, username); // set the username parameter
            ResultSet rs = ps.executeQuery(); // execute the query

            if (rs.next()) {
                return mapRowToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //  GET USER BY ID
    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

     //  LIST ALL USERS
    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY user_id";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

     //  UPDATE PASSWORD
    // ---------------------------
    @Override
    public void updatePassword(int userId, String newPasswordHash) {
        String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPasswordHash);
            ps.setInt(2, userId);

            int affected = ps.executeUpdate();
            System.out.println("updatePassword → updated rows = " + affected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE USER
     @Override
    public void updateUser(User user) {
        String sql =
                "UPDATE users SET username = ?, password_hash = ?, name = ?, surname = ?, phone = ?, birth_date = ?, role = ? " +
                "WHERE user_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getName());
            ps.setString(4, user.getSurname());
            ps.setString(5, user.getPhone());

            if (user.getBirthDate() != null) {
                ps.setDate(6, Date.valueOf(user.getBirthDate()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.setString(7, user.getRole().name());
            ps.setInt(8, user.getUserId());

            int affected = ps.executeUpdate();
            System.out.println("updateUser → updated rows = " + affected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  ADD USER 
    @Override
    public void addUser(User user) {
        String sql =
                "INSERT INTO users (username, password_hash, name, surname, phone, birth_date, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getName());
            ps.setString(4, user.getSurname());
            ps.setString(5, user.getPhone());

            if (user.getBirthDate() != null) {
                ps.setDate(6, Date.valueOf(user.getBirthDate()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.setString(7, user.getRole().name());

            int affected = ps.executeUpdate();
            System.out.println("addUser → inserted rows = " + affected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     //  DELETE USER
    @Override
    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            int affected = ps.executeUpdate();
            System.out.println("deleteUser → deleted rows = " + affected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  RESULTSET → USER MAPPER
    private User mapRowToUser(ResultSet rs) throws SQLException {

        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setPhone(rs.getString("phone"));

        Date birthDate = rs.getDate("birth_date");
        if (birthDate != null) {
            user.setBirthDate(birthDate.toLocalDate());
        }

        String roleStr = rs.getString("role");
        if (roleStr != null) {
            user.setRole(Role.valueOf(roleStr));
        }

        Timestamp createdAtTs = rs.getTimestamp("created_at");
        if (createdAtTs != null) {
            user.setCreatedAt(createdAtTs.toLocalDateTime());
        }

        return user;
    }
}