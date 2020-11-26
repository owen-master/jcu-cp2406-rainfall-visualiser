package rainfall;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * A Station contains analysed monthly rainfall data. {@link Loader#load} is used to
 * create a Station object given a dataset station name.
 *
 * @author Owen Herbert
 */
public class Station extends LinkedHashMap<String, Record> {

    private final String name; // station name

    /**
     * Sets the station name upon construction.
     *
     * @param name name of the station
     */
    public Station(String name) {
        this.name = name;
    }

    /**
     * Iterates through all records and returns an ArrayList of distinct years.
     *
     * @return distinct years
     */
    public ArrayList<Integer> getDistinctYears() {

        ArrayList<Integer> distinctYears = new ArrayList<>();

        for (Record record : this.values()) {
            int year = record.getYear();
            if (!distinctYears.contains(year)) {
                distinctYears.add(year);
            }
        }

        return distinctYears;
    }

    /**
     * Iterates through all records and returns the maximum total rainfall
     * found.
     *
     * @return maximum total rainfall
     */
    public double getRainfallMax() {

        double stationRainfallMax = -1; // rainfall max sentinel
        for (Record record : this.values()) {

            if (record.getRainfallTotal() > stationRainfallMax) {
                stationRainfallMax = record.getRainfallTotal();
            }
        }

        return stationRainfallMax;
    }

    /**
     * @return the name of the station
     */
    public String getName() {
        return name;
    }
}
