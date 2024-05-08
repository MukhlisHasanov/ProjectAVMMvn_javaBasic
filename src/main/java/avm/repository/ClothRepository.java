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
    private final String SQL_INSERT = "INSERT INTO market (name, quantity, price) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE market SET name = ?, quantity = ?, price = ? WHERE id = ?";
    private final String SQL_FIND_BY_ID = "SELECT * FROM market WHERE id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM market";
    private final String SQL_DELETE_BY_ID = "DELETE FROM market WHERE id = ?";

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
        try (Connection connection = DriverManager.getConnection(AvmDB)) {
            if (clothProduct.getId() == null) {
                try (PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, clothProduct.getName());
                    ps.setString(2, clothProduct.getSize());
                    ps.setInt(3, clothProduct.getQuantity());
                    ps.setFloat(4, clothProduct.getPrice());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            Integer clothProductId = rs.getInt(1);
                            System.out.println(clothProductId);
                        }
                    }
                }
            } else {
                try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
                    ps.setInt(1, clothProduct.getId());
                    ps.setString(2, clothProduct.getName());
                    ps.setString(3, clothProduct.getSize());
                    ps.setInt(4, clothProduct.getQuantity());
                    ps.setFloat(5, clothProduct.getPrice());

                    ps.executeUpdate();
                }
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
    public void remove(int id) {
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