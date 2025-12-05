package com.project.cms.dao.user;

import com.project.cms.model.RoleType;
import com.project.cms.model.User;
import com.project.cms.util.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link UserDao} interface.
 * <p>
 * Provides database operations for the User entity such as:
 * authentication (find by username), password update,
 * and full CRUD operations required by the Manager role.
 * <p>
 * Uses JDBC and {@link PreparedStatement} for safe SQL execution.
 * Contains no business logic; validations are handled in the Service layer.
 */

public class UserDaoImpl implements UserDao{

    //  FIND USER BY USERNAME
    @Override 
    public User findByUsername (String username){
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot fetch user. Database connection failed.");
                return null;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username); // set the username parameter
                ResultSet rs = ps.executeQuery(); // execute the query

                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in findByUsername: " + e.getMessage());
        }
        return null;
    }

    //  GET USER BY ID
    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot fetch user by ID. Database connection failed.");
                return null;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in getUserById: " + e.getMessage());
        }

        return null;
    }

     //  LIST ALL USERS
    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id";

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot load users. Database connection failed.");
                return list; 
            }

            try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapRowToUser(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in getAllUsers: " + e.getMessage());
        }

        return list; 
    }


     //  UPDATE PASSWORD-
    @Override
    public boolean updatePassword(int userId, String newPasswordHash) {
        String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot update password. Database connection failed.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, newPasswordHash);
                ps.setInt(2, userId);

                int affected = ps.executeUpdate();

                return affected == 1;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in updatePassword: " + e.getMessage());
            return false;
        }
    }

    // UPDATE USER
     @Override
    public boolean updateUser(User user) {
        String sql =
                "UPDATE users SET name = ?, surname = ?, phone = ?, birth_date = ?, role = ? " +
                "WHERE user_id = ?";
        
        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot update user. Database connection failed.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getSurname());
                ps.setString(3, user.getPhone());

                if (user.getBirthDate() != null) {
                    ps.setDate(4, Date.valueOf(user.getBirthDate()));
                } else {
                    ps.setNull(4, Types.DATE);
                }

                ps.setString(5, user.getRole().name());
                ps.setInt(6, user.getUserId());

                int affected = ps.executeUpdate();


                return affected == 1;
            }
        } catch (SQLException e) {
             System.out.println("❌ Error in updateUser: " + e.getMessage());
            return false;
        }
    }

    //  ADD USER 
    @Override
    public int addUser(User user) {
        String sql =
            "INSERT INTO users (username, password_hash, name, surname, phone, birth_date, role) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot add user. Database connection failed.");
                return -1;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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


                if (affected == 0) {
                    return -1;
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);  // ✔ new user_id
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in addUser: " + e.getMessage());
        }

        return -1;
    }



     //  DELETE USER
    @Override
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot delete user. Database connection failed.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);

                int affected = ps.executeUpdate();


                return affected == 1;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in deleteUser: " + e.getMessage());
            return false;
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
            user.setRole(RoleType.valueOf(roleStr));
        }

        Timestamp createdAtTs = rs.getTimestamp("created_at");
        if (createdAtTs != null) {
            user.setCreatedAt(createdAtTs.toLocalDateTime());
        }

        return user;
    }
}