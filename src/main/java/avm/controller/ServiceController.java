package avm.controller;

import avm.products.Personal;
import avm.products.PersonalState;
import avm.repository.CafeRepository;
import avm.repository.ClothRepository;
import avm.repository.MarketRepository;
import avm.repository.MovieRepository;

import java.util.Scanner;

public class ServiceController {
    private Scanner scanner;
    private Personal personal;
    private MarketRepository marketRepository;
    private CafeRepository cafeRepository;
    private ClothRepository clothRepository;
    private MovieRepository movieRepository;

    public ServiceController(Scanner scanner, Personal personal,
                             MarketRepository marketRepository,
                             CafeRepository cafeRepository,
                             ClothRepository clothRepository,
                             MovieRepository movieRepository) {
        this.scanner = scanner;
        this.personal = personal;
        this.marketRepository = marketRepository;
        this.cafeRepository = cafeRepository;
        this.clothRepository = clothRepository;
        this.movieRepository = movieRepository;
    }

    public void run() {
        PersonalState department = personal.getDepartment();
        MarketServiceMenu marketMenu = new MarketServiceMenu(scanner,marketRepository);
        CafeServiceMenu cafeMenu = new CafeServiceMenu(scanner, cafeRepository);
        ClothServiceMenu clothMenu = new ClothServiceMenu(scanner, clothRepository);
        MovieServiceMenu movieMenu = new MovieServiceMenu(scanner, movieRepository);

        switch (department) {
            case ADMIN:

                break;
            case MARKET:
                marketMenu.run();
                break;
            case CAFE:
                cafeMenu.run();
                break;
            case CLOTH:
                clothMenu.run();
                break;
            case MOVIE:
                movieMenu.run();
                break;
        }
    }
}
