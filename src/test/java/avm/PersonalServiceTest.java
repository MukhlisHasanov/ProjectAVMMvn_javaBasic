package avm;

import avm.products.Personal;
import avm.products.PersonalState;
import avm.repository.PersonalRepository;
import avm.service.PersonalService;
import org.junit.jupiter.api.*;

import java.util.Collection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonalServiceTest {
    final static String SQLITE_DB_AVM = "jdbc:sqlite:C:/temp/AvmDB_test.db";
    private static PersonalRepository personalRepository;
    private static PersonalService personalService;
    private static PersonalState state;

    @BeforeAll
    public static void init() {
        personalRepository = new PersonalRepository(SQLITE_DB_AVM);
        personalService = new PersonalService(personalRepository);
        personalRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testAdd() {
        Collection<Personal> personals = personalRepository.findAll();
        Assertions.assertEquals(0, personals.size());

        state = PersonalState.valueOf("ADMIN");
        personalService.add("Personal", state);
        personals = personalRepository.findAll();
        Assertions.assertEquals(1, personals.size());

        Personal personal = personals.iterator().next();
        Assertions.assertEquals("Personal", personal.getName());
    }

    @Test
    @Order(2)
    public void testFindById() {
        Collection<Personal> personals = personalRepository.findAll();
        Personal personal = personals.iterator().next();
        Integer personalId = personal.getId();

        personal = personalService.findById(personalId);
        Assertions.assertNotNull(personal);
        Assertions.assertEquals(personalId, personal.getId());

        personal = personalService.findById(personalId + 1);
        Assertions.assertNull(personal);
    }

    @Test
    @Order(3)
    public void testUpdate() {
        Collection<Personal> personals = personalRepository.findAll();
        Personal personal = personals.iterator().next();
        Integer personalId = personal.getId();

        state = PersonalState.valueOf("ADMIN");
        boolean updated = personalService.update(personalId, "NewName", state);
        Assertions.assertTrue(updated);

        personal = personalService.findById(personalId);
        Assertions.assertEquals("NewName", personal.getName());
        Assertions.assertEquals("ADMIN", personal.getDepartment().name());

        updated = personalService.update(personalId + 1, "NewName", state);
        Assertions.assertFalse(updated);
    }

    @Test
    @Order(4)
    public void testDelete() {
        Collection<Personal> personals = personalRepository.findAll();
        Personal personal = personals.iterator().next();
        Integer personalId = personal.getId();

        boolean deleted = personalService.delete(personalId);
        Assertions.assertTrue(deleted);

        deleted = personalService.delete(personalId);
        Assertions.assertFalse(deleted);
    }
}