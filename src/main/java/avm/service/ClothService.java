package avm.service;

import avm.repository.ClothRepository;
import avm.products.ClothProduct;
import avm.products.Client;

import java.sql.SQLException;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/ClothShop
 * @author Valerian
 * @version Apr-2024
 */
public class ClothService extends BaseService<ClothProduct, ClothRepository> {

    public ClothService(Client client, ClothRepository clothRepository) throws SQLException {
        super(client, clothRepository);
    }

    @Override
    protected ClothProduct createProduct(ClothProduct product) {
        return new ClothProduct(product);
    }
}