package events;

import city.GameService;
import java.util.*;

/**
 * Klasa zarządzająca wydarzeniami w grze
 */
public class EventManager {
    private List<Event> allEvents;
    private Random random;

    public EventManager() {
        this.allEvents = new ArrayList<>();
        this.random = new Random();
        initializeEvents();
    }

    /**
     * Inicjalizuje dostępne wydarzenia
     */
    private void initializeEvents() {
        // Wydarzenia społeczne
        allEvents.add(new ProtestEvent());

        // Wydarzenia ekonomiczne
        allEvents.add(new InvestorEvent());

        // Wydarzenia polityczne
        allEvents.add(new EUGrantEvent());

        // Można dodać więcej wydarzeń
    }

    /**
     * Zwraca losowe wydarzenie które może wystąpić
     */
    public Event getRandomEvent(GameService gameService) {
        List<Event> possibleEvents = new ArrayList<>();

        // Filtruj wydarzenia które mogą wystąpić
        for (Event event : allEvents) {
            if (event.canOccur(gameService)) {
                possibleEvents.add(event);
            }
        }

        if (possibleEvents.isEmpty()) {
            return null;
        }

        // Wybierz losowe wydarzenie
        return possibleEvents.get(random.nextInt(possibleEvents.size()));
    }

    /**
     * Zwraca wydarzenie określonego typu
     */
    public Event getEventOfType(EventType type, GameService gameService) {
        List<Event> eventsOfType = new ArrayList<>();

        for (Event event : allEvents) {
            if (event.getType() == type && event.canOccur(gameService)) {
                eventsOfType.add(event);
            }
        }

        if (eventsOfType.isEmpty()) {
            return null;
        }

        return eventsOfType.get(random.nextInt(eventsOfType.size()));
    }
}