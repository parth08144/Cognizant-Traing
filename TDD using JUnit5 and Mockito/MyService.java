/**
 * Exercise 1 (Mocking & Stubbing): MyService
 *
 * A service class that depends on ExternalApi.
 * This demonstrates the Dependency Injection pattern —
 * the ExternalApi is injected via the constructor, making
 * it easy to substitute a mock during testing.
 */
public class MyService {

    private final ExternalApi externalApi;

    /**
     * Constructor with Dependency Injection.
     * Accepts an ExternalApi instance, which can be a real
     * implementation in production or a mock in tests.
     *
     * @param externalApi the external API dependency
     */
    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    /**
     * Fetches data from the external API.
     * @return the data received from the API
     */
    public String fetchData() {
        return externalApi.getData();
    }

    /**
     * Fetches user data by ID from the external API.
     * Adds a prefix to indicate it was processed by MyService.
     *
     * @param userId the user ID
     * @return processed user data
     */
    public String fetchUserData(int userId) {
        String rawData = externalApi.getUserData(userId);
        return "Processed: " + rawData;
    }

    /**
     * Checks the health of the external API.
     * @return "Service is UP" or "Service is DOWN"
     */
    public String checkHealth() {
        if (externalApi.isServiceAvailable()) {
            return "Service is UP";
        }
        return "Service is DOWN";
    }

    /**
     * Sends data through the external API.
     * @param data the data to send
     * @return true if successful, false otherwise
     */
    public boolean submitData(String data) {
        if (data == null || data.isEmpty()) {
            return false;
        }
        return externalApi.sendData(data);
    }
}
