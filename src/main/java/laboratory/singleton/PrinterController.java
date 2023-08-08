package laboratory.singleton;

import java.util.Arrays;

public class PrinterController {

    private static final int NUM_PRINTERS = 5;

    private static final PrinterController[] instances = new PrinterController[NUM_PRINTERS];

    private PrinterController() {

    }

    public static PrinterController getInstance(int i) {
        if (i < NUM_PRINTERS && i >= 0 && instances[i] == null) {
            instances[i] = new PrinterController();
            System.out.println("I am instance number: " + i);
            return instances[i];
        }
        else if(i < NUM_PRINTERS && i >= 0 && instances[i] != null) {
            System.out.println("I am instance number: " + i);
            return instances[i];
        }
        throw new RuntimeException("No Instances Available!");
    }

    public static void showInstances() {
        Arrays.stream(instances).forEach(System.out::println);
    }
}
