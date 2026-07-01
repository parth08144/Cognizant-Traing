/**
 * Exercise 4: Arrange-Act-Assert (AAA) Pattern, Test Fixtures,
 *             Setup and Teardown Methods in JUnit
 *
 * Demonstrates:
 *   1. Writing tests using the Arrange-Act-Assert (AAA) pattern
 *      - Arrange: Set up test data and preconditions
 *      - Act:     Execute the method under test
 *      - Assert:  Verify the expected outcome
 *
 *   2. Setup and Teardown methods (simulating @Before and @After)
 *      - setUp()    runs before EACH test  (like @Before / @BeforeEach)
 *      - tearDown() runs after  EACH test  (like @After  / @AfterEach)
 *
 * Since Maven/JUnit JARs are not available, this uses a custom
 * assertion framework and test runner that mimics JUnit behavior.
 */
public class AAAPatternTest {

    private static int passed = 0;
    private static int failed = 0;

    // =====================================================
    //  Test Fixture — shared state initialized by setUp()
    // =====================================================
    private Calculator calculator;
    private int[] sampleArray;
    private String sampleString;

    // =====================================================
    //  @Before — Setup Method (runs before each test)
    // =====================================================

    /**
     * Simulates JUnit's @Before / @BeforeEach annotation.
     * Initializes fresh test fixtures before every test method
     * so that tests remain independent and repeatable.
     */
    public void setUp() {
        System.out.println("  [@Before] setUp() — initializing test fixtures");
        calculator   = new Calculator();
        sampleArray  = new int[]{10, 20, 30, 40, 50};
        sampleString = "JUnit Testing";
    }

    // =====================================================
    //  @After — Teardown Method (runs after each test)
    // =====================================================

    /**
     * Simulates JUnit's @After / @AfterEach annotation.
     * Cleans up resources after every test method to prevent
     * state leakage between tests.
     */
    public void tearDown() {
        System.out.println("  [@After]  tearDown() — cleaning up test fixtures");
        calculator   = null;
        sampleArray  = null;
        sampleString = null;
    }

    // =====================================================
    //  Custom Assert Methods (same style as previous exercises)
    // =====================================================

    private static void assertEquals(String message, Object expected, Object actual) {
        if (expected.equals(actual)) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }

    private static void assertEquals(String message, long expected, long actual) {
        if (expected == actual) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }

    private static void assertEquals(String message, double expected, double actual, double delta) {
        if (Math.abs(expected - actual) <= delta) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }

