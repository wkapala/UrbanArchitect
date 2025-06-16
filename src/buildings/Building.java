package buildings;

import java.util.Map;
import java.io.Serializable;

/**
 * Interfejs dla budynków specjalnych
 */
public interface Building extends Serializable {

    /**
     * Zwraca nazwę budynku
     */
    String getName();

    /**
     * Zwraca koszt budowy
     */
    int getBuildCost();

    /**
     * Zwraca miesięczny koszt utrzymania
     */
    int getMaintenanceCost();

    /**
     * Zwraca wpływ budynku na otoczenie
     */
    Map<String, Integer> getInfluence();

    /**
     * Zwraca zasięg wpływu (w kratkach)
     */
    int getInfluenceRadius();

    /**
     * Aktualizacja stanu budynku (co turę)
     */
    void update();

    /**
     * Zwraca typ budynku
     */
    BuildingType getType();

    /**
     * Zwraca poziom budynku (1-3)
     */
    int getLevel();

    /**
     * Ulepsza budynek (jeśli możliwe)
     */
    boolean upgrade();
}