package avm.service;

import avm.products.Client;
import avm.products.ClothProduct;
import avm.repository.ClothRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/ClothShop
 * @author Valerian
 * @version Apr-2024
 */
public class ClothService {
    private Client client;
    private ClothRepository clothRepository;
    Map<Integer, ClothProduct> productList;

    public ClothService(Client client, ClothRepository clothRepository) {
        this.client = client;
        this.clothRepository = clothRepository;
        productList = new HashMap<>();
    }

    public boolean addToOrder(int id, int quantity, int size) {
        ClothProduct clothProduct = clothRepository.get(id);
        if (clothProduct != null && clothProduct.getQuantity() >= quantity) {
            if (productList.containsKey(id)) {
                ClothProduct existingProduct = productList.get(id);
                existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
            } else {
                ClothProduct newProduct = new ClothProduct(clothProduct);
                newProduct.setQuantity(quantity);
                newProduct.setId(id);
                newProduct.setSize(size);
                productList.put(id, newProduct);
            }
            clothProduct.setQuantity(clothProduct.getQuantity() - quantity);
            System.out.println("You added: " + quantity + "pcs of " + clothProduct.getName() + " to shopping cart");
            return true;
        }
        return false;
    }

    public boolean removeFromOrder(int id) {
        ClothProduct product = productList.get(id);
        ClothProduct clothProduct = clothRepository.get(id);
        if (productList.containsKey(id)) {
            int currentQuantity = product.getQuantity();
            productList.remove(id);
            clothProduct.setQuantity(clothProduct.getQuantity() + currentQuantity);
            System.out.println("You removed: " + currentQuantity + " pcs of " + product.getName() + " from shopping cart");
            return true;
        }
    return false;
}

    public void removeFromOrder(int id, int quantityToRemove) {
        if (productList.containsKey(id)) {
            ClothProduct product = productList.get(id);
            ClothProduct clothProduct = clothRepository.get(id);
            int currentQuantity = product.getQuantity();
            int newQuantity = currentQuantity - quantityToRemove;
            if (newQuantity <= 0) {
                productList.remove(id);
            } else {
                product.setQuantity(newQuantity);
            }
            clothProduct.setQuantity(clothProduct.getQuantity() + quantityToRemove);
            System.out.println("You removed: " + currentQuantity + " pcs of " + product.getName() + " from shopping cart");
        }
    }

    public float sumOrder() {
        float sum = 0.0f;
        for (ClothProduct clothProduct : productList.values()) {
            sum += clothProduct.getPrice() * clothProduct.getQuantity();
        }
        return sum;
    }

    public void productList() {
        System.out.println(clothRepository);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCart of shop client: " + client + "\n");
        sb.append("Shopping cart: \n");
        productList.forEach((Integer, clothProduct) -> {
            sb.append(clothProduct).append("\n");
        });
        return sb.toString();
    }
}