/**
 * Exercise 1: Logging Error Messages and Warning Levels
 *
 * Demonstrates logging error messages and warning levels using SLF4J.
 *
 * In a real project with Maven/Gradle, you would:
 *   1. Add SLF4J + Logback dependencies to pom.xml
 *   2. Use: import org.slf4j.Logger;
 *           import org.slf4j.LoggerFactory;
 *           Logger logger = LoggerFactory.getLogger(MyClass.class);
 *           logger.error("error message");
 *           logger.warn("warning message");
 *
 * Since Maven/SLF4J/Logback JARs are not available, this uses a custom
 * lightweight logging framework that mimics SLF4J's API and Logback's
 * output format, demonstrating all 5 standard log levels:
 *
 *   TRACE < DEBUG < INFO < WARN < ERROR
 *
 * Key Concepts:
 *   - Log levels control which messages are displayed
 *   - Lower levels (TRACE, DEBUG) are filtered out in production
 *   - Higher levels (WARN, ERROR) always appear
 *   - Parameterized messages avoid unnecessary string concatenation
 */
public class LoggingExample {

    // =====================================================
    //  Custom Logger (simulates SLF4J Logger interface)
    // =====================================================

    /**
     * Simulates org.slf4j.Logger with 5 standard log levels.
     * Mimics Logback's default console output format:
     *   [timestamp] [level] [class] - message
     */
    static class Logger {

        /** Log levels in order of severity (like SLF4J/Logback) */
        enum Level {
            TRACE(0), DEBUG(1), INFO(2), WARN(3), ERROR(4);

            final int value;
            Level(int value) { this.value = value; }
        }

        private final String className;
        private Level currentLevel;
        private final java.util.List<String> logHistory = new java.util.ArrayList<>();

        public Logger(String className) {
            this.className = className;
            this.currentLevel = Level.DEBUG;  // Default level (like Logback)
        }

        /** Sets the minimum log level (messages below this are filtered) */
        public void setLevel(Level level) {
            this.currentLevel = level;
        }

        public Level getLevel() {
            return currentLevel;
        }

        // --- Core logging method ---
        private void log(Level level, String message) {
            if (level.value >= currentLevel.value) {
                String timestamp = java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                String color = getColorCode(level);
                String reset = "\u001B[0m";
                String formatted = String.format("%s [%-5s] [%s] - %s%s",
                        timestamp, color + level.name() + reset, className, message, "");
                System.out.println(formatted);
                logHistory.add("[" + level.name() + "] " + message);
            }
        }

        /** Parameterized logging (like SLF4J's {} placeholders) */
        private void log(Level level, String format, Object... args) {
            if (level.value >= currentLevel.value) {
                String message = format;
                for (Object arg : args) {
                    message = message.replaceFirst("\\{\\}", String.valueOf(arg));
                }
                log(level, message);
            }
        }

        // --- ANSI color codes for log levels ---
        private String getColorCode(Level level) {
            switch (level) {
                case ERROR: return "\u001B[31m"; // Red
                case WARN:  return "\u001B[33m"; // Yellow
                case INFO:  return "\u001B[32m"; // Green
                case DEBUG: return "\u001B[36m"; // Cyan
                case TRACE: return "\u001B[37m"; // White/Gray
                default:    return "";
            }
        }

        // --- SLF4J-style logging methods ---

        /** Logs at ERROR level (highest severity) */
        public void error(String message)                       { log(Level.ERROR, message); }
        public void error(String format, Object... args)        { log(Level.ERROR, format, args); }

        /** Logs at WARN level */
        public void warn(String message)                        { log(Level.WARN, message); }
        public void warn(String format, Object... args)         { log(Level.WARN, format, args); }

        /** Logs at INFO level */
        public void info(String message)                        { log(Level.INFO, message); }
        public void info(String format, Object... args)         { log(Level.INFO, format, args); }

        /** Logs at DEBUG level */
        public void debug(String message)                       { log(Level.DEBUG, message); }
        public void debug(String format, Object... args)        { log(Level.DEBUG, format, args); }

        /** Logs at TRACE level (lowest severity) */
        public void trace(String message)                       { log(Level.TRACE, message); }
        public void trace(String format, Object... args)        { log(Level.TRACE, format, args); }

        // --- Level check methods (avoids expensive string building) ---
        public boolean isErrorEnabled() { return currentLevel.value <= Level.ERROR.value; }
        public boolean isWarnEnabled()  { return currentLevel.value <= Level.WARN.value; }
        public boolean isInfoEnabled()  { return currentLevel.value <= Level.INFO.value; }
        public boolean isDebugEnabled() { return currentLevel.value <= Level.DEBUG.value; }
        public boolean isTraceEnabled() { return currentLevel.value <= Level.TRACE.value; }

