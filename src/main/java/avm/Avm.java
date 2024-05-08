package avm;

import avm.controller.AvmController;
import avm.controller.ClientController;
import avm.products.Client;
import avm.repository.*;
import avm.service.CafeService;
import avm.service.CinemaService;
import avm.service.ClothService;
import avm.service.MarketService;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project #2 AVM
 * @author Mukhlis
 * @version May-2024
 */
public class Avm {
    public static void main(String[] args) {

        final String SQLITE_DB_AVM = "jdbc:sqlite:C:/temp/AvmDB.db";

        ClientRepository clientRepository = new ClientRepository();
        ClientController clientController = new ClientController(clientRepository);
        clientRepository.initClient();
        Client client = clientController.start();

        MarketRepository marketRepository = new MarketRepository(SQLITE_DB_AVM);
        ClothRepository clothRepository = new ClothRepository(SQLITE_DB_AVM);
        MovieRepository movieRepository = new MovieRepository(SQLITE_DB_AVM);
        CafeRepository cafeRepository = new CafeRepository(SQLITE_DB_AVM);

        MarketService marketService = new MarketService(client,marketRepository);
        ClothService clothService = new ClothService(client, clothRepository);
        CinemaService cinemaService = new CinemaService(client, movieRepository);
        CafeService cafeService = new CafeService(client,cafeRepository);

//        marketRepository.initMarket();
//        clothRepository.initCloth();
//        movieRepository.initMovie();
//        cafeRepository.initCafe();

        new AvmController(marketService, clothService, cinemaService,cafeService).run();
    }
}
