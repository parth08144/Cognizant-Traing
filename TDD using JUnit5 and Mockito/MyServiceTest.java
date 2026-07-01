/**
 * Exercise 1 (Mocking & Stubbing): Mocking and Stubbing with Mockito
 *
 * Demonstrates:
 *   1. Creating mock objects for external dependencies
 *   2. Stubbing methods to return predefined values
 *   3. Writing test cases using mock objects
 *   4. Verifying mock interactions (verify)
 *
 * In a real project with Maven/Gradle, you would use:
 *   import static org.mockito.Mockito.*;
 *   import org.junit.jupiter.api.Test;
 *   import org.mockito.Mockito;
 *   import static org.junit.jupiter.api.Assertions.*;
 *
 * Since Maven/JUnit/Mockito JARs are not available, this uses
 * a custom lightweight mock framework that simulates Mockito's
 * core behavior: mock(), when().thenReturn(), and verify().
 */
public class MyServiceTest {

    private static int passed = 0;
    private static int failed = 0;

    // =====================================================
    //  Lightweight Mock Framework (simulates Mockito)
    // =====================================================

    /**
     * A manual mock of ExternalApi that simulates Mockito behavior:
     *   - Stubbed return values for each method
     *   - Invocation tracking for verification
     */
    static class MockExternalApi extends ExternalApi {

        // Stubbed return values (set via when().thenReturn() style)
        private String stubbedGetData = null;
        private String stubbedGetUserData = null;
        private Boolean stubbedIsServiceAvailable = null;
        private Boolean stubbedSendData = null;

        // Invocation counters (for verify())
        private int getDataCallCount = 0;
        private int getUserDataCallCount = 0;
        private int isServiceAvailableCallCount = 0;
        private int sendDataCallCount = 0;
        private int lastUserIdArg = -1;
        private String lastSendDataArg = null;

        // --- Stubbing methods (simulates when().thenReturn()) ---

        public MockExternalApi stubGetData(String value) {
            this.stubbedGetData = value;
            return this;
        }

        public MockExternalApi stubGetUserData(String value) {
            this.stubbedGetUserData = value;
            return this;
        }

        public MockExternalApi stubIsServiceAvailable(boolean value) {
            this.stubbedIsServiceAvailable = value;
            return this;
        }

        public MockExternalApi stubSendData(boolean value) {
            this.stubbedSendData = value;
            return this;
        }

        // --- Overridden methods (return stubbed values) ---

        @Override
        public String getData() {
            getDataCallCount++;
            return stubbedGetData != null ? stubbedGetData : "default mock data";
        }

        @Override
        public String getUserData(int userId) {
            getUserDataCallCount++;
            lastUserIdArg = userId;
            return stubbedGetUserData != null ? stubbedGetUserData : "default mock user data";
        }

        @Override
        public boolean isServiceAvailable() {
            isServiceAvailableCallCount++;
            return stubbedIsServiceAvailable != null ? stubbedIsServiceAvailable : false;
        }

        @Override
        public boolean sendData(String data) {
            sendDataCallCount++;
            lastSendDataArg = data;
            return stubbedSendData != null ? stubbedSendData : false;
        }

        // --- Verification methods (simulates Mockito.verify()) ---

        public int getGetDataCallCount()             { return getDataCallCount; }
        public int getGetUserDataCallCount()         { return getUserDataCallCount; }
        public int getIsServiceAvailableCallCount()  { return isServiceAvailableCallCount; }
        public int getSendDataCallCount()            { return sendDataCallCount; }
        public int getLastUserIdArg()                { return lastUserIdArg; }
        public String getLastSendDataArg()           { return lastSendDataArg; }
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

    private MockExternalApi mockApi;
    private MyService service;

    public void setUp() {
        System.out.println("  [@Before] setUp() — creating mock and service");
        mockApi = new MockExternalApi();   // Step 1: Create a mock object
        service = new MyService(mockApi);  // Inject mock into service
    }

    public void tearDown() {
        System.out.println("  [@After]  tearDown() — cleaning up");
        mockApi = null;
        service = null;
    }

