package com.project.cms.service;

<<<<<<< HEAD
import com.project.cms.model.Role;
import com.project.cms.model.User;
import com.project.cms.util.DbConnection;
import com.project.cms.util.PasswordHasher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements BaseService<User> {

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (username, password_hash, name, surname, phone, birth_date, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, PasswordHasher.hash(user.getPasswordHash())); // Hash password
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getPhone());
            stmt.setDate(6, Date.valueOf(user.getBirthDate()));
            stmt.setString(7, user.getRole().name());
            
            stmt.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        // Implementation can be added if needed
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getById(int id) {
        // Implementation can be added if needed
        return null;
    }

    @Override
    public List<User> getAll() {
        return getAllUsers();
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, PasswordHasher.hash(password)); // Hash input password
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFirstName(rs.getString("name"));
        user.setLastName(rs.getString("surname"));
        user.setPhone(rs.getString("phone"));
        if (rs.getDate("birth_date") != null) {
            user.setBirthDate(rs.getDate("birth_date").toLocalDate());
        }
        user.setRole(Role.valueOf(rs.getString("role")));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return user;
    }
=======
import com.project.cms.model.User;
import com.mysql.cj.x.protobuf.MysqlxCrud.Delete;
import com.project.cms.exception.InvalidCredentialsException;
import com.project.cms.exception.UserNotFoundException;
import com.project.cms.exception.ValidationException;

public interface UserService {

    /* Called when the user tries to log in. */
    User login(String userName, String passwordPlain)
            throws InvalidCredentialsException, UserNotFoundException;

    /* The user logged into the system updates own password. */
    void changePassword(int userId, String oldPlain, String newPlain)
            throws ValidationException, InvalidCredentialsException;

    /* For Manager: Adding a new user */
    User createUser(User user) 
            throws ValidationException;

    /* For Manager: User update */
    void updateUser(User user) 
            throws ValidationException, UserNotFoundException;

    /*For Manager: Delete User */
    void deletUser(int userId) 
            throws UserNotFoundException;
    
>>>>>>> f5718489d5185396536f59de0c762cdc48e8058d
}
