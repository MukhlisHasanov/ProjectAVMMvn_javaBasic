package avm.products;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/Cafe
 * @author Alexander Germanow
 * @version May-2024
 */

// In this class we show the methods associated with the cafe class
public class CafeProduct extends BaseProduct {
//    private static int idCounter = 0;

    public CafeProduct(String name, int quantity, float price) {
        super(name, quantity, price);
//        this.id = ++idCounter;
    }

    public CafeProduct(CafeProduct product) {
        super(product.getName(), product.getQuantity(), product.getPrice());
    }

    public CafeProduct(int id, String name, int quantity, float price) {
        super(name, quantity, price);
        this.id = id;
    }

    @Override
    public String toString() {
        return "[id: " + id +
                ", Product: " + name +
                ", Quan-ty: " + quantity +
                ", Price: " + price + " EUR" + "]";
    }
}