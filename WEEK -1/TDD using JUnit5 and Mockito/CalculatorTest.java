/**
 * Exercise 1: Setting Up JUnit
 *
 * Test class for Calculator.
 *
 * NOTE: Since Maven/Gradle is not set up on this system, this test class
 * uses a simple custom test runner that mimics JUnit behavior:
 *   - setUp()       : runs before each test (like @Before)
 *   - tearDown()    : runs after each test (like @After)
 *   - assertEquals  : checks if expected == actual
 *   - assertTrue    : checks if condition is true
 *   - assertNotNull : checks if object is not null
 *
 * In a real project with Maven/Gradle, you would use:
 *   import org.junit.Test;
 *   import org.junit.Before;
 *   import static org.junit.Assert.*;
 */
public class CalculatorTest {

    private Calculator calculator;
    private static int passed = 0;
    private static int failed = 0;

    // =====================================================
    // Setup & Teardown (mimics @Before and @After)
    // =====================================================

    public void setUp() {
        calculator = new Calculator();
    }

    public void tearDown() {
        calculator = null;
    }

    // =====================================================
    // Custom Assert Methods (mimics JUnit Assert class)
    // =====================================================

    private static void assertEquals(String message, long expected, long actual) {
        if (expected == actual) {
            System.out.println("  PASS: " + message);
            passed++;
        } else {
            System.out.println("  FAIL: " + message + " | Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }

    private static void assertEquals(String message, double expected, double actual, double delta) {
        if (Math.abs(expected - actual) <= delta) {
            System.out.println("  PASS: " + message);
            passed++;
        } else {
            System.out.println("  FAIL: " + message + " | Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }

    private static void assertTrue(String message, boolean condition) {
        if (condition) {
            System.out.println("  PASS: " + message);
            passed++;
        } else {
            System.out.println("  FAIL: " + message);
            failed++;
        }
    }

    private static void assertNotNull(String message, Object obj) {
        if (obj != null) {
            System.out.println("  PASS: " + message);
            passed++;
        } else {
            System.out.println("  FAIL: " + message + " | Object was null");
            failed++;
        }
    }

    // =====================================================
    // Addition Tests (like @Test methods)
    // =====================================================

    public void testAddPositiveNumbers() {
        int result = calculator.add(10, 20);
        assertEquals("10 + 20 should equal 30", 30, result);
    }

    public void testAddNegativeNumbers() {
        int result = calculator.add(-5, -3);
        assertEquals("-5 + (-3) should equal -8", -8, result);
    }

    public void testAddZero() {
        int result = calculator.add(7, 0);
        assertEquals("7 + 0 should equal 7", 7, result);
    }

    // =====================================================
    // Subtraction Tests
    // =====================================================

    public void testSubtract() {
        int result = calculator.subtract(20, 5);
        assertEquals("20 - 5 should equal 15", 15, result);
    }

    public void testSubtractNegativeResult() {
        int result = calculator.subtract(3, 10);
        assertEquals("3 - 10 should equal -7", -7, result);
    }

    // =====================================================
    // Multiplication Tests
    // =====================================================

    public void testMultiply() {
        int result = calculator.multiply(4, 5);
        assertEquals("4 * 5 should equal 20", 20, result);
    }

    public void testMultiplyByZero() {
        int result = calculator.multiply(100, 0);
        assertEquals("100 * 0 should equal 0", 0, result);
    }

    public void testMultiplyNegativeNumbers() {
        int result = calculator.multiply(-3, -4);
        assertEquals("-3 * -4 should equal 12", 12, result);
    }

    // =====================================================
    // Division Tests
    // =====================================================

    public void testDivide() {
        double result = calculator.divide(10, 2);
        assertEquals("10 / 2 should equal 5.0", 5.0, result, 0.001);
    }

    public void testDivideDecimalResult() {
        double result = calculator.divide(7, 2);
        assertEquals("7 / 2 should equal 3.5", 3.5, result, 0.001);
    }

    public void testDivideByZeroThrowsException() {
        try {
            calculator.divide(10, 0);
            System.out.println("  FAIL: Should have thrown ArithmeticException");
            failed++;
        } catch (ArithmeticException e) {
            System.out.println("  PASS: Division by zero correctly throws ArithmeticException");
            passed++;
        }
    }

    // =====================================================
    // Additional Assert Demonstrations
    // =====================================================

    public void testCalculatorNotNull() {
        assertNotNull("Calculator instance should not be null", calculator);
    }

    public void testAddResultIsPositive() {
        int result = calculator.add(5, 10);
        assertTrue("Sum of two positive numbers should be positive", result > 0);
    }

    // =====================================================
    // Test Runner (mimics JUnit test runner)
    // =====================================================

    private void runTest(String testName, Runnable test) {
        setUp();
        System.out.println("Running: " + testName);
        try {
            test.run();
        } catch (Exception e) {
            System.out.println("  ERROR: " + e.getMessage());
            failed++;
        }
        tearDown();
    }

    public static void main(String[] args) {
        CalculatorTest tester = new CalculatorTest();

        System.out.println("==============================================");
        System.out.println("   Exercise 1: Calculator Unit Tests");
        System.out.println("==============================================\n");

        // --- Addition Tests ---
        System.out.println("--- Addition Tests ---");
        tester.runTest("testAddPositiveNumbers",      tester::testAddPositiveNumbers);
        tester.runTest("testAddNegativeNumbers",      tester::testAddNegativeNumbers);
        tester.runTest("testAddZero",                 tester::testAddZero);

        // --- Subtraction Tests ---
        System.out.println("\n--- Subtraction Tests ---");
        tester.runTest("testSubtract",                tester::testSubtract);
        tester.runTest("testSubtractNegativeResult",  tester::testSubtractNegativeResult);

        // --- Multiplication Tests ---
        System.out.println("\n--- Multiplication Tests ---");
        tester.runTest("testMultiply",                tester::testMultiply);
        tester.runTest("testMultiplyByZero",          tester::testMultiplyByZero);
        tester.runTest("testMultiplyNegativeNumbers", tester::testMultiplyNegativeNumbers);

        // --- Division Tests ---
        System.out.println("\n--- Division Tests ---");
        tester.runTest("testDivide",                        tester::testDivide);
        tester.runTest("testDivideDecimalResult",           tester::testDivideDecimalResult);
        tester.runTest("testDivideByZeroThrowsException",   tester::testDivideByZeroThrowsException);

        // --- Additional Tests ---
        System.out.println("\n--- Additional Assert Tests ---");
        tester.runTest("testCalculatorNotNull",       tester::testCalculatorNotNull);
        tester.runTest("testAddResultIsPositive",     tester::testAddResultIsPositive);

        // --- Summary ---
        System.out.println("\n==============================================");
        System.out.println("   TEST RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("   Total Tests:  " + (passed + failed));
        System.out.println("==============================================");
    }
}
