package org.example.menu;

import org.example.model.Animal;
import org.example.shelter.Shelter;
import org.example.util.CollectionUtils;

import java.util.Comparator;
import java.util.Optional;

public class AnimalStatistics {
    private final Shelter<Animal> shelter;

    public AnimalStatistics(Shelter<Animal> shelter) {
        this.shelter = shelter;
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

    public void animalStatistics() {
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

}
