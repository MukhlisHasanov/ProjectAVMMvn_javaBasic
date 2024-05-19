package avm;

import avm.controller.*;
import avm.products.Client;
import avm.products.Personal;
import avm.repository.*;
import avm.service.CafeService;
import avm.service.CinemaService;
import avm.service.ClothService;
import avm.service.MarketService;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project #2 AVM
 * @author Mukhlis
 * @version May-2024
 */
public class Avm {
    public static void main(String[] args) throws SQLException {

        String choice;
        Scanner scanner = new Scanner(System.in);
        final String SQLITE_DB_AVM = "jdbc:sqlite:C:/temp/AvmDB.db";
        Client client = null;
        Personal personal = null;

        ClientRepository clientRepository = new ClientRepository(SQLITE_DB_AVM);
        PersonalRepository personalRepository = new PersonalRepository(SQLITE_DB_AVM);
        ClientController clientController = new ClientController(clientRepository);

        MarketRepository marketRepository = new MarketRepository(SQLITE_DB_AVM);
        ClothRepository clothRepository = new ClothRepository(SQLITE_DB_AVM);
        MovieRepository movieRepository = new MovieRepository(SQLITE_DB_AVM);
        CafeRepository cafeRepository = new CafeRepository(SQLITE_DB_AVM);

        do {
            System.out.println("Welcome to AVM!");
            System.out.print("[P]ersonal, [C]lient, E[x]it: ");
            choice = scanner.nextLine().toLowerCase();
            switch (choice) {
                case "p":
                    new ServiceController(scanner,
                            marketRepository,
                            cafeRepository,
                            clothRepository,
                            movieRepository,
                            personalRepository).run();
                    break;
                case "c":
                    client = clientController.start();
                    MarketService marketService = new MarketService(client, marketRepository);
                    ClothService clothService = new ClothService(client, clothRepository);
                    CinemaService cinemaService = new CinemaService(client, movieRepository);
                    CafeService cafeService = new CafeService(client, cafeRepository);

                    new AvmController(marketService, clothService, cinemaService, cafeService, client).run();
                    break;
                case "x":
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Unrecognized command. Try one more time");
            }
        } while (!choice.equals("x"));
        System.out.println("Exit from AVM");
    }
}