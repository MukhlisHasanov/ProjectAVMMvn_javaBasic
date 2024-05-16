package avm.repository;

import avm.products.ClothProduct;

import java.sql.*;
import java.util.*;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/ClothShop
 * @author Valerian
 * @version Apr-2024
 */
public class ClothRepository implements ProductRepository<ClothProduct> {
//    private Map<Integer, ClothProduct> clothMap;

    private String AvmDB;
    private final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS cloth (" +
            " id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " name        TEXT NOT NULL," +
            " size        TEXT NOT NULL," +
            " quantity    INTEGER NOT NULL," +
            " price       FLOAT NOT NULL)";
    private final String SQL_DELETE_TABLE = "DELETE FROM cloth";
    private final String SQL_INSERT = "INSERT INTO cloth (name, size, quantity, price) VALUES (?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE cloth SET name = ?, size = ?, quantity = ?, price = ? WHERE id = ?";
    private final String SQL_FIND_BY_ID = "SELECT * FROM cloth WHERE id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM cloth";
    private final String SQL_DELETE_BY_ID = "DELETE FROM cloth WHERE id = ?";

//    public ClothRepository() {
////        clothMap = new HashMap<>();
//    }

    public ClothRepository(String AvmDB) {
        this.AvmDB = AvmDB;
    }

    @Override
    public Collection<ClothProduct> findAll() {
        Collection<ClothProduct> clothes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(AvmDB);
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                clothes.add(new ClothProduct(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("size"),
                        rs.getInt("quantity"),
                        rs.getFloat("price")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clothes;
    }


    @Override
    public void save(ClothProduct clothProduct) {
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement psi = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psu = connection.prepareStatement(SQL_UPDATE)) {
            if (clothProduct.getId() == null) {
                psi.setString(1, clothProduct.getName());
                psi.setString(2, clothProduct.getSize());
                psi.setInt(3, clothProduct.getQuantity());
                psi.setFloat(4, clothProduct.getPrice());
                psi.executeUpdate();

                ResultSet rs = psi.getGeneratedKeys();
                if (rs.next()) {
                    clothProduct.setId(rs.getInt(1));
                }
            } else {
                psu.setString(1, clothProduct.getName());
                psu.setString(2, clothProduct.getSize());
                psu.setInt(3, clothProduct.getQuantity());
                psu.setFloat(4, clothProduct.getPrice());
                psu.setInt(5, clothProduct.getId());
                psu.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ClothProduct findById(Integer id) {
        ClothProduct clothProduct = null;
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement psm = connection.prepareStatement(SQL_FIND_BY_ID)) {
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            if (rs.next()) {
                clothProduct = new ClothProduct(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("size"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"));
            }
            return clothProduct;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initCloth() {
        List<ClothProduct> clothProducts = new ArrayList<>(List.of(
                new ClothProduct("Jeans", "S", 50, 150),
                new ClothProduct("Esprit", "M", 40, 500),
                new ClothProduct("Jeans", "L", 30, 90),
                new ClothProduct("Boss", "XL", 50, 150),
                new ClothProduct("Hilfiger", "XXL", 20, 9)
        ));
        clothProducts.forEach(clothProduct -> save(clothProduct));
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

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("\nCloth Repository:\n");
//        clothMap.forEach((id, clothProduct) -> {
//            sb.append(clothProduct).append("\n");
//        });
//        return sb.toString();
//    }
//}