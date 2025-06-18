package main;

import city.GameState;
import city.GameService;
import reputation.ScoreManager;
import utils.InputUtils;

/**
 * Główna klasa
 */
public class UrbanArchitect {

    public static void main(String[] args) {
        UrbanArchitect game = new UrbanArchitect();
        game.showMainMenu();
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║      URBAN ARCHITECT 2025 🏙️       ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.println("\n1. 🆕 Nowa gra");
            System.out.println("2. 💾 Wczytaj grę");
            System.out.println("3. 🏆 Ranking");
            System.out.println("4. 📖 Instrukcja");
            System.out.println("5. ❌ Wyjście");

            int choice = InputUtils.getInt("\nWybierz opcję: ", 1, 5);

            switch (choice) {
                case 1:
                    startNewGame();
                    break;
                case 2:
                    loadGame();
                    break;
                case 3:
                    showRanking();
                    break;
                case 4:
                    showInstructions();
                    break;
                case 5:
                    System.out.println("\nDziękujemy za grę! Do zobaczenia!");
                    return;
            }
        }
    }

    private void startNewGame() {
        System.out.println("\n=== WYBÓR SCENARIUSZA ===");
        System.out.println("1. 🌊 Miasto nad rzeką");
        System.out.println("2. ⛰️ Teren górzysty");
        System.out.println("3. 🏭 Były teren przemysłowy");
        System.out.println("4. 🎮 Tryb sandbox (Bez ograniczeń)");

        int scenario = InputUtils.getInt("Wybierz scenariusz: ", 1, 4);

        String playerName = InputUtils.getString("\nPodaj nazwę miasta: ", 3, 20);

        GameService gameService = new GameService(playerName, scenario);
        gameService.startGame();
    }

    private void loadGame() {
        System.out.println("\n=== WCZYTYWANIE GRY ===");

        GameState loadedState = GameState.loadFromFile();
        if (loadedState != null) {
            System.out.println("✓ Wczytano grę: " + loadedState.getCityName());
            GameService gameService = new GameService(loadedState);
            gameService.startGame();
        } else {
            System.out.println("❌ Nie znaleziono zapisanej gry!");
            InputUtils.waitForEnter();
        }
    }

    private void showRanking() {
        ScoreManager scoreManager = new ScoreManager();
        scoreManager.displayRanking();
        InputUtils.waitForEnter();
    }

    private void showInstructions() {
        System.out.println("\n╔═══════════════════════════════════════════════════╗");
        System.out.println("║                   INSTRUKCJA GRY                  ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");

        System.out.println("\n🎯 CEL GRY:");
        System.out.println("Stwórz stabilne miasto spełniające następujące warunki:");
        System.out.println("• Zadowolenie mieszkańców > 75%");
        System.out.println("• Zrównoważony budżet (przychody ≥ wydatki)");
        System.out.println("• Zagospodarowanie ≥ 40% terenu");

        System.out.println("\n TYPY STREF:");
        System.out.println("• [R] Mieszkalne - domy dla mieszkańców");
        System.out.println("• [C] Komercyjne - sklepy, biura");
        System.out.println("• [I] Przemysłowe - fabryki, magazyny");
        System.out.println("• [P] Parki - tereny zielone");
        System.out.println("• [S] Specjalne - szkoły, szpitale, kultura");

        System.out.println("\n MECHANIKI:");
        System.out.println("• Każdy budynek wpływa na otoczenie");
        System.out.println("• Mieszkańcy reagują na zmiany");
        System.out.println("• Wydarzenia losowe mogą pomóc lub zaszkodzić");
        System.out.println("• Konsultacje społeczne zwiększają akceptację");

        System.out.println("\n💡 WSKAZÓWKI:");
        System.out.println("• Zachowaj balans między różnymi strefami");
        System.out.println("• Parki zwiększają zadowolenie w pobliżu");
        System.out.println("• Przemysł generuje dochody, ale obniża zadowolenie");
        System.out.println("• Infrastruktura społeczna jest kosztowna, ale potrzebna do zadowolenia społeczności");

        InputUtils.waitForEnter();
    }
}