        /** Returns all logged messages (for testing/verification) */
        public java.util.List<String> getLogHistory() { return logHistory; }

        /** Clears log history */
        public void clearHistory() { logHistory.clear(); }
    }

    // =====================================================
    //  LoggerFactory (simulates org.slf4j.LoggerFactory)
    // =====================================================

    static class LoggerFactory {
        /**
         * Simulates: LoggerFactory.getLogger(MyClass.class)
         * In real SLF4J, this discovers the logging implementation
         * (Logback, Log4j, etc.) at runtime via SPI.
         */
        public static Logger getLogger(Class<?> clazz) {
            return new Logger(clazz.getSimpleName());
        }

        public static Logger getLogger(String name) {
            return new Logger(name);
        }
    }

    // =====================================================
    //  The actual LoggingExample (as per exercise)
    // =====================================================

    /**
     * In a real project, this would be:
     *   private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);
     */
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    // =====================================================
    //  Custom Assert for verification
    // =====================================================

    private static int passed = 0;
    private static int failed = 0;

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

    // =====================================================
    //  Main — Demonstrates all logging features
    // =====================================================

    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("   Exercise 1: Logging Error Messages and Warning Levels");
        System.out.println("==============================================================\n");

        // ---------------------------------------------------------
        // PART 1: Basic error and warning messages (exercise requirement)
        // ---------------------------------------------------------
        System.out.println("--- Part 1: Error and Warning Messages (Exercise Code) ---\n");

        logger.error("This is an error message");
        logger.warn("This is a warning message");

        // ---------------------------------------------------------
        // PART 2: All 5 log levels demonstration
        // ---------------------------------------------------------
        System.out.println("\n--- Part 2: All 5 Log Levels (TRACE → ERROR) ---\n");

        logger.setLevel(Logger.Level.TRACE);  // Enable all levels
        logger.trace("This is a TRACE message — most detailed, for fine-grained debugging");
        logger.debug("This is a DEBUG message — useful during development");
        logger.info("This is an INFO message — general application events");
        logger.warn("This is a WARN message — potential issues to watch");
        logger.error("This is an ERROR message — something went wrong!");

        // ---------------------------------------------------------
        // PART 3: Parameterized logging (SLF4J's {} placeholders)
        // ---------------------------------------------------------
        System.out.println("\n--- Part 3: Parameterized Logging (SLF4J {} style) ---\n");

        String userName = "Parth";
        int userId = 1001;
        logger.info("User {} logged in with ID {}", userName, userId);
        logger.warn("User {} has {} failed login attempts", userName, 3);
        logger.error("Database connection failed for user {} at port {}", userName, 5432);

        // ---------------------------------------------------------
        // PART 4: Log level filtering
        // ---------------------------------------------------------
        System.out.println("\n--- Part 4: Log Level Filtering ---\n");

        System.out.println("  Setting log level to WARN (only WARN and ERROR will appear):\n");
        logger.setLevel(Logger.Level.WARN);

        logger.trace("TRACE — This should NOT appear");
        logger.debug("DEBUG — This should NOT appear");
        logger.info("INFO  — This should NOT appear");
        logger.warn("WARN  — This SHOULD appear");
        logger.error("ERROR — This SHOULD appear");

        // ---------------------------------------------------------
        // PART 5: Level check methods (avoid expensive string ops)
        // ---------------------------------------------------------
        System.out.println("\n--- Part 5: isXxxEnabled() Methods ---\n");

        logger.setLevel(Logger.Level.INFO);
        System.out.println("  Current level: INFO");
        System.out.println("  isTraceEnabled() = " + logger.isTraceEnabled());
        System.out.println("  isDebugEnabled() = " + logger.isDebugEnabled());
        System.out.println("  isInfoEnabled()  = " + logger.isInfoEnabled());
        System.out.println("  isWarnEnabled()   = " + logger.isWarnEnabled());
        System.out.println("  isErrorEnabled() = " + logger.isErrorEnabled());

        // Best practice: guard expensive logging
        if (logger.isDebugEnabled()) {
            logger.debug("This won't print because DEBUG < INFO");
        }

        // ---------------------------------------------------------
        // PART 6: Logging in a real-world scenario
        // ---------------------------------------------------------
        System.out.println("\n--- Part 6: Real-World Logging Scenario ---\n");

        logger.setLevel(Logger.Level.DEBUG);
        simulateOrderProcessing(logger);

        // ---------------------------------------------------------
        // PART 7: Verification Tests
        // ---------------------------------------------------------
        System.out.println("\n--- Part 7: Verification Tests ---\n");
        runVerificationTests();

        // --- Summary ---
        System.out.println("\n==============================================================");
        System.out.println("   TEST RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("   Total Assertions: " + (passed + failed));
        System.out.println("==============================================================");
    }

    // =====================================================
    //  Real-world scenario: Order processing with logging
    // =====================================================

