import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Plant implements Comparable<Plant> {
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate watering;
    private int frequencyOfWatering;

    public Plant(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        setWatering(watering);
        setFrequencyOfWatering(frequencyOfWatering);
    }

    public Plant(String name, int frequencyOfWatering) throws PlantException {
        this.name = name;
        this.notes = "";
        this.planted = LocalDate.now();
        this.watering = LocalDate.now();
        setFrequencyOfWatering(frequencyOfWatering);
    }

    public Plant(String name) {
        this.name = name;
        this.notes = "";
        this.planted = LocalDate.now();
        this.watering = LocalDate.now();
        this.frequencyOfWatering = 7;
    }

    public static Plant parse(String line, int lineNumber, String delimiter) throws PlantException {
        String[] parts = line.split(delimiter);
            String name = parts[0].trim();
                if (name.isEmpty()) {
                    throw new PlantException(
                            "Popis květiny nesmí být prázdný, viz řádek "
                            +lineNumber);
        }
            try {
                String notes = parts[1].trim();
                int frequencyOfWatering = Integer.parseInt(parts[2].trim());
                LocalDate watering = LocalDate.parse(parts[3].trim());
                LocalDate planted = LocalDate.parse(parts[4].trim());
                return new Plant(name, notes, planted, watering, frequencyOfWatering);

            }
            catch (DateTimeParseException | IllegalArgumentException e) {
                throw new PlantException("Nesprávný údaj na řádku "+lineNumber+".");
            }
    }

    public String getWateringInfo() {
        LocalDate nextWatering = watering.plusDays(frequencyOfWatering);
        return "Název květiny: " + name + ", datum poslední zálivky: "
                + watering + ", datum doporučené další zálivky: "
                + nextWatering + ".";
    }

    public LocalDate doWateringNow() throws PlantException {
        LocalDate wateringToday = LocalDate.now();
        setWatering(wateringToday);
        return watering;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public void setPlanted(LocalDate planted) {
        this.planted = planted;
    }

    public LocalDate getWatering() {
        return watering;
    }

    public void setWatering(LocalDate watering) throws PlantException {
        if (watering.isBefore(planted)) {
            throw new PlantException("Datum poslední zálivky nesmí být dříve, než datum zasazení rostliny.");
        }
        this.watering = watering;
    }

    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }

    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        if (frequencyOfWatering < 1) {
            throw new PlantException("Frekvence zálivky nesmí být menší než jeden den.");
        }
        this.frequencyOfWatering = frequencyOfWatering;
    }
    public LocalDate shouldBeWatered() {
        return watering.plusDays(frequencyOfWatering);
    }

    @Override
    public int compareTo(Plant second) {
        return this.name.compareTo(second.getName());
    }

    @Override
    public String toString() {
        return "Název květiny: " + name +
                ", poznámky: " + notes +
                ", zasazena dne: " + planted +
                ", naposledy zalita dne: " + watering +
                ", frekvence zalévání: " + frequencyOfWatering +
                '.';
    }

    public String toFileString(String delimiter) {
        return name + delimiter + notes + delimiter + frequencyOfWatering +
                delimiter + watering + delimiter + planted;
    }
}
