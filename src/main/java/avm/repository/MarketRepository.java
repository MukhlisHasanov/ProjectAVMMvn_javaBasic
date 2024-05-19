package avm.repository;

import avm.products.MarketProduct;

import java.sql.*;
import java.util.*;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project #2 AVM/Hypermarket
 * @author Mukhlis
 * @version May-2024
 */
public class MarketRepository implements ProductRepository<MarketProduct> {
    private String AvmDB;

    private final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS market (" +
            " id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " name        TEXT NOT NULL," +
            " quantity    INTEGER NOT NULL," +
            " price       FLOAT NOT NULL)";
    private final String SQL_DELETE_TABLE = "DELETE FROM personal";
    private final String SQL_INSERT = "INSERT INTO market (name, quantity, price) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE market SET name = ?, quantity = ?, price = ? WHERE id = ?";
    private final String SQL_FIND_BY_ID = "SELECT * FROM market WHERE id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM market";
    private final String SQL_DELETE_BY_ID = "DELETE FROM market WHERE id = ?";

    public MarketRepository(String AvmDB) {
        this.AvmDB = AvmDB;
    }

    @Override
    public Collection<MarketProduct> findAll() {
        Collection<MarketProduct> products = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(AvmDB);
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                products.add(new MarketProduct(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("price")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public void save(MarketProduct product) {
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement psi = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psu = connection.prepareStatement(SQL_UPDATE)) {
            if (product.getId() == null) {
                // insert record
                psi.setString(1, product.getName());
                psi.setInt(2, product.getQuantity());
                psi.setFloat(3, product.getPrice());
                psi.executeUpdate();

                ResultSet rs = psi.getGeneratedKeys();
                if (rs.next()) {
                    product.setId(rs.getInt(1));
                }
            } else {
                // update record
                psu.setString(1, product.getName());
                psu.setInt(2, product.getQuantity());
                psu.setFloat(3, product.getPrice());
                psu.setInt(4, product.getId());
                psu.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MarketProduct findById(Integer id) {
        // return productMap.get(id)
        MarketProduct product = null;
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = new MarketProduct(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"));
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        // productMap.delete(id)
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void deleteAll() {
        try (Connection connection = DriverManager.getConnection(AvmDB);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(SQL_CREATE_TABLE);
            stmt.executeUpdate(SQL_DELETE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}