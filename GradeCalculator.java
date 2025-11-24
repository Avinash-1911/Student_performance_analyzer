public class GradeCalculator {

    public static String getGrade(double percentage) {
        if (percentage >= 85) return "A+";
        if (percentage >= 75) return "A";
        if (percentage >= 60) return "B";
        if (percentage >= 50) return "C";
        if (percentage >= 40) return "D";
        return "F";
    }

    public static String getPerformanceCategory(double percentage) {
        if (percentage >= 85) return "Outstanding";
        if (percentage >= 75) return "Excellent";
        if (percentage >= 60) return "Good";
        if (percentage >= 50) return "Average";
        if (percentage >= 40) return "Needs Improvement";
        return "Poor";
    }
}
