import java.util.Objects;
import java.util.Set;

public record FraudResult(Transaction transaction, Set<FraudReason> reasons) {
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FraudResult that = (FraudResult) o;
        return transaction.getTransactionId().equals(that.transaction.getTransactionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction.getTransactionId());
    }
}