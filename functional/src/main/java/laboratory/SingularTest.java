package laboratory;

import java.util.function.BiFunction;

public class SingularTest {

    public static void main(String[] args) {

        BiFunction<String, String, String> stringConcat = (str1, str2) -> str1+" "+str2;
        System.out.println(stringConcat.apply("Hey", "Lambda"));



    }
}
