// Java - How to find intersection of two or more collections?: https://www.logicbig.com/how-to/java-collections/intersection.html
// Generate Combinations in Java: https://www.baeldung.com/java-combinations-algorithm

package core;

import classes.CSVRow;
import org.apache.commons.math3.util.CombinatoricsUtils;
import ucl.cdt.cybersecurity.App;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class IntersectionFinder {

    LinkedHashSet<CSVRow> codeChurnSet = App.getCodeChurnSet();
    LinkedHashSet<CSVRow> cyclomaticComplexitySet = App.getCyclomaticComplexitySet();
    LinkedHashSet<CSVRow> dependencySet = App.getDependencySet();
    LinkedHashSet<CSVRow> linesOfCodeSet = App.getLinesOfCodeSet();
    LinkedHashMap<Integer, LinkedHashSet<CSVRow>> metricsMap = new LinkedHashMap<>();
    LinkedHashMap<CSVRow, Integer> csvRowAndIntersectionValue = App.getCsvRowAndIntersectionValue();

    public void buildSetMap() throws IOException {

        int numberOfMetricsSets = 4;
        LinkedHashSet<CSVRow> allPurposeSet = new LinkedHashSet<>();

        for (int i = 0; i < numberOfMetricsSets; i++) {
            switch (i) {
                case 0:
                    allPurposeSet = codeChurnSet;
                    break;
                case 1:
                    allPurposeSet = cyclomaticComplexitySet;
                    break;
                case 2:
                    allPurposeSet = dependencySet;
                    break;
                case 3:
                    allPurposeSet = linesOfCodeSet;
                    break;
                // Add another case if you add another metrics set.
            }
            metricsMap.put(i, allPurposeSet);
        }
        Files.write(Paths.get("metricsMap.txt"), () -> metricsMap.entrySet().stream().<CharSequence>map(e -> e.getKey() + " : " + e.getValue()).iterator());
        combinationsProcessor(numberOfMetricsSets);
    }

    void combinationsProcessor(int numberOfMetricsSets) {

        /*
            This code produces different combinations consisting of three ranges (from '2' to '4') that look like below:
            [0, 1]
            [0, 2]
            [1, 2]
            [0, 3]
            [1, 3]
            [2, 3]
            [0, 1, 2]
            [0, 1, 3]
            [0, 2, 3]
            [1, 2, 3]
            [0, 1, 2, 3]
         */

        for (int i = 2; i < numberOfMetricsSets + 1; i++) {
            Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(numberOfMetricsSets, i);
            while (iterator.hasNext()) {
                final int[] COMBINATION = iterator.next();
                System.out.println("------------------------------------------------------------");
                System.out.println("Processing combination: " + Arrays.toString(COMBINATION));
                System.out.println("------------------------------------------------------------");
//                Thread.sleep(60000);
                findIntersections(COMBINATION);
            }
        }
    }

    void findIntersections(int[] COMBINATION) {

        LinkedHashSet<LinkedHashSet<CSVRow>> setOfSets = new LinkedHashSet<>();

        // Use the combinations to select metrics sets to put into a global set.
        for (int value : COMBINATION) {
            setOfSets.add(metricsMap.get(value));
        }

        int counter = 0;
        boolean firstSet = true;
        LinkedHashSet<CSVRow> intersection = new LinkedHashSet<>();

        // Iterate the global set.
        for (LinkedHashSet<CSVRow> set : setOfSets) {

            counter++;

            System.out.println("Size of Set #" + counter + ": " + set.size());

            if (firstSet) {
                intersection.addAll(set);
                firstSet = false;
            } else {
                intersection.retainAll(set);
            }

            if (counter != 1) {

                System.out.println("Intersection Size: " + intersection.size());
                int intersectionValue = setOfSets.size();

                for (CSVRow csvRow : intersection) {
                    csvRowAndIntersectionValue.put(csvRow, intersectionValue);
                }
            }
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("\n");
    }

    public void updateConfidenceLevels() {

        int rowCounter = 0;

        for (Map.Entry<CSVRow, Integer> entry : csvRowAndIntersectionValue.entrySet()) {

            rowCounter++;
            CSVRow csvRow = entry.getKey();
            int intersectionValue = entry.getValue();

            if (rowCounter > 1) {
                double age = Double.parseDouble(csvRow.getAge());
                double fiftiethPercentileValue = App.getPthPercentileValue();
                // If the method's age is equal to or above the 80th percentile, then increment intersectionValue by '1';
                if (age >= fiftiethPercentileValue) {
                    intersectionValue++;
                    entry.setValue(intersectionValue);
                }
            }
        }
    }

    // TODO: Remove after testing.
    public void outputDataToFile() throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("csvRowAndIntersectionValue.txt"));

        for (Map.Entry<CSVRow, Integer> entry : csvRowAndIntersectionValue.entrySet()) {
            CSVRow csvRow = entry.getKey();
            int intersectionValue = entry.getValue();
            bufferedWriter.write(csvRow + " : " + intersectionValue + "\n");
        }

        bufferedWriter.close();
    }

    public void getTotalNumberOfIntersections () {
        System.out.println("Total Number of Intersections: " + csvRowAndIntersectionValue.size());
    }
}
