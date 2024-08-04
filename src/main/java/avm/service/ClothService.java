package avm.service;

import avm.repository.ClothRepository;
import avm.products.ClothProduct;
import avm.products.Client;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/ClothShop
 * @author Valerian
 * @version Apr-2024
 */
public class ClothService extends BaseService<ClothProduct, ClothRepository> {


    String size;

    public ClothService(Client client, ClothRepository repository) throws SQLException {
        super(client, repository);
    }

    public void minusProductQuantity(int id, int quantity) {
        try (PreparedStatement psu = connection.prepareStatement("UPDATE cloth SET quantity = quantity - ? WHERE id = ?")) {
            psu.setInt(1, quantity);
            psu.setInt(2, id);
            psu.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void plusProductQuantity(int id, int quantity) {
        try (PreparedStatement psu = connection.prepareStatement("UPDATE cloth SET quantity = quantity + ? WHERE id = ?")) {
            psu.setInt(1, quantity);
            psu.setInt(2, id);
            psu.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String addToOrder(int id, int quantity, String size) {
        ClothProduct product = repository.findById(id);
        if (product != null) {
            if (product.getQuantity() >= quantity) {
                if (productList.containsKey(id)) {
                    ClothProduct existingProduct = productList.get(id);
                    existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
                } else {
                    ClothProduct newProduct = createProduct(product);
                    newProduct.setQuantity(quantity);
                    newProduct.setId(id);
                    newProduct.getSize();
                    productList.put(id, newProduct);
                }
                product.setQuantity(product.getQuantity() - quantity);
                minusProductQuantity(id, quantity);
                return ("You added: " + quantity + " pcs of " + product.getName() + " to shopping cart");
            }
            return ("Not enough pcs, available only " + product.getQuantity() + " pcs");
        }
        return ("Incorrect ID entry");
    }


    @Override
    protected ClothProduct createProduct(ClothProduct product) {
        return new ClothProduct(product);
    }
}