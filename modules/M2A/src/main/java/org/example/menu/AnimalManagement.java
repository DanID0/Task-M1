package org.example.menu;

import org.example.model.*;
import org.example.shelter.Shelter;
import org.example.util.ConsoleInput;

import java.util.List;

public class AnimalManagement {
    private final ConsoleInput input;
    private final Shelter<Animal> shelter;

    public AnimalManagement(Shelter<Animal> shelter, ConsoleInput input) {
        this.shelter = shelter;
        this.input = input;
    }

    public void addNewAnimal() {
        System.out.println("Adding an animal");
        System.out.println("Choose animal type: 1-Dog, 2-Cat, 3-Bird 4-Rabbit");
        Integer animalType = input.readInt();

        if (animalType == null) {
            return;
        }
        if (animalType < 1 || animalType > 4) {
            System.out.println("Invalid animal type");
            return;
        }


        System.out.println("Enter animal name:");
        String name = input.readLine();
        if (name.isBlank()) {
            System.out.println("Animals name cannot be just empty spaces");
            return;
        }
        System.out.println("Enter animal age:");
        Integer age = input.readInt();
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

    public void listAllAnimals() {
        System.out.println("List of all animals:");
        for (Animal animal : shelter.getAllAnimals()) {
            System.out.println(animal);
        }
    }

    public void findBySpecies() {
        System.out.println("Enter species:");
        String species = input.readLine();
        for (Animal animal : shelter.findBySpecies(species)) {
            System.out.println(animal);
        }
    }

    public void findAvailableAnimals() {
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

    public void sortAnimals() {
        System.out.println("Choose what you want to sort by:");
        System.out.println("1. By name 2. By age");
        Integer answer = input.readInt();
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

}
