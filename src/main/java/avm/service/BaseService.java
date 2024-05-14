package avm.service;

import avm.products.BaseProduct;
import avm.products.Client;
import avm.repository.ProductRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseService <T extends BaseProduct, R extends ProductRepository<T>> {
    public String AvmDB = "jdbc:sqlite:C:/temp/AvmDB.db";
    Connection connection = DriverManager.getConnection(AvmDB);
    protected R repository;
    protected Map<Integer, T> productList;
    private Client client;


    public BaseService(Client client, R repository) throws SQLException {
        this.repository = repository;
        this.client = client;
        productList = new HashMap<>();
    }

    public String addToOrder(int id, int quantity) {
        T product = repository.findById(id);
        if (product != null) {
            if (product.getQuantity() >= quantity) {
                if (productList.containsKey(id)) {
                    T existingProduct = productList.get(id);
                    existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
                } else {
                    T newProduct = createProduct(product);
                    newProduct.setQuantity(quantity);
                    newProduct.setId(id);
                    productList.put(id, newProduct);
                }
                product.setQuantity(product.getQuantity() - quantity);

                try (PreparedStatement psu = connection.prepareStatement("UPDATE market SET quantity = ? WHERE id = ?")) {
                    psu.setInt(1, product.getQuantity());
                    psu.setInt(2, id);
                    psu.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return ("You added: " + quantity + " pcs of " + product.getName() + " to shopping cart");
            }
            return ("Not enough pcs, available only " + product.getQuantity() + " pcs");
        }
        return ("Incorrect ID entry");
    }

    public String removeFromOrder(int id) {
        T product = productList.get(id);
        if (productList.containsKey(id)) {
            int currentQuantity = product.getQuantity();
            productList.remove(id);
            T repositoryProduct = repository.findById(id);
            repositoryProduct.setQuantity(repositoryProduct.getQuantity() + currentQuantity);

            try (PreparedStatement psu = connection.prepareStatement("UPDATE market SET quantity = quantity + ? WHERE id = ?")) {
                psu.setInt(1, product.getQuantity());
                psu.setInt(2, id);
                psu.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return ("You removed: " + currentQuantity + " pcs of " + product.getName() + " from shopping cart");
        }
        return ("Incorrect ID entry");
    }

    public String removeFromOrder(int id, int quantityToRemove) {
        if (productList.containsKey(id)) {
            T product = productList.get(id);
            T repositoryProduct = repository.findById(id);
            int currentQuantity = product.getQuantity();
            int newQuantity = currentQuantity - quantityToRemove;
            if (newQuantity <= 0) {
                productList.remove(id);
            } else {
                product.setQuantity(newQuantity);
            }

            try (PreparedStatement psu = connection.prepareStatement("UPDATE market SET quantity = quantity + ? WHERE id = ?")) {
                psu.setInt(1, quantityToRemove);
                psu.setInt(2, id);
                psu.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            repositoryProduct.setQuantity(repositoryProduct.getQuantity() + quantityToRemove);

            return ("You removed: " + quantityToRemove + " pcs of " + product.getName() + " from shopping cart");
        }
        return ("Incorrect ID entry");
    }

    public float sumOrder() {
        float sum = 0.0f;
        for (T product : productList.values()) {
            sum += product.getPrice() * product.getQuantity();
        }
        return sum;
    }

    public float payTheBill() {
        client.setWallet(client.getWallet() - sumOrder());
        productList.clear();
        return client.getWallet();
    }

    public void productList() {
        repository.findAll().forEach(System.out::println);
    }

    protected abstract T createProduct(T product);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCart of client: " + client + "\n");
        sb.append("\nShopping cart: \n");
        productList.forEach((id, marketProduct) -> sb.append(marketProduct).append("\n"));
        return sb.toString();
    }
}