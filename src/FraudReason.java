public enum FraudReason {
    HIGH_AMOUNT("Yuksek Mebleg"),
    RAPID_LOCATION_CHANGE("Suretli Mekan Deyishikliyi");

    private final String description;

    FraudReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}