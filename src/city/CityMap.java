package city;

import zones.*;
import buildings.Building;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa reprezentująca mapę miasta
 */
public class CityMap {
    private final int width;
    private final int height;
    private final CityZone[][] grid;
    private int developedTiles;
    private int buildableTiles;

    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new CityZone[height][width];
        initializeMap();
    }

    /**
     * Inicjalizuje pustą mapę
     */
    private void initializeMap() {
        buildableTiles = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new CityZone(x, y, ZoneType.EMPTY);
                buildableTiles++;
            }
        }
        developedTiles = 0;
    }

    /**
     * Generuje mapę według scenariusza
     */
    public void generateScenario(int scenario) {
        switch (scenario) {
            case 1: // Miasto nad rzeką
                generateRiverCity();
                break;
            case 2: // Teren górzysty
                generateMountainCity();
                break;
            case 3: // Teren poprzemysłowy
                generateIndustrialCity();
                break;
            default: // Sandbox - pusta mapa
                break;
        }
        calculateBuildableTiles();
    }

    private void generateRiverCity() {
        // Rzeka przez środek miasta
        int riverY = height / 2;
        for (int x = 0; x < width; x++) {
            for (int dy = -1; dy <= 1; dy++) {
                int y = riverY + dy + (int) (Math.sin(x * 0.5) * 2);
                if (isValidPosition(x, y)) {
                    grid[y][x].setType(ZoneType.WATER);
                }
            }
        }

        // Mosty
        for (int x = width / 4; x < width; x += width / 4) {
            for (int dy = -1; dy <= 1; dy++) {
                int y = riverY + dy + (int) (Math.sin(x * 0.5) * 2);
                if (isValidPosition(x, y)) {
                    grid[y][x].setType(ZoneType.ROAD);  // Zastępuje wodę drogą
                }
            }
        }
    }

    private void generateMountainCity() {
        // Góry w rogach
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double distance = Math.min(
                        Math.sqrt(x * x + y * y),
                        Math.sqrt((width - x) * (width - x) + (height - y) * (height - y))
                );
                if (distance < 5) {
                    grid[y][x].setType(ZoneType.MOUNTAIN);
                }
            }
        }
    }

    private void generateIndustrialCity() {
        // Stare fabryki
        for (int i = 0; i < 5; i++) {
            int x = 2 + (i * 3);
            int y = 2;
            if (isValidPosition(x, y)) {
                grid[y][x].setType(ZoneType.INDUSTRIAL);
                grid[y][x].update(); // Stare budynki
                grid[y][x].update();
                grid[y][x].update();
            }
        }

        // Drogi
        for (int x = 0; x < width; x++) {
            if (isValidPosition(x, 3)) {
                grid[3][x].setType(ZoneType.ROAD);
            }
        }
    }

    private void calculateBuildableTiles() {
        buildableTiles = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x].getType().isBuildable()) {
                    buildableTiles++;
                }
            }
        }
    }

    /**
     * Buduje strefę na pozycji
     */
    public boolean buildZone(int x, int y, ZoneType type) {
        if (!isValidPosition(x, y) || !grid[y][x].canBuild()) {
            return false;
        }

        CityZone zone = grid[y][x];
        if (zone.getType() == ZoneType.EMPTY) {
            zone.setType(type);
            developedTiles++;
            return true;
        }
        return false;
    }

    /**
     * Buduje budynek specjalny
     */
    public boolean buildSpecialBuilding(int x, int y, Building building) {
        if (!isValidPosition(x, y) || !grid[y][x].canBuild()) {
            return false;
        }

        grid[y][x].setType(ZoneType.SPECIAL);
        grid[y][x].setBuilding(building);
        developedTiles++;
        return true;
    }

    /**
     * Zwraca strefę na pozycji
     */
    public CityZone getZone(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[y][x];
        }
        return null;
    }

    /**
     * Sprawdza czy pozycja jest prawidłowa
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Oblicza całkowity wpływ na pozycji
     */
    public Map<String, Integer> calculateTotalInfluence(int x, int y) {
        Map<String, Integer> totalInfluence = new HashMap<>();

        // Sprawdź wpływ wszystkich stref w promieniu
        for (int dy = -5; dy <= 5; dy++) {
            for (int dx = -5; dx <= 5; dx++) {
                int nx = x + dx;
                int ny = y + dy;

                if (isValidPosition(nx, ny)) {
                    CityZone zone = grid[ny][nx];
                    int distance = Math.abs(dx) + Math.abs(dy);

                    // Wpływ strefy
                    Map<String, Integer> zoneInfluence = zone.calculateInfluence();

                    // Wpływ budynku specjalnego
                    if (zone.getBuilding() != null) {
                        Building building = zone.getBuilding();
                        if (distance <= building.getInfluenceRadius()) {
                            Map<String, Integer> buildingInfluence = building.getInfluence();
                            for (Map.Entry<String, Integer> entry : buildingInfluence.entrySet()) {
                                zoneInfluence.merge(entry.getKey(), entry.getValue(), Integer::sum);
                            }
                        }
                    }

                    // Dodaj wpływ z uwzględnieniem odległości
                    for (Map.Entry<String, Integer> entry : zoneInfluence.entrySet()) {
                        int value = entry.getValue();
                        if (distance > 0) {
                            value = value / (distance + 1);
                        }
                        totalInfluence.merge(entry.getKey(), value, Integer::sum);
                    }
                }
            }
        }

        return totalInfluence;
    }

    /**
     * Aktualizuje wszystkie strefy
     */
    public void updateAllZones() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x].update();
            }
        }
    }

    /**
     * Zwraca procent zagospodarowania
     */
    public double getDevelopmentPercentage() {
        if (buildableTiles == 0) return 0;
        return (double) developedTiles / buildableTiles * 100;
    }

    /**
     * Zwraca symbol na danej pozycji
     */
    public char getSymbolAt(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[y][x].getType().getSymbol();
        }
        return '.';
    }

    /**
     * Wyświetla mapę
     */
    public void display() {
        // Nagłówek z numerami kolumn
        System.out.print("\n    ");
        for (int x = 0; x < width; x++) {
            System.out.print(x % 10 + " ");
        }
        System.out.println();

        // Górna ramka
        System.out.print("   ╔");
        for (int i = 0; i < width * 2 - 1; i++) {
            System.out.print("═");
        }
        System.out.println("╗");

        // Wiersze mapy
        for (int y = 0; y < height; y++) {
            System.out.printf("%2d ║", y);
            for (int x = 0; x < width; x++) {
                System.out.print(grid[y][x].getType().getSymbol());
                if (x < width - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println("║");
        }

        // Dolna ramka
        System.out.print("   ╚");
        for (int i = 0; i < width * 2 - 1; i++) {
            System.out.print("═");
        }
        System.out.println("╝");
    }

    /**
     * Zwraca listę wszystkich stref danego typu
     */
    public List<CityZone> getZonesOfType(ZoneType type) {
        List<CityZone> zones = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x].getType() == type) {
                    zones.add(grid[y][x]);
                }
            }
        }
        return zones;
    }

    /**
     * Zwraca listę wszystkich budynków
     */
    public List<Building> getAllBuildings() {
        List<Building> buildings = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Building building = grid[y][x].getBuilding();
                if (building != null) {
                    buildings.add(building);
                }
            }
        }
        return buildings;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}