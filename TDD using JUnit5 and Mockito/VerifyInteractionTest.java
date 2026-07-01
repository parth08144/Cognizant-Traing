/**
 * Exercise 2: Verifying Interactions
 *
 * Demonstrates:
 *   1. Creating mock objects
 *   2. Calling methods with specific arguments
 *   3. Verifying interactions using Mockito-style verification:
 *      - verify(mock).method()            — called at least once
 *      - verify(mock, times(n)).method()   — called exactly n times
 *      - verify(mock, never()).method()    — never called
 *      - verifyNoMoreInteractions(mock)    — no unexpected calls
 *      - Argument verification             — correct args passed
 *
 * In a real project with Maven/Gradle, you would use:
 *   import static org.mockito.Mockito.*;
 *   import org.junit.jupiter.api.Test;
 *   import org.mockito.Mockito;
 *
 * Since Maven/Mockito JARs are not available, this uses a custom
 * mock framework that tracks all method invocations for verification.
 */
public class VerifyInteractionTest {

    private static int passed = 0;
    private static int failed = 0;

    // =====================================================
    //  Verifiable Mock — tracks every method invocation
    // =====================================================

    static class VerifiableMockApi extends ExternalApi {

        // Invocation counters
        private int getDataCount = 0;
        private int getUserDataCount = 0;
        private int isServiceAvailableCount = 0;
        private int sendDataCount = 0;

        // Argument tracking
        private java.util.List<Integer> userIdArgs = new java.util.ArrayList<>();
        private java.util.List<String> sendDataArgs = new java.util.ArrayList<>();

        // Stubbed return values
        private String stubbedGetData = "mock data";
        private String stubbedGetUserData = "mock user data";
        private boolean stubbedIsAvailable = true;
        private boolean stubbedSendData = true;

        // --- Stubbing ---
        public VerifiableMockApi stubGetData(String val)          { stubbedGetData = val; return this; }
        public VerifiableMockApi stubGetUserData(String val)      { stubbedGetUserData = val; return this; }
        public VerifiableMockApi stubIsServiceAvailable(boolean v){ stubbedIsAvailable = v; return this; }
        public VerifiableMockApi stubSendData(boolean val)        { stubbedSendData = val; return this; }

        // --- Overridden methods (record invocations) ---

        @Override
        public String getData() {
            getDataCount++;
            return stubbedGetData;
        }

        @Override
        public String getUserData(int userId) {
            getUserDataCount++;
            userIdArgs.add(userId);
            return stubbedGetUserData;
        }

        @Override
        public boolean isServiceAvailable() {
            isServiceAvailableCount++;
            return stubbedIsAvailable;
        }

        @Override
        public boolean sendData(String data) {
            sendDataCount++;
            sendDataArgs.add(data);
            return stubbedSendData;
        }

        // =====================================================
        //  Verification API (simulates Mockito.verify())
        // =====================================================

        /** Equivalent to: verify(mock).getData() */
        public boolean verifyGetDataCalled() {
            return getDataCount > 0;
        }

        /** Equivalent to: verify(mock, times(n)).getData() */
        public boolean verifyGetDataCalledTimes(int n) {
            return getDataCount == n;
        }

        /** Equivalent to: verify(mock, never()).getData() */
        public boolean verifyGetDataNeverCalled() {
            return getDataCount == 0;
        }

        /** Equivalent to: verify(mock).getUserData(expectedId) */
        public boolean verifyGetUserDataCalledWith(int expectedId) {
            return userIdArgs.contains(expectedId);
        }

        /** Equivalent to: verify(mock, times(n)).getUserData(...) */
        public boolean verifyGetUserDataCalledTimes(int n) {
            return getUserDataCount == n;
        }

        /** Equivalent to: verify(mock).sendData(expectedData) */
        public boolean verifySendDataCalledWith(String expectedData) {
            return sendDataArgs.contains(expectedData);
        }

        /** Equivalent to: verify(mock, times(n)).sendData(...) */
        public boolean verifySendDataCalledTimes(int n) {
            return sendDataCount == n;
        }

        /** Equivalent to: verify(mock, never()).sendData(...) */
        public boolean verifySendDataNeverCalled() {
            return sendDataCount == 0;
        }

        /** Equivalent to: verify(mock).isServiceAvailable() */
        public boolean verifyIsServiceAvailableCalled() {
            return isServiceAvailableCount > 0;
        }

        public boolean verifyIsServiceAvailableCalledTimes(int n) {
            return isServiceAvailableCount == n;
        }

        /** Equivalent to: verifyNoMoreInteractions(mock) — total calls */
        public int getTotalInvocations() {
            return getDataCount + getUserDataCount + isServiceAvailableCount + sendDataCount;
        }

        // Getters for detailed assertions
        public int getGetDataCount()            { return getDataCount; }
        public int getGetUserDataCount()        { return getUserDataCount; }
        public int getSendDataCount()           { return sendDataCount; }
        public int getIsServiceAvailableCount() { return isServiceAvailableCount; }
        public java.util.List<Integer> getUserIdArgs()   { return userIdArgs; }
        public java.util.List<String> getSendDataArgs()  { return sendDataArgs; }
    }

