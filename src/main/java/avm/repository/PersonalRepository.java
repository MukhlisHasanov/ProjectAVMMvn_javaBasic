package avm.repository;

import avm.products.Personal;
import avm.products.PersonalState;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PersonalRepository {
    private String dbName;

    private final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS personal (" +
            " id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " name        TEXT NOT NULL," +
            " department  TEXT NOT NULL)";
    private final String SQL_INSERT = "INSERT INTO personal (name, department) VALUES (?, ?)";
    private final String SQL_UPDATE = "UPDATE personal SET name = ?, department = ? WHERE id = ?";
    private final String SQL_FIND_BY_ID = "SELECT * FROM personal WHERE id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM personal";
    private final String SQL_DELETE_BY_ID = "DELETE FROM personal WHERE id = ?";

    public PersonalRepository(String dbName) {
        this.dbName = dbName;
    }

    public void save(Personal personal) {
        try (Connection connection = DriverManager.getConnection(dbName);
             PreparedStatement psi = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psu = connection.prepareStatement(SQL_UPDATE)) {
            if (personal.getId() == null) {
                // insert record
                psi.setString(1, personal.getName());
                psi.setString(2, personal.getDepartment().name());
                psi.executeUpdate();

                ResultSet rs = psi.getGeneratedKeys();
                if (rs.next()) {
                    personal.setId(rs.getInt(1));
                }
            } else {
                // update record
                psu.setString(1, personal.getName());
                psu.setString(2, personal.getDepartment().name());
                psu.setInt(3, personal.getId());
                psu.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Personal findById(Integer id) {
        Personal personal = null;
        try (Connection connection = DriverManager.getConnection(dbName);
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                personal = new Personal(rs.getInt("id"),
                        rs.getString("name"),
                        PersonalState.valueOf(rs.getString("department")));
            }
            return personal;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer id) {
        try (Connection connection = DriverManager.getConnection(dbName);
             PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<Personal> findAll() {
        Collection<Personal> personals = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbName);
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                personals.add(new Personal(
                        rs.getInt("id"),
                        rs.getString("name"),
                        PersonalState.valueOf(rs.getString("department"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personals;
    }
}