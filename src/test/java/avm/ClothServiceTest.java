package avm;

import avm.products.Client;
import avm.products.ClothProduct;
import avm.repository.ClothRepository;
import avm.service.ClothService;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Collection;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClothServiceTest {
    static final String SQLITE_DB_AVM = "jdbc:sqlite:C:/temp/AvmDB_test.db";
    private static ClothRepository clothRepository;
    private static ClothService clothService;
    private static Client client;

    @BeforeAll
    public static void init() throws SQLException {
        clothRepository = new ClothRepository(SQLITE_DB_AVM);
        clothService = new ClothService(client, clothRepository);
        clothRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testsave() {
        Collection<ClothProduct> cloths = clothRepository.findAll();
        Assertions.assertEquals(0, cloths.size());

        clothRepository.save(new ClothProduct("name", "size", 1,100));
        cloths = clothRepository.findAll();
        Assertions.assertEquals(1, cloths.size());

        ClothProduct cloth = cloths.iterator().next();
        Assertions.assertEquals("name", cloth.getName());
    }
    @Test
    @Order(2)
    public void findById() {

    }
    @Test
    @Order(3)
    public void delete() {
        Collection<ClothProduct> cloths = clothRepository.findAll();
        Assertions.assertEquals(1, cloths.size());
        clothRepository.delete(1);
        Assertions.assertEquals(0, cloths.size());
    }

}

