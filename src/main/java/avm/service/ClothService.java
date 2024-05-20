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

    public ClothService(Client client, ClothRepository repository, String size) throws SQLException {
        super(client, repository);
        this.size = size;
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


    @Override
    protected ClothProduct createProduct(ClothProduct product) {
        return new ClothProduct(product);
    }
}