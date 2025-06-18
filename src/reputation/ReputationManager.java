package reputation;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zarządzająca reputacją miasta
 */
public class ReputationManager {
    private int reputation;
    private List<String> reputationHistory;
    private static final int MAX_REPUTATION = 100;
    private static final int MIN_REPUTATION = 0;

    public ReputationManager() {
        this.reputation = 50; // Startowa neutralna reputacja
        this.reputationHistory = new ArrayList<>();
        addHistoryEntry("Rozpoczęcie kadencji", 0);
    }

    /**
     * Modyfikuje reputację
     */
    public void modifyReputation(int change) {
        int oldReputation = reputation;
        reputation += change;
        reputation = Math.max(MIN_REPUTATION, Math.min(MAX_REPUTATION, reputation));

        if (change != 0) {
            String description = change > 0 ? "+" + change : String.valueOf(change);
            addHistoryEntry("Zmiana reputacji: " + description, change);
        }
    }

    /**
     * Dodaje wpis do historii
     */
    private void addHistoryEntry(String event, int change) {
        String entry = event + " (" + (change >= 0 ? "+" : "") + change + ")";
        reputationHistory.add(entry);

        // Ogranicz historię do 20 wpisów
        if (reputationHistory.size() > 20) {
            reputationHistory.remove(0);
        }
    }

    /**
     * Oblicza wpływ reputacji na różne aspekty gry
     */
    public ReputationEffects getEffects() {
        ReputationEffects effects = new ReputationEffects();

        if (reputation >= 80) {
            effects.investorInterest = 2.0;
            effects.migrationBonus = 20;
            effects.taxCompliance = 1.2;
            effects.eventChanceModifier = 1.5;
            effects.status = "Legendarny";
        } else if (reputation >= 60) {
            effects.investorInterest = 1.5;
            effects.migrationBonus = 10;
            effects.taxCompliance = 1.1;
            effects.eventChanceModifier = 1.2;
            effects.status = "Szanowany";
        } else if (reputation >= 40) {
            effects.investorInterest = 1.0;
            effects.migrationBonus = 0;
            effects.taxCompliance = 1.0;
            effects.eventChanceModifier = 1.0;
            effects.status = "Przeciętny";
        } else if (reputation >= 20) {
            effects.investorInterest = 0.7;
            effects.migrationBonus = -10;
            effects.taxCompliance = 0.9;
            effects.eventChanceModifier = 0.8;
            effects.status = "Kontrowersyjny";
        } else {
            effects.investorInterest = 0.5;
            effects.migrationBonus = -20;
            effects.taxCompliance = 0.7;
            effects.eventChanceModifier = 0.5;
            effects.status = "Persona non grata";
        }

        return effects;
    }

    /**
     * Generuje raport reputacji
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        ReputationEffects effects = getEffects();

        report.append("\n🏛️ REPUTACJA\n");
        report.append("═══════════════════════════════\n");
        report.append(String.format("Poziom reputacji: %d/100\n", reputation));
        report.append("Status: ").append(effects.status).append("\n");
        report.append(getReputationBar()).append("\n\n");

        report.append("Efekty reputacji:\n");
        report.append(String.format("• Zainteresowanie inwestorów: %.0f%%\n", effects.investorInterest * 100));
        report.append(String.format("• Migracje: %+d%%\n", effects.migrationBonus));
        report.append(String.format("• Ściągalność podatków: %.0f%%\n", effects.taxCompliance * 100));

        if (!reputationHistory.isEmpty()) {
            report.append("\nOstatnie wydarzenia:\n");
            int start = Math.max(0, reputationHistory.size() - 5);
            for (int i = start; i < reputationHistory.size(); i++) {
                report.append("• ").append(reputationHistory.get(i)).append("\n");
            }
        }

        return report.toString();
    }

    /**
     * Generuje pasek reputacji
     */
    private String getReputationBar() {
        int filled = reputation / 5; // 20 segmentów
        StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < 20; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }

        bar.append("]");
        return bar.toString();
    }

    // Gettery
    public int getReputation() { return reputation; }
    public List<String> getHistory() { return new ArrayList<>(reputationHistory); }

    /**
     * Klasa wewnętrzna przechowująca efekty reputacji
     */
    public static class ReputationEffects {
        public double investorInterest;
        public int migrationBonus;
        public double taxCompliance;
        public double eventChanceModifier;
        public String status;
    }
}