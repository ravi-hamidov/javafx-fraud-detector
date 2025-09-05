```Markdown

  📄DATA GİRİŞİ    ───▶    🔍 ANALIZ      ───▶    ⚠️ QIYMƏT      ───▶    📋HESABAT
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│ • CSV oxuma     │     │ • Qayda tətbiqi │     │ • Risk hesablama│     │ • Fayl yazma    │
│ • Validasiya    │     │ • Pattern analiz│     │ • Səviyyə təyin │     │ • Alert göndər  │
│ • Parse etmə    │     │ • Anomaly aşkar │     │ • Təsnifat      │     │ • Dashboard     │
└─────────────────┘     └─────────────────┘     └─────────────────┘     └─────────────────┘
```

# Firildaqchiliqin Ashkarlanmasi (Fraud Detection) Sistemi – Proyekt Plani

Bu sened, firildaqchi maliyye emeliyyatlarinin ashkar etmek uchun nezerde tutulmush proqram teminatinin planini tesvir edir.

## 1. Proyekt Haqqinda Umumi Melumat

**Esas Meqsed:**
Sistem, mueyyen bir fayldan (meselen, CSV) emeliyyat melumatlarini oxuyaraq, evvelceden teyin edilmis qaydalara esasen shubheli ve ya firildaqchi emeliyyatlari mueyyen edecek ve neticeleri ayrica hesabat faylina yazacaq.

**Sistemin Ish Prinsipi:**

- **Melumatin Oxunmasi:** Maliyyye emeliyyatlarinin siyahisini ehtiva eden fayli oxuyur.
- **Analiz:** Her bir emeliyyati mueyyen qaydalar toplusu ile yoxlayir.
- **Ashkarlama:** Qaydalara uygun gelmeyen ve ya shubheli gorunen emeliyyatlari "firildaqchi" kimi ishareleyir.
- **Hesabat:** Ashkar edilen butun shubheli emeliyyatlari hesabat faylina yazir.

**Girish (Input):**
`transactions.csv` adli fayl. Her setir bir emeliyyati temsil edir (meselen: `transaction_id,user_id,amount,timestamp,location`).

**Chixish (Output):**
`fraud_report.txt` ve ya `fraud_transactions.csv` adli fayl. Ashkar edilmis firildaqchi emeliyyatlarin detallarin ehtiva edir.

## 2. Kreativ Ideyalar ve Elave Funksionalliq

- **Dinamik Qayda Muherriki (Rule Engine):**
  Qaydalari kodun ichinde saxlamaq evvezine, ayrica konfiqurasiya faylindan (`rules.json`) oxumaq. Bu, proqrami yeniden ishledmeden yeni qaydalar elave etmeye imkan verir.

  - Numune: "Bir istifadechi 5 deqiqe erzinde 3-den chox emeliyyat ederse, bu shubhelidir."
  - Numune: "Emeliyyat meblegi istifadechinin orta emeliyyat mebleginden 5 defe choxdursa, bu shubhelidir."

- **Risk Qiymetlendirmesi (Risk Scoring):**
  Emeliyyatlari yalniz "firildaqchi" ve ya "temiz" kimi isharelemek evvezine, onlara risk bali (meselen, 1-den 100-e qeder) vermek.

- **Istifadechi Davranish Profili:**
  Her bir istifadechi uchun emeliyyat tarixchesini saxlamaq (`Map<String, UserProfile>`).
  Meselen: adeten Bakidan emeliyyat eden istifadechinin qefilden Braziliyadan emeliyyat etmesi shubhelidir.

- **Cografi Melumat Analizi:**
  Qisa muddet erzinde bir-birinden chox uzaq iki ferqli yerdən edilen emeliyyatlari ashkarlamaq.

## 3. Kodun Yazilma Planlamasi (Addim-addim)

**Addim 1: Proyekt Strukturunun Qurulmasi**

- `src` qovlugu: Butun Java `.java` fayllari burada saxlanilacaq.
- `data` qovlugu: Girish melumatlari uchun (meselen, `transactions.csv`).
- `reports` qovlugu: Netice hesabatlari uchun.

**Addim 2: Model (Data) Obyektlerinin Yaradilmasi (OOP)**

- `Transaction.java`: Bir emeliyyatin melumatlarini saxlayan sinif (`transactionId`, `userId`, `amount`, `timestamp`, `location`).
- `User.java` (isteye bagli): Istifadechi melumatlarini saxlayan sinif.

**Addim 3: Melumatlarin Oxunmasi (I/O ve Exception Handling)**

- `DataReader.java` sinfi.
- `readTransactions(String filePath)` metodu → `List<Transaction>` qaytaracaq.
- `FileNotFoundException` ve `IOException` uchun `try-catch` bloklari olacaq.

**Addim 4: Firildaqchiliq Ashkarlama Muherriki (Collections ve Generics)**

- `FraudDetector.java` sinfi.
- `detectFraud(List<Transaction> transactions)` metodu.
- Qaydalar:
  - Yuksək mebleg.
  - Tez-tez tekrarlanan emeliyyatlar (`Map<String, List<Transaction>>` istifade edilerek).
  - Ferqli mekanlar.
- Metod neticede shubheli emeliyyatlari `List<Transaction>` kimi qaytaracaq.

**Addim 5: Hesabatin Yaradilmasi (I/O)**

- `ReportGenerator.java` sinfi.
- `generateReport(List<Transaction> fraudulentTransactions, String outputPath)` metodu.
- Ashkar edilmis emeliyyatlari `.txt` faylina yazacaq.

**Addim 6: Esas Proqram (Main Class)**

- `Main.java` sinfi.
- Butun addimlari ardicil chagirmaq.

## 4. Teleb Olunan Movzularin Tetbiqi

- **OOP:** Mesuliyyetlerin `DataReader`, `FraudDetector`, `ReportGenerator` siniflerine bolunmesi.
- **Exception Handling:** Fayl emeliyyatlarinda xetalari idare etmek.
- **Input/Output (I/O):** Fayllardan oxuma ve fayllara yazma.
- **Generics:** `List<Transaction>`, `Map<String, User>` kimi tip-tehlukesiz kolleksiyalarin istifadesi.
- **Collections Framework:**
  - `List`: Emeliyyatlarin saxlanilmasi.
  - `Set`: Unikal istifadechilerin tapilmasi.
  - `Map`: Emeliyyatlarin istifadechiye gore qruplashdirilmasi.
