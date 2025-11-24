import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Student Performance Analyzer");
        FileManager.ensureDataFiles();

        boolean exit = false;
        while (!exit) {
            showMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    showAllStudentsText();
                    break;
                case 3:
                    showAllStudentsCSV();
                    break;
                case 4:
                    searchByRollNo();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Student Record");
        System.out.println("2. Display All Records (Text)");
        System.out.println("3. Display All Records (CSV)");
        System.out.println("4. Search Student by Roll No (CSV)");
        System.out.println("5. Exit");
    }

    private static void addStudent() {
        System.out.println("\nAdd Student Record");
        String roll = readString("Enter Roll Number: ");
        String name = readString("Enter Name: ");
        int numSubs = readInt("Enter number of subjects: ");
        while (numSubs <= 0) {
            System.out.println("Number of subjects should be >= 1");
            numSubs = readInt("Enter number of subjects: ");
        }

        double[] marks = new double[numSubs];
        for (int i = 0; i < numSubs; i++) {
            marks[i] = readDouble(String.format("Enter mark for subject %d (0-100): ", i + 1));
            while (marks[i] < 0 || marks[i] > 100) {
                System.out.println("Mark must be between 0 and 100.");
                marks[i] = readDouble(String.format("Enter mark for subject %d (0-100): ", i + 1));
            }
        }

        Student s = new Student(roll, name, marks);

        System.out.println("\n--- Result ---");
        System.out.println("Total      : " + String.format("%.2f", s.getTotal()));
        System.out.println("Percentage : " + String.format("%.2f", s.getPercentage()) + "%");
        System.out.println("Grade      : " + GradeCalculator.getGrade(s.getPercentage()));
        System.out.println("Category   : " + GradeCalculator.getPerformanceCategory(s.getPercentage()));

        boolean savedTxt = FileManager.saveToText(s);
        boolean savedCsv = FileManager.saveToCSV(s);

        if (savedTxt && savedCsv) {
            System.out.println("Record saved to data/students.txt and data/students.csv");
        } else if (savedTxt) {
            System.out.println("Record saved to data/students.txt (CSV failed)");
        } else if (savedCsv) {
            System.out.println("Record saved to data/students.csv (Text failed)");
        } else {
            System.out.println("Failed to save record.");
        }
    }

    private static void showAllStudentsText() {
        List<String> lines = FileManager.loadTextRecords();
        if (lines.isEmpty()) {
            System.out.println("No text records found.");
            return;
        }
        System.out.println("\n--- Students (Text) ---");
        for (String l : lines) {
            System.out.println(l);
        }
    }

    private static void showAllStudentsCSV() {
        List<String> lines = FileManager.loadCSVRecords();
        if (lines.isEmpty()) {
            System.out.println("No CSV records found.");
            return;
        }
        System.out.println("\n--- Students (CSV) ---");
        for (String line : lines) {
            // CSV format: RollNo,Name,NumSubjects,Marks,Total,Percentage,Grade,Category
            System.out.println(line);
        }
    }

    private static void searchByRollNo() {
        String roll = readString("Enter Roll Number to search: ");
        List<String> lines = FileManager.loadCSVRecords();
        boolean found = false;
        for (String line : lines) {
            if (line.startsWith(roll + ",") || line.contains("\"" + roll + "\"")) {
                System.out.println("Record Found: " + line);
                found = true;
            }
        }
        if (!found) System.out.println("No record found for Roll No: " + roll);
    }

    // Utility read methods
    private static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = sc.nextLine().trim();
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }
}
