```markdown
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                                                                                                                          │
│    UI (JavaFX)                                                                                                                           |
│ ┌───────────────────┐                                                                                                                    │
│ │ • Duymeler        │                                                                                                                    │
│ │ • Status bildirisi│   ───▶ (Duymeye basildiqda) ───▶   📄DATA GIRISI     ───▶     🔍 ANALIZ    ───▶     📋HESABAT                   │
│ │ • Netice cedveli  │                                   ┌─────────────────┐     ┌─────────────────┐     ┌──────────────────┐             │
│ └───────────────────┘                                   │ • CSV oxuma     │     │ • Qayda tetbiqi │     │ • Sebeblerle     │             │
│     ▲                                                   │ • Obyekt yaratma│     │ • Sebeb teyini  │     │   birlikde fayla │             │
│     │ (Netice geri                                      │ • Validasiya    │     │ • Anomaly askar │     │   yazma          │             │
│     │  qaytarilir)                                      └─────────────────┘     └─────────────────┘     └──────────────────┘             │
│     └──────────────────────────────────────────────────────────────────────────────────────────────────────────────┤                     |
│                                                                                                                                          │
└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
```

# Firildaqciligin Askarlanmasi (Fraud Detection) Sistemi – Proyekt Senedi

Bu sened, firildaqci maliyye emeliyyatlarini askarlamaq ucun nezerde tutulmus proqram teminatinin son veziyyetini ve arxitekturasini tesvir edir.

## 1. Proyekt Haqqinda Umumi Melumat

**Esas Meqsed:**
Sistem, maliyye emeliyyatları haqqinda melumatlari CSV faylindan oxuyaraq, evvelceden teyin edilmis qaydalara esasen subheli emeliyyatlari mueyyen edir. Neticeler hem istifadeciye **qrafik interfeys (GUI)** vasitesile teqdim edilir, hem de detalli sekilde hesabat faylina yazilir.

**Sistemin Is Prinsipi:**
Proqram **hadiseye-yonelik (event-driven)** prinsipe esaslanir ve iki esas hisseden ibaretdir: Frontend (UI) ve Backend (Mentiq).

- **Istifadeci Interfeysi (Frontend):** Proqram ise dusdukde istifadecini JavaFX ile yaradilmis pencere qarsilayir.
- **Analizin Basladilmasi:** Istifadeci "Analize Basla" duymesine klikleyerek prosesi basladir.
- **Arxa Plan Emali (Backend):** Analiz prosesi istifadeci interfeysini "dondurmamaq" ucun arxa planda (`Task`) ise dusur. Bu proses zamani `DataReader` fayli oxuyur ve `FraudDetector` subheli emeliyyatlari ve onlarin sebeblerini mueyyen edir.
- **Neticenin Teqdim Edilmesi:** Analiz bitdikden sonra neticeler hem penceredeki cedvelde gosterilir, hem de `ReportGenerator` vasitesile `.txt` faylina yazilir.

**Giris (Input):**
`data/transactions.csv` adli fayl. Her setir bir emeliyyati temsil edir (`transactionId,userId,amount,timestamp,type,details,location`).

**Cixis (Output):**

1.  **Esas Cixis:** Proqram penceresindeki interaktiv cedvel.
2.  **Ikinci Cixis:** `reports/fraud_report.txt` fayli. Her subheli emeliyyatin detallarini ve subheli olma sebeblerini ehtiva edir.

## 2. Funksionalliq ve Ideyalar

### Artiq Tetbiq Edilmis Funksionalliqlar

- **Qrafik Istifadeci Interfeysi (GUI):** Konsol tetbiqi istifadeci ucun daha rahat olan JavaFX penceresi ile evez edilib.
- **Obyekt Yonumlu Irsiyyet (Inheritance):** Ferqli emeliyyat novleri ucun `Transaction` sinfinden toreyen `CreditCardTransaction` ve `BankTransferTransaction` sinifleri yaradilib.
- **Detalli Hesabat:** Hesabatda her bir subheli emeliyyatin niye subheli oldugu aydin sekilde gosterilir (`FraudReason` ve `FraudResult` istifade olunur).
- **Asinxron Emal (Multithreading):** Analiz prosesinin arxa planda islemesi temin edilerek proqramin istifadeye yararliligi artirilib.

