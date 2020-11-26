package rainfall;

/**
 * A Record contains the analysed rainfall data specific to a single month in a
 * specific year that comes from a particular rainfall station.
 *
 * @author Owen Herbert
 */
public class Record {

    private final int month; // month of the record
    private final int year; // year of the record
    private double rainfallMin; // minimum daily rainfall amount in the record
    private double rainfallMax; // maximum daily rainfall amount in the record
    private double rainfallTotal; // total cumulative rainfall amount in the record
    public static final String CSV_HEADER = "year,month,total,min,max"; // header row for analysed csv files

    /**
     * Sets the records year and month upon construction. The
     * year and month should not be changed after construction.
     *
     * @param year record year
     * @param month record month
     * @param rainfallTotal record total rainfall
     * @param rainfallMin record minimum rainfall
     * @param rainfallMax record maximum rainfall
     */
    public Record(int year, int month, double rainfallTotal, double rainfallMin, double rainfallMax) {

        this.year = year;
        this.month = month;
        this.rainfallTotal = rainfallTotal;
        this.rainfallMin = rainfallMin;
        this.rainfallMax = rainfallMax;
    }

    /**
     * Returns the provided year and month in the
     * format of a key.
     *
     * @param year year value of the key
     * @param month month value of the key
     */
    public static String makeKey(int year, int month) {
        return String.format("_Y:%d_M:%d", year, month);
    }

    /**
     * Returns the record as a string in CSV format.
     *
     * @return record in CSV format
     */
    public String getCSVString() {

        return String.format("%d,%d,%1.2f,%1.2f,%1.2f", year, month, rainfallTotal,
                rainfallMin, rainfallMax);
    }

    /**
     * Returns the month as a human friendly string with the first letter
     * capitalised.
     *
     * @return human friendly month
     */
    public String getHumanMonth() {
        return switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "Unknown";
        };
    }

    /**
     * @return the month of the record
     */
    public int getMonth() {
        return month;
    }

    /**
     * @return the year of the record
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the minimum rainfall of the record
     */
    public double getRainfallMin() {
        return rainfallMin;
    }

    /**
     * @param rainfallMin minimum rainfall to set
     */
    public Record setRainfallMin(double rainfallMin) {
        this.rainfallMin = rainfallMin;
        return this;
    }

    /**
     * @return the maximum rainfall of the record
     */
    public double getRainfallMax() {
        return rainfallMax;
    }

    /**
     * @param rainfallMax maximum rainfall to set
     */
    public Record setRainfallMax(double rainfallMax) {
        this.rainfallMax = rainfallMax;
        return this;
    }

    /**
     * @return the total rainfall of the record
     */
    public double getRainfallTotal() {
        return rainfallTotal;
    }

    /**
     * @param rainfallTotal total rainfall to set
     */
    public Record setRainfallTotal(double rainfallTotal) {
        this.rainfallTotal = rainfallTotal;
        return this;
    }
}
