package buildings;

/**
 * Enum reprezentujący typy budynków specjalnych
 */
public enum BuildingType {
    SCHOOL("Szkoła", "📚"),
    HOSPITAL("Szpital", "🏥"),
    POLICE_STATION("Komisariat", "🚓"),
    FIRE_STATION("Straż pożarna", "🚒"),
    MUSEUM("Muzeum", "🏛️"),
    STADIUM("Stadion", "🏟️"),
    UNIVERSITY("Uniwersytet", "🎓"),
    THEATER("Teatr", "🎭"),
    SHOPPING_MALL("Centrum handlowe", "🛍️"),
    TRAIN_STATION("Dworzec kolejowy", "🚉");

    private final String displayName;
    private final String icon;

    BuildingType(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }
}