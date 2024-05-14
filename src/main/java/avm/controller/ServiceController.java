package avm.controller;

import avm.products.Personal;
import avm.products.PersonalState;
import avm.repository.*;

import java.util.Scanner;

public class ServiceController {
    private Scanner scanner;
    private MarketRepository marketRepository;
    private CafeRepository cafeRepository;
    private ClothRepository clothRepository;
    private MovieRepository movieRepository;
    private PersonalRepository personalRepository;

    public ServiceController(Scanner scanner, MarketRepository marketRepository,
                             CafeRepository cafeRepository,
                             ClothRepository clothRepository,
                             MovieRepository movieRepository,
                             PersonalRepository personalRepository) {
        this.scanner = scanner;
        this.marketRepository = marketRepository;
        this.cafeRepository = cafeRepository;
        this.clothRepository = clothRepository;
        this.movieRepository = movieRepository;
        this.personalRepository = personalRepository;
    }

    public void run() {
        PersonalController personalController = new PersonalController(scanner,personalRepository);
        Personal personal = personalController.start();
        PersonalState department = personal.getDepartment();

        PersonalServiceMenu personalMenu = new PersonalServiceMenu(scanner, personalRepository, personalController);
        MarketServiceMenu marketMenu = new MarketServiceMenu(scanner,marketRepository);
        CafeServiceMenu cafeMenu = new CafeServiceMenu(scanner, cafeRepository);
        ClothServiceMenu clothMenu = new ClothServiceMenu(scanner, clothRepository);
        CinemaServiceMenu movieMenu = new CinemaServiceMenu(scanner, movieRepository);

        switch (department) {
            case ADMIN:
                char cmd;
                do {
                    System.out.println("\nAdministrator service: \n" +
                            "[1] --> Market service\n" +
                            "[2] --> Cafe service\n" +
                            "[3] --> Cloth service\n" +
                            "[4] --> Movie service\n" +
                            "[5] --> Personal service\n" +
                            "[b] --> Back to AVM menu");
                    cmd = scanner.nextLine().charAt(0);
                    switch (cmd) {
                        case '1':
                            marketMenu.run();
                            break;
                        case '2':
                            cafeMenu.run();
                            break;
                        case '3':
                            clothMenu.run();
                            break;
                        case '4':
                            movieMenu.run();
                            break;
                        case '5':
                            personalMenu.run();
                            break;
                        case 'b':
                            break;
                        default:
                            System.out.println("Unrecognized command: " + cmd);
                    }
                } while (cmd != 'b');
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