package avm;

import avm.products.Client;
import avm.products.MarketProduct;
import avm.repository.MarketRepository;
import avm.service.MarketService;
import org.junit.jupiter.api.*;

import java.util.Collection;

public class MarketServiceTest {
    final static String SQLITE_DB_AVM = "jdbc:sqlite:C:/temp/AvmDB_test.db";
    private static MarketRepository marketRepository;
    private static MarketService marketService;
    private static Client client;

    @BeforeAll
    public static void init() {
        marketRepository = new MarketRepository(SQLITE_DB_AVM);
    //    marketService = new MarketService(client, marketRepository);
        marketRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testFindById() {
        Collection<MarketProduct> products = marketRepository.findAll();
        MarketProduct product = products.iterator().next();
        Integer productId = product.getId();

        product = marketRepository.findById(productId);
        Assertions.assertNotNull(product);
        Assertions.assertEquals(productId, product.getId());

        product = marketRepository.findById(productId + 1);
        Assertions.assertNull(product);

    }


    @Test
    @Order(2)
    public void testSave() {
    Collection<MarketProduct> products = new marketRopitory;    }

    @Test
    @Order(3)
    public void testDelete() {

    }

    @Test
    @Order(4)
    public void testUpdate() {

    }


}