    // =====================================================
    //  Custom Assert Methods
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

    // =====================================================
    //  Test Fixtures — Setup & Teardown
    // =====================================================

    private VerifiableMockApi mockApi;
    private MyService service;

    public void setUp() {
        System.out.println("  [@Before] setUp() — creating mock and service");
        mockApi = new VerifiableMockApi();
        service = new MyService(mockApi);
    }

    public void tearDown() {
        System.out.println("  [@After]  tearDown() — cleaning up");
        mockApi = null;
        service = null;
    }

    // =====================================================
    //  Test 1: Basic verify — getData() called once
    //  (Matches the exercise's solution code)
    // =====================================================

    /**
     * Directly corresponds to the exercise requirement:
     *
     *   ExternalApi mockApi = Mockito.mock(ExternalApi.class);
     *   MyService service = new MyService(mockApi);
     *   service.fetchData();
     *   verify(mockApi).getData();
     */
    public void testVerifyInteraction() {
        // Arrange — mock created in setUp()

        // Act
        service.fetchData();

        // Assert — verify(mockApi).getData();
        assertTrue("verify: getData() should be called",
                   mockApi.verifyGetDataCalled());
        assertTrue("verify: getData() called exactly 1 time",
                   mockApi.verifyGetDataCalledTimes(1));
    }

    // =====================================================
    //  Test 2: verify(times(n)) — method called N times
    // =====================================================

    public void testVerifyMultipleInvocations() {
        // Arrange — stub data
        mockApi.stubGetData("Data");

        // Act — call fetchData 3 times
        service.fetchData();
        service.fetchData();
        service.fetchData();

        // Assert — verify(mockApi, times(3)).getData();
        assertTrue("verify: getData() called exactly 3 times",
                   mockApi.verifyGetDataCalledTimes(3));
        assertEquals("getData() invocation count should be 3",
                     3, mockApi.getGetDataCount());
    }

    // =====================================================
    //  Test 3: verify(never()) — method NOT called
    // =====================================================

    public void testVerifyNeverCalled() {
        // Arrange — do nothing

        // Act — call checkHealth (only calls isServiceAvailable, NOT getData)
        service.checkHealth();

        // Assert — verify(mockApi, never()).getData();
        assertTrue("verify: getData() should NEVER be called by checkHealth()",
                   mockApi.verifyGetDataNeverCalled());
        assertTrue("verify: sendData() should NEVER be called by checkHealth()",
                   mockApi.verifySendDataNeverCalled());
    }

    // =====================================================
    //  Test 4: Verify with specific arguments
    // =====================================================

    public void testVerifyWithSpecificArguments() {
        // Arrange
        mockApi.stubGetUserData("User Info");

        // Act
        service.fetchUserData(42);

        // Assert — verify(mockApi).getUserData(42);
        assertTrue("verify: getUserData() was called",
                   mockApi.verifyGetUserDataCalledTimes(1));
        assertTrue("verify: getUserData() was called with argument 42",
                   mockApi.verifyGetUserDataCalledWith(42));
        assertFalse("verify: getUserData() was NOT called with argument 99",
                    mockApi.verifyGetUserDataCalledWith(99));
    }

    // =====================================================
    //  Test 5: Verify sendData() with correct argument
    // =====================================================

    public void testVerifySendDataArgument() {
        // Arrange
        mockApi.stubSendData(true);

        // Act
        service.submitData("Hello World");

        // Assert — verify(mockApi).sendData("Hello World");
        assertTrue("verify: sendData() was called exactly 1 time",
                   mockApi.verifySendDataCalledTimes(1));
        assertTrue("verify: sendData() received 'Hello World'",
                   mockApi.verifySendDataCalledWith("Hello World"));
    }

    // =====================================================
    //  Test 6: Verify no interaction when input is invalid
    // =====================================================

    public void testVerifyNoInteractionOnNullInput() {
        // Arrange — no stubbing needed

        // Act — submit null data (service should reject without calling API)
        service.submitData(null);

        // Assert — verify(mockApi, never()).sendData(any());
        assertTrue("verify: sendData() should NEVER be called for null input",
                   mockApi.verifySendDataNeverCalled());
        assertEquals("Total mock invocations should be 0",
                     0, mockApi.getTotalInvocations());
    }

    // =====================================================
    //  Test 7: Verify multiple methods called in sequence
    // =====================================================

    public void testVerifyMultipleMethodsCalled() {
        // Arrange
        mockApi.stubGetData("Data");
        mockApi.stubIsServiceAvailable(true);
        mockApi.stubSendData(true);

        // Act — call multiple service methods
        service.fetchData();          // calls getData()
        service.checkHealth();        // calls isServiceAvailable()
        service.submitData("Payload");// calls sendData()

        // Assert — verify each method was called once
        assertTrue("verify: getData() was called",
                   mockApi.verifyGetDataCalledTimes(1));
        assertTrue("verify: isServiceAvailable() was called",
                   mockApi.verifyIsServiceAvailableCalledTimes(1));
        assertTrue("verify: sendData() was called",
                   mockApi.verifySendDataCalledTimes(1));
        assertEquals("Total invocations should be 3",
                     3, mockApi.getTotalInvocations());
    }

