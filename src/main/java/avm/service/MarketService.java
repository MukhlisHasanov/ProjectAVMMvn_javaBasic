package avm.service;

import avm.products.MarketProduct;
import avm.repository.MarketRepository;
import avm.products.Client;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Hypermarket
 * @author Mukhlis
 * @version Apr-2024
 */
public class MarketService extends BaseService<MarketProduct, MarketRepository> {

    public MarketService(Client client, MarketRepository marketRepository) {
        super(client, marketRepository);
    }

    @Override
    protected MarketProduct createProduct(MarketProduct product) {
        return new MarketProduct(product);
    }
}
