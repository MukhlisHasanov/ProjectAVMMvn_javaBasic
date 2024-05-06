package avm.products;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/General
 * @author Andrey Hein
 * @version Apr-2024
 */

// This is base class from future product classes
public abstract class BaseProduct {
    protected int id;
    protected String name;
    protected float price;
    protected int quantity;

    public BaseProduct(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "[id: " + id +
                ", Product: " + name +
                ", Price: " + price + " EUR]";
    }
}