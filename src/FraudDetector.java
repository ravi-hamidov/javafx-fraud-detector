import java.time.Duration;
import java.util.*;

public class FraudDetector {
    private static final double HIGH_AMOUNT_THRESHOLD = 5000.0;
    private static final long SHORT_TIME_MINUTES = 60;

    public List<FraudResult> detectFraud(List<? extends Transaction> allTransactions) {

        // High amount tester Polis
        Map<String, FraudResult> fraudulentResultsMap = new HashMap<>();
        for (Transaction transaction : allTransactions) {
            if (transaction.getAmount() > HIGH_AMOUNT_THRESHOLD) {
                addFraudReasonToMap(fraudulentResultsMap, transaction, FraudReason.HIGH_AMOUNT);
            }
        }

        // Rapid location tester Polis
        Map<String, List<Transaction>> transactionsByUser = groupTransactionsByUser(allTransactions);
        for (List<Transaction> userTransactions : transactionsByUser.values()) {
            userTransactions.sort(Comparator.comparing(Transaction::getTimestamp));
            if (userTransactions.size() > 1) {
                for (int i = 0; i < userTransactions.size() - 1; i++) {
                    Transaction current = userTransactions.get(i);
                    Transaction next = userTransactions.get(i + 1);

                    long minutesBetween = Duration.between(current.getTimestamp(), next.getTimestamp()).toMinutes();
                    boolean isDifferentLocation = !current.getLocation().equals(next.getLocation());

                    if (minutesBetween < SHORT_TIME_MINUTES && isDifferentLocation) {
                        addFraudReasonToMap(fraudulentResultsMap, current, FraudReason.RAPID_LOCATION_CHANGE);
                        addFraudReasonToMap(fraudulentResultsMap, next, FraudReason.RAPID_LOCATION_CHANGE);
                    }
                }
            }
        }
        return new ArrayList<>(fraudulentResultsMap.values());
    }

    private Map<String, List<Transaction>> groupTransactionsByUser(List<? extends Transaction> transactions) {
        Map<String, List<Transaction>> map = new HashMap<>();
        for (Transaction t : transactions) {
            map.computeIfAbsent(t.getUserId(), k -> new ArrayList<>()).add(t);
        }
        return map;
    }

    private void addFraudReasonToMap(Map<String, FraudResult> map, Transaction transaction, FraudReason reason) {
        FraudResult currentResult = map.get(transaction.getTransactionId());
        Set<FraudReason> newReasons;

        if (currentResult != null) {
            newReasons = new HashSet<>(currentResult.reasons());
        } else {
            newReasons = new HashSet<>();
        }
        newReasons.add(reason);

        FraudResult updatedResult = new FraudResult(transaction, newReasons);
        map.put(transaction.getTransactionId(), updatedResult);
    }
}