### Gelecek Ucun Potensial Ideyalar

- **Dinamik Qayda Muherriki (Rule Engine):** Qaydalari kodun icinde saxlamaq evezine, ayrica konfiqurasiya faylindan (`rules.json`) oxumaq.
- **Risk Qiymetlendirmesi (Risk Scoring):** Emeliyyatlara "subheli/temiz" demek evezine, onlara risk bali (1-100) vermek ve cedvelde ferqli renglerle gostermek.
- **Istifadeci davranis Profili:** Her istifadeci ucun orta emeliyyat meblegi, en cox emeliyyat edilen mekan kimi profiller yaratmaq.
- **Melumatlarin Vizuallasdirilmasi:** Pencereye zaman ve mekana gore emeliyyatlarin sayini gosteren qrafikler (charts) elave etmek.

## 3. Proyektin Arxitekturasi

Proyekt **Frontend** ve **Backend** olmaqla iki mentiqi katmana bolunub.

### Backend (Mentiq Katmani)

Proqramin "beyni" rolunu oynayir.

- **Model Sinifleri:**
  - `Transaction.java`: Butun emeliyyatlar ucun baza sinif.
  - `CreditCardTransaction.java`, `BankTransferTransaction.java`: `Transaction`-dan toreyen ixtisaslasmis sinifler.
  - `FraudReason.java`: Subheli olma sebeblerini saxlayan `Enum` (tip-tehlukəsiz siyahı).
  - `FraudResult.java`: Bir emeliyyati onun subheli olma sebebleri ile birlikde saxlayan netice obyekti.
- **Servis Sinifleri:**
  - `DataReader.java`: `transactions.csv` faylindan melumatlari oxuyub `Transaction` obyektleri yaradir.
  - `FraudDetector.java`: Qaydalari tetbiq ederek `List<FraudResult>` qaytarir.
  - `ReportGenerator.java`: `List<FraudResult>` obyektini qebul edib detalli `.txt` hesabati yaradir.

### Frontend (Teqdimat Katmani - JavaFX)

Istifadeci ile vizual elaqeni temin edir.

- **Esas Tetbiq Sinifi:**
  - `FraudApp.java`: Penceresi, sehneni, duymeleri ve netice cedvelini yaradir ve idare edir. Istifadeci hereketlerine (events) reaksiya verir.
- **Komekci "View" Sinifi:**
  - `FraudResultView.java`: Backend-den gelen `FraudResult` obyektini JavaFX `TableView` cedvelinin anladigi formata cevirir.

## 4. Tetbiq Olunan Esas Konseptler

- **OOP (Obyekt Yonumlu Proqramlasdirma):** Mesuliyyetlerin siniflere bolunmesi, **Irsiyyet (Inheritance)** ve **Polimorfizm**.
- **Exception Handling:** Fayl emeliyyatları ve melumatlarin pars edilmesi zamani yaranan xetalari idare etmek.
- **Input/Output (I/O):** Fayllardan oxuma ve fayllara yazma.
- **Generics:** `List<?>`, `Map<,>` kimi tip-tehlukəsiz kolleksiyalarin istifadesi.
- **Collections Framework:** `List`, `Set`, `Map` kimi melumat strukturlarindan effektiv istifade.
- **JavaFX:** Muasir ve funksional qrafik istifadeci interfeysi yaratmaq.
- **Event-Driven Programming:** Istifadeci hereketlerine (duymeye basmaq) reaksiya veren kod yazmaq.
- **Concurrency (Multithreading):** Istifadeci interfeysini dondurmamaq ucun `javafx.concurrent.Task` ile arxa plan prosesleri yaratmaq.
- **Enums:** Deyismez ve tip-tehlukəsiz siyahilar (`FraudReason`)