package avm.controller;

import avm.products.ClothProduct;
import avm.repository.ClothRepository;

import java.util.Scanner;

public class ClothServiceMenu {
    private Scanner scanner;
    private ClothRepository clothRepository;

    public ClothServiceMenu(Scanner scanner, ClothRepository clothRepository) {
        this.scanner = scanner;
        this.clothRepository = clothRepository;
    }

    int id;
    char cmd;
    String[] input;

    public void run() {
        do {
            System.out.println("\nCloth shop service: \n" +
                    "[l] --> show product\n" +
                    "[a] --> add product\n" +
                    "[u] --> update product\n" +
                    "[d] --> delete product\n" +
                    "[b] --> back");
            cmd = scanner.nextLine().charAt(0);
            switch (cmd) {
                case 'l':
                    clothRepository.findAll().forEach(System.out::println);
                    break;
                case 'a':
                    System.out.print("Addition product: [0] --> back to previous menu" +
                            "\nPlease enter [name & size & quantity & price] for adding: ");
                    input = scanner.nextLine().split("&");
                    if (input.length > 3) {
                        String name = input[0].trim();
                        String size = input[1].trim();
                        int quantity = Integer.valueOf(input[2].trim());
                        float price = Float.valueOf(input[2].trim());
                        clothRepository.save(new ClothProduct(name, size, quantity, price));
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
                    ClothProduct product = clothRepository.findById(id);
                    if (product != null) {
                        System.out.println("Update product: " + product);
                        System.out.print("Please enter [name & quantity & price] for update: ");
                        input = scanner.nextLine().split("&");
                        if (input.length > 3) {
                            String name = input[0].trim();
                            String size = input[1].trim();
                            int quantity = Integer.valueOf(input[2].trim());
                            float price = Float.valueOf(input[2].trim());
                            clothRepository.save(new ClothProduct(id, name, size, quantity, price));
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
                    product = clothRepository.findById(id);
                    if (product != null) {
                        clothRepository.delete(id);
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
