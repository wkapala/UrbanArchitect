package buildings;

/**
 * Budynek: Szpital
 */
public class Hospital extends AbstractBuilding {
    private static final long serialVersionUID = 1L;

    public Hospital() {
        super("Przychodnia", BuildingType.HOSPITAL, 800, 100, 4);
    }

    @Override
    protected void initializeInfluence() {
        baseInfluence.put("health", 25);
        baseInfluence.put("happiness", 15);
        baseInfluence.put("safety", 10);
        baseInfluence.put("noise", 5); // Karetki
    }

    @Override
    public String getName() {
        return switch (level) {
            case 1 -> "Przychodnia";
            case 2 -> "Szpital miejski";
            case 3 -> "Centrum medyczne";
            default -> super.getName();
        };
    }
}
