package budget;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Klasa reprezentująca pojedynczą transakcję
 */
public class Transaction {
    private final TransactionType type;
    private final String description;
    private final int amount;
    private final LocalDateTime timestamp;

    public Transaction(TransactionType type, String description, int amount) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
        String sign = type == TransactionType.INCOME ? "+" : "-";
        return String.format("[%s] %s%d zł - %s",
                timestamp.format(formatter),
                sign,
                amount,
                description);
    }
}