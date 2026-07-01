
/**
 * Exercise 1: Setting Up JUnit
 *
 * A simple Calculator class to demonstrate unit testing with JUnit.
 * This class provides basic arithmetic operations that will be
 * tested using JUnit test cases.
 */
public class Calculator {

    /**
     * Adds two integers and returns the result.
     * @param a first number
     * @param b second number
     * @return sum of a and b
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts second integer from the first and returns the result.
     * @param a first number
     * @param b second number
     * @return difference of a and b
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Multiplies two integers and returns the result.
     * @param a first number
     * @param b second number
     * @return product of a and b
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divides the first integer by the second and returns the result.
     * @param a dividend
     * @param b divisor
     * @return quotient of a divided by b
     * @throws ArithmeticException if b is zero
     */
    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return (double) a / b;
    }

    /**
     * Main method to demonstrate Calculator operations.
     */
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println("===== Calculator Demo =====");
        System.out.println("Addition:       10 + 20 = " + calc.add(10, 20));
        System.out.println("Subtraction:    20 - 5  = " + calc.subtract(20, 5));
        System.out.println("Multiplication: 4 * 5   = " + calc.multiply(4, 5));
        System.out.println("Division:       10 / 3  = " + calc.divide(10, 3));

        try {
            System.out.println("Division:       10 / 0  = " + calc.divide(10, 0));
        } catch (ArithmeticException e) {
            System.out.println("Division:       10 / 0  = Error - " + e.getMessage());
        }
    }
}
