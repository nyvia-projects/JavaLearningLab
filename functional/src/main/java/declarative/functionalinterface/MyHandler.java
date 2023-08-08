package declarative.functionalinterface;

import java.util.Date;

@FunctionalInterface
interface CustomHandler{

    String handle(int one, Date two);

    default String sayHi() {
        return "Hi!";
    }
}

public class MyHandler {


    public static void main(String[] args) {

        CustomHandler handler = (one, two) -> "What's up?";

        Delegate delegate = new Delegate();

        System.out.println(handler);


    }

    private static class Delegate{
        String handleThis(int one, Date two) {
            return one + ":" + two;
        }
    }



}
