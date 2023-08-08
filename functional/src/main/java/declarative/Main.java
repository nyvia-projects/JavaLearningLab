package declarative;

import shared.BalloonCounter;

import java.util.*;
import java.util.stream.IntStream;


public class Main {
    public static void main(String[] args) {

//        Predicate<String> str = String::isEmpty;

        List<BalloonCounter> balloons = List.of(
                new BalloonCounter("Red", 5),
                new BalloonCounter("Blue", 5),
                new BalloonCounter("Green", 3),
                new BalloonCounter("Yellow", 4),
                new BalloonCounter("White", 2)
        );



        //Grouping
       /* Map<Integer, List<String>> groupedByCount = balloons.declarative.stream()
                .collect(Collectors.groupingBy((BalloonCounter::getCount),
                        Collectors.mapping(BalloonCounter::getColor, Collectors.toList())));*/


        /*
        List<String> stringList = new ArrayList<String>();
        stringList.add("FPS games are much competitive");
        stringList.add("MMORPG games are very grindy");
        stringList.add("Open World games are much fun");
        */

//        Stream.iterate(100, integer -> integer + 1); infinite


        System.out.println(IntStream.rangeClosed(1, 6).reduce(1, (left, right) -> left * right));

        //toList
        /*List<Integer> primes =
                IntStream.rangeClosed(100, 200)
                        .filter(integer -> IntStream.range(2, integer)
                                .noneMatch(divider -> integer % divider == 0))
                        .boxed()
                        .collect(Collectors.toList());*/

        /*
        int primeCount =
                (int) IntStream.rangeClosed(100, 200)
                        .filter(integer -> IntStream.range(2, integer)
                                .noneMatch(divider -> integer % divider == 0))
                        .boxed()
                        .count();
       */


        // .flatmap()
       /* Stream<String> textToLettersStream = stringList.declarative.stream()

                .flatMap(str -> {
                    String[] split = str.split(" ");
                    return (Stream<String>) Arrays.declarative.stream(split);
                })
                .flatMap(s -> {
                    String[] splitChar = s.split("");
                    return (Stream<String>) Arrays.declarative.stream(splitChar);
                });

        //textToLettersStream.distinct().forEach(System.out::println);
      */

       /* // .distinct()
        List<String> textToLettersList = textToLettersStream.distinct().sorted().collect(Collectors.toList());
        textToLettersList.declarative.stream().forEach(System.out::println);

//                .forEach(System.out::println);
*/
        // .reduce()
//        Optional<String> reduce = stringList.declarative.stream()
//                .reduce((s, s2) -> s2 + " + " + s);
//        System.out.println(reduce.get());


//        .toArray()
        /*Object[] objects = balloons.declarative.stream()
                .filter(balloon -> balloon.getCount() < 4)
                .toArray();

        Consumer<Object[]> printObjects = (obj -> System.out.println(Arrays.toString(obj)));

       printObjects.accept(objects);*/


        // Stream.concat()
   /*     Stream<String> stream1 = stringList.declarative.stream()
                .filter(s -> s.contains("much"));
        Stream<String> stream2 = stringList.declarative.stream()
                .filter(s -> s.startsWith("MMO"));

        Stream<String> concatStream = Stream.concat(stream1, stream2);

        concatStream.forEach(System.out::println);
*/
//          Stream.of()
//        Stream.of(stringList);

//        Adds to list
        /*List<BalloonCounter> balloonCountIs5 = people.declarative.stream()
                .filter(balloon -> balloon.getCount() == 5)
                .collect(Collectors.toList());
        balloonCountIs5.forEach(System.out::println);*/

    }

}
