package zones;

/**
 * Enum reprezentujący typy stref w mieście
 */
public enum ZoneType {
    EMPTY('.', "Pusty teren", 0, 0),
    RESIDENTIAL('R', "Mieszkalna", 100, 5),
    COMMERCIAL('C', "Komercyjna", 150, 10),
    INDUSTRIAL('I', "Przemysłowa", 200, 20),
    PARK('P', "Park", 50, -5),
    SPECIAL('S', "Specjalna", 300, 0),
    ROAD('#', "Droga", 20, 0),
    WATER('~', "Woda", 0, 0),
    MOUNTAIN('^', "Góry", 0, 0);

    private final char symbol;
    private final String name;
    private final int buildCost;
    private final int monthlyIncome;

    ZoneType(char symbol, String name, int buildCost, int monthlyIncome) {
        this.symbol = symbol;
        this.name = name;
        this.buildCost = buildCost;
        this.monthlyIncome = monthlyIncome;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getBuildCost() {
        return buildCost;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public boolean isBuildable() {
        return this != WATER && this != MOUNTAIN;
    }

    public boolean isRevenuGenerating() {
        return monthlyIncome > 0;
    }

    /**
     * Zwraca typ strefy na podstawie symbolu
     */
    public static ZoneType fromSymbol(char symbol) {
        for (ZoneType type : values()) {
            if (type.symbol == symbol) {
                return type;
            }
        }
        return EMPTY;
    }
}