package declarative.functionalinterface;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

//Boolean
public class _Predicate {

    public static void main(String[] args) {

        System.out.println(isValidPredicate.and(containsDashPredicate).test("188-9995522"));
        System.out.println(isValidPredicate.and(containsDashPredicate).test("818-2225599"));
        System.out.println(isCallerValidPredicate.test("818-2225599", 17));

        System.out.println(xor(isValidPredicate, containsDashPredicate).test("8-"));
    }

    static Predicate<String> isValidPredicate = phoneNumber ->
            phoneNumber.startsWith("818") && phoneNumber.length() == 11;

    static Predicate<String> containsDashPredicate = phoneNumber ->
            phoneNumber.contains("-");

    static BiPredicate<String, Integer> isCallerValidPredicate = (phoneNumber, age) ->
        isValidPredicate.and(containsDashPredicate).test(phoneNumber) && age > 18;




    static <T> Predicate<T> xor(Predicate<T> pred1, Predicate<T> pred2) {
        return t -> pred1.test(t) ^ pred2.test(t);
    }




}

