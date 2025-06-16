package buildings;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstrakcyjna klasa bazowa dla budynków
 */
public abstract class AbstractBuilding implements Building {
    protected String name;
    protected BuildingType type;
    protected int level;
    protected int buildCost;
    protected int maintenanceCost;
    protected int influenceRadius;
    protected Map<String, Integer> baseInfluence;

    public AbstractBuilding(String name, BuildingType type, int buildCost, int maintenanceCost, int influenceRadius) {
        this.name = name;
        this.type = type;
        this.level = 1;
        this.buildCost = buildCost;
        this.maintenanceCost = maintenanceCost;
        this.influenceRadius = influenceRadius;
        this.baseInfluence = new HashMap<>();
        initializeInfluence();
    }

    /**
     * Inicjalizuje bazowe wartości wpływu - do nadpisania w klasach pochodnych
     */
    protected abstract void initializeInfluence();

    @Override
    public String getName() {
        return name + " (Poziom " + level + ")";
    }

    @Override
    public int getBuildCost() {
        return buildCost;
    }

    @Override
    public int getMaintenanceCost() {
        return maintenanceCost * level;
    }

    @Override
    public Map<String, Integer> getInfluence() {
        Map<String, Integer> influence = new HashMap<>(baseInfluence);

        // Wpływ rośnie z poziomem
        for (Map.Entry<String, Integer> entry : influence.entrySet()) {
            entry.setValue((int)(entry.getValue() * (1 + (level - 1) * 0.5)));
        }

        return influence;
    }

    @Override
    public int getInfluenceRadius() {
        return influenceRadius + (level - 1);
    }

    @Override
    public BuildingType getType() {
        return type;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public boolean upgrade() {
        if (level < 3) {
            level++;
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        // Domyślnie budynki nie wymagają aktualizacji
        // Może być nadpisane w klasach pochodnych
    }
}