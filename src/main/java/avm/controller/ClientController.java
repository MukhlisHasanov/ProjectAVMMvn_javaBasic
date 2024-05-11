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
        do {
            System.out.print("Do you have client account? [y]es, [n]o:");
            String choice = scanner.nextLine().toLowerCase();
            if (choice.equals("y")) {
                System.out.print("Enter account ID: ");
                int clientId = scanner.nextInt();
                scanner.nextLine();

                this.client = clientRepository.findById(clientId);
                if (client != null) {
                    System.out.println("\nHello, " + client.getName() + "!\n");
                    return client;
                } else {
                    System.out.println("ID NOT FOUND! Please check ID or sign up");
                    return registerNewClient();
                }
            } else if (choice.equals("n")) {
                System.out.println("Please, sign up");
                return registerNewClient();
            } else {
                System.out.println("Unrecognized command. Try one more time");
            }
        } while (true);
    }

    private Client registerNewClient() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter money in the wallet: ");
        int wallet = scanner.nextInt();
        scanner.nextLine();

        Client client = new Client(name, age, wallet);

        clientRepository.save(client);
        System.out.println("Congratulations you are registered! Your id: " + client.getId());
        System.out.println("\nHello, " + client.getName() + "!\n");
        return client;
    }
}