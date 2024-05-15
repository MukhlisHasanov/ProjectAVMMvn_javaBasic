package avm;

import avm.products.Client;
import avm.products.MarketProduct;
import avm.repository.MarketRepository;
import avm.repository.ProductRepository;
import avm.service.MarketService;
import org.junit.jupiter.api.*;

public class MarketRepositoryTest {
    final static String SQLITE_DB_AVM = "jdbc:sqlite:C:/temp/AvmDB_test.db";
    private static MarketRepository marketRepository;

    @BeforeAll
    public static void init() {
        marketRepository = new MarketRepository(SQLITE_DB_AVM);
    }

    @Test
    @Order(1)
    public void testFindById() {
        MarketProduct product = new MarketProduct(null, "testProduct", 10, 9.99f);
        marketRepository.save(product);

        MarketProduct foundProduct = marketRepository.findById(product.getId());

        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(product.getName(), foundProduct.getName());
        Assertions.assertEquals(product.getQuantity(), foundProduct.getQuantity());
        Assertions.assertEquals(product.getPrice(), foundProduct.getPrice());
    }

    @Test
    @Order(2)
    public void testSave() {
        MarketProduct product = new MarketProduct(null, "testProduct", 10, 9.95f);
        marketRepository.save(product);

        MarketProduct savedProduct = marketRepository.findById(product.getId());
        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(product.getName(), savedProduct.getName());
        Assertions.assertEquals(product.getQuantity(), savedProduct.getQuantity());
        Assertions.assertEquals(product.getPrice(), savedProduct.getPrice());
    }

    @Test
    @Order(3)
    public void testUpdate() {
        MarketProduct product = new MarketProduct(null, "testProduct", 10, 9.99f);
        marketRepository.save(product);

        product.setName("updatedProduct");
        product.setQuantity(20);
        product.setPrice(19.99f);

        marketRepository.save(product);

        MarketProduct updatedProduct = marketRepository.findById(product.getId());

        Assertions.assertNotNull(updatedProduct);
        Assertions.assertEquals(product.getName(), updatedProduct.getName());
        Assertions.assertEquals(product.getQuantity(), updatedProduct.getQuantity());
        Assertions.assertEquals(product.getPrice(), updatedProduct.getPrice());
    }

    @Test
    @Order(4)
    public void testDelete() {
        MarketProduct product = new MarketProduct(null, "testProduct", 10, 9.99f);
        marketRepository.save(product);

        marketRepository.delete(product.getId());

        MarketProduct deletedProduct = marketRepository.findById(product.getId());
        Assertions.assertNull(deletedProduct);

    }
}
