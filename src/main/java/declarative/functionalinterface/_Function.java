package declarative.functionalinterface;

import java.math.BigInteger;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//method
public class _Function {
    public static void main(String[] args) {




        int integer = incrementByOneFunction.apply(1);
        System.out.println(integer);

        Integer multiply = multiplyByTenFunction.apply(integer);
        System.out.println(multiply);

        Function<Integer, Integer> addOneAndMultiplyByTen = incrementByOneFunction.andThen(multiplyByTenFunction);

        Function<Integer, Double> plusOneTimesTenToPowerOfe = incrementByOneFunction.andThen(multiplyByTenFunction).andThen(takeToPowerOfe);

//        System.out.println(plusOneTimesTenToPowerOfe.apply(9));

        Double sumOverTwo = addNumsTogetherAndDivideByTwoFunction.apply(10, 20);
//        System.out.println(sumOverTwo);

        System.out.println(getFactorial.apply(5));


    }

    static Function<Integer, Integer> getFactorial = num ->
            IntStream.rangeClosed(1, num).reduce(1, (num1,num2) -> num1 * num2);

//    static Function<Integer, List<BigInteger>> getFibonacci = lim ->  Stream.iterate(new BigInteger[] {BigInteger.ONE, BigInteger.ONE},
//                    bigIntegers -> new BigInteger[]{bigIntegers[1], bigIntegers[0].add(bigIntegers[1])})
//            .limit(lim).







    static Function<Integer, Integer> incrementByOneFunction = num -> ++num;

    static Function<Integer, Integer> multiplyByTenFunction = num -> num * 10;

    static Function<Integer, Double> takeToPowerOfe = Math::exp;

    static BiFunction<Integer, Integer, Double> addNumsTogetherAndDivideByTwoFunction = (num1, num2) -> (double) ((num1 + num2) / 2);

}
