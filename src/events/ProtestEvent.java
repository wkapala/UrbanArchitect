package events;

import city.GameService;

/**
 * Wydarzenie: Protest mieszkańców
 */
public class ProtestEvent implements Event {

    @Override
    public String getName() {
        return "Protest mieszkańców";
    }

    @Override
    public String getDescription() {
        return "Mieszkańcy protestują przeciwko wysokiemu zanieczyszczeniu przemysłowemu!\n" +
                "Żądają zamknięcia fabryk lub budowy parków.";
    }

    @Override
    public String[] getOptions() {
        return new String[] {
                "Przeprowadź konsultacje społeczne (-500 zł, +10 reputacji)",
                "Obiecaj budowę parków (-200 zł, +5 reputacji)",
                "Zignoruj protesty (-15 reputacji)"
        };
    }

    @Override
    public String handleChoice(int choice, GameService gameService) {
        switch (choice) {
            case 0:
                if (gameService.getBudgetManager().spend(500, "Konsultacje społeczne")) {
                    gameService.getReputationManager().modifyReputation(10);
                    return "Konsultacje zakończone sukcesem! Mieszkańcy są zadowoleni.";
                } else {
                    return "Brak środków na konsultacje!";
                }

            case 1:
                if (gameService.getBudgetManager().spend(200, "Obietnice wyborcze")) {
                    gameService.getReputationManager().modifyReputation(5);
                    gameService.addPendingPromise("park");
                    return "Obiecałeś budowę parków. Mieszkańcy oczekują realizacji!";
                } else {
                    return "Brak środków nawet na obietnice!";
                }

            case 2:
                gameService.getReputationManager().modifyReputation(-15);
                return "Protesty się nasiliły. Twoja reputacja ucierpiała!";

            default:
                return "Nieprawidłowy wybór";
        }
    }

    @Override
    public EventType getType() {
        return EventType.SOCIAL;
    }

    @Override
    public boolean canOccur(GameService gameService) {
        // Występuje gdy zanieczyszczenie jest wysokie
        return gameService.getCityStats().getPollution() > 50;
    }
}