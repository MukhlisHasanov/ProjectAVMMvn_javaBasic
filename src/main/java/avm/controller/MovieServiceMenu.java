package avm.controller;

import avm.products.MovieProduct;
import avm.repository.MovieRepository;

import java.util.Scanner;

public class MovieServiceMenu {
    private Scanner scanner;
    private MovieRepository movieRepository;

    public MovieServiceMenu(Scanner scanner, MovieRepository movieRepository) {
        this.scanner = scanner;
        this.movieRepository = movieRepository;
    }

    int id;
    char cmd;
    String[] input;

    public void run() {
        do {
            System.out.println("\nCinema service: \n" +
                    "[l] --> show list movies\n" +
                    "[a] --> add new movie\n" +
                    "[u] --> update movie\n" +
                    "[d] --> delete movie\n" +
                    "[b] --> back");
            cmd = scanner.nextLine().charAt(0);
            switch (cmd) {
                case 'l':
                    movieRepository.findAll().forEach(System.out::println);
                    break;
                case 'a':
                    System.out.print("Addition product: [0] --> back to previous menu" +
                            "\nPlease enter [name & genre & price & quantity] for adding: ");
                    input = scanner.nextLine().split("&");
                    if (input.length > 3) {
                        String name = input[0].trim();
                        String genre = input[1].trim();;
                        int price = Integer.valueOf(input[2].trim());
                        int quantity = Integer.valueOf(input[3].trim());
                        movieRepository.save(new MovieProduct(name, genre, price, quantity));
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
                    MovieProduct product = movieRepository.findById(id);
                    if (product != null) {
                        System.out.println("Update product: " + product);
                        System.out.print("Please enter [name & quantity & price] for update: ");
                        input = scanner.nextLine().split("&");
                        if (input.length > 3) {
                            String name = input[0].trim();
                            String genre = input[1].trim();
                            int price = Integer.valueOf(input[2].trim());
                            int quantity = Integer.valueOf(input[3].trim());
                            movieRepository.save(new MovieProduct(id, name, genre, price, quantity));
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
                    product = movieRepository.findById(id);
                    if (product != null) {
                        movieRepository.delete(id);
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
