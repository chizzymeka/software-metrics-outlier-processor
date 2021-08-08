package core;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import classes.CSVRow;
import ucl.cdt.cybersecurity.App;

import java.io.*;
import java.util.LinkedHashMap;

public class RicherSoftwareMetricsReportWriter {

    public void writeRicherSoftwareMetricsCSVReport () throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/software_metrics_report/software_metrics_report_76_version(s).csv"));
        //CSVReader csvReader = new CSVReader(bufferedReader);
        CSVReader csvReader = new CSVReader(bufferedReader, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, '\0', 0, CSVParser.DEFAULT_STRICT_QUOTES);

        String[] nextRecord;
        int rowCounter = 0;
        ConfidenceLevel level = null;

        File file = new File("src/main/java/output/software_metrics_report_with_confidence_levels.csv");
        FileWriter outputFile = new FileWriter(file);
        CSVWriter csvWriter = new CSVWriter(outputFile);

        // CSV HEADER
        // 1 - Method Signature
        // 2 - Class Name
        // 3 - Confidence Level - VERY HIGH, HIGH, MODERATE, LOW, VERY LOW
        // 4 - Age (Since First Appearance in Weeks)
        // 5 - Code Churn (since source file creation)
        // 6 - Cyclomatic Complexity
        // 7 - Dependency (Number of Method Calls Per Method)
        // 8 - Number of Lines of Code
        // 9 - Version
        // 10 - Source File Path
        // 11 - Line

        String[] csvData = new String[11];
        csvData[0] = "method_signature";
        csvData[1] = "class_name";
        csvData[2] = "confidence_level";
        csvData[3] = "age";
        csvData[4] = "code_churn";
        csvData[5] = "cyclomatic_complexity";
        csvData[6] = "dependency";
        csvData[7] = "lines_of_code";
        csvData[8] = "version";
        csvData[9] = "source_file_path";
        csvData[10] = "line";

        csvWriter.writeNext(csvData);

        while ((nextRecord = csvReader.readNext()) != null) {

            rowCounter++;

            if (rowCounter > 1) {

                CSVRow csvRow = new CSVRow();

                for (int i = 0; i < nextRecord.length; i++) {
                    switch (i) {
                        case 0:
                            csvRow.setMethodSignature(nextRecord[i]);
                            break;
                        case 1:
                            csvRow.setClassName(nextRecord[i]);
                            break;
                        case 2:
                            csvRow.setConfidenceLevel(nextRecord[i]);
                            break;
                        case 3:
                            csvRow.setAge(nextRecord[i]);
                            break;
                        case 4:
                            csvRow.setCodeChurn(nextRecord[i]);
                            break;
                        case 5:
                            csvRow.setCyclomaticComplexity(nextRecord[i]);
                            break;
                        case 6:
                            csvRow.setDependency(nextRecord[i]);
                            break;
                        case 7:
                            csvRow.setLinesOfCode(nextRecord[i]);
                            break;
                        case 8:
                            csvRow.setVersion(nextRecord[i]);
                            break;
                        case 9:
                            csvRow.setSourceFilepath(nextRecord[i]);
                            break;
                        case 10:
                            csvRow.setLine(nextRecord[i]);
                            break;
                    }
                }

                LinkedHashMap<CSVRow, Integer> csvRowAndIntersectionValue = App.getCsvRowAndIntersectionValue();

                // Obtain the Confidence Levels for each row where available.
                if (csvRowAndIntersectionValue.containsKey(csvRow)) {

                    int confidenceLevel = csvRowAndIntersectionValue.get(csvRow);

                    switch (confidenceLevel) {
                        case 5:
                            level = ConfidenceLevel.VERY_HIGH;
                            break;
                        case 4:
                            level = ConfidenceLevel.HIGH;
                            break;
                        case 3:
                            level = ConfidenceLevel.MODERATE;
                            break;
                        case 2:
                            level = ConfidenceLevel.LOW;
                            break;
                    }
                } else {
                    level = ConfidenceLevel.VERY_LOW;
                }
                assert level != null;
                csvRow.setConfidenceLevel(level.toString());

                // Write new CSV featuring 'Confidence Levels'.
                csvData[0] = csvRow.getMethodSignature();
                csvData[1] = csvRow.getClassName();
                csvData[2] = csvRow.getConfidenceLevel();
                csvData[3] = csvRow.getAge();
                csvData[4] = csvRow.getCodeChurn();
                csvData[5] = csvRow.getCyclomaticComplexity();
                csvData[6] = csvRow.getDependency();
                csvData[7] = csvRow.getLinesOfCode();
                csvData[8] = csvRow.getVersion();
                csvData[9] = csvRow.getSourceFilepath();
                csvData[10] = csvRow.getLine();
                csvWriter.writeNext(csvData);
            }
        }
        csvWriter.close();
    }
}
