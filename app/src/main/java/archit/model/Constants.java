package archit.model;

/**
 * Created by archit on 8/11/15.
 */
public enum Constants {
    HISTORY_LIST_TITLE("QR/Barcode Scan History"), DB_NAME("scanner"), HISTORY_TABLE_NAME("history");
    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
