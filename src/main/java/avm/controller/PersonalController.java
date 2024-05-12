package avm.controller;

import avm.products.Personal;
import avm.products.PersonalState;
import avm.repository.PersonalRepository;

import java.util.Scanner;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/General
 * @author Mukhlis/Andrey Hein
 * @version May-2024
 */
public class PersonalController {
    private Personal personal;
    private Scanner scanner;
    private PersonalRepository personalRepository;

    public PersonalController(Scanner scanner, PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
        this.scanner = scanner;
    }

    public Personal start() {
        do {
            System.out.print("Do you have personal account? [y]es, [n]o:");
            String choice = scanner.nextLine().toLowerCase();
            if (choice.equals("y")) {
                System.out.print("Enter account ID: ");
                int clientId = scanner.nextInt();
                scanner.nextLine();

                this.personal = personalRepository.findById(clientId);
                if (personal != null) {
                    System.out.println("\nHello, " + personal.getName() + "!\n");
                    return personal;
                } else {
                    System.out.println("ID NOT FOUND! Please check ID or sign up");
                    return registerNewPersonal();
                }
            } else if (choice.equals("n")) {
                System.out.println("Please, sign up");
                return registerNewPersonal();
            } else {
                System.out.println("Unrecognized command. Try one more time");
            }
        } while (true);
    }

    private Personal registerNewPersonal() {
        PersonalState department = null;
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter department[A]DMIN, [M]ARKET, [C]AFE, C[L]OTH, MO[V]IE]: ");
        String choice = scanner.nextLine().toLowerCase();
        switch (choice) {
            case "a":
                department = PersonalState.ADMIN;
                break;
            case "m":
                department = PersonalState.MARKET;
                break;
            case "c":
                department = PersonalState.CAFE;
                break;
            case "l":
                department = PersonalState.CLOTH;
                break;
            case "v":
                department = PersonalState.MOVIE;
                break;
        }
        Personal personal = new Personal(name, department);
        personalRepository.save(personal);
        System.out.println("Congratulations you are registered! Your id: " + personal.getId());
        System.out.println("\nHello, " + personal.getName() + "!\n");
        return personal;
    }
}
