import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistorianHysteria {

    public static List<Integer> list = new ArrayList<>();
    public static List<Integer> list2 = new ArrayList<>();
    public static long similarityScore = 0;

    public static void main(String[] args) throws FileNotFoundException {
        readFileAndPopulateLists();
        calculateDistances();
        calculateSimilarityScore();
    }

    private static void calculateDistances() {
        list = list.stream().sorted().toList();
        list2 = list2.stream().sorted().toList();

        List<Integer> distances = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            distances.add(Math.abs(list.get(i) - list2.get(i)));
        }
        System.out.println(distances.stream().reduce(0, Integer::sum));
    }

    private static void calculateSimilarityScore() {
        for (Integer i : list) {
            long sum = list2.stream().filter(k -> k.equals(i)).count();
            similarityScore += sum * i;
        }
        System.out.println(similarityScore);
    }

    private static void readFileAndPopulateLists() throws FileNotFoundException {
        URL getPath = HistorianHysteria.class.getResource("list.txt");
        assert getPath != null;
        File listTxt = new File(getPath.getPath());
        Scanner scanner = new Scanner(listTxt);
        while (scanner.hasNextLine()) {
            String[] splitLine = scanner.nextLine().splitWithDelimiters(" {3}", 0);
            list.add(Integer.valueOf(splitLine[0]));
            list2.add(Integer.valueOf(splitLine[2]));
        }
        scanner.close();
    }
}
