package avm.products;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/ClothShop
 * @author Valerian
 * @version Apr-2024
 */

// In this class we show the methods associated with the cloth products class
public class ClothProduct extends BaseProduct {
    private String size;
    private static int idCounter = 0;

    public ClothProduct(String name, String size, int quantity, float price) {
        super(name, quantity, price);
        this.size = size;
        this.id = ++idCounter;
    }

    public ClothProduct(ClothProduct clothProduct) {
        super(clothProduct.getName(), clothProduct.getSize(), clothProduct.getPrice(), clothProduct.getQuantity());
    }

    public ClothProduct(int id, String name, String size, int quantity, float price) {
        super(name, quantity, price);
    }
    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "[id: " + id +
                ", Product: " + name +
                ", Size: " + size +
                ", Price: " + price + " EUR, " +
                "Quan-ty: " + quantity + "]";
    }
}