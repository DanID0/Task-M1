package org.example.shelter;

import org.example.model.AdoptionStatus;
import org.example.model.Animal;
import org.example.model.AdoptionRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shelter<T extends Animal> {
    private final List<T> animals = new ArrayList<>();
    private final List<AdoptionRecord> adoptionHistory = new ArrayList<>();

    public void addAnimal(T animal) {
        animals.add(animal);
    }

    public List<T> getAllAnimals() {
        return animals;
    }


    public List<T> findBySpecies(String species) {
        List<T> matchingSpecies = new ArrayList<>();
        for (T animal : animals) {
            if (animal.getSpecies().equalsIgnoreCase(species)) {
                matchingSpecies.add(animal);
            }
        }
        return matchingSpecies;
    }

    public List<T> returnAnimalsByAge() {
        List<T> sortedAnimals = new ArrayList<>(animals);

        sortedAnimals.sort(Comparator.comparingInt(Animal::getAge));

        return sortedAnimals;
    }

    public List<T> returnAnimalsByName() {
        List<T> sortedAnimals = new ArrayList<>(animals);

        sortedAnimals.sort(Comparator.comparing(Animal::getName));

        return sortedAnimals;
    }

    public List<T> findAvailableAnimals() {
        List<T> availableAnimals = new ArrayList<>();
        for (T animal : animals) {
            if (animal.getAdoptionStatus() == AdoptionStatus.AVAILABLE) {
                availableAnimals.add(animal);
            }
        }
        return availableAnimals;
    }

    public boolean markAsAdopted(T animal, String adopterName) {

        if (animals.contains(animal) && animal.getAdoptionStatus() == AdoptionStatus.AVAILABLE) {
            if (adopterName != null && !adopterName.isBlank()) {
                animal.markAsAdopted();
                AdoptionRecord record = new AdoptionRecord(
                        animal,
                        LocalDate.now(),
                        adopterName
                );
                adoptionHistory.add(record);
                return true;
            }


        }
        return false;
    }

    public List<AdoptionRecord> getAdoptionHistory() {
        return new ArrayList<>(adoptionHistory);
    }
}
