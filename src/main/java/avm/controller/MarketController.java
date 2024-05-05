package avm.controller;

import avm.service.MarketService;

import java.util.Scanner;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/Hypermarket
 * @author Mukhlis
 * @version Apr-2024
 */
public class MarketController {
    private MarketService service;
    private Scanner scanner;

    public MarketController(MarketService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void run() {
        char cmd;
        String[] input;
        int quantity;
        int id;
        do {
            System.out.println("\nHypermarket: Please enter the command:\n" +
                    "[l] --> show product list\n" +
                    "[a] --> add product to shopping cart\n" +
                    "[r] --> remove product from shopping cart\n" +
                    "[p] --> show shopping cart\n" +
                    "[b] --> back to main menu");
            cmd = scanner.nextLine().charAt(0);
            switch (cmd) {
                case 'l':
                    service.productList();
                    break;
                case 'a':
                    System.out.print("Hypermarket: Please enter values of products for adding." +
                            "\n[0] --> back to previous menu\nValues: 'id' & 'quantity': ");
                    input = scanner.nextLine().split("&");
                    if (input.length > 1) {
                        id = Integer.valueOf(input[0].trim());
                        quantity = Integer.valueOf(input[1].trim());
                        service.addToOrder(id, quantity);
                    } else {
                        if (Integer.valueOf(input[0].trim())==0) {
                            break;
                        }
                    }
                    break;
                case 'r':
                    System.out.print("Hypermarket: Please enter values of products for removing." +
                            "\n[0] --> back to previous menu\nValues: 'id' & 'quantity': ");
                    input = scanner.nextLine().split("&");
                    if (input.length > 1) {
                        id = Integer.valueOf(input[0].trim());
                        quantity = Integer.valueOf(input[1].trim());
                        service.removeFromOrder(id, quantity);
                    } else {
                        if (Integer.valueOf(input[0].trim())==0) {
                            break;
                        }
                        id = Integer.valueOf(input[0].trim());
                        service.removeFromOrder(id);
                    }
                    break;
                case 'p':
                    System.out.println(service);
                    System.out.println("Amount to be paid: " + service.sumOrder() + " EUR" + "\n");
                    break;
                case 'b':
                    break;
                default:
                    System.out.println("Unrecognized command: " + cmd);
            }
        } while (cmd != 'b');
    }
}