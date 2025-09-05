import java.time.LocalDateTime;

public class CreditCardTransaction extends Transaction {
    private final String cardNumber;
    private final String cardType;

    public CreditCardTransaction(String transactionId, String userId, double amount,
                                 LocalDateTime timestamp,
                                 String cardNumber, String cardType, String location) {
        super(transactionId, userId, amount, timestamp, location);
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }
}