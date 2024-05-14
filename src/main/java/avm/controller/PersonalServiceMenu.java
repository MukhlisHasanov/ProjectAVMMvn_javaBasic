package avm.controller;

import avm.products.Personal;
import avm.repository.PersonalRepository;
import java.util.Scanner;

public class PersonalServiceMenu {
    private Scanner scanner;
    private PersonalRepository personalRepository;
    private PersonalController personalController;

    public PersonalServiceMenu(Scanner scanner, PersonalRepository personalRepository, PersonalController personalController) {
        this.scanner = scanner;
        this.personalRepository = personalRepository;
        this.personalController = personalController;
    }
    int id;
    char cmd;

    public void run() {

        do {
            System.out.println("\nPersonal service: \n" +
                    "[l] --> show personal\n" +
                    "[a] --> add personal\n" +
                    "[u] --> update personal\n" +
                    "[d] --> delete personal\n" +
                    "[b] --> back");
            cmd = scanner.nextLine().charAt(0);
            switch (cmd) {
                case 'l':
                    personalRepository.findAll().forEach(System.out::println);
                    break;
                case 'a':
                    personalController.registerNewPersonal();
                    break;
                case 'u':
                    System.out.print("Update personal: [0] --> back to previous menu" +
                            "\nPlease enter [ID] of personal: ");
                    id = Integer.valueOf(scanner.nextLine().trim());
                    Personal personal = personalRepository.findById(id);
                    if (personal != null) {
                        System.out.println("Update personal: " + personal);
                        personalController.updatePersonal(id);
                    } else if (id == 0) {
                        System.out.println("Cancel");
                        break;
                    } else {
                        System.out.println("Incorrect enter!");
                    }
                    break;
                case 'd':
                    System.out.print("Delete personal: [0] --> back to previous menu" +
                            "\nPlease enter [ID] of personal: ");
                    id = Integer.valueOf(scanner.nextLine().trim());
                    personal = personalRepository.findById(id);
                    if (personal != null) {
                        personalRepository.delete(id);
                        System.out.println("Personal: " + personal + " deleted!");
                    } else {
                        System.out.println("Personal not found!");
                    }
                    break;
                case 'b':
                    break;
                default:
                    System.out.println("Unrecognized command: " + cmd);
            }
        } while (cmd != 'b');
    }
}