public class Logger {

    // Sirf ek object store karne ke liye
    private static Logger instance;

    // Private constructor
    private Logger() {
        System.out.println("Logger instance create ho gaya");
    }

    // Singleton object return karega
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // Log message print karne ke liye
    public void log(String message) {
        System.out.println("LOG: " + message);
    }

    // Main method class ke andar hi honi chahiye
    public static void main(String[] args) {

        Logger logger1 = Logger.getInstance();
        logger1.log("This is the first log message.");

        Logger logger2 = Logger.getInstance();
        logger2.log("This is the second log message.");

        if (logger1 == logger2) {
            System.out.println("Only one Logger instance exists.");
        } else {
            System.out.println("Multiple Logger instances exist.");
        }
    }
}