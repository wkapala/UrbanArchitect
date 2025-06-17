package city;

import java.io.*;

/**
 * Klasa reprezentująca stan gry (do zapisu/wczytywania)
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SAVE_FILE = "urban_architect_save.dat";

    private String cityName;
    private int currentMonth;
    private int scenario;
    private int budget;
    private int reputation;
    private CityMap cityMap;

    // Konstruktor dla nowej gry
    public GameState(String cityName, int scenario) {
        this.cityName = cityName;
        this.scenario = scenario;
        this.currentMonth = 1;
        this.budget = 2000; // Startowy budżet
        this.reputation = 50;
    }

    // Konstruktor do wczytywania
    public GameState() {
    }

    /**
     * Zapisuje stan gry do pliku
     */
    public boolean saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            System.err.println("Błąd zapisu gry: " + e.getMessage());
            return false;
        }
    }

    /**
     * Wczytuje stan gry z pliku
     */
    public static GameState loadFromFile() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd wczytywania gry: " + e.getMessage());
            return null;
        }
    }

    /**
     * Usuwa plik zapisu
     */
    public static void deleteSaveFile() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    // Gettery i settery
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public int getCurrentMonth() { return currentMonth; }
    public void setCurrentMonth(int currentMonth) { this.currentMonth = currentMonth; }

    public int getScenario() { return scenario; }
    public void setScenario(int scenario) { this.scenario = scenario; }

    public int getBudget() { return budget; }
    public void setBudget(int budget) { this.budget = budget; }

    public int getReputation() { return reputation; }
    public void setReputation(int reputation) { this.reputation = reputation; }

    public CityMap getCityMap() { return cityMap; }
    public void setCityMap(CityMap cityMap) { this.cityMap = cityMap; }
}