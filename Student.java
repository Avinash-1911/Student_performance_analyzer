import java.util.Arrays;

public class Student {
    private String name;
    private String rollNo;
    private double[] marks; // marks per subject

    public Student(String rollNo, String name, double[] marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public double[] getMarks() {
        return marks;
    }

    public double getTotal() {
        double sum = 0.0;
        for (double m : marks) sum += m;
        return sum;
    }

    public double getPercentage() {
        if (marks.length == 0) return 0.0;
        double total = getTotal();
        return (total / (marks.length * 100.0)) * 100.0; // assuming each subject is out of 100
    }

    public String marksToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < marks.length; i++) {
            sb.append(String.format("%.2f", marks[i]));
            if (i != marks.length - 1) sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Marks: [%s] - Total: %.2f - Percentage: %.2f%%",
                name, rollNo, marksToString(), getTotal(), getPercentage());
    }
}
