import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class RedNosedReport {

    static List<List<Integer>> listOfReports = new ArrayList<>();
    static int safeReports;

    public static void main(String[] args) throws FileNotFoundException {

        readFileAndPopulateReportList();

        listOfReports.forEach(report -> {
            List<Integer> reportDistances = new ArrayList<>();
            for (int i = 0; i < report.size(); i++) {
                Integer currentValue = report.get(i);
                if (i+1 < report.size()) {
                    Integer nextValue = report.get(i+1);
                    reportDistances.add(currentValue - nextValue);
                }
            }

            List<Integer> validRanges = checkRangeCriteria(reportDistances);

            if (hasOnlyPositiveOrNegative(validRanges)) {
                if (Math.abs(validRanges.size() - reportDistances.size()) <= 1) {
                    safeReports += 1;
                }
            } else if (canApplyProblemDampener(validRanges)) {
                if (validRanges.size() == reportDistances.size()) {
                    safeReports += 1;
                }
            }
        });

        System.out.println(safeReports);
    }

    private static boolean canApplyProblemDampener(List<Integer> list) {
        long positives = list.stream().filter(n -> n > 0).count();
        long negatives = list.stream().filter(n -> n < 0).count();
        return Math.abs(positives - negatives) == 0;
    }

    private static boolean hasOnlyPositiveOrNegative(List<Integer> list) {
        return list.stream().noneMatch(n -> n == 0) &&
                (list.stream().allMatch(n -> n > 0) || list.stream().allMatch(n -> n < 0));
    }

    private static List<Integer> checkRangeCriteria(List<Integer> list) {
        return list.stream().filter(n -> Math.abs(n) <= 3 && Math.abs(n) >= 1).toList();
    }

    private static void readFileAndPopulateReportList() throws FileNotFoundException {
        URL getPath = RedNosedReport.class.getResource("list.txt");
        assert getPath != null;
        File listTxt = new File(getPath.getPath());
        Scanner scanner = new Scanner(listTxt);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            List<Integer> report = Arrays.stream(line.split(" "))
                    .map(Integer::parseInt)
                    .toList();
            listOfReports.add(report);
        }
        scanner.close();
    }
}