package avm.service;

import avm.products.MarketProduct;
import avm.repository.MarketRepository;
import avm.products.Client;

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

    public void add(String name, int quantity, float price) {
        MarketProduct newProduct = new MarketProduct(null,name, quantity, price);
        repository.save(newProduct);
    }

    public boolean update(int id, String name, int quantity, float price) {
        MarketProduct updProduct = repository.findById(id);
        if (updProduct != null) {
            updProduct.update(name, quantity, price);
            repository.save(updProduct);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        MarketProduct delProduct = repository.findById(id);
        if (delProduct != null) {
            repository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    protected MarketProduct createProduct(MarketProduct product) {
        return new MarketProduct(product);
    }
}
