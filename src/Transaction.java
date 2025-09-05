import java.time.LocalDateTime;

public class Transaction {
    private final String transactionId;
    private final String userId;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String location;


    public Transaction (String transactionId, String userId, double amount, LocalDateTime timestamp, String location ) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Transaction {" +
                "ID='" + transactionId + '\'' +
                ", UserID='" + userId + '\'' +
                ", Amount=" + amount +
                ", Timestamp=" + timestamp +
                ", Location='" + location + '\'' +
                '}';
    }
}
