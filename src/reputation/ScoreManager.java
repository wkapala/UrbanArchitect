package reputation;

import java.io.*;
import java.util.*;

/**
 * Klasa zarządzająca rankingiem wyników
 */
public class ScoreManager {
    private static final String SCORE_FILE = "urban_scores.txt";
    private static final int MAX_ENTRIES = 20;
    private List<ScoreEntry> scores;

    public ScoreManager() {
        this.scores = new ArrayList<>();
        loadScores();
    }

    /**
     * Wczytuje wyniki z pliku
     */
    private void loadScores() {
        File file = new File(SCORE_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    scores.add(new ScoreEntry(line));
                } catch (Exception e) {
                    // Pomiń błędne linie
                }
            }
        } catch (IOException e) {
            System.err.println("Błąd wczytywania rankingu: " + e.getMessage());
        }

        Collections.sort(scores);
    }

    /**
     * Zapisuje wyniki do pliku
     */
    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_FILE))) {
            int count = Math.min(scores.size(), MAX_ENTRIES);
            for (int i = 0; i < count; i++) {
                writer.println(scores.get(i).toFileFormat());
            }
        } catch (IOException e) {
            System.err.println("Błąd zapisywania rankingu: " + e.getMessage());
        }
    }

    /**
     * Dodaje nowy wynik
     */
    public void addScore(ScoreEntry entry) {
        scores.add(entry);
        Collections.sort(scores);

        // Ogranicz do MAX_ENTRIES
        if (scores.size() > MAX_ENTRIES) {
            scores = scores.subList(0, MAX_ENTRIES);
        }

        saveScores();
    }

    /**
     * Wyświetla ranking
     */
    public void displayRanking() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              🏆 HALL OF FAME 🏆                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");

        if (scores.isEmpty()) {
            System.out.println("\nRanking jest pusty. Bądź pierwszym architektem!");
            return;
        }

        System.out.println("\n#   Gracz        Miasto          Punkty  Scenariusz      ✓/✗  Data");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════");

        for (int i = 0; i < Math.min(scores.size(), 10); i++) {
            System.out.printf("%-3d %s\n", i + 1, scores.get(i));
        }

        // Statystyki
        displayStatistics();
    }

    /**
     * Wyświetla statystyki
     */
    private void displayStatistics() {
        if (scores.isEmpty()) return;

        int victories = 0;
        Map<Integer, Integer> scenarioCount = new HashMap<>();

        for (ScoreEntry entry : scores) {
            if (entry.isVictory()) victories++;
            scenarioCount.merge(entry.getScenario(), 1, Integer::sum);
        }

        System.out.println("\n📊 STATYSTYKI:");
        System.out.println("Łączna liczba gier: " + scores.size());
        System.out.println("Zwycięstwa: " + victories + " (" +
                (victories * 100 / scores.size()) + "%)");
        System.out.println("Najwyższy wynik: " + scores.get(0).getScore() + " pkt");
    }
}