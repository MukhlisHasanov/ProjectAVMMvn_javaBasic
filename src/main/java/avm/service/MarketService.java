package avm.service;

import avm.products.MarketProduct;
import avm.repository.MarketRepository;
import avm.products.Client;

import java.sql.SQLException;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Hypermarket
 * @author Mukhlis
 * @version May-2024
 */
public class MarketService extends BaseService<MarketProduct, MarketRepository> {

    public MarketService(Client client, MarketRepository marketRepository) throws SQLException {
        super(client, marketRepository);
    }

    @Override
    protected MarketProduct createProduct(MarketProduct product) {
        return new MarketProduct(product);
    }
}
