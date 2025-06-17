package city;

import zones.*;
import buildings.*;
import events.*;
import budget.*;
import reputation.*;
import utils.InputUtils;
import utils.MapRenderer;
import java.util.*;

/**
 * Główny serwis zarządzający grą
 */
public class GameService {
    private GameState gameState;
    private CityMap cityMap;
    private CityStats cityStats;
    private BudgetManager budgetManager;
    private ReputationManager reputationManager;
    private EventManager eventManager;

    private List<String> pendingPromises;
    private List<String> goals;
    private boolean gameRunning;

    // Parametry gry
    private int maxMonths;
    private static final Map<Integer, String> SCENARIO_NAMES = Map.of(
            1, "Miasto nad rzeką",
            2, "Teren górzysty",
            3, "Teren poprzemysłowy",
            4, "Sandbox"
    );

    // Konstruktor dla nowej gry
    public GameService(String cityName, int scenario) {
        this.gameState = new GameState(cityName, scenario);
        initializeGame();
    }

    // Konstruktor dla wczytanej gry
    public GameService(GameState loadedState) {
        this.gameState = loadedState;
        initializeFromState();
    }

    private void initializeGame() {
        // Inicjalizacja mapy
        this.cityMap = new CityMap(20, 15);
        cityMap.generateScenario(gameState.getScenario());
        gameState.setCityMap(cityMap);

        // Inicjalizacja systemów
        this.cityStats = new CityStats();
        this.budgetManager = new BudgetManager(gameState.getBudget());
        this.reputationManager = new ReputationManager();
        this.eventManager = new EventManager();

        this.pendingPromises = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.gameRunning = true;

        this.maxMonths = (gameState.getScenario() == 4) ? 999 : 24;
    }

    private void initializeFromState() {
        this.cityMap = gameState.getCityMap();
        this.cityStats = new CityStats();
        this.budgetManager = new BudgetManager(gameState.getBudget());
        this.reputationManager = new ReputationManager();
        // Odtwórz reputację
        this.reputationManager.modifyReputation(gameState.getReputation() - 50);

        this.eventManager = new EventManager();
        this.pendingPromises = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.gameRunning = true;

        this.maxMonths = (gameState.getScenario() == 4) ? 999 : 24;
    }

    /**
     * Główna pętla gry
     */
    public void startGame() {
        displayWelcome();

        while (gameRunning && gameState.getCurrentMonth() <= maxMonths) {
            displayHeader();

            // Sprawdź warunki zwycięstwa/przegranej
            if (checkVictoryConditions()) {
                endGame(true);
                break;
            }

            // Wydarzenia losowe
            if (gameState.getCurrentMonth() > 2 && Math.random() < 0.3) {
                handleRandomEvent();
            }

            // Tura gracza
            playerTurn();

            // Koniec miesiąca
            if (gameRunning) {
                processEndOfMonth();
                gameState.setCurrentMonth(gameState.getCurrentMonth() + 1);
            }
        }

        if (gameRunning && gameState.getCurrentMonth() > maxMonths) {
            endGame(false);
        }
    }

