package org.example.menu;


import org.example.util.ConsoleInput;


import java.util.List;


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
    private final ConsoleInput input;
    private final AnimalManagement animalManagement;
    private final AdoptionManagement adoptionManagement;
    private final AnimalStatistics animalStatistics;

    public ConsoleMenu(ConsoleInput input, AnimalManagement animalManagement, AdoptionManagement adoptionManagement, AnimalStatistics animalStatistics) {
        this.input = input;
        this.animalManagement = animalManagement;
        this.adoptionManagement = adoptionManagement;
        this.animalStatistics = animalStatistics;
    }


    public void start() {
        Integer executionCode;
        while (true) {
            printMenu();

            executionCode = input.readInt();
            if (executionCode == null) {
                continue;
            }
            if (executionCode == 0) {
                return;
            }


            switch (executionCode) {
                case 1 -> animalManagement.addNewAnimal();

                case 2 -> animalManagement.listAllAnimals();

                case 3 -> animalManagement.findBySpecies();

                case 4 -> animalManagement.findAvailableAnimals();

                case 5 -> adoptionManagement.markAsAdopted();

                case 6 -> animalManagement.sortAnimals();

                case 7 -> animalStatistics.animalStatistics();

                case 8 -> adoptionManagement.displayAdoptionHistory();
                default -> System.out.println("Invalid option");
            }
        }
    }


    private void printMenu() {
        for (MenuOption option : OPTIONS) {
            System.out.println(option.number() + ". " + option.label());
        }
    }
}
