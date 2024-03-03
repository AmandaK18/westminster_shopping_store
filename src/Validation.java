import java.util.Scanner;
import java.util.InputMismatchException;
public class Validation {

    private static  Scanner scanner  = new Scanner(System.in);
    public static String StringOnlyValidator(String prompt){
        String regex = "^[a-zA-Z]+$";
        while (true) {
            System.out.println(prompt);
            String input = scanner.next();

            if (input.matches(regex)){
                return input;
            }
            System.out.println("Invalid data type! Please enter again! ");
        }
    }
    public static double doubleValidator(String prompt){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(prompt);
                double input = scanner.nextDouble();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid double value.");
                scanner.next();
            }
        }

    }
    public static int intValidator(String prompt){

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next();
            }
        }

    }
    public static String StringValidator(String prompt){

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.next();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next();
            }
        }

    }
    public static String charValidation(String prompt) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$"; // Defining a regular expression pattern for the product ID

        while (true) {
            System.out.print(prompt);
            String input = scanner.next();

            if (input.matches(regex)) {
                return input;
            }

            System.out.println("Invalid product ID. Please enter a product ID with both letters and numbers!");
        }
    }
    public static String sizeValidation(String prompt) {
        String[] validSizes = { "S", "M", "L", "XL"};
        String userInput;

        while (true) {
            System.out.print(prompt);
            userInput = scanner.next().toUpperCase(); // Convert input to uppercase for case-insensitive comparison

            for (String validSize : validSizes) {
                if (userInput.equals(validSize)) {
                    return userInput;
                }
            }
            System.out.println("Invalid cloth size! Please enter a valid size ( S, M, L, XL).");
        }
    }
}
