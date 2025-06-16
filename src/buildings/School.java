package buildings;

/**
 * Budynek: Szkoła
 */
public class School extends AbstractBuilding {

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
        switch (level) {
            case 1: return "Szkoła podstawowa";
            case 2: return "Szkoła średnia";
            case 3: return "Kompleks edukacyjny";
            default: return super.getName();
        }
    }
}