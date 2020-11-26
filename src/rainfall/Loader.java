package rainfall;

import textio.TextIO;

import java.io.File;
import java.time.Year;

/**
 * The Loader is responsible for converting raw BOM csv files
 * or analysed rainfall csv files into Station objects.
 *
 * @author Owen Herbert
 */
public class Loader {

    // indexes used in raw bom csv file
    private static final int IDX_BOM_YEAR = 2, IDX_BOM_MONTH = 3, IDX_BOM_RAINFALL = 5;

    // indexes used in analysed rainfall csv file
    private static final int IDX_ANALYSED_YEAR = 0, IDX_ANALYSED_MONTH = 1, IDX_ANALYSED_TOTAL = 2,
            IDX_ANALYSED_MIN = 3, IDX_ANALYSED_MAX = 4;

    /**
     * Loads the analysed rainfall csv file of a given station into a Station
     * object and returns it. If the analysed csv file doesn't exist, then its
     * raw counterpart is read and the analysed csv file is created.
     *
     * @param directoryName name of the directory
     * @param stationName name of the BOM station
     * @throws LoaderException if an error occurs
     * @return a station
     */
    public static Station load(String directoryName, String stationName) throws LoaderException {

        if (directoryName.length() < 1) {
            throw new LoaderException("directory name required!");
        } else if (stationName.length() < 1) {
            throw new LoaderException("station name required!");
        }

        String analysedFilePath = "./" + directoryName + "/" + stationName + "_analysed.csv";
        String rawFilePath = "./" + directoryName + "/" + stationName + ".csv";

        File analysedCSVFile = new File(analysedFilePath);
        File rawCSVFile = new File(rawFilePath);

        Station station = new Station(stationName); // station to load statistics into

        // throw an exception if neither analysed or raw csv files exist
        if (!analysedCSVFile.exists() && !rawCSVFile.exists()) {
            throw new LoaderException("file does not exist!");
        }

        // what file path to read from
        String filePath = analysedCSVFile.exists() ? analysedFilePath : rawFilePath;

        TextIO.readFile(filePath);

        boolean isHeaderRow = true; // if loop is encountering the header row

        while (!TextIO.eof()) {

            String rawRow = TextIO.getln();
            String[] rowColumns = rawRow.split(",", -1);

            // skip header row
            if (isHeaderRow) {
                isHeaderRow = false;
                continue;
            }

            // check year and month data
            int yearIndex = analysedCSVFile.exists() ? IDX_ANALYSED_YEAR : IDX_BOM_YEAR;
            int monthIndex = analysedCSVFile.exists() ? IDX_ANALYSED_MONTH : IDX_BOM_MONTH;

            try {

                int recordYear = Integer.parseInt(rowColumns[yearIndex]);
                int recordMonth = Integer.parseInt(rowColumns[monthIndex]);

                if (!isYearValid(recordYear)) {
                    throw new LoaderException("invalid value for record year!");
                } else if (!isMonthValid(recordMonth)) {
                    throw new LoaderException("invalid value for record month!");
                }

                // key to identify year distinct months
                String yearMonthKey = Record.makeKey(recordYear, recordMonth);

                // handle analysed and raw csv files differently
                if (analysedCSVFile.exists()) {

                    // convert columns to suitable types
                    double recordTotalRainfall = Double.parseDouble(rowColumns[IDX_ANALYSED_TOTAL]);
                    double recordMinRainfall = Double.parseDouble(rowColumns[IDX_ANALYSED_MIN]);
                    double recordMaxRainfall = Double.parseDouble(rowColumns[IDX_ANALYSED_MAX]);

                    // create new record object
                    Record record = new Record(recordYear, recordMonth, recordTotalRainfall,
                            recordMinRainfall, recordMaxRainfall);

                    station.put(yearMonthKey, record);
                } else {

                    // convert columns to suitable types
                    double recordRainfall = (rowColumns[IDX_BOM_RAINFALL].length() > 0) ?
                            Double.parseDouble(rowColumns[IDX_BOM_RAINFALL]) : 0;

                    if (station.containsKey(yearMonthKey)) {

                        Record existingRecord = station.get(yearMonthKey);
                        double totalRainfall = existingRecord.getRainfallTotal();

                        // check if the minimum rainfall statistic needs to be updated
                        if (recordRainfall < existingRecord.getRainfallMin()) {
                            existingRecord.setRainfallMin(recordRainfall);
                        }

                        // check if the maximum rainfall statistic needs to be updated
                        if (recordRainfall > existingRecord.getRainfallMax()) {
                            existingRecord.setRainfallMax(recordRainfall);
                        }

                        totalRainfall += recordRainfall;
                        existingRecord.setRainfallTotal(totalRainfall);

                    } else {

                        Record newRecord = new Record(recordYear, recordMonth, recordRainfall,
                                recordRainfall, recordRainfall);

                        station.put(yearMonthKey, newRecord);
                    }
                }
            } catch (NumberFormatException err) {
                throw new LoaderException("nonnumerical value encountered!");
            }
        }

        // check if station records are empty
        if (station.values().isEmpty()) {
            throw new LoaderException("no rainfall data found!");
        }

        // write analysed csv file
        if (!analysedCSVFile.exists() && !station.isEmpty()) {
            writeAnalysedCSVFile(station, analysedFilePath);
        }

        return station;
    }

    /**
     * Writes the provided station object to the specified file path.
     *
     * @param station the station
     * @param filePath the write location for the analysed file
     */
    private static void writeAnalysedCSVFile(Station station, String filePath) {

        // write records to analysed csv file
        TextIO.writeFile(filePath);
        TextIO.putln(Record.CSV_HEADER);
        for (Record record : station.values()) {
            TextIO.putln(record.getCSVString());
        }

        TextIO.writeStandardOutput();
    }

    /**
     * Checks if the year is valid, returns boolean.
     *
     * @param year the record year
     * @return if the year is valid
     */
    private static boolean isYearValid(int year) {
        return year > 0 && year <= Year.now().getValue();
    }

    /**
     * Checks if the month is valid, returns boolean.
     *
     * @param month the record month
     * @return if the month is valid
     */
    private static boolean isMonthValid(int month) {
        return month >= 1 && month <= 12;
    }

    /**
     * The LoaderException is a custom exception that is thrown when the
     * loader class cannot load a raw or analysed BOM csv file.
     */
    public static class LoaderException extends Exception {

        LoaderException(String message) {
            super(message);
        }
    }
}
