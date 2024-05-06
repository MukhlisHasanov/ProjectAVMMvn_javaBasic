package avm.repository;

import avm.products.CafeProduct;
import java.util.*;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/Cafe
 * @author Alexander Germanow
 * @version Apr-2024
 */
public class CafeRepository implements ProductRepository<CafeProduct>{
    private Map<Integer, CafeProduct> cafeMap;

    public CafeRepository() {
        cafeMap = new HashMap<>();
    }

    @Override
    public void put(CafeProduct cafeProduct) {
        cafeMap.put(cafeProduct.getId(), cafeProduct);
    }

    @Override
    public CafeProduct get(int id) {
        return cafeMap.get(id);
    }

    @Override
    public void remove(int id) {
        cafeMap.remove(id);
    }

    public void initCafe() {
        List<CafeProduct> cafeProducts = new ArrayList<>(List.of(
                new CafeProduct("Chocolate", 1.5f, 50),
                new CafeProduct("Strawberry with chocolate", 1.3f, 50),
                new CafeProduct("Latte macchiate", 2.5f, 50),
                new CafeProduct("Vanil", 1.5f, 100),
                new CafeProduct("Cherry", 2.2f, 100),
                new CafeProduct("Caramel", 2.2f, 100),
                new CafeProduct("Black tea",2.5f, 120),
                new CafeProduct("Green tea", 2.5f, 120),
                new CafeProduct("Coffee", 2.5f, 200),
                new CafeProduct("Espresso", 2.2f, 50)
        ));
        cafeProducts.forEach(this::put);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCafe menu:\n");
        cafeMap.forEach((Integer, cafeProduct) -> {
            sb.append(cafeProduct).append("\n");
        });
        return sb.toString();
    }
}
