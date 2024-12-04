import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Will only solve part 1
 */
public class CeresSearch {
    private static final int[][] DIRECTIONS = {
            {0, 1}, {0, -1},
            {1, 0}, {-1, 0},
            {1, 1}, {-1, -1},
            {1, -1}, {-1, 1}
    };
    public static final String SEARCH_WORD = "XMAS";
    private static char[][] grid;
    public static int occurrences = 0;

    public static void main(String[] args) throws FileNotFoundException {
        populateWordGrid();
        findWordOccurrences(grid, SEARCH_WORD);
        System.out.println(occurrences);
    }

    public static void findWordOccurrences(char[][] grid, String word) {
        int rows = grid.length;
        int cols = grid[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int[] dir : DIRECTIONS) {
                    if (checkWord(grid, word, r, c, dir[0], dir[1])) {
                        occurrences += 1;
                    }
                }
            }
        }
    }

    private static boolean checkWord(char[][] grid, String word, int startRow, int startCol, int dRow, int dCol) {
        int rows = grid.length;
        int cols = grid[0].length;
        int len = word.length();

        for (int i = 0; i < len; i++) {
            int newRow = startRow + i * dRow;
            int newCol = startCol + i * dCol;

            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                return false;
            }

            if (grid[newRow][newCol] != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    private static void populateWordGrid() throws FileNotFoundException {
        URL getPath = CeresSearch.class.getResource("input.txt");
        assert getPath != null;
        File listTxt = new File(getPath.getPath());
        Scanner scanner = new Scanner(listTxt);

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        scanner.close();

        int rows = lines.size();
        int cols = lines.getFirst().length();

        grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }
    }
}
