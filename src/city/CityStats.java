package city;

import zones.*;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

/**
 * Klasa przechowująca statystyki miasta
 */
public class CityStats implements Serializable {
    private static final long serialVersionUID = 1L;

    private int population;
    private int happiness;
    private int jobs;
    private int unemployment;
    private int pollution;
    private int noise;
    private int education;
    private int health;
    private int safety;
    private int culture;

    private Map<String, Integer> zoneCount;

    public CityStats() {
        this.population = 100; // Startowa populacja
        this.happiness = 50;   // Neutralne szczęście
        this.zoneCount = new HashMap<>();
        resetStats();
    }

    /**
     * Resetuje statystyki do wartości bazowych
     */
    private void resetStats() {
        jobs = 0;
        unemployment = 0;
        pollution = 0;
        noise = 0;
        education = 0;
        health = 0;
        safety = 0;
        culture = 0;
    }

    /**
     * Oblicza statystyki na podstawie mapy miasta
     */
    public void calculateStats(CityMap cityMap) {
        resetStats();
        zoneCount.clear();

        // Zlicz strefy
        for (ZoneType type : ZoneType.values()) {
            int count = cityMap.getZonesOfType(type).size();
            if (count > 0) {
                zoneCount.put(type.getName(), count);
            }
        }

        // Oblicz populację
        int residentialZones = cityMap.getZonesOfType(ZoneType.RESIDENTIAL).size();
        population = 100 + residentialZones * 50;

        // Oblicz miejsca pracy
        int commercialZones = cityMap.getZonesOfType(ZoneType.COMMERCIAL).size();
        int industrialZones = cityMap.getZonesOfType(ZoneType.INDUSTRIAL).size();
        jobs = commercialZones * 30 + industrialZones * 50;

        // Oblicz bezrobocie
        unemployment = Math.max(0, population - jobs);

        // Sumuj wpływy ze wszystkich stref
        int totalHappiness = 0;
        int happinessPoints = 0;

        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {
                CityZone zone = cityMap.getZone(x, y);
                if (zone.getType() == ZoneType.RESIDENTIAL) {
                    Map<String, Integer> influence = cityMap.calculateTotalInfluence(x, y);

                    // Wpływ na szczęście mieszkańców
                    int localHappiness = 50; // Bazowe szczęście

                    localHappiness += influence.getOrDefault("happiness", 0);
                    localHappiness -= influence.getOrDefault("pollution", 0) * 2;
                    localHappiness -= influence.getOrDefault("noise", 0);
                    localHappiness += influence.getOrDefault("education", 0) / 2;
                    localHappiness += influence.getOrDefault("health", 0) / 2;
                    localHappiness += influence.getOrDefault("culture", 0) / 3;
                    localHappiness += influence.getOrDefault("safety", 0) / 3;

                    // Wpływ bezrobocia
                    if (unemployment > population * 0.1) {
                        localHappiness -= 10;
                    }

                    totalHappiness += Math.max(0, Math.min(100, localHappiness));
                    happinessPoints++;

                    // Agreguj statystyki
                    pollution += influence.getOrDefault("pollution", 0);
                    noise += influence.getOrDefault("noise", 0);
                    education += influence.getOrDefault("education", 0);
                    health += influence.getOrDefault("health", 0);
                    safety += influence.getOrDefault("safety", 0);
                    culture += influence.getOrDefault("culture", 0);
                }
            }
        }

        // Oblicz średnie szczęście
        if (happinessPoints > 0) {
            happiness = totalHappiness / happinessPoints;
        }

        // Dodatkowe modyfikatory szczęścia
        int parkCount = cityMap.getZonesOfType(ZoneType.PARK).size();
        if (parkCount > 0) {
            happiness += Math.min(10, parkCount * 2);
        }

        happiness = Math.max(0, Math.min(100, happiness));
    }

    /**
     * Generuje raport tekstowy
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();

        report.append("╔═══════════════════════════════════╗\n");
        report.append("║         RAPORT MIESIĘCZNY         ║\n");
        report.append("╚═══════════════════════════════════╝\n\n");

        report.append("👥 DEMOGRAFIA:\n");
        report.append(String.format("   Populacja: %d mieszkańców\n", population));
        report.append(String.format("   Miejsca pracy: %d\n", jobs));
        report.append(String.format("   Bezrobocie: %d (%.1f%%)\n",
                unemployment, (double)unemployment/population * 100));

        report.append("\n😊 JAKOŚĆ ŻYCIA:\n");
        report.append(String.format("   Zadowolenie: %d%% %s\n",
                happiness, getHappinessEmoji()));
        report.append(String.format("   Edukacja: %d pkt\n", education));
        report.append(String.format("   Zdrowie: %d pkt\n", health));
        report.append(String.format("   Kultura: %d pkt\n", culture));
        report.append(String.format("   Bezpieczeństwo: %d pkt\n", safety));

        report.append("\n🏭 ŚRODOWISKO:\n");
        report.append(String.format("   Zanieczyszczenie: %d\n", pollution));
        report.append(String.format("   Hałas: %d\n", noise));

        return report.toString();
    }

    private String getHappinessEmoji() {
        if (happiness >= 80) return "😍 Świetnie!";
        if (happiness >= 60) return "😊 Dobrze";
        if (happiness >= 40) return "😐 Średnio";
        if (happiness >= 20) return "😟 Źle";
        return "😢 Tragicznie!";
    }

    // Gettery
    public int getPopulation() { return population; }
    public int getHappiness() { return happiness; }
    public int getJobs() { return jobs; }
    public int getUnemployment() { return unemployment; }
    public int getPollution() { return pollution; }
    public int getNoise() { return noise; }
    public int getEducation() { return education; }
    public int getHealth() { return health; }
    public int getSafety() { return safety; }
    public int getCulture() { return culture; }
    public Map<String, Integer> getZoneCount() { return new HashMap<>(zoneCount); }
}