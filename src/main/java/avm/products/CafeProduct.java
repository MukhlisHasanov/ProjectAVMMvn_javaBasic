package avm.products;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/Cafe
 * @author Alexander Germanow
 * @version Apr-2024
 */

// In this class we show the methods associated with the cafe class
public class CafeProduct extends BaseProduct {

    public CafeProduct(String name, int quantity, float price) {
        super(name, quantity, price);
    }

    public CafeProduct(CafeProduct product) {
        super(product.getName(), product.getQuantity(), product.getPrice());
    }

    public CafeProduct(Integer id, String name, int quantity, float price) {
        super(name, quantity, price);
        this.id = id;
    }

    @Override
    public String toString() {
        return "[id: " + id +
                ", Product: " + name +
                ", Quan-ty: " + quantity +
                ", Price: " + price + " EUR, " + "]";
    }
}