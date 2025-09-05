import java.util.List;

public class Main {

    public static void main(String[] args) {

        String inputFile = "data/transactions.csv";
        String outputFile = "reports/fraud_report.txt";

        System.out.println("Proses basladilir...");

        DataReader dataReader = new DataReader();
        List<Transaction> allTransactions = dataReader.readTransaction(inputFile);

        if (allTransactions.isEmpty()) {
            System.err.println("Hec bir emeliyyat oxuna bilmedi. Proqram dayandirilir.");
            return;
        }
        System.out.println(allTransactions.size() + " eded emeliyyat ugurla oxundu.");

        FraudDetector fraudDetector = new FraudDetector();
        List<FraudResult> fraudulentResults = fraudDetector.detectFraud(allTransactions);
        System.out.println("Analiz bitdi. " + fraudulentResults.size() + " eded subheli emeliyyat askarlandi.");

        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateReport(fraudulentResults, outputFile);

        System.out.println("\nProses ugurla basa catdi!");
    }
}
