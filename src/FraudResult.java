import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FraudResult {
    private final Transaction transaction;
    private final Set<FraudReason> reasons = new HashSet<>();

    public FraudResult(Transaction transaction) {
        this.transaction = transaction;
    }

    public void addReason(FraudReason reason) {
        this.reasons.add(reason);
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Set<FraudReason> getReasons() {
        return reasons;
    }

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