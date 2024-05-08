package avm.repository;

import avm.products.ClothProduct;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/ClothShop
 * @author Valerian
 * @version Mai-2024
 */
public  class ClothRepository implements ProductRepository<ClothProduct>{
    //private Map<Integer, ClothProduct> clothMap;
    private String avmDB;
    private final String SQL_INSERT = "INSERT INTO cloth (name, size, price, quantity) VALUES (?,?,?,?)";
    private final String SQL_FIND_BY_ID = "SELECT * FROM cloth WHERE id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM cloth";
    private final String SQL_DELETE_BY_ID = "DELETE FROM cloth WHERE id = ?";

    public ClothRepository(String avmDB) {
      //  clothMap = new HashMap<>();
        this.avmDB = avmDB;
    }

    @Override
    public void put(ClothProduct product) {

    }

    @Override
    public ClothProduct get(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void save(ClothProduct clothProduct) {
        if (clothProduct.getId() == null) {
            try (Connection connection = DriverManager.getConnection(avmDB);
                 PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, clothProduct.getName());
                ps.setString(2, clothProduct.getSize());
                ps.setFloat(3, clothProduct.getPrice());
                ps.setInt(4,clothProduct.getQuantity());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                Integer clothProductId = null;
                if (rs.next()) {
                    clothProductId = rs.getInt(1);
                }
                System.out.println(clothProductId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // TODO
        }
    }
    @Override
    public ClothProduct findById(Integer id) {
        ClothProduct clothProduct = null;
        try (Connection connection = DriverManager.getConnection(avmDB);
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                clothProduct = new ClothProduct(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("size"),
                        rs.getInt("price"),
                        rs.getInt("quantity"));
            }
            return clothProduct;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void remove(Integer id) {
        try (Connection connection = DriverManager.getConnection(avmDB);
             PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Collection<ClothProduct> findAll() {
        Collection<ClothProduct> clothProducts = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(avmDB);
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                clothProducts.add(new ClothProduct(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("size"),
                        rs.getInt("price"),
                        rs.getInt("quantity")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clothProducts;
    }





//    @Override
//    public void put(ClothProduct clothProduct) {
//        clothMap.put(clothProduct.getId(), clothProduct);
//    }
//
//    @Override
//    public ClothProduct get(int id) {
//        return clothMap.get(id);
//    }
//
//    @Override
//    public void remove(int id) {
//        clothMap.remove(id);
//    }

    public void initCloth() {
        List<ClothProduct> clothProducts = new ArrayList<>(List.of(
                new ClothProduct("Jeans", "M", 150, 100),
                new ClothProduct("Esprit","M", 500, 200),
                new ClothProduct("Jeans","M", 90,  300),
                new ClothProduct("Boss","M", 150, 400),
                new ClothProduct("Hilfiger","M", 9, 500)
        ));
        clothProducts.forEach(clothProduct -> save(clothProduct));
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
}