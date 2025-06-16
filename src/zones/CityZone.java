package zones;

import buildings.Building;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementacja strefy miejskiej
 */
public class CityZone implements Zone, Serializable {
    private static final long serialVersionUID = 1L;

    private ZoneType type;
    private Building building;
    private final int x;
    private final int y;
    private int developmentLevel;
    private int monthsActive;

    public CityZone(int x, int y) {
        this(x, y, ZoneType.EMPTY);
    }

    public CityZone(int x, int y, ZoneType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.building = null;
        this.developmentLevel = 0;
        this.monthsActive = 0;
    }

    @Override
    public ZoneType getType() {
        return type;
    }

    @Override
    public void setType(ZoneType type) {
        this.type = type;
        this.monthsActive = 0;
        this.developmentLevel = 0;
    }

    @Override
    public Building getBuilding() {
        return building;
    }

    @Override
    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public Map<String, Integer> calculateInfluence() {
        Map<String, Integer> influence = new HashMap<>();

        switch (type) {
            case RESIDENTIAL -> {
                influence.put("population", 10 + developmentLevel * 2);
                influence.put("happiness", 0);
            }
            case COMMERCIAL -> {
                influence.put("happiness", 5);
                influence.put("noise", 3);
                influence.put("jobs", 5 + developmentLevel);
            }
            case INDUSTRIAL -> {
                influence.put("happiness", -10);
                influence.put("pollution", 8);
                influence.put("noise", 5);
                influence.put("jobs", 15 + developmentLevel * 3);
            }
            case PARK -> {
                influence.put("happiness", 15);
                influence.put("pollution", -5);
                influence.put("noise", -3);
            }
            case SPECIAL -> {
                if (building != null) return building.getInfluence();
                influence.put("happiness", 10);
                influence.put("education", 10);
            }
            case ROAD -> {
                influence.put("accessibility", 10);
                influence.put("noise", 2);
            }
            default -> {
                // EMPTY, WATER, MOUNTAIN: brak wpływu
            }
        }

        return influence;
    }

    @Override
    public boolean canBuild() {
        return type.isBuildable() && building == null;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void update() {
        if (type != ZoneType.EMPTY && type != ZoneType.WATER && type != ZoneType.MOUNTAIN) {
            monthsActive++;
            if (monthsActive % 6 == 0 && developmentLevel < 5) {
                developmentLevel++;
            }
        }
        if (building != null) {
            building.update();
        }
    }

    public int getDevelopmentLevel() {
        return developmentLevel;
    }

    public int getMonthsActive() {
        return monthsActive;
    }

    public void clear() {
        this.type = ZoneType.EMPTY;
        this.building = null;
        this.developmentLevel = 0;
        this.monthsActive = 0;
    }

    /** Zwraca symbol graficzny do mapy */
    public char getSymbol() {
        return type.getSymbol();
    }
}
