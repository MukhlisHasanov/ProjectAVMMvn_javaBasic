package avm.repository;

import avm.products.MarketProduct;

import java.sql.*;
import java.util.*;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Hypermarket
 * @author Mukhlis
 * @version Apr-2024
 */
public class MarketRepository implements ProductRepository<MarketProduct> {
//    private Map<Integer, MarketProduct> marketMap;

    private String AvmDB;
    private final String SQL_INSERT = "INSERT INTO market (name, quantity, price) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE market SET name = ?, quantity = ?, price = ? WHERE id = ?";
    private final String SQL_FIND_BY_ID = "SELECT * FROM market WHERE id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM market";
    private final String SQL_DELETE_BY_ID = "DELETE FROM market WHERE id = ?";

    public MarketRepository(String AvmDB) {
        this.AvmDB = AvmDB;
    }

//    public MarketRepository() {
////        marketMap = new HashMap<>();
//    }

    @Override
    public Collection<MarketProduct> findAll() {
        Collection<MarketProduct> products = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(AvmDB);
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                products.add(new MarketProduct(
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("quantity")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public void save(MarketProduct product) {
        try (Connection connection = DriverManager.getConnection(AvmDB)) {
            if (product.getId() == null) {
                try (PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, product.getName());
                    ps.setInt(2, product.getQuantity());
                    ps.setFloat(3, product.getPrice());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            Integer marketProductId = rs.getInt(1);
                            System.out.println(marketProductId);
                        }
                    }
                }
            } else {
                try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
                    ps.setString(1, product.getName());
                    ps.setInt(2, product.getQuantity());
                    ps.setFloat(3, product.getPrice());
                    ps.setInt(4, product.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public MarketProduct findById(int id) {
//        return null;
//    }

    @Override
    public MarketProduct findById(Integer id) {
        MarketProduct marketProduct = null;
        try (Connection connection = DriverManager.getConnection(AvmDB);
             PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                marketProduct = new MarketProduct(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"));
            }
            return marketProduct;
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

    public void initMarket() {
        List<MarketProduct> marketProducts = new ArrayList<>(List.of(
                new MarketProduct("Bublik", 1.98f, 100),
                new MarketProduct("Beef", 6.50f, 15),
                new MarketProduct("Coca-Cola", 0.5f, 120),
                new MarketProduct("Salmon", 15.5f, 30),
                new MarketProduct("Chicken", 8.5f, 40),
                new MarketProduct("Turkey", 9, 18),
                new MarketProduct("Milk", 1, 100),
                new MarketProduct("Yoghurt", 1.2f, 30),
                new MarketProduct("Butter", 2.2f, 15),
                new MarketProduct("Cheese", 7.5f, 22),
                new MarketProduct("Salt", 0.5f, 25),
                new MarketProduct("Sugar", 1.25f, 250),
                new MarketProduct("Flour", 0.60f, 200),
                new MarketProduct("Olive", 12, 30),
                new MarketProduct("Soap", 1.3f, 70),
                new MarketProduct("Shampoo", 2.4f, 30)
        ));
        marketProducts.forEach(marketProduct -> save(marketProduct));
    }
}

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("\nHypermarket product list:\n");
//        marketMap.forEach((Integer, marketProduct) -> {
//            sb.append(marketProduct).append("\n");
//        });
//        return sb.toString();
//    }
