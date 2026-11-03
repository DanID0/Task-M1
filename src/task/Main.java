

package task;

public class Main {

    static int[][] studentsMarks = {
            {4, 7, 10},
            {5, 6, 8},
            {2, 5, 6},
            {6, 7, 8}
    };

    static final double GREAT = 9.0;
    static final double GOOD = 7.0;
    static final double PASS = 5.0;

    public static void main(String[] args) {

        for (int i = 0; i < studentsMarks.length; i++) {

            double result = averageMarks(i);
            String classification;

            if (result >= GREAT) {
                classification = "Congrats";
            } else if (result >= GOOD) {
                classification = "Nice";
            } else if (result >= PASS) {
                classification = "Fine";
            } else {
                classification = "Failed";
            }
            System.out.println(
                    "Student " + (i + 1) +
                            " | Average: " + String.format("%.2f", result) +
                            " | " + classification
            );
        }
    }

    static double averageMarks(int studentIndex) {
        int sum = 0;

        for (int j = 0; j < studentsMarks[studentIndex].length; j++) {
            sum += studentsMarks[studentIndex][j];
        }

        return (double) sum / studentsMarks[studentIndex].length;
    }
}
