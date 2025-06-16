package events;

/**
 * Enum reprezentujący typy wydarzeń
 */
public enum EventType {
    SOCIAL("Społeczne", "👥"),
    ECONOMIC("Ekonomiczne", "💰"),
    ENVIRONMENTAL("Środowiskowe", "🌿"),
    POLITICAL("Polityczne", "🏛️"),
    RANDOM("Losowe", "🎲");

    private final String name;
    private final String icon;

    EventType(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}