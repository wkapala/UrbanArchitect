package zones;

import buildings.Building;
import java.util.Map;

/**
 * Interfejs reprezentujący strefę w mieście
 */
public interface Zone {
    /**
     * Zwraca typ strefy
     */
    ZoneType getType();

    /**
     * Ustawia typ strefy
     */
    void setType(ZoneType type);

    /**
     * Zwraca budynek w strefie (jeśli istnieje)
     */
    Building getBuilding();

    /**
     * Ustawia budynek w strefie
     */
    void setBuilding(Building building);

    /**
     * Oblicza wpływ strefy na otoczenie
     * @return mapa efektów (np. "happiness" -> wartość)
     */
    Map<String, Integer> calculateInfluence();

    /**
     * Sprawdza czy można zbudować w tej strefie
     */
    boolean canBuild();

    /**
     * Zwraca pozycję X strefy
     */
    int getX();

    /**
     * Zwraca pozycję Y strefy
     */
    int getY();

    /**
     * Aktualizuje stan strefy (wywołane co turę)
     */
    void update();
}