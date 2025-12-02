package com.project.cms.dao.contact;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.util.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl implements ContactDao {

    // ADD SINGLE CONTACT
     @Override
    public void addContact(Contact c) {
        String sql = "INSERT INTO contacts " +
                "(first_name, middle_name, last_name, nickname, city, phone_primary, phone_secondary, email, linkedin_url, birth_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getMiddleName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getNickname());
            ps.setString(5, c.getCity());
            ps.setString(6, c.getPhonePrimary());
            ps.setString(7, c.getPhoneSecondary());
            ps.setString(8, c.getEmail());
            ps.setString(9, c.getLinkedinUrl());

            if (c.getBirthDate() != null) {
                ps.setDate(10, Date.valueOf(c.getBirthDate()));
            } else {
                ps.setNull(10, Types.DATE);
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting contact", e);
        }
    }
    
    // ADD MULTIPLE CONTACTS
    @Override
    public void addContacts(List<Contact> contacts) {
        for (Contact c : contacts) {
            addContact(c);
        }
    }

    // FIND CONTACT BY ID
    @Override
    public Contact findById(int id) {
        String sql = "SELECT * FROM contacts WHERE contact_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding contact", e);
        }

        return null;
    }

    // FIND ALL CONTACTS
    @Override
    public List<Contact> findAll() {
        String sql = "SELECT * FROM contacts ORDER BY contact_id ASC";

        List<Contact> list = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listing contacts", e);
        }

        return list;
    }

    //UPDATE CONTACT
    @Override
    public void updateContact(Contact c) {
        String sql = """
                UPDATE contacts SET
                  first_name=?, middle_name=?, last_name=?, nickname=?, city=?,
                  phone_primary=?, phone_secondary=?, email=?, linkedin_url=?, birth_date=?,
                  updated_at=CURRENT_TIMESTAMP
                WHERE contact_id=?
                """;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getMiddleName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getNickname());
            ps.setString(5, c.getCity());
            ps.setString(6, c.getPhonePrimary());
            ps.setString(7, c.getPhoneSecondary());
            ps.setString(8, c.getEmail());
            ps.setString(9, c.getLinkedinUrl());

            if (c.getBirthDate() != null) {
                ps.setDate(10, Date.valueOf(c.getBirthDate()));
            } else {
                ps.setNull(10, Types.DATE);
            }

            ps.setInt(11, c.getContactId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating contact", e);
        }
    }

    // DELETE CONTACT BY ID
     @Override
    public void deleteContactById(int id) {
        String sql = "DELETE FROM contacts WHERE contact_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting contact", e);
        }
    }

    // DELETE MULTIPLE (IN clause)
    @Override
    public void deleteContactsByIds(List<Integer> ids) {

        if (ids == null || ids.isEmpty()) return;

        StringBuilder placeholders = new StringBuilder();

        for (int i = 0; i < ids.size(); i++) {
            placeholders.append("?");
            if (i < ids.size() - 1) placeholders.append(",");
        }

        String sql = "DELETE FROM contacts WHERE contact_id IN (" + placeholders + ")";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < ids.size(); i++) {
                ps.setInt(i + 1, ids.get(i));
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting contacts", e);
        }
    }

    // SORT
    @Override
    public List<Contact> findAllSorted(String field, boolean asc) {

        List<String> allowed = List.of(
                "first_name", "last_name", "nickname", "city",
                "phone_primary", "email", "birth_date", "created_at"
        );

        if (!allowed.contains(field)) {
            field = "first_name";
        }

        String sql = "SELECT * FROM contacts ORDER BY " + field + (asc ? " ASC" : " DESC");

        List<Contact> list = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error sorting contacts", e);
        }

        return list;
    }

    // SEARCH (DYNAMIC)
    @Override
    public List<Contact> search(SearchCriteria criteria) {

        StringBuilder sql = new StringBuilder("SELECT * FROM contacts WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        criteria.getCriteria().forEach((field, value) -> {
            sql.append(" AND ").append(field).append(" LIKE ? ");
            params.add("%" + value + "%");
        });

        List<Contact> list = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching contacts", e);
        }

        return list;
    }

    // MAP RESULTSET → CONTACT OBJECT
    private Contact map(ResultSet rs) throws SQLException {
        Contact c = new Contact();

        c.setContactId(rs.getInt("contact_id"));
        c.setFirstName(rs.getString("first_name"));
        c.setMiddleName(rs.getString("middle_name"));
        c.setLastName(rs.getString("last_name"));
        c.setNickname(rs.getString("nickname"));
        c.setCity(rs.getString("city"));

        c.setPhonePrimary(rs.getString("phone_primary"));
        c.setPhoneSecondary(rs.getString("phone_secondary"));

        c.setEmail(rs.getString("email"));
        c.setLinkedinUrl(rs.getString("linkedin_url"));

        Date birth = rs.getDate("birth_date");
        if (birth != null) {
            c.setBirthDate(birth.toLocalDate());
        }

        return c;
    }


    //             STATISTICS METHODS (MANAGER ONLY)
    // ===========================================================
    @Override
    public int countAllContacts() {
        String sql = "SELECT COUNT(*) FROM contacts";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countContactsWithLinkedin() {
        String sql = "SELECT COUNT(*) FROM contacts WHERE linkedin_url IS NOT NULL AND linkedin_url <> ''";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countContactsWithoutLinkedin() {
        String sql = "SELECT COUNT(*) FROM contacts WHERE linkedin_url IS NULL OR linkedin_url = ''";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findMostCommonFirstName() {
        String sql = "SELECT first_name, COUNT(*) cnt FROM contacts GROUP BY first_name ORDER BY cnt DESC LIMIT 1";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getString("first_name") : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public String findMostCommonLastName() {
        String sql = "SELECT last_name, COUNT(*) cnt FROM contacts GROUP BY last_name ORDER BY cnt DESC LIMIT 1";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getString("last_name") : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Contact findYoungestContact() {
        String sql = "SELECT * FROM contacts WHERE birth_date IS NOT NULL ORDER BY birth_date DESC LIMIT 1";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Contact findOldestContact() {
        String sql = "SELECT * FROM contacts WHERE birth_date IS NOT NULL ORDER BY birth_date ASC LIMIT 1";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public double getAverageAge() {
        String sql = "SELECT AVG(TIMESTAMPDIFF(YEAR, birth_date, CURDATE())) AS avg_age FROM contacts WHERE birth_date IS NOT NULL";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getDouble("avg_age") : 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<String[]> getCityDistribution() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT city, COUNT(*) cnt FROM contacts GROUP BY city ORDER BY cnt DESC";

        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("city"),
                    String.valueOf(rs.getInt("cnt"))
                });
            }
        } catch (SQLException e) { throw new RuntimeException(e); }

        return list;
    }

    @Override
    public List<String[]> getAgeGroupDistribution() {
        List<String[]> list = new ArrayList<>();
        String sql = """
        SELECT 
            CASE 
                WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 18 AND 25 THEN '18-25'
                WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 26 AND 30 THEN '26-30'
                WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 31 AND 40 THEN '31-40'
                ELSE '40+' 
            END AS age_group,
            COUNT(*) cnt
        FROM contacts
        WHERE birth_date IS NOT NULL
        GROUP BY age_group
        ORDER BY cnt DESC
        """;

        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("age_group"),
                    String.valueOf(rs.getInt("cnt"))
                });
            }
        } catch (SQLException e) { throw new RuntimeException(e); }

        return list;
    }

    @Override
    public List<String[]> getTopFirstNames() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT first_name, COUNT(*) cnt FROM contacts GROUP BY first_name ORDER BY cnt DESC LIMIT 5";

        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("first_name"),
                    String.valueOf(rs.getInt("cnt"))
                });
            }

        } catch (SQLException e) { throw new RuntimeException(e); }

        return list;
    }

    @Override
    public List<String[]> getTopLastNames() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT last_name, COUNT(*) cnt FROM contacts GROUP BY last_name ORDER BY cnt DESC LIMIT 5";

        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("last_name"),
                    String.valueOf(rs.getInt("cnt"))
                });
            }

        } catch (SQLException e) { throw new RuntimeException(e); }

        return list;
    }

    @Override
    public List<String[]> getBirthMonthDistribution() {
        List<String[]> list = new ArrayList<>();

        String sql = """
            SELECT 
                MONTH(birth_date) AS month_num,
                MONTHNAME(birth_date) AS month_name,
                COUNT(*) AS cnt
            FROM contacts
            WHERE birth_date IS NOT NULL
            GROUP BY month_num, month_name
            ORDER BY month_num
        """;

        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("month_name"),
                    String.valueOf(rs.getInt("cnt"))
                });
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error generating birth month distribution", e);
        }
        return list;
    }
}

