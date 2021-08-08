// Reading a CSV file in Java using OpenCSV: https://www.geeksforgeeks.org/reading-csv-file-java-using-opencsv/

package core;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import classes.CSVRow;
import ucl.cdt.cybersecurity.App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class CSVFileLoader {

    ArrayList<Double> ageValuesList = App.getAgeValuesList();
    LinkedHashSet<CSVRow> codeChurnSet = App.getCodeChurnSet();
    LinkedHashSet<CSVRow> cyclomaticComplexitySet = App.getCyclomaticComplexitySet();
    LinkedHashSet<CSVRow> dependencySet = App.getDependencySet();
    LinkedHashSet<CSVRow> linesOfCodeSet = App.getLinesOfCodeSet();

    public void loadCSVFiles() throws IOException {

        // Age
        BufferedReader ageCsvFileBufferedReader = new BufferedReader(new FileReader("src/main/java/software_metrics_report/software_metrics_report_76_version(s).csv"));
        CSVReader ageCsvFileBufferedReaderObject = new CSVReader(ageCsvFileBufferedReader);

        // Code Churn
        BufferedReader codeChurnOutliersCsvFileBufferedReader = new BufferedReader(new FileReader("src/main/java/outlier_reports/code_churn_outliers.csv"));
        //CSVReader codeChurnOutliersCsvFileBufferedReaderObject = new CSVReader(codeChurnOutliersCsvFileBufferedReader);
        CSVReader codeChurnOutliersCsvFileBufferedReaderObject = new CSVReader(codeChurnOutliersCsvFileBufferedReader, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, '\0', 0, CSVParser.DEFAULT_STRICT_QUOTES);

        // Cyclomatic Complexity
        BufferedReader cyclomaticComplexityOutliersCsvFileBufferedReader = new BufferedReader(new FileReader("src/main/java/outlier_reports/cyclomatic_complexity_outliers.csv"));
        //CSVReader cyclomaticComplexityOutliersCsvFileBufferedReaderObject = new CSVReader(cyclomaticComplexityOutliersCsvFileBufferedReader);
        CSVReader cyclomaticComplexityOutliersCsvFileBufferedReaderObject = new CSVReader(cyclomaticComplexityOutliersCsvFileBufferedReader, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, '\0', 0, CSVParser.DEFAULT_STRICT_QUOTES);

        // Dependency
        BufferedReader dependencyOutliersCsvFileBufferedReader = new BufferedReader(new FileReader("src/main/java/outlier_reports/dependency_outliers.csv"));
        //CSVReader dependencyOutliersCsvFileBufferedReaderObject = new CSVReader(dependencyOutliersCsvFileBufferedReader);
        CSVReader dependencyOutliersCsvFileBufferedReaderObject = new CSVReader(dependencyOutliersCsvFileBufferedReader, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, '\0', 0, CSVParser.DEFAULT_STRICT_QUOTES);

        // Lines of Code
        BufferedReader linesOfCodeOutliersCsvFileBufferedReader = new BufferedReader(new FileReader("src/main/java/outlier_reports/lines_of_code_outliers.csv"));
        //CSVReader linesOfCodeOutliersCsvFileBufferedReaderObject = new CSVReader(linesOfCodeOutliersCsvFileBufferedReader);
        CSVReader linesOfCodeOutliersCsvFileBufferedReaderObject = new CSVReader(linesOfCodeOutliersCsvFileBufferedReader, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, '\0', 0, CSVParser.DEFAULT_STRICT_QUOTES);

        String[] ageValuesNextRecord;
        String[] codeChurnNextRecord;
        String[] cyclomaticComplexityNextRecord;
        String[] dependencyNextRecord;
        String[] linesOfCodeNextRecord;

        int ageRowCounter = 0;
        while ((ageValuesNextRecord = ageCsvFileBufferedReaderObject.readNext()) != null) {
            ageRowCounter++;
            if (ageRowCounter > 1) {
                for (int i = 0; i < ageValuesNextRecord.length; i++) {
                    if ((i == 3)) {
                        ageValuesList.add(Double.parseDouble(ageValuesNextRecord[i].trim()));
                    }
                }
            }
        }

        // Set up percentile.
        new AgePercentileProcessor().setPercentileData(ageValuesList);
        AgePercentileProcessor agePercentileProcessorObject = new AgePercentileProcessor();
        double fiftiethPercentileValue = agePercentileProcessorObject.getPthPercentileValue(50);
        App.setPthPercentileValue(fiftiethPercentileValue);

        int codeChurnRowCounter = 0;
        while ((codeChurnNextRecord = codeChurnOutliersCsvFileBufferedReaderObject.readNext()) != null) {
            codeChurnRowCounter++;
            if (codeChurnRowCounter > 1) {

                CSVRow csvRow = new CSVRow();

                for (int i = 0; i < codeChurnNextRecord.length; i++) {
                    switch (i) {
                        case 0:
                            csvRow.setMethodSignature(codeChurnNextRecord[i]);
                            break;
                        case 1:
                            csvRow.setClassName(codeChurnNextRecord[i]);
                            break;
                        case 2:
                            csvRow.setConfidenceLevel(codeChurnNextRecord[i]);
                            break;
                        case 3:
                            csvRow.setAge(codeChurnNextRecord[i]);
                            break;
                        case 4:
                            csvRow.setCodeChurn(codeChurnNextRecord[i]);
                            break;
                        case 5:
                            csvRow.setCyclomaticComplexity(codeChurnNextRecord[i]);
                            break;
                        case 6:
                            csvRow.setDependency(codeChurnNextRecord[i]);
                            break;
                        case 7:
                            csvRow.setLinesOfCode(codeChurnNextRecord[i]);
                            break;
                        case 8:
                            csvRow.setVersion(codeChurnNextRecord[i]);
                            break;
                        case 9:
                            csvRow.setSourceFilepath(codeChurnNextRecord[i]);
                            break;
                        case 10:
                            csvRow.setLine(codeChurnNextRecord[i]);
                            break;
                    }
                }
                codeChurnSet.add(csvRow);
            }
        }

        int cyclomaticComplexityRowCounter = 0;
        while ((cyclomaticComplexityNextRecord = cyclomaticComplexityOutliersCsvFileBufferedReaderObject.readNext()) != null) {
            cyclomaticComplexityRowCounter++;
            if (cyclomaticComplexityRowCounter > 1) {

                CSVRow csvRow = new CSVRow();

                for (int i = 0; i < cyclomaticComplexityNextRecord.length; i++) {
                    switch (i) {
                        case 0:
                            csvRow.setMethodSignature(cyclomaticComplexityNextRecord[i]);
                            break;
                        case 1:
                            csvRow.setClassName(cyclomaticComplexityNextRecord[i]);
                            break;
                        case 2:
                            csvRow.setConfidenceLevel(cyclomaticComplexityNextRecord[i]);
                            break;
                        case 3:
                            csvRow.setAge(cyclomaticComplexityNextRecord[i]);
                            break;
                        case 4:
                            csvRow.setCodeChurn(cyclomaticComplexityNextRecord[i]);
                            break;
                        case 5:
                            csvRow.setCyclomaticComplexity(cyclomaticComplexityNextRecord[i]);
                            break;
                        case 6:
                            csvRow.setDependency(cyclomaticComplexityNextRecord[i]);
                            break;
                        case 7:
                            csvRow.setLinesOfCode(cyclomaticComplexityNextRecord[i]);
                            break;
                        case 8:
                            csvRow.setVersion(cyclomaticComplexityNextRecord[i]);
                            break;
                        case 9:
                            csvRow.setSourceFilepath(cyclomaticComplexityNextRecord[i]);
                            break;
                        case 10:
                            csvRow.setLine(cyclomaticComplexityNextRecord[i]);
                            break;
                    }
                }
                cyclomaticComplexitySet.add(csvRow);
            }
        }

        int dependencyRowCounter = 0;
        while ((dependencyNextRecord = dependencyOutliersCsvFileBufferedReaderObject.readNext()) != null) {
            dependencyRowCounter++;

            if (dependencyRowCounter > 1) {

                CSVRow csvRow = new CSVRow();

                for (int i = 0; i < dependencyNextRecord.length; i++) {
                    switch (i) {
                        case 0:
                            csvRow.setMethodSignature(dependencyNextRecord[i]);
                            break;
                        case 1:
                            csvRow.setClassName(dependencyNextRecord[i]);
                            break;
                        case 2:
                            csvRow.setConfidenceLevel(dependencyNextRecord[i]);
                            break;
                        case 3:
                            csvRow.setAge(dependencyNextRecord[i]);
                            break;
                        case 4:
                            csvRow.setCodeChurn(dependencyNextRecord[i]);
                            break;
                        case 5:
                            csvRow.setCyclomaticComplexity(dependencyNextRecord[i]);
                            break;
                        case 6:
                            csvRow.setDependency(dependencyNextRecord[i]);
                            break;
                        case 7:
                            csvRow.setLinesOfCode(dependencyNextRecord[i]);
                            break;
                        case 8:
                            csvRow.setVersion(dependencyNextRecord[i]);
                            break;
                        case 9:
                            csvRow.setSourceFilepath(dependencyNextRecord[i]);
                            break;
                        case 10:
                            csvRow.setLine(dependencyNextRecord[i]);
                            break;
                    }
                }
                dependencySet.add(csvRow);
            }
        }

        int linesOfCodeRowCounter = 0;
        while ((linesOfCodeNextRecord = linesOfCodeOutliersCsvFileBufferedReaderObject.readNext()) != null) {
            linesOfCodeRowCounter++;

            if (linesOfCodeRowCounter > 1) {

                CSVRow csvRow = new CSVRow();

                for (int i = 0; i < linesOfCodeNextRecord.length; i++) {
                    switch (i) {
                        case 0:
                            csvRow.setMethodSignature(linesOfCodeNextRecord[i]);
                            break;
                        case 1:
                            csvRow.setClassName(linesOfCodeNextRecord[i]);
                            break;
                        case 2:
                            csvRow.setConfidenceLevel(linesOfCodeNextRecord[i]);
                            break;
                        case 3:
                            csvRow.setAge(linesOfCodeNextRecord[i]);
                            break;
                        case 4:
                            csvRow.setCodeChurn(linesOfCodeNextRecord[i]);
                            break;
                        case 5:
                            csvRow.setCyclomaticComplexity(linesOfCodeNextRecord[i]);
                            break;
                        case 6:
                            csvRow.setDependency(linesOfCodeNextRecord[i]);
                            break;
                        case 7:
                            csvRow.setLinesOfCode(linesOfCodeNextRecord[i]);
                            break;
                        case 8:
                            csvRow.setVersion(linesOfCodeNextRecord[i]);
                            break;
                        case 9:
                            csvRow.setSourceFilepath(linesOfCodeNextRecord[i]);
                            break;
                        case 10:
                            csvRow.setLine(linesOfCodeNextRecord[i]);
                            break;
                    }
                }
                linesOfCodeSet.add(csvRow);
            }
        }
    }
}
