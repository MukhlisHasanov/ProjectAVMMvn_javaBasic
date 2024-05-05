package avm.products;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Cinema
 * @author Rodion
 * @version Apr-2024
 */

// In this class we show the methods associated with the movie class
public class MovieProduct extends BaseProduct{
    private int quantity;
    private String genre;
    private static int idCounter = 0;

    public MovieProduct(String name, String genre, float price, int quantity) {
        super(name, price);
        this.genre = genre;
        this.quantity = quantity;
        this.id = ++idCounter;
    }

    public MovieProduct(MovieProduct product) {
        super(product.getName(), product.getPrice());
        this.quantity = product.getQuantity();
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
                ", Genre: " + genre +
                ", Movie: " + name +
                ", Price: " + price + " EUR, " +
                "Quan-ty: " + quantity + "]";
    }
}
