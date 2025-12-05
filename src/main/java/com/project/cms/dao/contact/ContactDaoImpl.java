package com.project.cms.dao.contact;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.util.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ContactDao} responsible for performing all database
 * operations related to the {@link Contact} entity.
 * <p>
 * This class uses MySQL as the backend and performs CRUD, sorting,
 * dynamic searching, and statistical queries on the contacts table.
 * All SQL operations are executed using {@link PreparedStatement}
 * to ensure safety against SQL injection.
 * @author Nezihat Kılıç
 */

public class ContactDaoImpl implements ContactDao {

    // ADD SINGLE CONTACT
    @Override
    public int addContact(Contact c) {
        String sql = "INSERT INTO contacts " +
                "(first_name, middle_name, last_name, nickname, city, phone_primary, phone_secondary, email, linkedin_url, birth_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot add contact. Database connection failed.");
                return -1;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

                int affected = ps.executeUpdate();

                if (affected == 0) {
                    return -1; // insert failed
                }

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1); // return auto-generated ID
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error inserting contact: " + e.getMessage());
        }

        return -1; // fail
    }

    // ADD CONTACT WITH SPECIFIC ID (FOR UNDO)
    @Override
    public int addContactWithId(Contact c) {
        String sql = "INSERT INTO contacts " +
                "(contact_id, first_name, middle_name, last_name, nickname, city, phone_primary, phone_secondary, email, linkedin_url, birth_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) return -1;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, c.getContactId());
                ps.setString(2, c.getFirstName());
                ps.setString(3, c.getMiddleName());
                ps.setString(4, c.getLastName());
                ps.setString(5, c.getNickname());
                ps.setString(6, c.getCity());
                ps.setString(7, c.getPhonePrimary());
                ps.setString(8, c.getPhoneSecondary());
                ps.setString(9, c.getEmail());
                ps.setString(10, c.getLinkedinUrl());

                if (c.getBirthDate() != null) {
                    ps.setDate(11, Date.valueOf(c.getBirthDate()));
                } else {
                    ps.setNull(11, Types.DATE);
                }

                int affected = ps.executeUpdate();
                return affected > 0 ? c.getContactId() : -1;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error restoring contact: " + e.getMessage());
            return -1;
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

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot find contact. DB connection failed.");
                return null;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in findById: " + e.getMessage());
            return null;
        }
    }


    // FIND ALL CONTACTS
    @Override
    public List<Contact> findAll() {
        String sql = "SELECT * FROM contacts ORDER BY contact_id ASC";
        List<Contact> list = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot load contacts. DB connection failed.");
                return list;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in findAll: " + e.getMessage());
        }

        return list;
    }

    //UPDATE CONTACT
    @Override
    public boolean updateContact(Contact c) {
        String sql = """
                UPDATE contacts SET
                  first_name=?, middle_name=?, last_name=?, nickname=?, city=?,
                  phone_primary=?, phone_secondary=?, email=?, linkedin_url=?, birth_date=?,
                  updated_at=CURRENT_TIMESTAMP
                WHERE contact_id=?
                """;

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot update contact. Database connection failed.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
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

                int affected = ps.executeUpdate();


                return affected == 1;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in updateContact: " + e.getMessage());
            return false;
        }
    }
    // DELETE CONTACT BY ID
     @Override
    public boolean deleteContactById(int id) {
        String sql = "DELETE FROM contacts WHERE contact_id = ?";

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot delete contact. Database connection failed.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);

                int affected = ps.executeUpdate();


                return affected == 1;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in deleteContactById: " + e.getMessage());
            return false;
        }
    }

    // DELETE MULTIPLE 
    @Override
    public void deleteContactsByIds(List<Integer> ids) {

        if (ids == null || ids.isEmpty()) return;

        StringBuilder placeholders = new StringBuilder();

        for (int i = 0; i < ids.size(); i++) {
            placeholders.append("?");
            if (i < ids.size() - 1) placeholders.append(",");
        }

        String sql = "DELETE FROM contacts WHERE contact_id IN (" + placeholders + ")";

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot delete contacts. DB connection failed.");
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < ids.size(); i++) {
                    ps.setInt(i + 1, ids.get(i));
                }

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in deleteContactsByIds: " + e.getMessage());
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

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot sort contacts. DB connection failed.");
                return list;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in findAllSorted: " + e.getMessage());
        }

        return list;
    }

    // SEARCH (DYNAMIC)
    @Override
    public List<Contact> search(SearchCriteria criteria) {

        List<String> allowedFields = List.of(
                "first_name", "last_name", "nickname", "city",
                "email", "phone_primary", "phone_secondary", "linkedin_url"
        );

        StringBuilder sql = new StringBuilder("SELECT * FROM contacts WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        criteria.getCriteria().forEach((field, value) -> {

            if (!allowedFields.contains(field)) {
                System.out.println("⚠ Skipping invalid search field: " + field);
                return; 
            }

            sql.append(" AND ").append(field).append(" LIKE ? ");
            params.add("%" + value + "%");
        });

        List<Contact> list = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Cannot search contacts. Database connection failed.");
                return list;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error in search: " + e.getMessage());
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

        c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        c.setUpdatedAt(rs.getTimestamp("updated_at") != null 
                ? rs.getTimestamp("updated_at").toLocalDateTime()
                : null);

        return c;
    }


    //             STATISTICS METHODS (MANAGER ONLY)
    // ===========================================================

    //  TOTAL CONTACT COUNT
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

    // CONTACTS WITH LINKEDIN COUNT
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

    // CONTACTS WITHOUT LINKEDIN COUNT
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

    // MOST COMMON FIRST NAME
    @Override
    public String findMostCommonFirstName() {
        String sql = "SELECT first_name, COUNT(*) cnt FROM contacts GROUP BY first_name ORDER BY cnt DESC LIMIT 1";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getString("first_name") : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    
    // MOST COMMON LAST NAME
    @Override
    public String findMostCommonLastName() {
        String sql = "SELECT last_name, COUNT(*) cnt FROM contacts GROUP BY last_name ORDER BY cnt DESC LIMIT 1";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getString("last_name") : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // YOUNGEST CONTACT
    @Override
    public Contact findYoungestContact() {
        String sql = "SELECT * FROM contacts WHERE birth_date IS NOT NULL ORDER BY birth_date DESC LIMIT 1";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // OLDEST CONTACT
    @Override
    public Contact findOldestContact() {
        String sql = "SELECT * FROM contacts WHERE birth_date IS NOT NULL ORDER BY birth_date ASC LIMIT 1";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // AVERAGE AGE OF CONTACTS
    @Override
    public double getAverageAge() {
        String sql = "SELECT AVG(TIMESTAMPDIFF(YEAR, birth_date, CURDATE())) AS avg_age FROM contacts WHERE birth_date IS NOT NULL";
        try (Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getDouble("avg_age") : 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // CITY DISTRIBUTION
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

    // AGE GROUP DISTRIBUTION
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

    // TOP FIRST NAMES
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

    // TOP LAST NAMES
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

    // BIRTH MONTH DISTRIBUTION
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
