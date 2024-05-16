package avm.service;

import avm.products.MarketProduct;
import avm.repository.MarketRepository;
import avm.products.Client;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Hypermarket
 * @author Mukhlis
 * @version Apr-2024
 */
public class MarketService extends BaseService<MarketProduct, MarketRepository> {

    public MarketService(Client client, MarketRepository marketRepository) throws SQLException {
        super(client, marketRepository);
    }
    public void minusProductQuantity(int id, int quantity) {
        try (PreparedStatement psu = connection.prepareStatement("UPDATE market SET quantity = quantity - ? WHERE id = ?")) {
            psu.setInt(1, quantity);
            psu.setInt(2, id);
            psu.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void plusProductQuantity(int id, int quantity) {
        try (PreparedStatement psu = connection.prepareStatement("UPDATE market SET quantity = quantity + ? WHERE id = ?")) {
            psu.setInt(1, quantity);
            psu.setInt(2, id);
            psu.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected MarketProduct createProduct(MarketProduct product) {
        return new MarketProduct(product);
    }
}