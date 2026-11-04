package org.example.shelter;

import org.example.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class Shelter <T extends Animal>{
    private final List<T> animals = new ArrayList<>();

    public void addAnimal(T animal){
        animals.add(animal);
    }

    public List<T> getAllAnimals(){
        return  animals;
    }

    public List<T> findBySpecies(String species){
        List<T> matchingSpecies = new ArrayList<>();
        for (T animal : animals){
        if (animal.getSpecies().equals(species)){
            matchingSpecies.add(animal);
        }}
        return  matchingSpecies;
    }


    public List<T> findAvailableAnimals(){
        // TODO
        return null;
    }

    public void markAsAdopted(String id){
        // TODO
    }
}
