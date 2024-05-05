package avm.repository;

import avm.products.MarketProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Hypermarket
 * @author Mukhlis
 * @version Apr-2024
 */
public class MarketRepository {
    private Map<Integer, MarketProduct> marketMap;

    public MarketRepository() {
        marketMap = new HashMap<>();
    }

    public void put(MarketProduct marketProduct) {
        marketMap.put(marketProduct.getId(), marketProduct);
    }

    public MarketProduct get(int id) {
        return marketMap.get(id);
    }

    public void remove(int id) {
        marketMap.remove(id);
    }

    public void initMarket() {
        List<MarketProduct> marketProducts = new ArrayList<>(List.of(
                new MarketProduct("Bublik", 1.98f, 100),
                new MarketProduct("Beef", 6.50f, 15),
                new MarketProduct("Coca-Cola", 0.5f, 120),
                new MarketProduct("Salmon", 15.5f, 30),
                new MarketProduct("Chicken", 8.5f, 40),
                new MarketProduct("Turkey", 9, 18),
                new MarketProduct("Milk", 1, 100),
                new MarketProduct("Yoghurt", 1.2f, 30),
                new MarketProduct("Butter", 2.2f, 15),
                new MarketProduct("Cheese", 7.5f, 22),
                new MarketProduct("Salt", 0.5f, 25),
                new MarketProduct("Sugar", 1.25f, 250),
                new MarketProduct("Flour", 0.60f, 200),
                new MarketProduct("Olive", 12, 30),
                new MarketProduct("Soap", 1.3f, 70),
                new MarketProduct("Shampoo", 2.4f, 30)
        ));
        marketProducts.forEach(marketProduct -> put(marketProduct));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nHypernarket product list:\n");
        marketMap.forEach((Integer, marketProduct) -> {
            sb.append(marketProduct).append("\n");
        });
        return sb.toString();
    }
}