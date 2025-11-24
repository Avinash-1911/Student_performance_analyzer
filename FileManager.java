import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String TXT_PATH = "data/students.txt";
    private static final String CSV_PATH = "data/students.csv";

    // Ensure data directory exists and files created
    public static void ensureDataFiles() {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdir();

            File txt = new File(TXT_PATH);
            if (!txt.exists()) txt.createNewFile();

            File csv = new File(CSV_PATH);
            if (!csv.exists()) {
                csv.createNewFile();
                // Add header row for CSV
                try (FileWriter fw = new FileWriter(csv, true)) {
                    fw.append("RollNo,Name,NumSubjects,Marks (semicolon-separated),Total,Percentage,Grade,Category\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error creating data files: " + e.getMessage());
        }
    }

    public static boolean saveToText(Student s) {
        ensureDataFiles();
        try (FileWriter fw = new FileWriter(TXT_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            StringBuilder sb = new StringBuilder();
            sb.append("Roll No: ").append(s.getRollNo()).append("\n");
            sb.append("Name   : ").append(s.getName()).append("\n");
            sb.append("Marks  : ").append(s.marksToString()).append("\n");
            sb.append(String.format("Total  : %.2f\n", s.getTotal()));
            sb.append(String.format("Percent: %.2f%%\n", s.getPercentage()));
            sb.append("Grade  : ").append(GradeCalculator.getGrade(s.getPercentage())).append("\n");
            sb.append("Category: ").append(GradeCalculator.getPerformanceCategory(s.getPercentage())).append("\n");
            sb.append("------------------------------------------------------\n");

            out.print(sb.toString());
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to text file: " + e.getMessage());
            return false;
        }
    }

    public static boolean saveToCSV(Student s) {
        ensureDataFiles();
        try (FileWriter fw = new FileWriter(CSV_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            StringBuilder marksSemicolon = new StringBuilder();
            double[] marks = s.getMarks();
            for (int i = 0; i < marks.length; i++) {
                marksSemicolon.append(String.format("%.2f", marks[i]));
                if (i != marks.length - 1) marksSemicolon.append(";");
            }

            String csvLine = String.format("%s,%s,%d,%s,%.2f,%.2f,%s,%s\n",
                    escapeCSV(s.getRollNo()),
                    escapeCSV(s.getName()),
                    marks.length,
                    escapeCSV(marksSemicolon.toString()),
                    s.getTotal(),
                    s.getPercentage(),
                    GradeCalculator.getGrade(s.getPercentage()),
                    GradeCalculator.getPerformanceCategory(s.getPercentage())
            );

            out.print(csvLine);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to CSV: " + e.getMessage());
            return false;
        }
    }

    private static String escapeCSV(String s) {
        if (s.contains(",") || s.contains("\"")) {
            s = s.replace("\"", "\"\"");
            return "\"" + s + "\"";
        }
        return s;
    }

    public static List<String> loadTextRecords() {
        ensureDataFiles();
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TXT_PATH))) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line);
        } catch (IOException e) {
            System.out.println("Error reading text file: " + e.getMessage());
        }
        return lines;
    }

    // Very simple CSV reader: returns raw CSV lines (skips header)
    public static List<String> loadCSVRecords() {
        ensureDataFiles();
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // skip header
                if (!line.trim().isEmpty()) lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return lines;
    }
}
