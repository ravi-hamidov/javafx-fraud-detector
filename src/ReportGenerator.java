import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ReportGenerator {
    public void generateReport(List<FraudResult> fraudulentResults, String outputPath) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String reportTime = LocalDateTime.now().format(formatter);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {
            writer.write("========================================\n");
            writer.write("       FIRILDAQCILIQ HESABATI \n");
            writer.write("========================================\n");
            writer.write("Hesabat Tarixi: " + reportTime + "\n");
            writer.write("Askarlanan Subheli Emeliyyatlarin Sayi: " + fraudulentResults.size() + "\n");
            writer.write("----------------------------------------\n\n");

            if (fraudulentResults.isEmpty()) {
                writer.write("Hec bir subheli emeliyyat askarlanmadi.\n");
            } else {
                int count = 1;
                for (FraudResult result : fraudulentResults) {

                    Transaction t = result.transaction();
                    String reasons = result.reasons().stream()
                            .map(FraudReason::getDescription)
                            .collect(Collectors.joining(", "));

                    writer.write("Emeliyyat #" + count++ + ":\n");
                    writer.write("  - Transaction ID: " + t.getTransactionId() + "\n");
                    writer.write("  - User ID: " + t.getUserId() + "\n");
                    writer.write("  - Nov: " + t.getClass().getSimpleName() + "\n");
                    writer.write("  - Mebleg: " + String.format("%.2f", t.getAmount()) + " AZN\n");
                    writer.write("  - Zaman: " + t.getTimestamp().format(formatter) + "\n");
                    writer.write("  - Mekan: " + t.getLocation() + "\n");
                    writer.write("  - SUBHELI OLMA SEBEBI: " + reasons + "\n\n");
                }
            }

            writer.write("========================================\n");
            writer.write("           HESABATIN SONU\n");
            writer.write("========================================\n");

            System.out.println("Hesabat ugurla yaradildi: " + outputPath);

        } catch (IOException e) {
            System.err.println("XETA: Hesabat faylini yazarken problem yarandi: " + e.getMessage());
        }
    }
}