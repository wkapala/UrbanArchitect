package budget;

/**
 * Enum reprezentujący typ transakcji
 */
public enum TransactionType {
    INCOME("Przychód", "+", "🟢"),
    EXPENSE("Wydatek", "-", "🔴");

    private final String name;
    private final String sign;
    private final String icon;

    TransactionType(String name, String sign, String icon) {
        this.name = name;
        this.sign = sign;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    public String getIcon() {
        return icon;
    }
}