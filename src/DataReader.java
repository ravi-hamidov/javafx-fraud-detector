import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<Transaction> readTransaction(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) {
                    continue;
                }
                try {
                    String transactionId = data[0];
                    String userId = data[1];
                    double amount = Double.parseDouble(data[2]);
                    LocalDateTime timestamp = LocalDateTime.parse(data[3], formatter);
                    String location = data[4];
                    String type = data[5].trim().toUpperCase();
                    Transaction transaction = null;

                    switch (type) {
                        case "CREDIT_CARD":
                            if (data.length > 6) {
                                String[] cardDetails = data[6].split("-");
                                transaction = new CreditCardTransaction(transactionId, userId, amount, timestamp, location, cardDetails[0], cardDetails[1]);
                            }
                            break;
                        case "BANK_TRANSFER":
                            if (data.length > 6) {
                                String iban = data[6];
                                transaction = new BankTransferTransaction(transactionId, userId, amount, timestamp, location, iban);
                            }
                            break;
                        default:
                            transaction = new Transaction(transactionId, userId, amount, timestamp, location);
                            break;
                    }

                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                } catch (Exception e) {
                    System.err.println("XETA: Setrin formati yanlishdir: " + line + " -> " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("XETA: Fayl tapilmadi: " + filePath);
        } catch (IOException e) {
            System.err.println("XETA: Faylı oxuyarken problem yarandi: " + e.getMessage());
        }

        return transactions;
    }
}
