package utils;

import city.CityMap;

/**
 * Klasa pomocnicza do renderowania mapy
 */
public class MapRenderer {

    /**
     * Wyświetla legendę mapy
     */
    public static void displayLegend() {
        System.out.println("\n📋 LEGENDA:");
        System.out.println("  . - Pusty teren");
        System.out.println("  R - Strefa mieszkalna");
        System.out.println("  C - Strefa komercyjna");
        System.out.println("  I - Strefa przemysłowa");
        System.out.println("  P - Park");
        System.out.println("  S - Budynek specjalny");
        System.out.println("  # - Droga");
        System.out.println("  ~ - Woda");
        System.out.println("  ^ - Góry");
    }

    /**
     * Generuje pasek postępu
     */
    public static String generateProgressBar(double percentage) {
        int filled = (int)(percentage / 5); // 20 segmentów
        StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < 20; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }

        bar.append("] ");
        bar.append(String.format("%.1f%%", percentage));

        return bar.toString();
    }

    /**
     * Wyświetla nagłówki kolumn (dziesiątki i jedności)
     */
    public static void printColumnHeaders(int width) {
        // Dziesiątki
        System.out.print("    ");
        for (int i = 0; i < width; i++) {
            System.out.print(i / 10);
            if (i < width - 1) {
                System.out.print(" ");  // Spacja między cyframi
            }
        }
        System.out.println();

        // Jedności
        System.out.print("    ");
        for (int i = 0; i < width; i++) {
            System.out.print(i % 10);
            if (i < width - 1) {
                System.out.print(" ");  // Spacja między cyframi
            }
        }
        System.out.println();
    }

    /**
     * Główna metoda wyświetlająca mapę miasta
     */
    public static void display(CityMap cityMap) {
        int width = cityMap.getWidth();
        int height = cityMap.getHeight();

        printColumnHeaders(width);

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
                char symbol = cityMap.getSymbolAt(x, y);
                System.out.print(symbol);
                if (x < width - 1) {
                    System.out.print(" ");  // Dodaj spację między symbolami
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
}