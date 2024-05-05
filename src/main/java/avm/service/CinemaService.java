package avm.service;

import avm.products.Client;
import avm.products.MovieProduct;
import avm.repository.MovieRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Cinema
 * @author Rodion
 * @version Apr-2024
 */
public class CinemaService {
    private Client client;
    private MovieRepository movieRepository;
    Map<Integer, MovieProduct> productList;

    public CinemaService(Client client, MovieRepository movieRepository) {
        this.client = client;
        this.movieRepository = movieRepository;
        productList = new HashMap<>();
    }

    public boolean addToOrder(int id, int quantity) {
        MovieProduct movieProduct = movieRepository.get(id);
        if (movieProduct != null && movieProduct.getQuantity() >= quantity) {
            if (productList.containsKey(id)) {
                MovieProduct existingProduct = productList.get(id);
                existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
            } else {
                MovieProduct newProduct = new MovieProduct(movieProduct);
                newProduct.setQuantity(quantity);
                newProduct.setId(id);
                productList.put(id, newProduct);
            }
            movieProduct.setQuantity(movieProduct.getQuantity() - quantity);
            System.out.println("You added: " + quantity + " ticket(s) to movie: " + movieProduct.getName() + ". With price: " + movieProduct.getPrice());
            return true;
        }
        return false;
    }

    public boolean removeFromOrder (int id) {
        MovieProduct product = productList.get(id);
        MovieProduct movieProduct = movieRepository.get(id);
        if (productList.containsKey(id)) {
            int currentQuantity = product.getQuantity();
            productList.remove(id);
            movieProduct.setQuantity(movieProduct.getQuantity() + currentQuantity);
            System.out.println("You removed " + currentQuantity + " ticket(s) on Movie: " + product.getName());
            return true;
        }
        return false;
    }

    public void removeFromOrder(int id, int quantityToRemove) {
        if (productList.containsKey(id)) {
            MovieProduct product = productList.get(id);
            MovieProduct movieProduct = movieRepository.get(id);
            int currentQuantity = product.getQuantity();

            int newQuantity = currentQuantity - quantityToRemove;
            if (newQuantity <= 0) {
                productList.remove(id);
            } else {
                product.setQuantity(newQuantity);;
            }
            movieProduct.setQuantity(movieProduct.getQuantity() + quantityToRemove);
            System.out.println("You removed " + quantityToRemove + " ticket(s) on Movie: " + product.getName());
        }
    }

    public float sumOrder() {
        float sum = 0.0f;
        for (MovieProduct movieProduct : productList.values()) {
            sum += movieProduct.getPrice() * movieProduct.getQuantity();
        }
        return sum;
    }

    public void productList() {
        System.out.println(movieRepository);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCustomer cinema cart: " + client + "\n");
        sb.append("Shopping cart: \n");
        productList.forEach((Integer, movieProduct) -> {
            sb.append(movieProduct).append("\n");
        });
        return sb.toString();
    }
}