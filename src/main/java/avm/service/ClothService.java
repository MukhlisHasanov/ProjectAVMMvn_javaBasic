package avm.service;

import avm.repository.ClothRepository;
import avm.products.ClothProduct;
import avm.products.Client;

import java.sql.PreparedStatement;
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