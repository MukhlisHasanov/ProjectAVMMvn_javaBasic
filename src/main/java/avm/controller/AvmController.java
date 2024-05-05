package avm.controller;

import avm.service.CafeService;
import avm.service.CinemaService;
import avm.service.ClothService;
import avm.service.MarketService;

import java.util.Scanner;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Hypermarket
 * @author Mukhlis
 * @version Apr-2024
 */
public class AvmController {
    private final MarketService marketService;
    private final ClothService clothService;
    private final CinemaService cinemaService;
    private final CafeService cafeService;
    private final Scanner scanner;

    public AvmController(final MarketService marketService,
                         final ClothService clothService,
                         final CinemaService cinemaService,
                         final CafeService cafeService) {
        this.marketService = marketService;
        this.clothService = clothService;
        this.cinemaService = cinemaService;
        this.cafeService = cafeService;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        char cmd;
        MarketController marketController = new MarketController(marketService, scanner);
        ShopController shopController = new ShopController(clothService, scanner);
        CinemaController cinemaController = new CinemaController(cinemaService, scanner);
        CafeController cafeController = new CafeController(cafeService, scanner);
        do {
            System.out.println("Choose service:\n" +
                    "[h] --> Hypermarket\n" +
                    "[c] --> Cafe\n" +
                    "[s] --> Cloth Shop\n" +
                    "[i] --> Cinema\n" +
                    "[x] --> Exit");
            cmd = scanner.nextLine().charAt(0);
            switch (cmd) {
                case 'h':
                    System.out.println("Welcome to AVM Hypermarket!");
                    marketController.run();
                    break;
                case 'c':
                    System.out.println("Welcome to AVM Ice Cafe!");
                    cafeController.run();
                    break;
                case 's':
                    System.out.println("Welcome to AVM Cloth Shop!");
                    shopController.run();
                    break;
                case 'i':
                    System.out.println("Welcome to AVM Cinema!");
                    cinemaController.run();
                    break;
                case 'x':
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Unrecognized command");
            }
        } while (cmd != 'x');
        System.out.println("Exit from AVM");
    }
}