package declarative.functionalinterface;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import java.util.stream.Collectors;
import java.util.stream.Stream;


//return
public class _Supplier {

    public static void main(String[] args) {

        System.out.println(getActiveThreadCount.get());
    }

//    static Supplier<List<BigInteger[]>> getFibonacciSequence =

    static Supplier<String> getDataSupplier = () -> "username:root\npassword:toor";

    static Supplier<Integer> getActiveThreadCount = Thread::activeCount;
}

