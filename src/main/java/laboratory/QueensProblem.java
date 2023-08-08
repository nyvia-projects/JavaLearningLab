package laboratory;


import java.util.Scanner;

public class QueensProblem {

    public static void main(String[] args) {

        System.out.print("N Queens Problem Chess Board Size: ");
        QueensProblemSolver.initializeAndRunProblem_WithBoardSizeOf(new Scanner(System.in).nextInt());

//        QueensProblemSolver problem = QueensProblemSolver.initializeProblem_WithBoardSizeOf(4);

    }

}
