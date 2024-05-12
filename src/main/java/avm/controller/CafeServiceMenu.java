package avm.controller;

import avm.products.CafeProduct;
import avm.repository.CafeRepository;

import java.util.Scanner;

public class CafeServiceMenu {
    private Scanner scanner;
    private CafeRepository cafeRepository;

    public CafeServiceMenu(Scanner scanner, CafeRepository cafeRepository) {
        this.scanner = scanner;
        this.cafeRepository = cafeRepository;
    }

    int id;
    char cmd;
    String[] input;

    public void run() {
        do {
            System.out.println("\nCafe service: \n" +
                    "[l] --> show product\n" +
                    "[a] --> add product\n" +
                    "[u] --> update product\n" +
                    "[d] --> delete product\n" +
                    "[b] --> back");
            cmd = scanner.nextLine().charAt(0);
            switch (cmd) {
                case 'l':
                    cafeRepository.findAll().forEach(System.out::println);
                    break;
                case 'a':
                    System.out.print("Addition product: [0] --> back to previous menu" +
                            "\nPlease enter [name & quantity & price] for adding: ");
                    input = scanner.nextLine().split("&");
                    if (input.length > 2) {
                        String name = input[0].trim();
                        int quantity = Integer.valueOf(input[1].trim());
                        int price = Integer.valueOf(input[2].trim());
                        cafeRepository.save(new CafeProduct(name, quantity, price));
                        System.out.println("Product added!");
                    } else {
                        if (Integer.valueOf(input[0].trim()) == 0) {
                            System.out.println("Cancel");
                            break;
                        }
                    }
                    break;
                case 'u':
                    System.out.print("Update product: [0] --> back to previous menu" +
                            "\nPlease enter [ID] of product: ");
                    id = Integer.valueOf(scanner.nextLine().trim());
                    CafeProduct product = cafeRepository.findById(id);
                    if (product != null) {
                        System.out.println("Update product: " + product);
                        System.out.print("Please enter [name & quantity & price] for update: ");
                        input = scanner.nextLine().split("&");
                        if (input.length > 2) {
                            String name = input[0].trim();
                            int quantity = Integer.valueOf(input[1].trim());
                            int price = Integer.valueOf(input[2].trim());
                            cafeRepository.save(new CafeProduct(id, name, quantity, price));
                            System.out.println("Product updated!");
                        } else {
                            if (Integer.valueOf(input[0].trim()) == 0) {
                                System.out.println("Cancel");
                                break;
                            }
                        }
                    } else {
                        System.out.println("Incorrect enter!");
                    }
                    break;
                case 'd':
                    System.out.print("Delete product: [0] --> back to previous menu" +
                            "\nPlease enter [ID] of product: ");
                    id = Integer.valueOf(scanner.nextLine().trim());
                    product = cafeRepository.findById(id);
                    if (product != null) {
                        cafeRepository.delete(id);
                        System.out.println("Product: " + product + " deleted!");
                    } else {
                        System.out.println("Product not found!");
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
