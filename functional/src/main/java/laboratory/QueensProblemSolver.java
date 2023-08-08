package laboratory;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class QueensProblemSolver {

    private static String[] column, leftDiagonal, rightDiagonal = new String[100];

    private static int boardSize;

    private QueensProblemSolver(int n) {
        column = IntStream.range(1, 99)
                .mapToObj(value -> "O")
                .toArray(String[]::new);

        leftDiagonal = IntStream.range(1, 99)
                .mapToObj(value -> "O")
                .toArray(String[]::new);

        rightDiagonal = IntStream.range(1, 99)
                .mapToObj(value -> "O")
                .toArray(String[]::new);

        boardSize = n;
        System.out.println("Size: " + n);


        Supplier<String[][]> resetStringBoard = () -> IntStream.range(0, boardSize)
                .mapToObj(elem -> IntStream.range(0, boardSize)
                        .mapToObj(value -> "O")
                        .toArray(String[]::new))
                .toArray(String[][]::new);

        String[][] stringBoard = resetStringBoard.get();

        Consumer<String[][]> printStringBoard = ary -> Stream.of(ary)
                .forEach(col -> {
                    Arrays.stream(col)
                            .forEach(row -> System.out.print(row + "  "));
                    System.out.println();
                });

        if (solveQueensProblem(stringBoard, 0)) {
            System.out.println("\nSolution Found!");
            printStringBoard.accept(stringBoard);
        }
        else System.out.println("\nNo Solution Found!");
    }

    public static QueensProblemSolver initializeProblem_WithBoardSizeOf(int n) {
        return new QueensProblemSolver(n);
    }
    public static void initializeAndRunProblem_WithBoardSizeOf(int n) {
        new QueensProblemSolver(n);
    }

    private final Consumer<String[][]> printStringBoard = ary -> Stream.of(ary)
            .forEach(col -> {
                Arrays.stream(col)
                        .forEach(row -> System.out.print(row + "  "));
                System.out.println();
            });

    private final BiConsumer<String[], String> printMemory = (mem, type) ->
        System.out.println(type + Stream.of(mem)
                //.filter(s -> !s.equals("E"))
                        .limit(boardSize * 2L - 2)
                .collect(Collectors.joining(", ", "[", "]")));

    private boolean solveQueensProblem(String[][] ary, int col) {

        if (col >= boardSize) return true; // last step, if last column => DONE


        for (int row = 0; row < boardSize; row++) { // goes through rows for each column

            // if no row has queen placed & (right-side & left-side diagonals don't have queens)
            if (!column[row].equals("X") &&
                    (!rightDiagonal[row + col].equals("X") && !leftDiagonal[row - col + boardSize - 1].equals("X"))) {

                ary[row][col] = "X"; // place queen

                // adds to 'memory' where queen placed
                column[row] = rightDiagonal[row + col] = leftDiagonal[boardSize - 1 + row - col] = "X";

                if (solveQueensProblem(ary, col + 1)) return true; // recursive call

                ary[row][col] = "O"; // resets position, backtracking

                //resets 'memory' coordinate for current iteration
                column[row] = rightDiagonal[row + col] = leftDiagonal[boardSize - 1 + row - col] = "O";
            }
            System.out.println("\n--------------------------------------------------" +
                    "\nTrying...");
            printStringBoard.accept(ary);
            printMemory.accept(column, "\nColumn Memory: \t\t\t");
            printMemory.accept(rightDiagonal,"Right Diagonal Memory: \t");
            printMemory.accept(leftDiagonal, "Left Diagonal Memory: \t");
        }

        return false;
    }


}
