import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String FILENAME = "resources/kvetiny.txt";
    private static final String DELIMITER = "\t";
    private static final String FILENAMEWRITE = "resources/kvetiny2.txt";

    public static void main(String[] args)  {
        PlantManager manager = new PlantManager();

        try {
            manager.readFromFile(FILENAME,DELIMITER);
            for (Plant plant : manager.getListOfPlants()) {
                System.out.println("Květina "+ plant.getName() +
                        " má být zalita " + plant.getWatering());
            }
            manager.addPlant(new Plant("Zamiokulkas na okně"));
            for (int i = 1; i <= 10; i++) {
                manager.addPlant(new Plant("Tulipán na prodej "+i,
                        14));
            }
            manager.removePlant(2);
            manager.writeToFile(FILENAMEWRITE,DELIMITER);
            manager.readFromFile(FILENAMEWRITE,DELIMITER);
            manager.sortByNameAndWatering();
            manager.getListOfPlants().forEach(plant ->
                    System.out.println(plant.toString()));

        } catch (PlantException e) {
            System.err.println(e.getMessage());
        }

        }
    }