package avm.controller;

import avm.products.Client;
import avm.service.ClothService;

import java.util.Scanner;

/**
 * AIT-TR, Cohort 42.1, Java Basic, Project AVM/ClothShop
 * @author Valerian
 * @version May-2024
 */
public class ShopController {
    public ClothService service;
    public Scanner scanner;
    private Client client;

    public ShopController(ClothService service, Scanner scanner, Client client) {
        this.service = service;
        this.scanner = scanner;
        this.client = client;
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
                    "[b] --> back to main menu\n" +
                    "[m] --> pay the bill\n" +
                    "[w] --> show wallet\n" +
                    "[b] --> back to places menu");

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
//                        size = Integer.valueOf(input[2].trim());
                        service.addToOrder(id, quantity);
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
                case'm':
                    System.out.println(service);
                    System.out.println("Amount to be paid: " + service.sumOrder() + " EUR" + "\n");
                    System.out.println("Do you want to pay the bill? [y]es/[n]o" );
                    String choice = scanner.nextLine().toLowerCase();
                    if (choice.equals("y")) {
                        Float answer = service.payTheBill();
                        System.out.println(answer);
                        System.out.println("\nMoney in the wallet: "+ client.getWallet() + " EUR");
                    } else if (choice.equals("n")) {
                        // TODO rewrite code
                    } else {
                        System.out.println("INCORRECT CHOICE! PLEASE ENTER Y/N");
                    }
                    break;
                case 'w':
                    System.out.println("\nMoney in the wallet: "+ client.getWallet() + " EUR");
                    break;
                case 'b':
                    break;
                default:
                    System.out.println("Unrecognized command: " + cdm);
            }
        }while (cdm != 'b');
    }
}