package avm.service;

import avm.repository.ClothRepository;
import avm.products.ClothProduct;
import avm.products.Client;

import java.sql.SQLException;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/ClothShop
 * @author Valerian
 * @version May-2024
 */
public class ClothService extends BaseService<ClothProduct, ClothRepository> {

    public ClothService(Client client, ClothRepository clothRepository) throws SQLException {
        super(client, clothRepository);
    }

    @Override
    protected ClothProduct createProduct(ClothProduct product) {
        return new ClothProduct(product);
    }

    @Override
    protected void minusProductQuantity(int id, int quantity) {

    }

    @Override
    protected void plusProductQuantity(int id, int quantity) {

    }
}