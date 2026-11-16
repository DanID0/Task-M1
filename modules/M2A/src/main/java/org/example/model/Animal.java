package org.example.model;

import lombok.Getter;

@Getter
public sealed abstract class Animal permits Dog, Cat, Bird, Rabbit {
    private final AnimalId id;
    private String name;
    private int age;
    private AdoptionStatus adoptionStatus;


    protected Animal(AnimalId id, String name, int age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Animal name cannot be null or blank");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Animal age cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.age = age;
        this.adoptionStatus = AdoptionStatus.AVAILABLE;
    }

    public void markAsAdopted() {
        this.adoptionStatus = AdoptionStatus.ADOPTED;
    }

    public abstract String getSpecies();

    @Override
    public String toString() {
        return id + " | " + name + " | " + age + " years old | " + getSpecies() + " | " + adoptionStatus;
    }
}
