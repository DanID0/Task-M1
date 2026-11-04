package org.example.menu;

import org.example.model.*;
import org.example.shelter.Shelter;

import java.util.Scanner;

public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start() {
        int executionCode;
        while (true) {
            printMenu();
            executionCode = scanner.nextInt();
            scanner.nextLine();
            if (executionCode == 0) {
                break;
            }
            switch (executionCode) {
                case 1 -> addNewAnimal();

                case 2 -> listAllAnimals();

                case 3 -> findBySpecies();

                default -> System.out.println("Invalid option");
            }
        }
        // Call correct 'Shelter' methods based on selected option
    }

    private void addNewAnimal() {
        System.out.println("Adding an animal");
        System.out.println("Choose animal type: 1-Dog, 2-Cat, 3-Bird");
        int animalType = scanner.nextInt();
        scanner.nextLine();

        if (animalType < 1 || animalType > 3) {
            System.out.println("Invalid animal type");
            return;
        }

        System.out.println("Enter animal name:");
        String name = scanner.nextLine();

        System.out.println("Enter animal age:");
        int age = scanner.nextInt();
        scanner.nextLine();
        String added = " added successfully!";
        switch (animalType) {
            case 1 -> {
                shelter.addAnimal(new Dog(new AnimalId(), name, age));
                System.out.println("Dog " + name + added);
            }
            case 2 -> {
                shelter.addAnimal(new Cat(new AnimalId(), name, age));
                System.out.println("Cat " + name + added);
            }
            case 3 -> {
                shelter.addAnimal(new Bird(new AnimalId(), name, age));
                System.out.println("Bird " + name + added);
            }
            default -> System.out.println("There is no such animal");
        }
    }

    private void listAllAnimals() {
        System.out.println("List of all animals:");
        for (Animal animal : shelter.getAllAnimals()) {
            System.out.println(animal);
        }
    }

    private void findBySpecies() {
        System.out.println("Enter species (first letter in capital e.g. Cat):");
        String species = scanner.nextLine();

        for (Animal animal : shelter.findBySpecies(species)) {
            System.out.println(animal);
        }
    }

    private void printMenu() {
        System.out.println("""
                1. Add animal
                2. List all animals
                3. Find animals by species
                4. List available animals
                5. Mark animal as adopted
                0. Exit
                """);
    }
}
