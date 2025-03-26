import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PlantManager {
    private List<Plant> listOfPlants = new ArrayList<>();

    public List<Plant> getListOfPlants() {
        return new ArrayList<>(listOfPlants);
    }

    public void setListOfPlants(List<Plant> listOfPlants) {
        this.listOfPlants = listOfPlants;
    }

    public void addPlant(Plant plant) {
        listOfPlants.add(plant);
    }

    public Plant getIndexOfPlant(int index) {
        return listOfPlants.get(index);
    }

    public void removePlant(int index) {
        listOfPlants.remove(index);
    }

    public List<Plant> copyList() {
        return new ArrayList<>(listOfPlants);
    }

    public List<Plant> plantsInNeedOfWatering() {
        List<Plant> needsWatering = new ArrayList<>();
        for (Plant plant : listOfPlants) {
            if (plant.shouldBeWatered().isBefore(LocalDate.now())) {
                needsWatering.add(plant);
            }
        }
        return needsWatering;
    }

    public List<Plant> sortByNameAndWatering() {
        listOfPlants.sort(Comparator.comparing(Plant::getName)
                .thenComparing(Plant::getWatering));
        return List.of();
    }

    public void readFromFile(String filename, String delimiter) throws PlantException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNumber++;
                listOfPlants.add(Plant.parse(line, lineNumber, delimiter));
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + filename + " nebyl nalezen.");
        }
    }

    public void writeToFile(String filenameWrite, String delimiter) throws PlantException {
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(filenameWrite)))) {
            for (Plant plant : listOfPlants) {
                writer.println(plant.toFileString(delimiter));
            }

        } catch (IOException e) {
            throw new PlantException(("Soubor" + filenameWrite + " nebyl nalezen.\n"
                    + e.getLocalizedMessage()));
        }
    }
}
