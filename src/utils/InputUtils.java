package utils;

import java.util.Scanner;

/**
 * Klasa pomocnicza do obsługi wejścia użytkownika
 */
public class InputUtils {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Pobiera liczbę całkowitą z zakresu
     */
    public static int getInt(String prompt, int min, int max) {
        int value;

        while (true) {
            System.out.print(prompt);

            try {
                value = scanner.nextInt();
                scanner.nextLine(); // Czyszczenie bufora

                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("❌ Podaj liczbę z zakresu " + min + " - " + max);
                }
            } catch (Exception e) {
                System.out.println("❌ Nieprawidłowa wartość! Podaj liczbę.");
                scanner.nextLine(); // Czyszczenie bufora
            }
        }
    }

    /**
     * Pobiera string z ograniczeniem długości
     */
    public static String getString(String prompt, int minLength, int maxLength) {
        String value;

        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();

            if (value.length() >= minLength && value.length() <= maxLength) {
                return value;
            } else {
                System.out.println("❌ Tekst musi mieć od " + minLength +
                        " do " + maxLength + " znaków.");
            }
        }
    }

    /**
     * Pobiera odpowiedź tak/nie
     */
    public static boolean getYesNo(String prompt) {
        String response;

        while (true) {
            System.out.print(prompt + " (t/n): ");
            response = scanner.nextLine().toLowerCase().trim();

            if (response.equals("t") || response.equals("tak")) {
                return true;
            } else if (response.equals("n") || response.equals("nie")) {
                return false;
            } else {
                System.out.println("❌ Wpisz 't' (tak) lub 'n' (nie).");
            }
        }
    }

    /**
     * Czeka na Enter
     */
    public static void waitForEnter() {
        System.out.print("\n[Naciśnij Enter aby kontynuować...]");
        scanner.nextLine();
    }

    /**
     * Czyści ekran (próbuje różne metody)
     */
    public static void clearScreen() {
        // Dla konsoli IntelliJ IDEA używamy pustych linii
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }

        // Alternatywnie dla prawdziwej konsoli:
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Ignoruj błędy
        }
    }
}