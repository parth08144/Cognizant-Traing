public class Logger {

    // Logger class ka sirf ek object store karne ke liye
    private static Logger instance;

    // Private constructor taki bahar se object na ban sake
    private Logger() {
        System.out.println("Logger instance create ho gaya");
    }

    // Single object return karne wali method
    public static Logger getInstance() {

        // Agar object pehle se nahi bana hai to naya banao
        if (instance == null) {
            instance = new Logger();
        }

        // Wahi same object return karo
        return instance;
    }

    // Log message print karne ki method
    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}