package laboratory.singleton;

public class Tester {

    public static void main(String[] args) {

        try {
            PrinterController.getInstance(2);

            PrinterController.getInstance(2);

            PrinterController.getInstance(3);

            PrinterController.showInstances();

            PrinterController.getInstance(5);

        } catch (RuntimeException e) {
            System.out.println("\nRuntimeException: " + e.getMessage());
        }

    }
}
