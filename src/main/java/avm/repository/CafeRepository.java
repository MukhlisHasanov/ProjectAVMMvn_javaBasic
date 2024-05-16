package avm.repository;

import avm.products.CafeProduct;

import java.sql.*;
import java.util.*;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/Cafe
 * @author Alexander Germanow
 * @version Apr-2024
 */
public class CafeRepository implements ProductRepository<CafeProduct> {
    private String AvmDB;

    private final String SQL_INSERT = "INSERT INTO cafe (name, quantity, price) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE cafe SET name = ?, quantity = ?, price = ? WHERE id = ?";
    private final String SQL_FIND_BY_ID = "SELECT * FROM cafe WHERE id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM cafe";
    private final String SQL_DELETE_BY_ID = "DELETE FROM cafe WHERE id = ?";

    public CafeRepository(String AvmDB) {
        this.AvmDB = AvmDB;
    }

    @Override
    public Collection<CafeProduct> findAll() {
        Collection<CafeProduct> products = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(AvmDB);
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                products.add(new CafeProduct(
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
    public void save(CafeProduct cafeProduct) {
        try (Connection connection = DriverManager.getConnection(AvmDB)) {
            if (cafeProduct.getId() == null) {
                try (PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, cafeProduct.getName());
                    ps.setInt(2, cafeProduct.getQuantity());
                    ps.setFloat(3, cafeProduct.getPrice());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            Integer cafeProductId = rs.getInt(1);
                            System.out.println(cafeProductId);
                        }
                    }
                }
            } else {
                try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
                    ps.setString(1, cafeProduct.getName());
                    ps.setInt(2, cafeProduct.getQuantity());
                    ps.setFloat(3, cafeProduct.getPrice());
                    ps.setInt(4, cafeProduct.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CafeProduct findById(Integer id) {
        CafeProduct cafeProduct = null;
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement psm = connection.prepareStatement(SQL_FIND_BY_ID)) {
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            if (rs.next()) {
                cafeProduct = new CafeProduct(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"));
            }
            return cafeProduct;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void initCafe() {
        List<CafeProduct> cafeProducts = new ArrayList<>(List.of(
                new CafeProduct("Chocolate", 50, 1.5f),
                new CafeProduct("Strawberry with chocolate", 50, 1.3f),
                new CafeProduct("Latte macchiate", 50, 2.5f),
                new CafeProduct("Vanil", 100, 1.5f),
                new CafeProduct("Cherry", 100, 2.2f),
                new CafeProduct("Caramel", 100, 2.2f),
                new CafeProduct("Black tea", 120, 2.5f),
                new CafeProduct("Green tea", 120, 2.5f),
                new CafeProduct("Coffee", 200, 2.5f),
                new CafeProduct("Espresso", 50, 2.2f)
        ));
        cafeProducts.forEach(cafeProduct -> save(cafeProduct));
    }
}
