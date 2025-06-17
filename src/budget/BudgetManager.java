package budget;

import zones.*;
import buildings.Building;
import city.CityMap;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa zarządzająca budżetem miasta
 */
public class BudgetManager {
    private int balance;
    private int monthlyIncome;
    private int monthlyExpenses;
    private List<Transaction> transactionHistory;
    private Map<String, Integer> incomeBreakdown;
    private Map<String, Integer> expenseBreakdown;

    // Stałe podatkowe
    private static final int RESIDENTIAL_TAX = 10;
    private static final int COMMERCIAL_TAX = 20;
    private static final int INDUSTRIAL_TAX = 30;

    public BudgetManager(int startingBudget) {
        this.balance = startingBudget;
        this.transactionHistory = new ArrayList<>();
        this.incomeBreakdown = new HashMap<>();
        this.expenseBreakdown = new HashMap<>();
    }

    /**
     * Oblicza miesięczny budżet
     */
    public void calculateMonthlyBudget(CityMap cityMap, int population) {
        monthlyIncome = 0;
        monthlyExpenses = 0;
        incomeBreakdown.clear();
        expenseBreakdown.clear();

        // Przychody z podatków
        int residentialIncome = cityMap.getZonesOfType(ZoneType.RESIDENTIAL).size() * RESIDENTIAL_TAX;
        int commercialIncome = cityMap.getZonesOfType(ZoneType.COMMERCIAL).size() * COMMERCIAL_TAX;
        int industrialIncome = cityMap.getZonesOfType(ZoneType.INDUSTRIAL).size() * INDUSTRIAL_TAX;

        incomeBreakdown.put("Podatki mieszkaniowe", residentialIncome);
        incomeBreakdown.put("Podatki handlowe", commercialIncome);
        incomeBreakdown.put("Podatki przemysłowe", industrialIncome);

        // Dodatkowe przychody z populacji
        int populationTax = population * 2;
        incomeBreakdown.put("Podatek od mieszkańców", populationTax);

        // Suma przychodów
        monthlyIncome = residentialIncome + commercialIncome + industrialIncome + populationTax;

        // Koszty utrzymania
        int roadMaintenance = cityMap.getZonesOfType(ZoneType.ROAD).size() * 5;
        int parkMaintenance = cityMap.getZonesOfType(ZoneType.PARK).size() * 10;

        expenseBreakdown.put("Utrzymanie dróg", roadMaintenance);
        expenseBreakdown.put("Utrzymanie parków", parkMaintenance);

        // Koszty budynków specjalnych
        int buildingMaintenance = 0;
        for (Building building : cityMap.getAllBuildings()) {
            buildingMaintenance += building.getMaintenanceCost();
        }
        expenseBreakdown.put("Utrzymanie budynków", buildingMaintenance);

        // Suma wydatków
        monthlyExpenses = roadMaintenance + parkMaintenance + buildingMaintenance;
    }

    /**
     * Przetwarza miesięczny budżet
     */
    public void processMonthlyBudget() {
        int netIncome = monthlyIncome - monthlyExpenses;
        balance += netIncome;

        addTransaction(
                netIncome >= 0 ? TransactionType.INCOME : TransactionType.EXPENSE,
                "Bilans miesięczny",
                Math.abs(netIncome)
        );
    }

    /**
     * Sprawdza czy można wydać pieniądze
     */
    public boolean canAfford(int amount) {
        return balance >= amount;
    }

    /**
     * Wydaje pieniądze
     */
    public boolean spend(int amount, String description) {
        if (canAfford(amount)) {
            balance -= amount;
            addTransaction(TransactionType.EXPENSE, description, amount);
            return true;
        }
        return false;
    }

    /**
     * Dodaje przychód
     */
    public void addIncome(int amount, String description) {
        balance += amount;
        addTransaction(TransactionType.INCOME, description, amount);
    }

    /**
     * Dodaje transakcję do historii
     */
    private void addTransaction(TransactionType type, String description, int amount) {
        transactionHistory.add(new Transaction(type, description, amount));

        // Ogranicz historię do ostatnich 50 transakcji
        if (transactionHistory.size() > 50) {
            transactionHistory.remove(0);
        }
    }

    /**
     * Generuje raport budżetowy
     */
    public String generateBudgetReport() {
        StringBuilder report = new StringBuilder();

        report.append("\n💰 RAPORT BUDŻETOWY\n");
        report.append("═══════════════════════════════\n");
        report.append(String.format("Saldo: %d zł %s\n", balance, getBalanceStatus()));
        report.append(String.format("Miesięczny przychód: +%d zł\n", monthlyIncome));
        report.append(String.format("Miesięczne wydatki: -%d zł\n", monthlyExpenses));
        report.append(String.format("Bilans: %s%d zł\n",
                (monthlyIncome - monthlyExpenses) >= 0 ? "+" : "",
                monthlyIncome - monthlyExpenses));

        if (!incomeBreakdown.isEmpty()) {
            report.append("\nPrzychody:\n");
            for (Map.Entry<String, Integer> entry : incomeBreakdown.entrySet()) {
                report.append(String.format("  • %s: %d zł\n", entry.getKey(), entry.getValue()));
            }
        }

        if (!expenseBreakdown.isEmpty()) {
            report.append("\nWydatki:\n");
            for (Map.Entry<String, Integer> entry : expenseBreakdown.entrySet()) {
                report.append(String.format("  • %s: %d zł\n", entry.getKey(), entry.getValue()));
            }
        }

        return report.toString();
    }

    /**
     * Zwraca status balansu
     */
    private String getBalanceStatus() {
        if (balance > 5000) return "💎 Świetnie!";
        if (balance > 3000) return "✅ Dobrze";
        if (balance > 1500) return "⚠️ Uwaga";
        if (balance > 500) return "🟡 Krytycznie";
        if (balance > 0) return "🔴 Niebezpiecznie";
        return "💀 Bankructwo!";
    }

    /**
     * Sprawdza czy budżet jest zrównoważony
     */
    public boolean isBalanced() {
        return monthlyIncome >= monthlyExpenses;
    }

    // Gettery
    public int getBalance() { return balance; }
    public int getMonthlyIncome() { return monthlyIncome; }
    public int getMonthlyExpenses() { return monthlyExpenses; }
    public List<Transaction> getRecentTransactions() {
        int start = Math.max(0, transactionHistory.size() - 10);
        return new ArrayList<>(transactionHistory.subList(start, transactionHistory.size()));
    }
}