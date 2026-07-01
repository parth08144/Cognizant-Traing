/**
 * Exercise 1 (Mocking & Stubbing): External API
 *
 * Represents an external API dependency that MyService relies on.
 * In real-world scenarios, this could be a REST client, database
 * connector, or any third-party service.
 *
 * In production, this class would make actual network calls.
 * During testing, we mock this class to avoid real API calls
 * and to control the return values.
 */
public class ExternalApi {

    /**
     * Fetches data from the external API.
     * @return data string from the API
     */
    public String getData() {
        // In production, this would call a real external service
        return "Real API Data";
    }

    /**
     * Fetches data for a specific user by ID.
     * @param userId the user's ID
     * @return user data string
     */
    public String getUserData(int userId) {
        // In production, this would query a real database or API
        return "Real User Data for ID: " + userId;
    }

    /**
     * Checks if the external API service is available.
     * @return true if the service is up
     */
    public boolean isServiceAvailable() {
        // In production, this would perform a health check
        return true;
    }

    /**
     * Sends data to the external API.
     * @param data the data to send
     * @return true if data was sent successfully
     */
    public boolean sendData(String data) {
        // In production, this would POST data to the API
        System.out.println("Sending data to real API: " + data);
        return true;
    }
}
