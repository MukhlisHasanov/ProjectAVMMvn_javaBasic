package avm.service;

import avm.products.CafeProduct;
import avm.products.Client;
import avm.repository.CafeRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/Cafe
 * @author Alexander Germanow
 * @version Apr-2024
 */
public class CafeService {
    private Client client;
    private CafeRepository cafeRepository;
    Map<Integer, CafeProduct> productList;

    public CafeService(Client client, CafeRepository cafeRepository) {
        this.client = client;
        this.cafeRepository = cafeRepository;
        productList = new HashMap<>();
    }

    public boolean addToOrder(int id, int quantity) {
        CafeProduct cafeProduct = cafeRepository.get(id);
        if (cafeProduct != null && cafeProduct.getQuantity() >= quantity) {
            if (productList.containsKey(id)) {
                CafeProduct existingProduct = productList.get(id);
                existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
            } else {
                CafeProduct newProduct = new CafeProduct(cafeProduct);
                newProduct.setQuantity(quantity);
                newProduct.setId(id);
                productList.put(id, newProduct);
            }
            cafeProduct.setQuantity(cafeProduct.getQuantity() - quantity);
            System.out.println("You added: " + quantity + " pcs of  " + cafeProduct.getName() + " to order");
            return true;
        }
        return false;
    }

    public boolean removeFromOrder(int id) {
        CafeProduct product = productList.get(id);
        CafeProduct cafeProduct = cafeRepository.get(id);
        if (productList.containsKey(id)) {
            int currentQuantity = product.getQuantity();
            productList.remove(id);
            cafeProduct.setQuantity(cafeProduct.getQuantity() + currentQuantity);
            System.out.println("You removed: " + currentQuantity + " pcs of " + product.getName() + " from order");
            return true;
        }
        return false;
    }

    public void removeFromOrder(int id, int quantityToRemove) {
        if (productList.containsKey(id)) {
            CafeProduct product = productList.get(id);
            CafeProduct cafeProduct = cafeRepository.get(id);
            int currentQuantity = product.getQuantity();

            int newQuantity = currentQuantity - quantityToRemove;
            if (newQuantity <= 0) {
                productList.remove(id);
            } else {
                product.setQuantity(newQuantity);
            }
            cafeProduct.setQuantity(cafeProduct.getQuantity() + quantityToRemove);
            System.out.println("You removed: " + currentQuantity + " pcs of " + product.getName() + " from order");
        }
    }

    public float sumOrder() {
        float sum = 0.0f;
        for (CafeProduct cafeProduct : productList.values()) {
            sum += cafeProduct.getPrice() * cafeProduct.getQuantity();
        }
        return sum;
    }

    public void productList() {
        System.out.println(cafeRepository);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nOrder of client: " + client + "\n");
        sb.append("Order list: \n");
        productList.forEach((Integer, cafeProduct) -> {
            sb.append(cafeProduct).append("\n");
        });
        return sb.toString();
    }
}