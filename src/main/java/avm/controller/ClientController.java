package avm.controller;

import avm.products.Client;
import avm.repository.ClientRepository;

import java.util.Scanner;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/General
 * @author Mukhlis/Andrey Hein
 * @version Apr-2024
 */
public class ClientController {
    private Client client;
    private Scanner scanner;
    private ClientRepository clientRepository;
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.scanner = new Scanner(System.in);
    }

    public Client start() {
        System.out.println("Welcome to AVM!");
        System.out.print("Do you have client account? [y]es, [n]o:");
        String choice = scanner.nextLine().toLowerCase();
        if (choice.equals("y")) {
            System.out.print("Enter account ID: ");
            int clientId = scanner.nextInt();
            scanner.nextLine();

            this.client = clientRepository.get(clientId);
            if (client != null) {
                System.out.println("\nHello, " + client.getName() + "!\n");
            } else {
                System.out.println("ID NOT FOUND! Please check ID or sign up");
                return registerNewClient();
            }
        } else if (choice.equals("n")) {
            System.out.println("Please, sign up");
            return registerNewClient();
        } else {
            System.out.println("Unrecognized command. Try one more time");
            start();
        }
        return client;
    }

    private Client registerNewClient() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        Client client = new Client(name, age);

        clientRepository.put(client);
        System.out.println("Congratulations you are registered! Your id: " + client.getId());
        System.out.println("\nHello, " + client.getName() + "!\n");
        return client;
    }
}