    private static void simulateOrderProcessing(Logger log) {
        log.info("=== Starting Order Processing ===");

        // Simulate processing steps
        String orderId = "ORD-2026-001";
        log.debug("Processing order: {}", orderId);

        // Step 1: Validate
        log.info("Validating order {}", orderId);

        // Step 2: Check inventory
        log.debug("Checking inventory for order {}", orderId);
        int stock = 5;
        if (stock < 10) {
            log.warn("Low stock alert! Only {} items remaining for order {}", stock, orderId);
        }

        // Step 3: Process payment
        log.info("Processing payment for order {}", orderId);
        boolean paymentSuccess = true;
        if (!paymentSuccess) {
            log.error("Payment failed for order {}", orderId);
        }

        // Step 4: Complete
        log.info("Order {} processed successfully", orderId);
        log.info("=== Order Processing Complete ===");
    }

    // =====================================================
    //  Verification Tests
    // =====================================================

    private static void runVerificationTests() {

        // Test 1: Logger creation
        System.out.println("Running: testLoggerCreation");
        Logger testLogger = LoggerFactory.getLogger(LoggingExample.class);
        assertTrue("Logger should not be null", testLogger != null);
        assertTrue("isErrorEnabled() should be true by default", testLogger.isErrorEnabled());
        assertTrue("isWarnEnabled() should be true by default", testLogger.isWarnEnabled());
        System.out.println();

        // Test 2: Log level filtering works correctly
        System.out.println("Running: testLogLevelFiltering");
        Logger filterLogger = LoggerFactory.getLogger("FilterTest");
        filterLogger.setLevel(Logger.Level.WARN);
        filterLogger.clearHistory();

        filterLogger.trace("trace msg");
        filterLogger.debug("debug msg");
        filterLogger.info("info msg");
        filterLogger.warn("warn msg");
        filterLogger.error("error msg");

        assertEquals("Only WARN and ERROR should be logged (2 entries)",
                     2, filterLogger.getLogHistory().size());
        assertTrue("First logged message should be WARN",
                   filterLogger.getLogHistory().get(0).contains("[WARN]"));
        assertTrue("Second logged message should be ERROR",
                   filterLogger.getLogHistory().get(1).contains("[ERROR]"));
        System.out.println();

        // Test 3: ERROR level logs everything at ERROR and above
        System.out.println("Running: testErrorLevelOnly");
        Logger errorLogger = LoggerFactory.getLogger("ErrorTest");
        errorLogger.setLevel(Logger.Level.ERROR);
        errorLogger.clearHistory();

        errorLogger.warn("should be filtered");
        errorLogger.error("should appear");

        assertEquals("Only ERROR should be logged (1 entry)",
                     1, errorLogger.getLogHistory().size());
        assertTrue("Logged message should be ERROR",
                   errorLogger.getLogHistory().get(0).contains("[ERROR]"));
        System.out.println();

        // Test 4: TRACE level logs everything
        System.out.println("Running: testTraceLevelLogsAll");
        Logger traceLogger = LoggerFactory.getLogger("TraceTest");
        traceLogger.setLevel(Logger.Level.TRACE);
        traceLogger.clearHistory();

        traceLogger.trace("t");
        traceLogger.debug("d");
        traceLogger.info("i");
        traceLogger.warn("w");
        traceLogger.error("e");

        assertEquals("All 5 levels should be logged",
                     5, traceLogger.getLogHistory().size());
        System.out.println();

        // Test 5: Parameterized messages resolve correctly
        System.out.println("Running: testParameterizedMessages");
        Logger paramLogger = LoggerFactory.getLogger("ParamTest");
        paramLogger.setLevel(Logger.Level.TRACE);
        paramLogger.clearHistory();

        paramLogger.info("Hello {}, your ID is {}", "Parth", 42);

        assertTrue("Parameterized message should resolve placeholders",
                   paramLogger.getLogHistory().get(0).contains("Hello Parth, your ID is 42"));
        System.out.println();

        // Test 6: isXxxEnabled() reflects current level
        System.out.println("Running: testIsEnabledMethods");
        Logger levelLogger = LoggerFactory.getLogger("LevelTest");

        levelLogger.setLevel(Logger.Level.INFO);
        assertFalse("isTraceEnabled() should be false at INFO level", levelLogger.isTraceEnabled());
        assertFalse("isDebugEnabled() should be false at INFO level", levelLogger.isDebugEnabled());
        assertTrue("isInfoEnabled() should be true at INFO level",   levelLogger.isInfoEnabled());
        assertTrue("isWarnEnabled() should be true at INFO level",    levelLogger.isWarnEnabled());
        assertTrue("isErrorEnabled() should be true at INFO level",  levelLogger.isErrorEnabled());
        System.out.println();
    }
}
