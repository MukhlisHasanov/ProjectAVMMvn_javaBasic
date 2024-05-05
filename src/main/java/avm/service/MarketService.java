package avm.service;

import avm.products.Client;
import avm.products.MarketProduct;
import avm.repository.MarketRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Hypermarket
 * @author Mukhlis
 * @version Apr-2024
 */
public class MarketService {
    private Client client;
    private MarketRepository marketRepository;
    Map<Integer, MarketProduct> productList;

    public MarketService(Client client, MarketRepository marketRepository) {
        this.client = client;
        this.marketRepository = marketRepository;
        productList = new HashMap<>();
    }

    public boolean addToOrder(int id, int quantity) {
        MarketProduct marketProduct = marketRepository.get(id);
        if (marketProduct != null && marketProduct.getQuantity() >= quantity) {
            if (productList.containsKey(id)) {
                MarketProduct existingProduct = productList.get(id);
                existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
            } else {
                MarketProduct newProduct = new MarketProduct(marketProduct);
                newProduct.setQuantity(quantity);
                newProduct.setId(id);
                productList.put(id, newProduct);
            }
            marketProduct.setQuantity(marketProduct.getQuantity() - quantity);
            System.out.println("You added: " + quantity + " pcs of " + marketProduct.getName() + " to shopping cart");
            return true;
        }
        return false;
        }

    public boolean removeFromOrder(int id) {
        MarketProduct product = productList.get(id);
        MarketProduct marketProduct = marketRepository.get(id);
        if (productList.containsKey(id)) {
            int currentQuantity = product.getQuantity();
            productList.remove(id);
            marketProduct.setQuantity(marketProduct.getQuantity() + currentQuantity);
            System.out.println("You removed: " + currentQuantity + " pcs of " + product.getName() + " from shopping cart");
            return true;
        }
        return false;
    }

    public void removeFromOrder(int id, int quantityToRemove) {
        if (productList.containsKey(id)) {
            MarketProduct product = productList.get(id);
            MarketProduct marketProduct = marketRepository.get(id);
            int currentQuantity = product.getQuantity();

            int newQuantity = currentQuantity - quantityToRemove;
            if (newQuantity <= 0) {
                productList.remove(id);
            } else {
                product.setQuantity(newQuantity);
            }
            marketProduct.setQuantity(marketProduct.getQuantity() + quantityToRemove);
            System.out.println("You removed: " + currentQuantity + " pcs of " + product.getName() + " from shopping cart");
        }
    }

    public float sumOrder() {
        float sum = 0.0f;
        for (MarketProduct marketProduct : productList.values()) {
            sum += marketProduct.getPrice() * marketProduct.getQuantity();
        }
        return sum;
    }

    public void productList() {
        System.out.println(marketRepository);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCart of client: " + client + "\n");
        sb.append("Shopping cart: \n");
        productList.forEach((Integer, marketProduct) -> {
            sb.append(marketProduct).append("\n");
        });
        return sb.toString();
    }
}
