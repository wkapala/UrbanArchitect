package buildings;

/**
 * Budynek: Szpital
 */
public class Hospital extends AbstractBuilding {

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
        switch (level) {
            case 1: return "Przychodnia";
            case 2: return "Szpital miejski";
            case 3: return "Centrum medyczne";
            default: return super.getName();
        }
    }
}