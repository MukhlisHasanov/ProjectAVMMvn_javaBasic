package avm.controller;

import avm.service.ClothService;

import java.util.Scanner;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/ClothShop
 * @author Valerian
 * @version Apr-2024
 */
public class ShopController {
    public ClothService service;
    public Scanner scanner;

    public ShopController(ClothService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void run() {
        char cdm;
        String[] input;
        int quantity;
        int id;
        int size;
        do {
            System.out.println("\nCloth Shop: Please enter the command:\n" +
                    "[l] --> show clothes list,\n" +
                    "[a] --> add cloth to shopping cart\n" +
                    "[r] --> remove cloth from shopping cart\n" +
                    "[p] --> show shopping cart\n" +
                    "[b] --> back to main menu");
            cdm = scanner.nextLine().charAt(0);
            switch (cdm) {
                case 'l':
                    service.productList();
                    break;
                case 'a':
                    System.out.print("Cloth Shop: Please enter values of cloth for adding." +
                            "\n[0] --> back to previous menu\nValues: 'id' & 'quantity' & 'size': ");
                    input = scanner.nextLine().split("&");
                    if (input.length > 1) {
                        id = Integer.valueOf(input[0].trim());
                        quantity = Integer.valueOf(input[1].trim());
                        size = Integer.valueOf(input[2].trim());
                        service.addToOrder(id, quantity, size);
                    } else {
                        if (Integer.valueOf(input[0].trim())==0) {
                            break;
                        }
                    }
                    break;
                case 'r':
                    System.out.println("Cloth Shop: Please enter values of cloth for adding." +
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
                    System.out.println("Unrecognized command: " + cdm);
            }
        }while (cdm != 'b');
    }
}