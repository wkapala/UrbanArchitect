package events;

import city.GameService;

/**
 * Wydarzenie: Dotacja unijna
 */
public class EUGrantEvent implements Event {

    @Override
    public String getName() {
        return "Dotacja unijna";
    }

    @Override
    public String getDescription() {
        return "Możesz ubiegać się o dotację unijną na rozwój zielonej infrastruktury!\n" +
                "Warunek: miasto musi mieć niskie zanieczyszczenie i parki.";
    }

    @Override
    public String[] getOptions() {
        return new String[] {
                "Złóż wniosek o dotację (wymaga: zanieczyszczenie < 30, min. 3 parki)",
                "Przygotuj miasto do przyszłych dotacji",
                "Zrezygnuj"
        };
    }

    @Override
    public String handleChoice(int choice, GameService gameService) {
        switch (choice) {
            case 0:
                int parkCount = gameService.getCityMap().getZonesOfType(zones.ZoneType.PARK).size();
                int pollution = gameService.getCityStats().getPollution();

                if (pollution < 30 && parkCount >= 3) {
                    gameService.getBudgetManager().addIncome(3000, "Dotacja unijna");
                    gameService.getReputationManager().modifyReputation(15);
                    return "Gratulacje! Otrzymałeś dotację 3000 zł! Twoja reputacja wzrosła.";
                } else {
                    gameService.getReputationManager().modifyReputation(-5);
                    return "Wniosek odrzucony! Nie spełniasz warunków ekologicznych.";
                }

            case 1:
                gameService.addGoal("Zbuduj 3 parki i zmniejsz zanieczyszczenie");
                return "Cel dodany do listy zadań. Przygotuj miasto na następną okazję!";

            case 2:
                return "Może następnym razem...";

            default:
                return "Nieprawidłowy wybór";
        }
    }

    @Override
    public EventType getType() {
        return EventType.POLITICAL;
    }

    @Override
    public boolean canOccur(GameService gameService) {
        // Występuje losowo
        return true;
    }
}