    private void displayWelcome() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║          WITAJ W URBAN ARCHITECT          ║");
        System.out.println("╚═══════════════════════════════════════════╝");
        System.out.println("\nMiasto: " + gameState.getCityName());
        System.out.println("Scenariusz: " + SCENARIO_NAMES.get(gameState.getScenario()));
        System.out.println("Cel: Stwórz stabilne miasto w " + maxMonths + " miesięcy!");
        InputUtils.waitForEnter();
    }

    private void displayHeader() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.printf("║  %s - Miesiąc %d/%d  ║\n",
                gameState.getCityName(),
                gameState.getCurrentMonth(),
                maxMonths);
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }

    private void playerTurn() {
        boolean turnActive = true;

        while (turnActive) {
            displayMainMenu();
            int choice = InputUtils.getInt("Wybierz akcję: ", 1, 9);

            switch (choice) {
                case 1:
                    planZone();
                    break;
                case 2:
                    buildSpecialBuilding();
                    break;
                case 3:
                    viewMap();
                    break;
                case 4:
                    viewReports();
                    break;
                case 5:
                    viewBudget();
                    break;
                case 6:
                    consultCitizens();
                    break;
                case 7:
                    saveGame();
                    break;
                case 8:
                    turnActive = false;
                    break;
                case 9:
                    if (confirmExit()) {
                        gameRunning = false;
                        turnActive = false;
                    }
                    break;
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n=== MENU GŁÓWNE ===");
        System.out.println("1. 🏗️  Planuj strefę");
        System.out.println("2. 🏛️  Buduj budynek specjalny");
        System.out.println("3. 🗺️  Zobacz mapę");
        System.out.println("4. 📊 Zobacz raporty");
        System.out.println("5. 💰 Budżet");
        System.out.println("6. 👥 Konsultacje społeczne");
        System.out.println("7. 💾 Zapisz grę");
        System.out.println("8. ⏭️  Zakończ miesiąc");
        System.out.println("9. ❌ Wyjście");
    }

    private void planZone() {
        boolean continuePlanning = true;

        while (continuePlanning) {
            MapRenderer.display(cityMap);

            // Pokazuj aktualny stan budżetu
            System.out.println("\n💰 Budżet: " + budgetManager.getBalance() + " zł");

            System.out.println("\n=== PLANOWANIE STREFY ===");
            System.out.println("⚠️ Uwaga: Budynki (oprócz parków i dróg) muszą być przy drodze!");
            System.out.println("\nDostępne strefy:");
            System.out.println("1. [R] Mieszkalna (100 zł)");
            System.out.println("2. [C] Komercyjna (150 zł)");
            System.out.println("3. [I] Przemysłowa (200 zł)");
            System.out.println("4. [P] Park (50 zł)");
            System.out.println("5. [#] Droga (20 zł)");
            System.out.println("6. ❌ Wyjdź z planowania");

            int choice = InputUtils.getInt("Wybierz typ: ", 1, 6);

            if (choice == 6) {
                continuePlanning = false;
                break;
            }

            ZoneType selectedType = null;
            switch (choice) {
                case 1: selectedType = ZoneType.RESIDENTIAL; break;
                case 2: selectedType = ZoneType.COMMERCIAL; break;
                case 3: selectedType = ZoneType.INDUSTRIAL; break;
                case 4: selectedType = ZoneType.PARK; break;
                case 5: selectedType = ZoneType.ROAD; break;
            }

            // Pokazuj mapę z współrzędnymi jeszcze raz przed wyborem
            System.out.println("\nWybierz lokalizację na mapie:");

            int x = InputUtils.getInt("Współrzędna X (0-" + (cityMap.getWidth()-1) + "): ",
                    0, cityMap.getWidth() - 1);
            int y = InputUtils.getInt("Współrzędna Y (0-" + (cityMap.getHeight()-1) + "): ",
                    0, cityMap.getHeight() - 1);

            // Sprawdź środki
            if (!budgetManager.canAfford(selectedType.getBuildCost())) {
                System.out.println("❌ Brak środków! Potrzebujesz " + selectedType.getBuildCost() + " zł");

                // Zapytaj czy kontynuować mimo braku środków
                if (!InputUtils.getYesNo("Czy chcesz kontynuować planowanie?")) {
                    continuePlanning = false;
                }
                continue;
            }

            // Sprawdź wymóg drogi
            if (selectedType != ZoneType.ROAD && selectedType != ZoneType.PARK) {
                if (!cityMap.isNextToRoad(x, y)) {
                    System.out.println("❌ Ta strefa wymaga dostępu do drogi!");
                    System.out.println("❌  Zbuduj najpierw drogę obok tej lokalizacji.");
                    showNearbyRoads(x, y);

                    if (!InputUtils.getYesNo("Czy chcesz spróbować w innym miejscu?")) {
                        continuePlanning = false;
                    }
                    continue;
                }
            }

            // Próbuj zbudować
            if (cityMap.buildZone(x, y, selectedType)) {
                budgetManager.spend(selectedType.getBuildCost(),
                        "Budowa strefy: " + selectedType.getName());
                System.out.println("✅ Strefa " + selectedType.getName() + " wybudowana!");
                System.out.println("💰 Pozostały budżet: " + budgetManager.getBalance() + " zł");

                // Dodatkowa informacja dla dróg
                if (selectedType == ZoneType.ROAD) {
                    System.out.println("💡 Teraz możesz budować inne strefy przy tej drodze.");
                }

                // Zapytaj czy kontynuować
                if (!InputUtils.getYesNo("\nCzy chcesz zbudować coś jeszcze?")) {
                    continuePlanning = false;
                }
            } else {
                // Sprawdź dlaczego nie można zbudować
                CityZone zone = cityMap.getZone(x, y);
                if (zone == null) {
                    System.out.println("Nieprawidłowa pozycja!");
                } else if (!zone.getType().isBuildable()) {
                    System.out.println("Nie można budować na terenie: " + zone.getType().getName());
                } else if (zone.getType() != ZoneType.EMPTY) {
                    System.out.println("To miejsce jest już zabudowane!");
                }

                if (!InputUtils.getYesNo("Czy chcesz spróbować w innym miejscu?")) {
                    continuePlanning = false;
                }
            }
        }

        System.out.println("\n✓ Zakończono planowanie");
        InputUtils.waitForEnter();
    }

    // Pomocnicza metoda pokazująca gdzie są drogi
    private void showNearbyRoads(int x, int y) {
        System.out.println("\nNajbliższe drogi:");
        boolean foundRoad = false;

        // Sprawdź obszar 3x3 wokół
        for (int dy = -3; dy <= 3; dy++) {
            for (int dx = -3; dx <= 3; dx++) {
                int nx = x + dx;
                int ny = y + dy;

                if (cityMap.isValidPosition(nx, ny)) {
                    CityZone zone = cityMap.getZone(nx, ny);
                    if (zone != null && zone.getType() == ZoneType.ROAD) {
                        System.out.println("  • Droga na pozycji [" + nx + "," + ny + "]");
                        foundRoad = true;
                    }
                }
            }
        }

        if (!foundRoad) {
            System.out.println("❌  Brak dróg w pobliżu - musisz najpierw zbudować drogę!");
        }
    }

    private void buildSpecialBuilding() {
        System.out.println("\n=== BUDYNKI SPECJALNE ===");
        System.out.println("1. 📚 Szkoła (500 zł, utrzymanie: 50 zł/mies)");
        System.out.println("2. 🏥 Szpital (800 zł, utrzymanie: 100 zł/mies)");
        System.out.println("3. 🏛️ Muzeum (600 zł, utrzymanie: 60 zł/mies)");
        System.out.println("4. Anuluj");

        int choice = InputUtils.getInt("Wybierz budynek: ", 1, 4);
        if (choice == 4) return;

        Building building = null;
        switch (choice) {
            case 1: building = new School(); break;
            case 2: building = new Hospital(); break;
            case 3: building = new Museum(); break;
        }

        MapRenderer.display(cityMap);
        int x = InputUtils.getInt("Podaj współrzędną X: ", 0, cityMap.getWidth() - 1);
        int y = InputUtils.getInt("Podaj współrzędną Y: ", 0, cityMap.getHeight() - 1);

        if (budgetManager.canAfford(building.getBuildCost())) {
            if (cityMap.buildSpecialBuilding(x, y, building)) {
                budgetManager.spend(building.getBuildCost(),
                        "Budowa: " + building.getName());
                System.out.println("✓ " + building.getName() + " wybudowany!");
            } else {
                System.out.println("❌ Nie można zbudować w tym miejscu!");
            }
        } else {
            System.out.println("❌ Brak środków!");
        }

        InputUtils.waitForEnter();
    }

    private void viewMap() {
        MapRenderer.display(cityMap);
        MapRenderer.displayLegend();
        InputUtils.waitForEnter();
    }

    private void viewReports() {
        cityStats.calculateStats(cityMap);
        System.out.println(cityStats.generateReport());
        System.out.println(reputationManager.generateReport());

        if (!goals.isEmpty()) {
            System.out.println("\n📋 CELE:");
            for (String goal : goals) {
                System.out.println("• " + goal);
            }
        }

        if (!pendingPromises.isEmpty()) {
            System.out.println("\n⚠️ OCZEKUJĄCE OBIETNICE:");
            for (String promise : pendingPromises) {
                System.out.println("• " + promise);
            }
        }

        InputUtils.waitForEnter();
    }

    private void viewBudget() {
        System.out.println(budgetManager.generateBudgetReport());

        System.out.println("\n📜 Ostatnie transakcje:");
        for (Transaction t : budgetManager.getRecentTransactions()) {
            System.out.println(t);
        }

        InputUtils.waitForEnter();
    }

    private void consultCitizens() {
        System.out.println("\n👥 KONSULTACJE SPOŁECZNE");
        System.out.println("Koszt: 300 zł, Bonus: +5 reputacji");

        if (InputUtils.getYesNo("Przeprowadzić konsultacje?")) {
            if (budgetManager.spend(300, "Konsultacje społeczne")) {
                reputationManager.modifyReputation(5);
                System.out.println("✓ Konsultacje przeprowadzone! Mieszkańcy doceniają dialog.");
            } else {
                System.out.println("❌ Brak środków na konsultacje!");
            }
        }

        InputUtils.waitForEnter();
    }

    private void handleRandomEvent() {
        Event event = eventManager.getRandomEvent(this);
        if (event != null) {
            System.out.println("\n⚡ WYDARZENIE: " + event.getName() + " ⚡");
            System.out.println(event.getDescription());

            String[] options = event.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }

            int choice = InputUtils.getInt("Twój wybór: ", 1, options.length) - 1;
            String result = event.handleChoice(choice, this);
            System.out.println("\n" + result);

            InputUtils.waitForEnter();
        }
    }

    private void processEndOfMonth() {
        System.out.println("\n=== KONIEC MIESIĄCA " + gameState.getCurrentMonth() + " ===");

        // Aktualizuj strefy
        cityMap.updateAllZones();

        // Oblicz statystyki
        cityStats.calculateStats(cityMap);

        // Przetwórz budżet
        budgetManager.calculateMonthlyBudget(cityMap, cityStats.getPopulation());
        budgetManager.processMonthlyBudget();

        // Sprawdź obietnice
        checkPromises();

        // Aktualizuj stan gry
        gameState.setBudget(budgetManager.getBalance());
        gameState.setReputation(reputationManager.getReputation());

        System.out.println("\nPodsumowanie miesiąca:");
        System.out.println("• Nowe saldo: " + budgetManager.getBalance() + " zł");
        System.out.println("• Zadowolenie: " + cityStats.getHappiness() + "%");
        System.out.println("• Populacja: " + cityStats.getPopulation());

        InputUtils.waitForEnter();
    }

    private void checkPromises() {
        if (!pendingPromises.isEmpty()) {
            Iterator<String> it = pendingPromises.iterator();
            while (it.hasNext()) {
                String promise = it.next();
                if (promise.equals("park") && cityMap.getZonesOfType(ZoneType.PARK).size() > 0) {
                    reputationManager.modifyReputation(10);
                    System.out.println("✓ Dotrzymałeś obietnicy budowy parku! +10 reputacji");
                    it.remove();
                }
            }

            // Kara za niedotrzymane obietnice
            if (!pendingPromises.isEmpty() && gameState.getCurrentMonth() % 6 == 0) {
                reputationManager.modifyReputation(-5);
                System.out.println("⚠️ Mieszkańcy pamiętają o niedotrzymanych obietnicach! -5 reputacji");
            }
        }
    }

    private boolean checkVictoryConditions() {
        if (gameState.getScenario() == 4) return false; // Sandbox nie ma warunków wygranej

        cityStats.calculateStats(cityMap);

        boolean happinessOk = cityStats.getHappiness() > 75;
        boolean budgetOk = budgetManager.isBalanced();
        boolean developmentOk = cityMap.getDevelopmentPercentage() >= 40;

        if (happinessOk && budgetOk && developmentOk) {
            return true;
        }

        // Sprawdź warunki przegranej
        if (budgetManager.getBalance() < -5000) {
            System.out.println("\n💀 BANKRUCTWO! Miasto jest zbyt zadłużone!");
            endGame(false);
            return true;
        }

        if (cityStats.getHappiness() < 20) {
            System.out.println("\n😡 BUNT! Mieszkańcy są zbyt niezadowoleni!");
            endGame(false);
            return true;
        }

        return false;
    }

    private void endGame(boolean victory) {
        gameRunning = false;

        System.out.println("\n\n════════════════════════════════════════");
        if (victory) {
            System.out.println("🎉 GRATULACJE! ZWYCIĘSTWO! 🎉");
            System.out.println("Stworzyłeś stabilne i dobrze prosperujące miasto!");
        } else {
            System.out.println("😢 KONIEC GRY 😢");
            System.out.println("Nie udało się spełnić celów w wyznaczonym czasie.");
        }
        System.out.println("════════════════════════════════════════");

        // Oblicz wynik
        int score = calculateScore(victory);
        System.out.println("\n📊 TWÓJ WYNIK: " + score + " punktów");

        // Zapisz wynik
        String playerName = InputUtils.getString("Podaj swoje imię: ", 1, 20);
        ScoreManager scoreManager = new ScoreManager();
        scoreManager.addScore(new ScoreEntry(
                playerName,
                gameState.getCityName(),
                score,
                gameState.getScenario(),
                victory
        ));

        // Usuń zapis gry
        GameState.deleteSaveFile();

        InputUtils.waitForEnter();
    }

    /**
    * Obliczanie punktów do rankingu
    */

    private int calculateScore(boolean victory) {
        int score = 0;

        //  za zwycięstwo
        if (victory) {
            score += 1000;
            // Bonus za szybkość
            score += (maxMonths - gameState.getCurrentMonth()) * 50;
        }

        // za rozwój
        score += (int)(cityMap.getDevelopmentPercentage() * 10);

        // za szczęście
        score += cityStats.getHappiness() * 5;

        // za budżet
        score += Math.min(budgetManager.getBalance() / 10, 500);

        // za reputację
        score += reputationManager.getReputation() * 10;

        // trudnosc
        switch (gameState.getScenario()) {
            case 2: score = (int)(score * 1.5); break;
            case 3: score = (int)(score * 2.0); break;
        }

        return Math.max(0, score);
    }

    private void saveGame() {
        System.out.println("\n💾 Zapisywanie gry...");
        if (gameState.saveToFile()) {
            System.out.println("Gra zapisana pomyślnie!");
        } else {
            System.out.println("❌ Błąd zapisu gry!");
        }
        InputUtils.waitForEnter();
    }

    private boolean confirmExit() {
        return InputUtils.getYesNo("\nCzy na pewno chcesz wyjść? Niezapisany postęp zostanie utracony!");
    }

    // Metody pomocnicze dla wydarzeń
    public void addCommercialZone(boolean large) {
        // Znajdź wolne miejsce i dodaj strefę komercyjną
        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {
                CityZone zone = cityMap.getZone(x, y);
                if (zone != null && zone.canBuild()) {
                    cityMap.buildZone(x, y, ZoneType.COMMERCIAL);
                    if (large) {
                        // Dodaj więcej stref obok
                        if (x + 1 < cityMap.getWidth()) {
                            cityMap.buildZone(x + 1, y, ZoneType.COMMERCIAL);
                        }
                    }
                    return;
                }
            }
        }
    }

    public void addPendingPromise(String promise) {
        pendingPromises.add(promise);
    }

    public void addGoal(String goal) {
        goals.add(goal);
    }

    // Gettery dla wydarzeń
    public CityMap getCityMap() { return cityMap; }
    public CityStats getCityStats() { return cityStats; }
    public BudgetManager getBudgetManager() { return budgetManager; }
    public ReputationManager getReputationManager() { return reputationManager; }
}