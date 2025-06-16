package buildings;

/**
 * Budynek: Muzeum
 */
public class Museum extends AbstractBuilding {

    public Museum() {
        super("Muzeum lokalne", BuildingType.MUSEUM, 600, 60, 5);
    }

    @Override
    protected void initializeInfluence() {
        baseInfluence.put("culture", 30);
        baseInfluence.put("happiness", 20);
        baseInfluence.put("education", 15);
        baseInfluence.put("tourism", 10);
    }

    @Override
    public String getName() {
        switch (level) {
            case 1: return "Muzeum lokalne";
            case 2: return "Muzeum miejskie";
            case 3: return "Muzeum narodowe";
            default: return super.getName();
        }
    }
}