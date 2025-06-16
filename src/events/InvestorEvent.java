package events;

import city.GameService;

/**
 * Wydarzenie: Oferta inwestora
 */
public class InvestorEvent implements Event {

    @Override
    public String getName() {
        return "Oferta inwestora";
    }

    @Override
    public String getDescription() {
        return "Duża korporacja chce zbudować centrum handlowe w Twoim mieście!\n" +
                "Oferują 2000 zł, ale żądają dużej działki.";
    }

    @Override
    public String[] getOptions() {
        return new String[] {
                "Zaakceptuj ofertę (+2000 zł, -5 reputacji u mieszkańców)",
                "Wynegocjuj lepsze warunki (wymaga reputacji > 70)",
                "Odrzuć ofertę (+5 reputacji)"
        };
    }

    @Override
    public String handleChoice(int choice, GameService gameService) {
        switch (choice) {
            case 0:
                gameService.getBudgetManager().addIncome(2000, "Inwestycja korporacji");
                gameService.getReputationManager().modifyReputation(-5);
                gameService.addCommercialZone(true); // Duże centrum handlowe
                return "Centrum handlowe zostanie wybudowane. Niektórzy mieszkańcy są niezadowoleni.";

            case 1:
                if (gameService.getReputationManager().getReputation() > 70) {
                    gameService.getBudgetManager().addIncome(2500, "Inwestycja wynegocjowana");
                    gameService.addCommercialZone(false); // Mniejsze centrum
                    return "Świetne negocjacje! Otrzymujesz lepszą ofertę i mniejszy budynek.";
                } else {
                    return "Twoja reputacja jest za niska do negocjacji!";
                }

            case 2:
                gameService.getReputationManager().modifyReputation(5);
                return "Mieszkańcy doceniają Twoją decyzję o zachowaniu charakteru miasta.";

            default:
                return "Nieprawidłowy wybór";
        }
    }

    @Override
    public EventType getType() {
        return EventType.ECONOMIC;
    }

    @Override
    public boolean canOccur(GameService gameService) {
        // Występuje gdy miasto jest średnio rozwinięte
        return gameService.getCityMap().getDevelopmentPercentage() > 30;
    }
}