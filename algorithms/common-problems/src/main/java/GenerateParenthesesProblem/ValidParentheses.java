package GenerateParenthesesProblem;

import java.util.ArrayList;
import java.util.List;

public class ValidParentheses {


    private int opening;

    private int closing = 0;

    private String generatedCombination;


    private ValidParentheses(int count) {
        this.opening = 2 * count;
    }

    public static ValidParentheses getValidParenthesesGeneratorInstance(int count) {
        return new ValidParentheses(count);
    }

    public String generateValidParenthesesPermutations() {


        return generateRecursively(opening, closing, generatedCombination);
    }

    private String generateRecursively(int open, int close, String permutations) {
        if (open == 0 && close == 0)
            permutations += permutations;
        if(open > 0)
            return generateRecursively(open - 1, close + 1, permutations + "(");

        if(close > 0)
            return generateRecursively(open, close - 1, permutations + "(");


       return permutations;
    }


}
