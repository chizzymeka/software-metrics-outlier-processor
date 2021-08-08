package ucl.cdt.cybersecurity;

import classes.CSVRow;
import core.CSVFileLoader;
import core.IntersectionFinder;
import core.RicherSoftwareMetricsReportWriter;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;


public class App {

    private static ArrayList<Double> ageValuesList = new ArrayList<>();
    private static LinkedHashSet<CSVRow> codeChurnSet = new LinkedHashSet<>();
    private static LinkedHashSet<CSVRow> cyclomaticComplexitySet = new LinkedHashSet<>();
    private static LinkedHashSet<CSVRow> dependencySet = new LinkedHashSet<>();
    private static LinkedHashSet<CSVRow> linesOfCodeSet = new LinkedHashSet<>();
    private static LinkedHashMap<CSVRow, Integer> csvRowAndIntersectionValue = new LinkedHashMap<>();
    private static Percentile percentile = new Percentile();
    private static double pthPercentileValue;

    public static void main( String[] args ) throws IOException {
        CSVFileLoader csvFileLoader = new CSVFileLoader();
        csvFileLoader.loadCSVFiles();

        IntersectionFinder intersectionFinder = new IntersectionFinder();
        intersectionFinder.buildSetMap();
        intersectionFinder.getTotalNumberOfIntersections();
        intersectionFinder.updateConfidenceLevels();
        intersectionFinder.outputDataToFile();

        new RicherSoftwareMetricsReportWriter().writeRicherSoftwareMetricsCSVReport();
    }

    public static ArrayList<Double> getAgeValuesList() {
        return ageValuesList;
    }

    public static LinkedHashSet<CSVRow> getCodeChurnSet() {
        return codeChurnSet;
    }

    public static LinkedHashSet<CSVRow> getCyclomaticComplexitySet() {
        return cyclomaticComplexitySet;
    }

    public static LinkedHashSet<CSVRow> getDependencySet() {
        return dependencySet;
    }

    public static LinkedHashSet<CSVRow> getLinesOfCodeSet() {
        return linesOfCodeSet;
    }

    public static LinkedHashMap<CSVRow, Integer> getCsvRowAndIntersectionValue() {
        return csvRowAndIntersectionValue;
    }

    public static Percentile getPercentile() {
        return percentile;
    }

    public static double getPthPercentileValue() {
        return pthPercentileValue;
    }

    public static void setPthPercentileValue(double pthPercentileValue) {
        App.pthPercentileValue = pthPercentileValue;
    }
}
