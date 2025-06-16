package reputation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Klasa reprezentująca wpis w rankingu
 */
public class ScoreEntry implements Comparable<ScoreEntry> {
    private String playerName;
    private String cityName;
    private int score;
    private int scenario;
    private boolean victory;
    private LocalDateTime dateTime;

    public ScoreEntry(String playerName, String cityName, int score, int scenario, boolean victory) {
        this.playerName = playerName;
        this.cityName = cityName;
        this.score = score;
        this.scenario = scenario;
        this.victory = victory;
        this.dateTime = LocalDateTime.now();
    }

    // Konstruktor do wczytywania z pliku
    public ScoreEntry(String line) {
        String[] parts = line.split(";");
        if (parts.length >= 6) {
            this.playerName = parts[0];
            this.cityName = parts[1];
            this.score = Integer.parseInt(parts[2]);
            this.scenario = Integer.parseInt(parts[3]);
            this.victory = Boolean.parseBoolean(parts[4]);
            this.dateTime = LocalDateTime.parse(parts[5], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    @Override
    public int compareTo(ScoreEntry other) {
        return Integer.compare(other.score, this.score); // Malejąco
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String scenarioName = getScenarioName();
        String result = victory ? "✓" : "✗";

        return String.format("%-12s %-15s %6d pkt  %-15s %s  %s",
                playerName,
                cityName,
                score,
                scenarioName,
                result,
                dateTime.format(formatter)
        );
    }

    /**
     * Konwertuje do formatu zapisu
     */
    public String toFileFormat() {
        return String.join(";",
                playerName,
                cityName,
                String.valueOf(score),
                String.valueOf(scenario),
                String.valueOf(victory),
                dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }

    private String getScenarioName() {
        switch (scenario) {
            case 1: return "Miasto nad rzeką";
            case 2: return "Teren górzysty";
            case 3: return "Poprzemysłowy";
            case 4: return "Sandbox";
            default: return "Nieznany";
        }
    }

    // Gettery
    public String getPlayerName() { return playerName; }
    public String getCityName() { return cityName; }
    public int getScore() { return score; }
    public int getScenario() { return scenario; }
    public boolean isVictory() { return victory; }
    public LocalDateTime getDateTime() { return dateTime; }
}