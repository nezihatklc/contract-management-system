package com.project.cms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class responsible for creating and providing
 * a connection to the application's MySQL database.
 * <p>
 * This class loads the MySQL JDBC driver and returns
 * a {@link java.sql.Connection} object using the predefined
 * database URL, username, and password.
 * <br><br>
 * If the connection attempt fails, the method prints an
 * error message and returns {@code null}.
 * </p>
 *
 * <p><b>Database:</b> contact_app_db<br>
 * <b>User:</b> myuser<br>
 * <b>Password:</b> 1234</p>
 *
 * @author Nezihat Kılıç
 * @version 1.0
 */
public class DbConnection {

    /** JDBC URL pointing to the MySQL database. */
    private static final String URL =
            "jdbc:mysql://localhost:3306/contact_app_db?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    /** Username for database authentication. */
    private static final String USER = "myuser";

    /** Password for database authentication. */
    private static final String PASSWORD = "1234";

    /**
     * Establishes a new connection to the application's MySQL database.
     *
     * <p>The method attempts to load the MySQL JDBC driver and
     * then creates a connection using the {@link DriverManager}.
     * In case of failure, it prints a descriptive error message
     * and returns {@code null}.</p>
     *
     * @return a valid {@link Connection} object if successful,
     *         otherwise {@code null}
     */
      public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(true); // Ensure auto-commit is on
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Database connection error: " + e.getMessage());
            return null; 
        }
    }
    
    public static Connection safeGetConnection() {
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("❌ Database error: Could not establish connection.");
        }
        return conn;
    }

}


