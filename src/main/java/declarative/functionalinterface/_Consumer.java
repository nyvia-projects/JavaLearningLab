package declarative.functionalinterface;

import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

//void
public class _Consumer {
    public static void main(String[] args) {

        Customer mia = new Customer("Mia", "403");
        greetCustomerConsumerV2.accept(mia, false);



        System.out.println(getFibonacciListBiFunction.apply(fibonacciStreamSupplier.get(), 10));

        System.out.println(getFactorialBiFunction.apply(factorialStreamSupplier.get(), 5));

    }

    static BiFunction<LongStream, Integer, Long> getFactorialBiFunction = (longStream, num) ->
            longStream.limit(num + 1)
                    .reduce((first, next) -> next)
                    .orElse(0L);

    static BiFunction<LongStream, Integer, List<Long>> getFibonacciListBiFunction = (longStream, lim) ->
            longStream.limit(lim)
                    .boxed()
                    .collect(Collectors.toList());

    static Supplier<LongStream> factorialStreamSupplier = () -> Stream.iterate(new long[]{1, 1},
                    element -> new long[]{element[0] * element[1], element[1] + 1})
            .mapToLong(value -> value[0]);

    static Supplier<LongStream> fibonacciStreamSupplier = () -> Stream.iterate(new long[]{0, 1},
                    element -> new long[]{element[1], element[0] + element[1]})
            .mapToLong(element -> element[0]);




    static Consumer<Customer> greetCustomerConsumer = customer ->
            System.out.println("Hello " + customer.customerName + "#" + customer.customerID);

    static BiConsumer<Customer, Boolean> greetCustomerConsumerV2 = (customer, showCustomerID) ->
            System.out.println("Hello " + customer.customerName + "#" +
                    (showCustomerID ? customer.customerID : "HIDDEN"));

    static class Customer {


        private final String customerName;
        private final String customerID;

        Customer(String customerName, String customerID) {
            this.customerName = customerName;
            this.customerID = customerID;
        }
    }


}
