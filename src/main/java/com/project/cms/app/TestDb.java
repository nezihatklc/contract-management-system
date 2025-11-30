package com.project.cms.app;

import com.project.cms.util.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDb {

    public static void main(String[] args) {
        String sql = "SELECT COUNT(*) AS cnt FROM users";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int count = rs.getInt("cnt");
                System.out.println("Users tablosundaki kayıt sayısı: " + count);
            }
        } catch (SQLException e) {
            System.err.println("DB bağlantı/test sırasında hata: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
