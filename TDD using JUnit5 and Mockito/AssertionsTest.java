a/**
 * Exercise 3: Assertions in JUnit
 *
 * Demonstrates different types of assertions used in JUnit testing.
 * Since Maven/JUnit JARs are not available, this uses a custom
 * assertion framework that mimics JUnit assertion methods:
 *
 *   assertEquals(expected, actual)  - checks equality
 *   assertTrue(condition)           - checks if true
 *   assertFalse(condition)          - checks if false
 *   assertNull(object)              - checks if null
 *   assertNotNull(object)           - checks if not null
 *   assertArrayEquals(arr1, arr2)   - checks array equality
 *   assertSame(obj1, obj2)          - checks reference equality
 *   assertNotSame(obj1, obj2)       - checks reference inequality
 */
public class AssertionsTest {

    private static int passed = 0;
    private static int failed = 0;

    // =====================================================
    // Custom Assert Methods (mimics JUnit Assert class)
    // =====================================================

    private static void assertEquals(String message, Object expected, Object actual) {
        if (expected.equals(actual)) {
            System.out.println("  PASS: " + message);
            passed++;
        } else {
            System.out.println("  FAIL: " + message + " | Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }

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
            System.out.println("  FAIL: " + message + " | Condition was false");
            failed++;
        }
    }

    private static void assertFalse(String message, boolean condition) {
        if (!condition) {
            System.out.println("  PASS: " + message);
            passed++;
        } else {
            System.out.println("  FAIL: " + message + " | Condition was true");
            failed++;
        }
    }

    private static void assertNull(String message, Object obj) {
        if (obj == null) {
            System.out.println("  PASS: " + message);
            passed++;
        } else {
            System.out.println("  FAIL: " + message + " | Object was not null: " + obj);
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

    private static void assertSame(String message, Object expected, Object actual) {
        if (expected == actual) {
            System.out.println("  PASS: " + message);
            passed++;
        } else {
            System.out.println("  FAIL: " + message + " | Objects are not the same reference");
            failed++;
        }
    }

    private static void assertNotSame(String message, Object expected, Object actual) {
        if (expected != actual) {
            System.out.println("  PASS: " + message);
            passed++;
        } else {
            System.out.println("  FAIL: " + message + " | Objects are the same reference");
            failed++;
        }
    }

    private static void assertArrayEquals(String message, int[] expected, int[] actual) {
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("  PASS: " + message);
            passed++;
        } else {
            System.out.println("  FAIL: " + message +
                " | Expected: " + java.util.Arrays.toString(expected) +
                ", Got: " + java.util.Arrays.toString(actual));
            failed++;
        }
    }

    // =====================================================
    // Test 1: assertEquals - Checks if two values are equal
    // =====================================================

    public void testAssertEquals() {
        // Integer equality
        assertEquals("2 + 3 should equal 5", 5, 2 + 3);

        // String equality
        assertEquals("String concatenation", "HelloWorld", "Hello" + "World");

        // Double equality with delta
        assertEquals("0.1 + 0.2 should be close to 0.3", 0.3, 0.1 + 0.2, 0.0001);
    }

    // =====================================================
    // Test 2: assertTrue - Checks if condition is true
    // =====================================================

    public void testAssertTrue() {
        assertTrue("5 is greater than 3", 5 > 3);
        assertTrue("String 'Hello' is not empty", !"Hello".isEmpty());
        assertTrue("10 is even", 10 % 2 == 0);
    }

    // =====================================================
    // Test 3: assertFalse - Checks if condition is false
    // =====================================================

    public void testAssertFalse() {
        assertFalse("5 is NOT less than 3", 5 < 3);
        assertFalse("Empty string is empty", "".length() > 0);
        assertFalse("7 is NOT even", 7 % 2 == 0);
    }

    // =====================================================
    // Test 4: assertNull - Checks if object is null
    // =====================================================

    public void testAssertNull() {
        Object obj = null;
        assertNull("null object should be null", null);
        assertNull("Variable set to null should be null", obj);
    }

    // =====================================================
    // Test 5: assertNotNull - Checks if object is not null
    // =====================================================

    public void testAssertNotNull() {
        assertNotNull("new Object() should not be null", new Object());
        assertNotNull("String literal should not be null", "Hello");
        assertNotNull("Integer array should not be null", new int[]{1, 2, 3});
    }

    // =====================================================
    // Test 6: assertSame - Checks reference equality
    // =====================================================

    public void testAssertSame() {
        String str1 = "Hello";
        String str2 = str1;  // same reference
        assertSame("str1 and str2 should point to same object", str1, str2);
    }

    // =====================================================
    // Test 7: assertNotSame - Checks reference inequality
    // =====================================================

    public void testAssertNotSame() {
        String str1 = new String("Hello");
        String str2 = new String("Hello");
        assertNotSame("Two new String objects should be different references", str1, str2);
    }

    // =====================================================
    // Test 8: assertArrayEquals - Checks array equality
    // =====================================================

    public void testAssertArrayEquals() {
        int[] expected = {1, 2, 3, 4, 5};
        int[] actual   = {1, 2, 3, 4, 5};
        assertArrayEquals("Arrays with same elements should be equal", expected, actual);
    }

    // =====================================================
    // Test Runner
    // =====================================================

    private void runTest(String testName, Runnable test) {
        System.out.println("Running: " + testName);
        try {
            test.run();
        } catch (Exception e) {
            System.out.println("  ERROR: " + e.getMessage());
            failed++;
        }
    }

    public static void main(String[] args) {
        AssertionsTest tester = new AssertionsTest();

        System.out.println("==============================================");
        System.out.println("   Exercise 3: JUnit Assertions Demo");
        System.out.println("==============================================\n");

        tester.runTest("testAssertEquals",      tester::testAssertEquals);
        System.out.println();
        tester.runTest("testAssertTrue",        tester::testAssertTrue);
        System.out.println();
        tester.runTest("testAssertFalse",       tester::testAssertFalse);
        System.out.println();
        tester.runTest("testAssertNull",        tester::testAssertNull);
        System.out.println();
        tester.runTest("testAssertNotNull",     tester::testAssertNotNull);
        System.out.println();
        tester.runTest("testAssertSame",        tester::testAssertSame);
        System.out.println();
        tester.runTest("testAssertNotSame",     tester::testAssertNotSame);
        System.out.println();
        tester.runTest("testAssertArrayEquals", tester::testAssertArrayEquals);

        // --- Summary ---
        System.out.println("\n==============================================");
        System.out.println("   TEST RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("   Total Assertions: " + (passed + failed));
        System.out.println("==============================================");
    }
}
