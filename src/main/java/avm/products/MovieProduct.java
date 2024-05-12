package avm.products;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Cinema
 * @author Rodion
 * @version Apr-2024
 */

// In this class we show the methods associated with the movie class
public class MovieProduct extends BaseProduct{
    private String genre;

    public MovieProduct(String name, String genre, float price, int quantity) {
        super(name, quantity, price);
        this.genre = genre;
    }

    public MovieProduct(MovieProduct movieProduct) {
        super(movieProduct.getName(), movieProduct.getQuantity(), movieProduct.getPrice());
        this.genre = movieProduct.getGenre();
    }

    public MovieProduct(Integer id, String name, String genre, float price, int quantity) {
        super(name, quantity, price);
        this.id = id;
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
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