    private static void assertTrue(String message, boolean condition) {
        if (condition) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Condition was false");
            failed++;
        }
    }

    private static void assertFalse(String message, boolean condition) {
        if (!condition) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Condition was true");
            failed++;
        }
    }

    private static void assertNotNull(String message, Object obj) {
        if (obj != null) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Object was null");
            failed++;
        }
    }

    private static void assertNull(String message, Object obj) {
        if (obj == null) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Object was not null: " + obj);
            failed++;
        }
    }

    // =====================================================
    //  Test 1: AAA Pattern — Addition
    // =====================================================

    public void testAddition_AAA() {
        // -------- Arrange --------
        int a = 15;
        int b = 25;

        // -------- Act --------
        int result = calculator.add(a, b);

        // -------- Assert --------
        assertEquals("15 + 25 should equal 40", 40, result);
    }

    // =====================================================
    //  Test 2: AAA Pattern — Subtraction
    // =====================================================

    public void testSubtraction_AAA() {
        // -------- Arrange --------
        int a = 50;
        int b = 18;

        // -------- Act --------
        int result = calculator.subtract(a, b);

        // -------- Assert --------
        assertEquals("50 - 18 should equal 32", 32, result);
    }

    // =====================================================
    //  Test 3: AAA Pattern — Multiplication
    // =====================================================

    public void testMultiplication_AAA() {
        // -------- Arrange --------
        int a = 7;
        int b = 8;

        // -------- Act --------
        int result = calculator.multiply(a, b);

        // -------- Assert --------
        assertEquals("7 * 8 should equal 56", 56, result);
    }

    // =====================================================
    //  Test 4: AAA Pattern — Division
    // =====================================================

    public void testDivision_AAA() {
        // -------- Arrange --------
        int a = 22;
        int b = 7;

        // -------- Act --------
        double result = calculator.divide(a, b);

        // -------- Assert --------
        assertEquals("22 / 7 should be approximately 3.1428", 3.1428, result, 0.001);
    }

    // =====================================================
    //  Test 5: AAA Pattern — Division by Zero (exception)
    // =====================================================

    public void testDivisionByZero_AAA() {
        // -------- Arrange --------
        int a = 10;
        int b = 0;
        boolean exceptionThrown = false;
        String exceptionMessage = "";

        // -------- Act --------
        try {
            calculator.divide(a, b);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
            exceptionMessage = e.getMessage();
        }

        // -------- Assert --------
        assertTrue("Dividing by zero should throw ArithmeticException", exceptionThrown);
        assertEquals("Exception message should be 'Cannot divide by zero'",
                     "Cannot divide by zero", exceptionMessage);
    }

    // =====================================================
    //  Test 6: AAA Pattern — Fixture Verification
    //  (proves setUp() creates valid fixtures)
    // =====================================================

    public void testFixtureInitialization_AAA() {
        // -------- Arrange --------
        // (setUp already arranged the fixtures)

        // -------- Act --------
        int arrayLength = sampleArray.length;
        int stringLength = sampleString.length();

        // -------- Assert --------
        assertNotNull("Calculator fixture should not be null", calculator);
        assertNotNull("Sample array fixture should not be null", sampleArray);
        assertNotNull("Sample string fixture should not be null", sampleString);
        assertEquals("Sample array should have 5 elements", 5, arrayLength);
        assertEquals("Sample string should be 'JUnit Testing'", "JUnit Testing", sampleString);
        assertEquals("Sample string length should be 13", 13, stringLength);
    }

    // =====================================================
    //  Test 7: AAA Pattern — Chained Operations
    // =====================================================

    public void testChainedOperations_AAA() {
        // -------- Arrange --------
        int a = 10;
        int b = 5;
        int c = 3;

        // -------- Act --------
        int addResult  = calculator.add(a, b);        // 10 + 5 = 15
        int mulResult  = calculator.multiply(addResult, c); // 15 * 3 = 45
        int finalResult = calculator.subtract(mulResult, a); // 45 - 10 = 35

        // -------- Assert --------
        assertEquals("(10 + 5) * 3 - 10 should equal 35", 35, finalResult);
    }

    // =====================================================
    //  Test 8: AAA Pattern — Negative Numbers
    // =====================================================

    public void testNegativeNumbers_AAA() {
        // -------- Arrange --------
        int a = -15;
        int b = -25;

        // -------- Act --------
        int sum  = calculator.add(a, b);
        int diff = calculator.subtract(a, b);

        // -------- Assert --------
        assertEquals("-15 + (-25) should equal -40", -40, sum);
        assertEquals("-15 - (-25) should equal 10",  10, diff);
    }

    // =====================================================
    //  Test 9: AAA Pattern — Multiply by Zero
    // =====================================================

    public void testMultiplyByZero_AAA() {
        // -------- Arrange --------
        int a = 12345;
        int b = 0;

        // -------- Act --------
        int result = calculator.multiply(a, b);

        // -------- Assert --------
        assertEquals("Any number multiplied by 0 should equal 0", 0, result);
    }

    // =====================================================
    //  Test 10: AAA Pattern — Teardown Verification
    //  (proves tearDown() nullifies fixtures)
    // =====================================================

    public void testTeardownCleansUp() {
        // -------- Arrange --------
        // Manually trigger teardown to verify cleanup
        tearDown();

        // -------- Act --------
        // (tearDown already acted)

        // -------- Assert --------
        assertNull("Calculator should be null after tearDown", calculator);
        assertNull("Sample array should be null after tearDown", sampleArray);
        assertNull("Sample string should be null after tearDown", sampleString);

        // Re-initialize for the normal tearDown call in runTest
        setUp();
    }

    // =====================================================
    //  Test Runner with Setup / Teardown Lifecycle
    // =====================================================

    /**
     * Executes a single test method wrapped by setUp() and tearDown()
     * calls — exactly like the JUnit lifecycle:
     *
     *   1. setUp()     — @Before
     *   2. test()      — @Test
     *   3. tearDown()  — @After
     */
    private void runTest(String testName, Runnable test) {
        System.out.println("Running: " + testName);
        try {
            setUp();            // @Before
            test.run();         // @Test
            tearDown();         // @After
        } catch (Exception e) {
            System.out.println("    ERROR: " + e.getMessage());
            failed++;
            try { tearDown(); } catch (Exception ignored) {}
        }
        System.out.println();
    }

    // =====================================================
    //  Main — Runs All Tests
    // =====================================================

    public static void main(String[] args) {
        AAAPatternTest tester = new AAAPatternTest();

        System.out.println("==============================================================");
        System.out.println("   Exercise 4: AAA Pattern, Test Fixtures, Setup & Teardown");
        System.out.println("==============================================================\n");

        tester.runTest("testAddition_AAA",            tester::testAddition_AAA);
        tester.runTest("testSubtraction_AAA",         tester::testSubtraction_AAA);
        tester.runTest("testMultiplication_AAA",       tester::testMultiplication_AAA);
        tester.runTest("testDivision_AAA",            tester::testDivision_AAA);
        tester.runTest("testDivisionByZero_AAA",      tester::testDivisionByZero_AAA);
        tester.runTest("testFixtureInitialization_AAA", tester::testFixtureInitialization_AAA);
        tester.runTest("testChainedOperations_AAA",   tester::testChainedOperations_AAA);
        tester.runTest("testNegativeNumbers_AAA",     tester::testNegativeNumbers_AAA);
        tester.runTest("testMultiplyByZero_AAA",      tester::testMultiplyByZero_AAA);
        tester.runTest("testTeardownCleansUp",        tester::testTeardownCleansUp);

        // --- Summary ---
        System.out.println("==============================================================");
        System.out.println("   TEST RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("   Total Assertions: " + (passed + failed));
        System.out.println("==============================================================");
    }
}
