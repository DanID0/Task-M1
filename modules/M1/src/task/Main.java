

package task;

public class Main {

    static final int[][] STUDENTS_MARKS = {
            {4, 7, 10},
            {5, 6, 8},
            {2, 5, 6},
            {6, 7, 8}
    };

    static final double GREAT = 9.0;
    static final double GOOD = 7.0;
    static final double PASS = 5.0;

    static String classifyResult(double average) {
        if (average >= GREAT) {
            return "Congrats";
        } else if (average >= GOOD) {
            return "Nice";
        } else if (average >= PASS) {
            return "Fine";
        } else {
            return "Failed";
        }
    }
    public static void main(String[] args) {

        for (int i = 0; i < STUDENTS_MARKS.length; i++) {

            double result = averageMarks(STUDENTS_MARKS[i]);

            System.out.println(
                    "Student " + (i + 1) +
                            " | Average: " + String.format("%.2f", result) +
                            " | " + classifyResult(result)
            );
        }
    }

    static double averageMarks(int[] marks) {
        int sum = 0;

        for (int mark : marks) {
            sum += mark;
        }

        return (double) sum / marks.length;
    }
}
