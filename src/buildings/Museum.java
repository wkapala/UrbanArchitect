package buildings;

/**
 * Budynek: Muzeum
 */
public class Museum extends AbstractBuilding {
    private static final long serialVersionUID = 1L;

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
        return switch (level) {
            case 1 -> "Muzeum lokalne";
            case 2 -> "Muzeum miejskie";
            case 3 -> "Muzeum narodowe";
            default -> super.getName();
        };
    }
}