    // =====================================================
    //  Test 8: Verify argument list for multiple calls
    // =====================================================

    public void testVerifyArgumentsAcrossMultipleCalls() {
        // Arrange
        mockApi.stubGetUserData("User");

        // Act — call fetchUserData with different IDs
        service.fetchUserData(1);
        service.fetchUserData(2);
        service.fetchUserData(3);

        // Assert — verify all arguments were recorded
        assertEquals("getUserData() should be called 3 times",
                     3, mockApi.getGetUserDataCount());
        assertTrue("verify: getUserData() was called with arg 1",
                   mockApi.verifyGetUserDataCalledWith(1));
        assertTrue("verify: getUserData() was called with arg 2",
                   mockApi.verifyGetUserDataCalledWith(2));
        assertTrue("verify: getUserData() was called with arg 3",
                   mockApi.verifyGetUserDataCalledWith(3));
        assertFalse("verify: getUserData() was NOT called with arg 999",
                    mockApi.verifyGetUserDataCalledWith(999));
    }

    // =====================================================
    //  Test 9: verifyNoMoreInteractions — only expected calls
    // =====================================================

    public void testVerifyNoMoreInteractions() {
        // Arrange
        mockApi.stubGetData("Data");

        // Act — only call fetchData()
        service.fetchData();

        // Assert — verifyNoMoreInteractions: only getData was called
        assertEquals("getData() called once", 1, mockApi.getGetDataCount());
        assertEquals("getUserData() never called", 0, mockApi.getGetUserDataCount());
        assertEquals("sendData() never called", 0, mockApi.getSendDataCount());
        assertEquals("isServiceAvailable() never called", 0, mockApi.getIsServiceAvailableCount());
        assertEquals("Total invocations should be exactly 1", 1, mockApi.getTotalInvocations());
    }

    // =====================================================
    //  Test 10: Verify sendData() arguments across calls
    // =====================================================

    public void testVerifySendDataMultipleArguments() {
        // Arrange
        mockApi.stubSendData(true);

        // Act — send multiple payloads
        service.submitData("First");
        service.submitData("Second");
        service.submitData("Third");

        // Assert
        assertEquals("sendData() should be called 3 times",
                     3, mockApi.getSendDataCount());
        assertTrue("verify: sendData() received 'First'",
                   mockApi.verifySendDataCalledWith("First"));
        assertTrue("verify: sendData() received 'Second'",
                   mockApi.verifySendDataCalledWith("Second"));
        assertTrue("verify: sendData() received 'Third'",
                   mockApi.verifySendDataCalledWith("Third"));
        assertFalse("verify: sendData() did NOT receive 'Fourth'",
                    mockApi.verifySendDataCalledWith("Fourth"));
    }

    // =====================================================
    //  Test Runner with Setup / Teardown Lifecycle
    // =====================================================

    private void runTest(String testName, Runnable test) {
        System.out.println("Running: " + testName);
        try {
            setUp();
            test.run();
            tearDown();
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
        VerifyInteractionTest tester = new VerifyInteractionTest();

        System.out.println("==============================================================");
        System.out.println("   Exercise 2: Verifying Interactions");
        System.out.println("==============================================================\n");

        System.out.println("--- Basic Verification ---");
        tester.runTest("testVerifyInteraction",            tester::testVerifyInteraction);
        tester.runTest("testVerifyMultipleInvocations",    tester::testVerifyMultipleInvocations);
        tester.runTest("testVerifyNeverCalled",            tester::testVerifyNeverCalled);

        System.out.println("--- Argument Verification ---");
        tester.runTest("testVerifyWithSpecificArguments",   tester::testVerifyWithSpecificArguments);
        tester.runTest("testVerifySendDataArgument",        tester::testVerifySendDataArgument);

        System.out.println("--- No Interaction Verification ---");
        tester.runTest("testVerifyNoInteractionOnNullInput", tester::testVerifyNoInteractionOnNullInput);
        tester.runTest("testVerifyNoMoreInteractions",       tester::testVerifyNoMoreInteractions);

        System.out.println("--- Multiple Calls & Arguments ---");
        tester.runTest("testVerifyMultipleMethodsCalled",        tester::testVerifyMultipleMethodsCalled);
        tester.runTest("testVerifyArgumentsAcrossMultipleCalls", tester::testVerifyArgumentsAcrossMultipleCalls);
        tester.runTest("testVerifySendDataMultipleArguments",    tester::testVerifySendDataMultipleArguments);

        // --- Summary ---
        System.out.println("==============================================================");
        System.out.println("   TEST RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("   Total Assertions: " + (passed + failed));
        System.out.println("==============================================================");
    }
}