    // =====================================================
    //  Test 1: Basic Mocking & Stubbing — getData()
    //  (Matches the exercise's solution code)
    // =====================================================

    /**
     * This test directly corresponds to the exercise requirement:
     *
     *   ExternalApi mockApi = Mockito.mock(ExternalApi.class);
     *   when(mockApi.getData()).thenReturn("Mock Data");
     *   MyService service = new MyService(mockApi);
     *   String result = service.fetchData();
     *   assertEquals("Mock Data", result);
     */
    public void testExternalApi() {
        // Arrange — mock is created in setUp(); now stub it
        // Equivalent to: when(mockApi.getData()).thenReturn("Mock Data");
        mockApi.stubGetData("Mock Data");

        // Act
        String result = service.fetchData();

        // Assert
        assertEquals("fetchData() should return stubbed 'Mock Data'", "Mock Data", result);
    }

    // =====================================================
    //  Test 2: Stub getUserData() with predefined value
    // =====================================================

    public void testGetUserDataWithStub() {
        // Arrange — stub getUserData to return a predefined value
        // Equivalent to: when(mockApi.getUserData(42)).thenReturn("John Doe");
        mockApi.stubGetUserData("John Doe");

        // Act
        String result = service.fetchUserData(42);

        // Assert — MyService prefixes "Processed: " to the raw data
        assertEquals("fetchUserData(42) should return 'Processed: John Doe'",
                     "Processed: John Doe", result);
    }

    // =====================================================
    //  Test 3: Stub isServiceAvailable() — Service UP
    // =====================================================

    public void testCheckHealth_ServiceUp() {
        // Arrange
        // Equivalent to: when(mockApi.isServiceAvailable()).thenReturn(true);
        mockApi.stubIsServiceAvailable(true);

        // Act
        String health = service.checkHealth();

        // Assert
        assertEquals("checkHealth() should return 'Service is UP'",
                     "Service is UP", health);
    }

    // =====================================================
    //  Test 4: Stub isServiceAvailable() — Service DOWN
    // =====================================================

    public void testCheckHealth_ServiceDown() {
        // Arrange
        // Equivalent to: when(mockApi.isServiceAvailable()).thenReturn(false);
        mockApi.stubIsServiceAvailable(false);

        // Act
        String health = service.checkHealth();

        // Assert
        assertEquals("checkHealth() should return 'Service is DOWN'",
                     "Service is DOWN", health);
    }

    // =====================================================
    //  Test 5: Stub sendData() — successful send
    // =====================================================

    public void testSubmitData_Success() {
        // Arrange
        // Equivalent to: when(mockApi.sendData(anyString())).thenReturn(true);
        mockApi.stubSendData(true);

        // Act
        boolean result = service.submitData("Hello API");

        // Assert
        assertTrue("submitData('Hello API') should return true", result);
    }

    // =====================================================
    //  Test 6: Stub sendData() — failed send
    // =====================================================

    public void testSubmitData_Failure() {
        // Arrange
        // Equivalent to: when(mockApi.sendData(anyString())).thenReturn(false);
        mockApi.stubSendData(false);

        // Act
        boolean result = service.submitData("Hello API");

        // Assert
        assertFalse("submitData('Hello API') should return false when API fails", result);
    }

    // =====================================================
    //  Test 7: Null/Empty input — no API call needed
    // =====================================================

    public void testSubmitData_NullInput() {
        // Arrange — no stubbing needed, service rejects null before calling API

        // Act
        boolean result = service.submitData(null);

        // Assert
        assertFalse("submitData(null) should return false", result);
        assertEquals("sendData() should NOT be called for null input",
                     0, mockApi.getSendDataCallCount());
    }

    public void testSubmitData_EmptyInput() {
        // Arrange — no stubbing needed

        // Act
        boolean result = service.submitData("");

        // Assert
        assertFalse("submitData('') should return false", result);
        assertEquals("sendData() should NOT be called for empty input",
                     0, mockApi.getSendDataCallCount());
    }

