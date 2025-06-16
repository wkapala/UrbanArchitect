package buildings;

/**
 * Budynek: Szkoła
 */
public class School extends AbstractBuilding {
    private static final long serialVersionUID = 1L;

    public School() {
        super("Szkoła podstawowa", BuildingType.SCHOOL, 500, 50, 3);
    }

    @Override
    protected void initializeInfluence() {
        baseInfluence.put("education", 20);
        baseInfluence.put("happiness", 10);
        baseInfluence.put("noise", 2);
        baseInfluence.put("safety", 5);
    }

    @Override
    public String getName() {
        return switch (level) {
            case 1 -> "Szkoła podstawowa";
            case 2 -> "Szkoła średnia";
            case 3 -> "Kompleks edukacyjny";
            default -> super.getName();
        };
    }
}
