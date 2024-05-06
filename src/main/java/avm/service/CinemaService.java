package avm.service;

import avm.products.MovieProduct;
import avm.repository.MovieRepository;
import avm.products.Client;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Cinema
 * @author Rodion
 * @version Apr-2024
 */
public class CinemaService extends BaseService<MovieProduct, MovieRepository> {

    public CinemaService(Client client, MovieRepository movieRepository) {
        super(client, movieRepository);
    }

    @Override
    protected MovieProduct createProduct(MovieProduct product) {
        return new MovieProduct(product);
    }
}