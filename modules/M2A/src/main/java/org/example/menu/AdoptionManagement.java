package org.example.menu;

import org.example.model.AdoptionRecord;
import org.example.model.Animal;
import org.example.shelter.Shelter;
import org.example.util.ConsoleInput;

import java.util.List;

public class AdoptionManagement {
    private final ConsoleInput input;
    private final Shelter<Animal> shelter;

    public AdoptionManagement(Shelter<Animal> shelter, ConsoleInput input) {
        this.shelter = shelter;
        this.input = input;
    }

    public void markAsAdopted() {
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
        Integer selection = input.readInt();
        if (selection == null) {
            return;
        }
        if (selection < 1 || selection > availableAnimals.size()) {
            System.out.println("Invalid animal number.");
            return;
        }

        Animal selectedAnimal = availableAnimals.get(selection - 1);

        System.out.println("Enter your name as an adopter");
        String adopterName = input.readLine();

        if (adopterName.isBlank()) {
            System.out.println("Adopter name cannot be empty.");
            return;
        }
        boolean result = shelter.markAsAdopted(selectedAnimal, adopterName);
        if (result) {
            System.out.println("Animal marked as adopted");

        } else {
            System.out.println("Unable to adopt the selected animal.");
        }

    }

    public void displayAdoptionHistory() {
        if (shelter.getAdoptionHistory().isEmpty()) {
            System.out.println("No adoption history yet.");
            return;
        }
        System.out.println("List of adopted animals:");
        for (AdoptionRecord adoptionRecord : shelter.getAdoptionHistory()) {
            System.out.println(adoptionRecord);
        }
    }


}
