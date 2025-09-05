import java.time.LocalDateTime;

public class BankTransferTransaction extends Transaction {
    private final String receiverIban;

    public BankTransferTransaction(String transactionId, String userId, double amount,
                                   LocalDateTime timestamp,
                                   String receiverIban, String location) {
        super(transactionId, userId, amount, timestamp, location);
        this.receiverIban = receiverIban;
    }

    public String getReceiverIban() {
        return receiverIban;
    }
}