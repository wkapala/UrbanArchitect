package events;

import city.GameService;

/**
 * Interfejs dla wydarzeń w grze
 */
public interface Event {
    /**
     * Zwraca nazwę wydarzenia
     */
    String getName();

    /**
     * Zwraca opis wydarzenia
     */
    String getDescription();

    /**
     * Zwraca opcje dostępne dla gracza
     */
    String[] getOptions();

    /**
     * Obsługuje wybór gracza
     * @param choice indeks wybranej opcji
     * @param gameService referencja do serwisu gry
     * @return opis rezultatu
     */
    String handleChoice(int choice, GameService gameService);

    /**
     * Zwraca typ wydarzenia
     */
    EventType getType();

    /**
     * Sprawdza czy wydarzenie może wystąpić
     */
    boolean canOccur(GameService gameService);
}