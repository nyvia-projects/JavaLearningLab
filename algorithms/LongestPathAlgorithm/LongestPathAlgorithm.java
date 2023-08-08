

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class LongestPathAlgorithm {


    public static class LongestPathProblem {

        private final int rowSize;

        private final int colSize;

        private final int[][] matrix;

        /*path value/solutions matrix*/
        private int[][] solutions;

        private LongestPathProblem(int rowSize, int colSize, int[][] matrix) {
            this.rowSize = rowSize;
            this.colSize = colSize;
            this.matrix = matrix;
//            solutions matrix with one extra row and column
            solutions = new int[rowSize + 1][colSize + 1];
//            autofill all values to -1
            for (int[] ints : solutions)
                Arrays.fill(ints, -1);
//            autofill first row to 0
            solutions[0] = Arrays.stream(solutions[0])
                    .map(i -> 0)
                    .toArray();
//            autofill first column to 0
            for (int row = 1; row <= rowSize; row++)
                solutions[row][0] = 0;
        }
        /* problem would start with the following solution matrix
         * 0  0  0  0 ...  0
         * 0 -1 -1 -1 ... -1
         * 0 -1 -1 -1 ... -1
         * ..-1 -1 -1 ... -1
         * 0 -1 -1 -1 ... -1
         * */

        /*returns instance of the problem*/
        public static LongestPathProblem getProblemInstance(int rowSize, int colSize, int[][] matrix) {
            return new LongestPathProblem(rowSize, colSize, matrix);
        }

        /*prints the matrix*/
        private void printMatrix() {
            Arrays.stream(matrix)
                    .map(ints -> String.join(" ", Arrays.toString(ints)))
                    .forEach(System.out::println);
        }

        /*prints the solution matrix*/
        private void printSolutionsMatrix() {
            Arrays.stream(solutions)
                    .map(ints -> String.join(" ", Arrays.toString(ints)))
                    .forEach(System.out::println);
        }

        /*finds max value from solution matrix*/
        private int findMaxSolution() {
            return Arrays.stream(solutions)
                    .flatMapToInt(Arrays::stream)
                    .max()
                    .orElse(-55);
        }

        /*longestPath algorithm from top left cell of the matrix*/
        private void longestPathAlgorithm(int row, int col) {
//            path values for top and left (since we are going to go down and to the right)
            int top = 0, left = 0;
//            checks values for cell above
            if (row - 2 >= 0 && (matrix[row - 1][col - 1] < matrix[row - 2][col - 1]))
                top = solutions[row - 1][col];
//            checks values for cell below
            if (col - 2 >= 0 && (matrix[row - 1][col - 1] < matrix[row - 1][col - 2]))
                left = solutions[row][col - 1];
//           current max path value
            int max;
            max = Math.max(top, left);
//            increments the result matrix value
            solutions[row][col] = 1 + max;
//            next 2 lines check path lengths from other cells recursively
//            other cell = one on the bottom or to the right
            if (row < solutions.length - 1) longestPathAlgorithm(row + 1, col);
            if (col < solutions[0].length - 1) longestPathAlgorithm(row, col + 1);
        }

        public void solve() {
//            run the algorithm from the top left cell of the matrix
//            meaning [1,1] of the solution matrix
            longestPathAlgorithm(1, 1);
            System.out.println(findMaxSolution());
        }

    }

    /*
     * Reads the input file and gives problem instance
     * */
    public static LongestPathProblem readFileAndReceiveProblemInstance(String fileName) {
        List<List<Integer>> matrix = new ArrayList<>();
//        info about array dimensions
        int rowSize = 0, colSize = 0;

//        keeps line content
        String currentData;

        try {

            Scanner reader = new Scanner(new File(fileName));
            if (reader.hasNextLine()) {
//                read line
                currentData = reader.nextLine();
//                read integer values to queue
                Queue<Integer> firstLine = Arrays.stream(currentData.split(" "))
                        .filter(s -> !Objects.equals(s, ""))
                        .map(Integer::valueOf)
                        .collect(Collectors.toCollection(LinkedList::new));
//                get rowSize
                rowSize = Optional.ofNullable(firstLine.poll())
                        .orElse(-500);
//                get colSize
                colSize = Optional.ofNullable(firstLine.poll())
                        .orElse(-500);
//                clear queue
                firstLine.clear();
                matrix = new ArrayList<>(rowSize);
            }
//            current line to work with
            int lineCount = 1;
            while (lineCount <= rowSize && reader.hasNextLine()) {
                try {
//                    read line
                    currentData = reader.nextLine();
//                    read integer values to list
                    matrix.add(Arrays.stream(currentData.split(" "))
                            .filter(s -> !Objects.equals(s, ""))
                            .map(Integer::valueOf)
                            .collect(Collectors.toList()));

                    lineCount += 1;
                } catch (NumberFormatException e) {
                    System.out.println("Error with integer input from " + fileName);
                }
            }
        } catch (
                FileNotFoundException e) {
            System.out.println("Error reading file " + fileName);
            e.printStackTrace();
        }

//        2d array
        int[][] primitiveMatrix;

//        populate primitive 2d array from List of List<Integer>
        primitiveMatrix = matrix.stream()
                .map(row -> row
                        .stream()
                        .mapToInt(Integer::valueOf)
                        .toArray())
                .toArray(int[][]::new);

        // return sorting problem instance
        return LongestPathProblem.getProblemInstance(rowSize, colSize, primitiveMatrix);
    }


    public static void main(String[] args) {
        String fileName = Arrays.stream(args)
                .filter(s -> Objects.equals(s, "input3.txt"))
                .findFirst()
                .orElse("not found");

        LongestPathProblem problem = readFileAndReceiveProblemInstance(fileName);
        problem.solve();

    }
}