    // =====================================================
    //  Test 8: Verify mock interactions
    //  (Simulates Mockito.verify())
    // =====================================================

    public void testVerify_GetDataCalledOnce() {
        // Arrange
        mockApi.stubGetData("Verified Data");

        // Act
        service.fetchData();

        // Assert — verify the mock was called exactly once
        // Equivalent to: verify(mockApi, times(1)).getData();
        assertEquals("getData() should be called exactly 1 time",
                     1, mockApi.getGetDataCallCount());
    }

    public void testVerify_GetUserDataCalledWithCorrectArg() {
        // Arrange
        mockApi.stubGetUserData("User Info");

        // Act
        service.fetchUserData(99);

        // Assert — verify called once with correct argument
        // Equivalent to: verify(mockApi).getUserData(99);
        assertEquals("getUserData() should be called exactly 1 time",
                     1, mockApi.getGetUserDataCallCount());
        assertEquals("getUserData() should be called with userId = 99",
                     99, mockApi.getLastUserIdArg());
    }

    public void testVerify_SendDataCalledWithCorrectArg() {
        // Arrange
        mockApi.stubSendData(true);

        // Act
        service.submitData("Important Payload");

        // Assert — verify the argument passed to the mock
        // Equivalent to: verify(mockApi).sendData("Important Payload");
        assertEquals("sendData() should be called exactly 1 time",
                     1, mockApi.getSendDataCallCount());
        assertEquals("sendData() should receive 'Important Payload'",
                     "Important Payload", mockApi.getLastSendDataArg());
    }

    // =====================================================
    //  Test 9: Default mock behavior (unstubbed methods)
    // =====================================================

    public void testDefaultMockBehavior() {
        // Arrange — do NOT stub anything; use default mock values

        // Act
        String data = service.fetchData();
        String health = service.checkHealth();

        // Assert — unstubbed mock returns default values
        assertEquals("Unstubbed getData() should return default 'default mock data'",
                     "default mock data", data);
        assertEquals("Unstubbed isServiceAvailable() returns false → 'Service is DOWN'",
                     "Service is DOWN", health);
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
        MyServiceTest tester = new MyServiceTest();

        System.out.println("==============================================================");
        System.out.println("   Exercise 1: Mocking and Stubbing with Mockito");
        System.out.println("==============================================================\n");

        System.out.println("--- Basic Mocking & Stubbing ---");
        tester.runTest("testExternalApi",               tester::testExternalApi);
        tester.runTest("testGetUserDataWithStub",       tester::testGetUserDataWithStub);

        System.out.println("--- Stubbing Boolean Returns ---");
        tester.runTest("testCheckHealth_ServiceUp",     tester::testCheckHealth_ServiceUp);
        tester.runTest("testCheckHealth_ServiceDown",   tester::testCheckHealth_ServiceDown);
        tester.runTest("testSubmitData_Success",        tester::testSubmitData_Success);
        tester.runTest("testSubmitData_Failure",        tester::testSubmitData_Failure);

        System.out.println("--- Input Validation (No API Call) ---");
        tester.runTest("testSubmitData_NullInput",      tester::testSubmitData_NullInput);
        tester.runTest("testSubmitData_EmptyInput",     tester::testSubmitData_EmptyInput);

        System.out.println("--- Verify Mock Interactions ---");
        tester.runTest("testVerify_GetDataCalledOnce",           tester::testVerify_GetDataCalledOnce);
        tester.runTest("testVerify_GetUserDataCalledWithCorrectArg", tester::testVerify_GetUserDataCalledWithCorrectArg);
        tester.runTest("testVerify_SendDataCalledWithCorrectArg",    tester::testVerify_SendDataCalledWithCorrectArg);

        System.out.println("--- Default Mock Behavior ---");
        tester.runTest("testDefaultMockBehavior",       tester::testDefaultMockBehavior);

        // --- Summary ---
        System.out.println("==============================================================");
        System.out.println("   TEST RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("   Total Assertions: " + (passed + failed));
        System.out.println("==============================================================");
    }
}
