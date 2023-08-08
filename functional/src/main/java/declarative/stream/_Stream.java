package declarative.stream;

import shared.BalloonCounter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class _Stream {

    public static void main(String[] args) {

        List<BalloonCounter> balloons = List.of(
                new BalloonCounter("Red", 5),
                new BalloonCounter("Blue", 5),
                new BalloonCounter("Green", 3),
                new BalloonCounter("Yellow", 4),
                new BalloonCounter("White", 2)
        );

        List<Integer> values1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


        List<Integer> oneToTen = IntStream.rangeClosed(0, 10)
                .boxed()
                .collect(Collectors.toList());

        List<String> values2 = List.of("x","y","z","m","n","o");



            Integer result1 = values1.stream()
                    .filter(integer -> integer % 2 == 0)
                    .reduce(0, Integer::sum);

            String result2 = values2.stream()
                    .sorted(Comparator.reverseOrder())
                    .reduce("", String::concat);



            balloons.stream()
                    .sorted(Comparator.comparing(BalloonCounter::getCount))
                    .forEach(System.out::println);


            balloons.stream()
                    .map(BalloonCounter::getColor)
                    //.collect(Collectors.toSet())
                    .forEach(System.out::println);

            System.out.println(result1);
            System.out.println(result2);




    }


}
