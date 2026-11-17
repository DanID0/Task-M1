package org.example.menu;

import org.example.model.*;
import org.example.shelter.Shelter;
import org.example.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleMenu {
    private static final List<MenuOption> OPTIONS = List.of(
            new MenuOption(1, "Add animal"),
            new MenuOption(2, "List all animals"),
            new MenuOption(3, "Find animals by species"),
            new MenuOption(4, "Find available animals"),
            new MenuOption(5, "Adopt an animal"),
            new MenuOption(6, "Sort animals"),
            new MenuOption(7, "Animal statistics"),
            new MenuOption(8, "View adoption history"),
            new MenuOption(0, "Exit")
    );
    private final Shelter<Animal> shelter;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    private Integer readInt() {
        if (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
            return null;
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }


    public void start() {
        Integer executionCode;
        while (true) {
            printMenu();

            executionCode = readInt();
            if (executionCode == null) {
                continue;
            }
            if (executionCode == 0) {
                return;
            }


            switch (executionCode) {
                case 1 -> addNewAnimal();

                case 2 -> listAllAnimals();

                case 3 -> findBySpecies();

                case 4 -> findAvailableAnimals();

                case 5 -> markAsAdopted();

                case 6 -> sortAnimals();

                case 7 -> animalStatistics();

                case 8 -> displayAdoptionHistory();
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void addNewAnimal() {
        System.out.println("Adding an animal");
        System.out.println("Choose animal type: 1-Dog, 2-Cat, 3-Bird 4-Rabbit");
        Integer animalType = readInt();

        if (animalType == null) {
            return;
        }
        if (animalType < 1 || animalType > 4) {
            System.out.println("Invalid animal type");
            return;
        }


        System.out.println("Enter animal name:");
        String name = scanner.nextLine();
        if (name.isBlank()) {
            System.out.println("Animals name cannot be just empty spaces");
            return;
        }
        System.out.println("Enter animal age:");
        Integer age = readInt();
        if (age == null) {
            return;
        }
        if (age < 0) {
            System.out.println("Animals age cant be negative");
            return;
        }
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
            case 4 -> {
                shelter.addAnimal(new Rabbit(new AnimalId(), name, age));
                System.out.println("Rabbit " + name + added);
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

    private void displayAdoptionHistory() {
        if (shelter.getAdoptionHistory().isEmpty()) {
            System.out.println("No adoption history yet.");
            return;
        }
        System.out.println("List of adopted animals:");
        for (AdoptionRecord adoptionRecord : shelter.getAdoptionHistory()) {
            System.out.println(adoptionRecord);
        }
    }

    private void findBySpecies() {
        System.out.println("Enter species:");
        String species = scanner.nextLine();

        for (Animal animal : shelter.findBySpecies(species)) {
            System.out.println(animal);
        }
    }

    private int countSpecies(String species) {
        int animalSpecies = 0;

        for (Animal animal : shelter.getAllAnimals()) {
            if (animal.getSpecies().equalsIgnoreCase(species)) {
                animalSpecies++;
            }
        }

        return animalSpecies;
    }

    private void animalStatistics() {
        double averageAge = CollectionUtils.average(
                shelter.getAllAnimals(),
                Animal::getAge
        );
        System.out.println("Average animal age: " + averageAge);
        Optional<Animal> oldestAnimal = CollectionUtils.findMax(
                shelter.getAllAnimals(),
                Comparator.comparingInt(Animal::getAge)
        );
        oldestAnimal.ifPresent(animal ->
                System.out.println("Oldest animal: " + animal)
        );
        System.out.println("Dogs: " + countSpecies("Dog"));
        System.out.println("Cats: " + countSpecies("Cat"));
        System.out.println("Birds: " + countSpecies("Bird"));
        System.out.println("Rabbits: " + countSpecies("Rabbit"));
    }

    private void findAvailableAnimals() {
        List<Animal> availableAnimals = shelter.findAvailableAnimals();
        if (availableAnimals.isEmpty()) {
            System.out.println("There are no available animals");
            return;
        }
        System.out.println("List of available animals:");
        for (Animal animal : availableAnimals) {
            System.out.println(animal);
        }
    }

    private void markAsAdopted() {
        List<Animal> availableAnimals = shelter.findAvailableAnimals();
        if (availableAnimals.isEmpty()) {
            System.out.println("There are no available animals to adopt.");

            return;
        }

        System.out.println("Choose an animal to adopt:");

        for (int i = 0; i < availableAnimals.size(); i++) {
            System.out.println((i + 1) + ". " + availableAnimals.get(i));
        }

        System.out.println("Enter the number of the animal to adopt:");
        Integer selection = readInt();
        if (selection == null) {
            return;
        }
        if (selection < 1 || selection > availableAnimals.size()) {
            System.out.println("Invalid animal number.");
            return;
        }

        Animal selectedAnimal = availableAnimals.get(selection - 1);

        System.out.println("Enter your name as an adopter");
        String adopterName = scanner.nextLine();

        boolean result = shelter.markAsAdopted(selectedAnimal, adopterName);
        if (result) {
            System.out.println("Animal marked as adopted");

        } else {
            System.out.println("Unable to adopt the selected animal.");
        }

    }

    private void sortAnimals() {
        System.out.println("Choose what you want to sort by:");
        System.out.println("1. By name 2. By age");
        Integer answer = readInt();
        if (answer == null) {
            return;
        }
        if (answer == 1) {
            List<Animal> sortedAnimals = shelter.returnAnimalsByName();

            for (Animal animal : sortedAnimals) {
                System.out.println(animal);
            }
        } else if (answer == 2) {
            List<Animal> sortedAnimals = shelter.returnAnimalsByAge();

            for (Animal animal : sortedAnimals) {
                System.out.println(animal);
            }
        } else {
            System.out.println("No such option exists, try again");
        }
    }

    private void printMenu() {
        for (MenuOption option : OPTIONS) {
            System.out.println(option.number() + ". " + option.label());
        }
    }
}
