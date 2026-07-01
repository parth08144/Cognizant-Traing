
public class FinancialForecast {

    
    public static double calculateFutureValue(double currentValue, double growthRate, int years) {

        // Base case
        if (years == 0) {
            return currentValue;
        }

        // Recursive case
        return calculateFutureValue(currentValue, growthRate, years - 1) * (1 + growthRate);
    }

    public static void main(String[] args) {

        
        double currentValue = 10000.0;

        
        double growthRate = 0.10;

        
        int years = 5;

        
        double futureValue = calculateFutureValue(currentValue, growthRate, years);

        
        System.out.println("Financial Forecasting");
        System.out.println("----------------------");
        System.out.println("Current Value : ₹" + currentValue);
        System.out.println("Growth Rate   : " + (growthRate * 100) + "%");
        System.out.println("Years         : " + years);
        System.out.printf("Future Value  : ₹%.2f%n", futureValue);
    }
}