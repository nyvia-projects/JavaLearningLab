import FirstAndLastIndexProblem.FirstAndLastIndex;
import GenerateParenthesesProblem.ValidParentheses;
import ValidAnagramProblem.ValidAnagram;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Panel {


    private static class Calculator{

        private int numOne;
        private int numTwo;
        private char operation;

        public Calculator(int numOne, int numTwo) {
            this.numOne = numOne;
            this.numTwo = numTwo;
        }

        public Calculator(int numOne, int numTwo, char operation) {


            Set<Character> operators = new HashSet<>();
            operators.add('+');
            operators.add('-');
            operators.add('*');
            operators.add('/');

            if (numOne <= 1 || numTwo <= 1 || numOne > 10 || numTwo > 10 ||
                    !operators.contains(operation)){
                String ERROR = "An error occurred during execution. " +
                        "Please contact the development team at dev@team.com";
                System.out.println(ERROR);
            }
            if (operation == '+')  add();
            if (operation == '-')  subtract();
            if (operation == '*')  multiply();
            if (operation == '/')  divide();


        }

        public void add() {
            System.out.println(numOne + numTwo);
        }

        public void subtract() {
            System.out.println(numOne - numTwo);
        }

        public void multiply() {
            System.out.println(numOne * numTwo);
        }

        public void divide(){
            System.out.println((double) numOne / numTwo);
        }

    }
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int num1, num2;
        char op;
        System.out.print("Num1: ");
        num1 = scanner.nextInt();
        System.out.print("Num2: ");
        num2 = scanner.nextInt();
        System.out.print("+ - * /: ");
        op = scanner.next()
                .charAt(0);
        Calculator calc = new Calculator(num1, num2, op);


    }
}


        /*List<Integer> list = List.of(1, 12, 4, 12);

        list.stream().mapToInt(integer -> integer).distinct().forEach();

        Math.pow(1,2);*/
//        ValidAnagram instance = ValidAnagram.getValidAnagramProblemInstance("", "");
//        ValidParentheses validParenthesesProblem = ValidParentheses.getValidParenthesesGeneratorInstance(3);
//
//        System.out.println(validParenthesesProblem.generateValidParenthesesPermutations());